package com.weightcount.app.ui.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.weightcount.app.WeightCountApp
import com.weightcount.app.data.entity.Tag
import com.weightcount.app.data.datastore.PeriodConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class SettingsUiState(
    val tags: List<Tag> = emptyList(),
    val periodConfigs: Map<String, PeriodConfig> = emptyMap(),
    val showAddTagDialog: Boolean = false,
    val showRenameTagDialog: Boolean = false,
    val showDeleteTagConfirm: Boolean = false,
    val editingTag: Tag? = null,
    val newTagName: String = "",
    val tagError: String? = null,
    val showEditPeriodDialog: Boolean = false,
    val editingPeriodKey: String? = null,
    val editingPeriodName: String = "",
    val editingPeriodValue: String = ""
)

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val app = application as WeightCountApp
    private val tagRepo = app.tagRepository
    private val settingsDataStore = app.settingsDataStore

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    val allTags = tagRepo.getAllTags()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch {
            tagRepo.getAllTags().collect { tags ->
                _uiState.value = _uiState.value.copy(tags = tags)
            }
        }
        viewModelScope.launch {
            settingsDataStore.allPeriodConfigs.collect { configs ->
                _uiState.value = _uiState.value.copy(periodConfigs = configs)
            }
        }
    }

    fun showAddTagDialog() {
        _uiState.value = _uiState.value.copy(
            showAddTagDialog = true,
            newTagName = "",
            tagError = null
        )
    }

    fun dismissAddTagDialog() {
        _uiState.value = _uiState.value.copy(showAddTagDialog = false)
    }

    fun setNewTagName(name: String) {
        _uiState.value = _uiState.value.copy(newTagName = name, tagError = null)
    }

    fun confirmAddTag() {
        val name = _uiState.value.newTagName.trim()
        if (name.isEmpty()) {
            _uiState.value = _uiState.value.copy(tagError = "请输入标签名称")
            return
        }
        if (_uiState.value.tags.any { it.name == name }) {
            _uiState.value = _uiState.value.copy(tagError = "标签已存在")
            return
        }
        viewModelScope.launch {
            try {
                tagRepo.addTag(name)
                _uiState.value = _uiState.value.copy(
                    showAddTagDialog = false,
                    newTagName = "",
                    tagError = null
                )
            } catch (_: Exception) {
                _uiState.value = _uiState.value.copy(tagError = "标签已存在")
            }
        }
    }

    fun showRenameTagDialog(tag: Tag) {
        _uiState.value = _uiState.value.copy(
            showRenameTagDialog = true,
            editingTag = tag,
            newTagName = tag.name,
            tagError = null
        )
    }

    fun dismissRenameTagDialog() {
        _uiState.value = _uiState.value.copy(showRenameTagDialog = false, editingTag = null)
    }

    fun confirmRenameTag() {
        val tag = _uiState.value.editingTag ?: return
        val name = _uiState.value.newTagName.trim()
        if (name.isEmpty()) {
            _uiState.value = _uiState.value.copy(tagError = "请输入标签名称")
            return
        }
        viewModelScope.launch {
            try {
                tagRepo.updateTag(tag.id, name)
                _uiState.value = _uiState.value.copy(
                    showRenameTagDialog = false,
                    editingTag = null,
                    newTagName = "",
                    tagError = null
                )
            } catch (_: Exception) {
                _uiState.value = _uiState.value.copy(tagError = "标签已存在")
            }
        }
    }

    fun showDeleteTagConfirm(tag: Tag) {
        _uiState.value = _uiState.value.copy(
            showDeleteTagConfirm = true,
            editingTag = tag
        )
    }

    fun dismissDeleteTagConfirm() {
        _uiState.value = _uiState.value.copy(showDeleteTagConfirm = false, editingTag = null)
    }

    fun confirmDeleteTag() {
        val tag = _uiState.value.editingTag ?: return
        viewModelScope.launch {
            tagRepo.deleteTag(tag.id)
            _uiState.value = _uiState.value.copy(
                showDeleteTagConfirm = false,
                editingTag = null
            )
        }
    }

    fun showEditPeriodDialog(key: String, config: PeriodConfig) {
        _uiState.value = _uiState.value.copy(
            showEditPeriodDialog = true,
            editingPeriodKey = key,
            editingPeriodName = config.label,
            editingPeriodValue = config.days.toString()
        )
    }

    fun setEditingPeriodName(value: String) {
        _uiState.value = _uiState.value.copy(editingPeriodName = value)
    }

    fun setEditingPeriodValue(value: String) {
        _uiState.value = _uiState.value.copy(editingPeriodValue = value)
    }

    fun dismissEditPeriodDialog() {
        _uiState.value = _uiState.value.copy(
            showEditPeriodDialog = false,
            editingPeriodKey = null
        )
    }

    fun confirmEditPeriod() {
        val key = _uiState.value.editingPeriodKey ?: return
        val name = _uiState.value.editingPeriodName.trim()
        val days = _uiState.value.editingPeriodValue.toIntOrNull() ?: return
        viewModelScope.launch {
            if (name.isNotEmpty()) {
                settingsDataStore.setChartPeriodLabel(key, name)
            }
            settingsDataStore.setChartPeriod(key, days)
            _uiState.value = _uiState.value.copy(
                showEditPeriodDialog = false,
                editingPeriodKey = null
            )
        }
    }
}
