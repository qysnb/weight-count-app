package com.weightcount.app.ui.report

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
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale

enum class ChartPeriod(val key: String, val defaultLabel: String) {
    WEEKLY("weekly", "7日"),
    MONTHLY("monthly", "30日"),
    QUARTERLY("quarterly", "90日"),
    SEMI_ANNUALLY("semiannually", "180日"),
    YEARLY("yearly", "年度")
}

data class ReportUiState(
    val selectedPeriod: ChartPeriod = ChartPeriod.MONTHLY,
    val records: List<WeightRecordWithTags> = emptyList(),
    val allRecords: List<WeightRecordWithTags> = emptyList(),
    val chartData: List<ChartDataPoint> = emptyList(),
    val useCustomRange: Boolean = false,
    val rangeStart: Long? = null,
    val rangeEnd: Long? = null,
    val periodLabels: Map<String, String> = emptyMap(),
    val periodDays: Map<String, Int> = emptyMap(),
    val allTags: List<Tag> = emptyList(),
    val selectedTagIds: Set<Long> = emptySet()
)

data class ChartDataPoint(
    val timestamp: Long,
    val weight: Double,
    val tags: List<Tag> = emptyList()
)

class ReportViewModel(application: Application) : AndroidViewModel(application) {

    private val app = application as WeightCountApp
    private val weightRepo = app.weightRepository
    private val tagRepo = app.tagRepository
    private val settingsDataStore = app.settingsDataStore

    private val _uiState = MutableStateFlow(ReportUiState())
    val uiState: StateFlow<ReportUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            weightRepo.getAllRecordsWithTags().collect { records ->
                val sorted = records.sortedBy { it.record.timestamp }
                _uiState.value = _uiState.value.copy(allRecords = sorted)
                updateChart()
            }
        }
        viewModelScope.launch {
            tagRepo.getAllTags().collect { tags ->
                _uiState.value = _uiState.value.copy(allTags = tags)
            }
        }
        viewModelScope.launch {
            settingsDataStore.allPeriodConfigs.collect { configs ->
                val periodDays = configs.mapValues { it.value.days }
                val periodLabels = configs.mapValues { it.value.label }
                _uiState.value = _uiState.value.copy(
                    periodDays = periodDays,
                    periodLabels = periodLabels
                )
            }
        }
    }

    fun selectPeriod(period: ChartPeriod) {
        _uiState.value = _uiState.value.copy(
            selectedPeriod = period,
            useCustomRange = false,
            rangeStart = null,
            rangeEnd = null
        )
        updateChart()
    }

    fun setCustomRangeStart(millis: Long) {
        val cal = Calendar.getInstance(Locale.CHINA).apply { timeInMillis = millis }
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        _uiState.value = _uiState.value.copy(rangeStart = cal.timeInMillis, useCustomRange = true)
        updateChart()
    }

    fun setCustomRangeEnd(millis: Long) {
        val cal = Calendar.getInstance(Locale.CHINA).apply { timeInMillis = millis }
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        cal.add(Calendar.DAY_OF_YEAR, 1)
        cal.add(Calendar.MILLISECOND, -1)
        _uiState.value = _uiState.value.copy(rangeEnd = cal.timeInMillis, useCustomRange = true)
        updateChart()
    }

    fun clearCustomRange() {
        _uiState.value = _uiState.value.copy(
            useCustomRange = false,
            rangeStart = null,
            rangeEnd = null
        )
        updateChart()
    }

    fun toggleTag(tagId: Long) {
        val current = _uiState.value.selectedTagIds
        _uiState.value = _uiState.value.copy(
            selectedTagIds = if (tagId in current) current - tagId else current + tagId
        )
        updateChart()
    }

    fun selectAllTags() {
        _uiState.value = _uiState.value.copy(selectedTagIds = emptySet())
        updateChart()
    }

    private fun updateChart() {
        val state = _uiState.value
        val dateFiltered = if (state.useCustomRange) {
            val start = state.rangeStart ?: return
            val end = state.rangeEnd ?: return
            state.allRecords.filter { it.record.timestamp in start..end }
        } else {
            val now = System.currentTimeMillis()
            val cal = Calendar.getInstance(Locale.CHINA)
            val periodDaysMap = state.periodDays
            val days = when (state.selectedPeriod) {
                ChartPeriod.WEEKLY -> periodDaysMap["7d"] ?: 7
                ChartPeriod.MONTHLY -> periodDaysMap["30d"] ?: 30
                ChartPeriod.QUARTERLY -> periodDaysMap["90d"] ?: 90
                ChartPeriod.SEMI_ANNUALLY -> periodDaysMap["180d"] ?: 180
                ChartPeriod.YEARLY -> periodDaysMap["year"] ?: 365
            }
            cal.timeInMillis = now
            cal.add(Calendar.DAY_OF_YEAR, -days)
            state.allRecords.filter { it.record.timestamp >= cal.timeInMillis }
        }
        val tagFiltered = if (state.selectedTagIds.isNotEmpty()) {
            dateFiltered.filter { rec -> state.selectedTagIds.all { id -> rec.tags.any { it.id == id } } }
        } else {
            dateFiltered
        }
        val chartData = tagFiltered.map {
            ChartDataPoint(timestamp = it.record.timestamp, weight = it.record.weight, tags = it.tags)
        }
        _uiState.value = _uiState.value.copy(
            records = tagFiltered,
            chartData = chartData
        )
    }
}
