package com.weightcount.app.ui.record

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.weightcount.app.WeightCountApp
import com.weightcount.app.data.entity.Tag
import com.weightcount.app.data.entity.WeightRecordWithTags
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar

data class RecordUiState(
    val records: List<WeightRecordWithTags> = emptyList(),
    val tags: List<Tag> = emptyList(),
    val selectedDate: Long = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.timeInMillis,
    val displayYear: Int = Calendar.getInstance().get(Calendar.YEAR),
    val displayMonth: Int = Calendar.getInstance().get(Calendar.MONTH),
    val weightInput: String = "",
    val selectedTagIds: Set<Long> = emptySet(),
    val selectedTimestamp: Long = System.currentTimeMillis(),
    val editRecord: WeightRecordWithTags? = null,
    val weightError: String? = null,
    val showAddDialog: Boolean = false,
    val showEditDialog: Boolean = false,
    val showDeleteConfirm: Boolean = false
)

class RecordViewModel(application: Application) : AndroidViewModel(application) {

    private val app = application as WeightCountApp
    private val weightRepo = app.weightRepository
    private val tagRepo = app.tagRepository

    private val _uiState = MutableStateFlow(RecordUiState())
    val uiState: StateFlow<RecordUiState> = _uiState.asStateFlow()

    val allTags = tagRepo.getAllTags()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch {
            combine(
                weightRepo.getAllRecordsWithTags(),
                tagRepo.getAllTags()
            ) { records, tags ->
                _uiState.value = _uiState.value.copy(records = records, tags = tags)
            }.collect {}
        }
    }

    fun setSelectedDate(millis: Long) {
        _uiState.value = _uiState.value.copy(selectedDate = millis)
    }

    fun navigateMonth(delta: Int) {
        val cal = Calendar.getInstance()
        cal.set(_uiState.value.displayYear, _uiState.value.displayMonth + delta, 1)
        _uiState.value = _uiState.value.copy(
            displayYear = cal.get(Calendar.YEAR),
            displayMonth = cal.get(Calendar.MONTH)
        )
    }

    fun navigateYear(delta: Int) {
        _uiState.value = _uiState.value.copy(
            displayYear = _uiState.value.displayYear + delta
        )
    }

    fun setWeightInput(value: String) {
        _uiState.value = _uiState.value.copy(weightInput = value, weightError = null)
    }

    fun setSelectedTimestamp(millis: Long) {
        _uiState.value = _uiState.value.copy(selectedTimestamp = millis)
    }

    fun toggleTag(tagId: Long) {
        val current = _uiState.value.selectedTagIds
        _uiState.value = _uiState.value.copy(
            selectedTagIds = if (tagId in current) current - tagId else current + tagId
        )
    }

    fun showAddDialog() {
        _uiState.value = _uiState.value.copy(
            showAddDialog = true,
            weightInput = "",
            selectedTagIds = emptySet(),
            selectedTimestamp = System.currentTimeMillis(),
            weightError = null
        )
    }

    fun dismissAddDialog() {
        _uiState.value = _uiState.value.copy(showAddDialog = false)
    }

    fun confirmAddRecord() {
        val state = _uiState.value
        val weightText = state.weightInput.trim()

        if (weightText.isEmpty()) {
            _uiState.value = state.copy(weightError = "请输入体重")
            return
        }

        val weight = weightText.toDoubleOrNull()
        if (weight == null || weight < 20.0 || weight > 500.0) {
            _uiState.value = state.copy(weightError = "请输入有效体重（20-500）")
            return
        }

        viewModelScope.launch {
            weightRepo.addRecord(
                weight = weight,
                timestamp = state.selectedTimestamp,
                tagIds = state.selectedTagIds.toList()
            )
            _uiState.value = _uiState.value.copy(
                showAddDialog = false,
                weightInput = "",
                selectedTagIds = emptySet(),
                weightError = null
            )
        }
    }

    fun showEditDialog(record: WeightRecordWithTags) {
        _uiState.value = _uiState.value.copy(
            showEditDialog = true,
            editRecord = record,
            weightInput = record.record.weight.toString(),
            selectedTimestamp = record.record.timestamp,
            selectedTagIds = record.tags.map { it.id }.toSet(),
            weightError = null
        )
    }

    fun dismissEditDialog() {
        _uiState.value = _uiState.value.copy(showEditDialog = false, editRecord = null)
    }

    fun confirmEditRecord() {
        val state = _uiState.value
        val record = state.editRecord ?: return
        val weightText = state.weightInput.trim()

        if (weightText.isEmpty()) {
            _uiState.value = state.copy(weightError = "请输入体重")
            return
        }

        val weight = weightText.toDoubleOrNull()
        if (weight == null || weight < 20.0 || weight > 500.0) {
            _uiState.value = state.copy(weightError = "请输入有效体重（20-500）")
            return
        }

        viewModelScope.launch {
            weightRepo.updateRecord(
                id = record.record.id,
                weight = weight,
                timestamp = state.selectedTimestamp,
                tagIds = state.selectedTagIds.toList()
            )
            _uiState.value = _uiState.value.copy(
                showEditDialog = false,
                editRecord = null,
                weightInput = "",
                selectedTagIds = emptySet(),
                weightError = null
            )
        }
    }

    fun showDeleteConfirm() {
        _uiState.value = _uiState.value.copy(showDeleteConfirm = true)
    }

    fun dismissDeleteConfirm() {
        _uiState.value = _uiState.value.copy(showDeleteConfirm = false)
    }

    fun confirmDelete() {
        val record = _uiState.value.editRecord ?: return
        viewModelScope.launch {
            weightRepo.deleteRecord(record.record.id)
            _uiState.value = _uiState.value.copy(
                showDeleteConfirm = false,
                showEditDialog = false,
                editRecord = null
            )
        }
    }
}
