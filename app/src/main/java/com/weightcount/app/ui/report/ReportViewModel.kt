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
    val periodDays: Map<String, Int> = emptyMap()
)

data class ChartDataPoint(
    val timestamp: Long,
    val weight: Double,
    val tags: List<Tag> = emptyList()
)

class ReportViewModel(application: Application) : AndroidViewModel(application) {

    private val app = application as WeightCountApp
    private val weightRepo = app.weightRepository
    private val settingsDataStore = app.settingsDataStore

    private val _uiState = MutableStateFlow(ReportUiState())
    val uiState: StateFlow<ReportUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            weightRepo.getAllRecordsWithTags().collect { records ->
                val sorted = records.sortedBy { it.record.timestamp }
                _uiState.value = _uiState.value.copy(allRecords = sorted)
                updateChartData(_uiState.value.selectedPeriod, sorted)
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
        updateChartData(period, _uiState.value.allRecords)
    }

    fun setCustomRangeStart(millis: Long) {
        val cal = Calendar.getInstance(Locale.CHINA).apply { timeInMillis = millis }
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        _uiState.value = _uiState.value.copy(rangeStart = cal.timeInMillis, useCustomRange = true)
        updateChartWithRange()
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
        updateChartWithRange()
    }

    fun clearCustomRange() {
        _uiState.value = _uiState.value.copy(
            useCustomRange = false,
            rangeStart = null,
            rangeEnd = null
        )
        updateChartData(_uiState.value.selectedPeriod, _uiState.value.allRecords)
    }

    private fun updateChartWithRange() {
        val state = _uiState.value
        val start = state.rangeStart
        val end = state.rangeEnd
        if (start == null || end == null) return
        val filtered = state.allRecords.filter {
            it.record.timestamp in start..end
        }
        val chartData = filtered.map {
            ChartDataPoint(timestamp = it.record.timestamp, weight = it.record.weight, tags = it.tags)
        }
        _uiState.value = _uiState.value.copy(
            records = filtered,
            chartData = chartData
        )
    }

    private fun updateChartData(period: ChartPeriod, allRecords: List<WeightRecordWithTags>) {
        val now = System.currentTimeMillis()
        val cal = Calendar.getInstance(Locale.CHINA)

        val periodDaysMap = _uiState.value.periodDays
        val days = when (period) {
            ChartPeriod.WEEKLY -> periodDaysMap["7d"] ?: 7
            ChartPeriod.MONTHLY -> periodDaysMap["30d"] ?: 30
            ChartPeriod.QUARTERLY -> periodDaysMap["90d"] ?: 90
            ChartPeriod.SEMI_ANNUALLY -> periodDaysMap["180d"] ?: 180
            ChartPeriod.YEARLY -> periodDaysMap["year"] ?: 365
        }

        cal.timeInMillis = now
        cal.add(Calendar.DAY_OF_YEAR, -days)
        val startTime = cal.timeInMillis

        val filtered = allRecords.filter { it.record.timestamp >= startTime }
        val chartData = filtered.map {
            ChartDataPoint(timestamp = it.record.timestamp, weight = it.record.weight, tags = it.tags)
        }

        _uiState.value = _uiState.value.copy(
            records = filtered,
            chartData = chartData
        )
    }
}
