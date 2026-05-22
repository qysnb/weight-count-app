package com.weightcount.app.ui.record

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.util.Calendar
import java.util.Locale

@Composable
fun RecordScreen(
    viewModel: RecordViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsState()
    val allTags by viewModel.allTags.collectAsState()

    val weightByDay = buildWeightByDay(state.records)

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp)
        ) {
            MonthYearSelector(
                displayYear = state.displayYear,
                displayMonth = state.displayMonth,
                onPreviousMonth = { viewModel.navigateMonth(-1) },
                onNextMonth = { viewModel.navigateMonth(1) },
                onPreviousYear = { viewModel.navigateYear(-1) },
                onNextYear = { viewModel.navigateYear(1) }
            )

            CalendarHeader(
                selectedDate = state.selectedDate,
                displayYear = state.displayYear,
                displayMonth = state.displayMonth,
                weightByDay = weightByDay,
                onDaySelected = { viewModel.setSelectedDate(it) }
            )

            Spacer(modifier = Modifier.height(8.dp))

            val dateFormat = java.text.SimpleDateFormat("M月d日", Locale.CHINA)
            Text(
                text = dateFormat.format(java.util.Date(state.selectedDate)),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            WeightLogList(
                records = state.records.filter {
                    val cal = Calendar.getInstance(Locale.CHINA).apply { timeInMillis = state.selectedDate }
                    val startOfDay = cal.apply {
                        set(Calendar.HOUR_OF_DAY, 0)
                        set(Calendar.MINUTE, 0)
                        set(Calendar.SECOND, 0)
                        set(Calendar.MILLISECOND, 0)
                    }.timeInMillis
                    val endOfDay = startOfDay + 86400000L
                    it.record.timestamp in startOfDay until endOfDay
                }.sortedByDescending { it.record.timestamp },
                onRecordClick = { viewModel.showEditDialog(it) }
            )
        }

        FloatingActionButton(
            onClick = { viewModel.showAddDialog() },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(Icons.Default.Add, contentDescription = "记录")
        }
    }

    if (state.showAddDialog) {
        AddWeightDialog(
            weightInput = state.weightInput,
            selectedTagIds = state.selectedTagIds,
            selectedTimestamp = state.selectedTimestamp,
            tags = allTags,
            weightError = state.weightError,
            onWeightChange = viewModel::setWeightInput,
            onTagToggle = viewModel::toggleTag,
            onTimestampChange = viewModel::setSelectedTimestamp,
            onConfirm = viewModel::confirmAddRecord,
            onDismiss = viewModel::dismissAddDialog
        )
    }

    if (state.showEditDialog) {
        EditWeightDialog(
            weightInput = state.weightInput,
            selectedTagIds = state.selectedTagIds,
            selectedTimestamp = state.selectedTimestamp,
            tags = allTags,
            weightError = state.weightError,
            showDeleteConfirm = state.showDeleteConfirm,
            onWeightChange = viewModel::setWeightInput,
            onTagToggle = viewModel::toggleTag,
            onTimestampChange = viewModel::setSelectedTimestamp,
            onConfirm = viewModel::confirmEditRecord,
            onDismiss = viewModel::dismissEditDialog,
            onDelete = viewModel::showDeleteConfirm,
            onDeleteConfirm = viewModel::confirmDelete,
            onDeleteDismiss = viewModel::dismissDeleteConfirm
        )
    }
}

internal fun buildWeightByDay(records: List<com.weightcount.app.data.entity.WeightRecordWithTags>): Map<Long, String> {
    val result = mutableMapOf<Long, MutableList<Double>>()
    val cal = Calendar.getInstance(Locale.CHINA)

    records.forEach { rw ->
        cal.timeInMillis = rw.record.timestamp
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        val dayStart = cal.timeInMillis
        result.getOrPut(dayStart) { mutableListOf() }.add(rw.record.weight)
    }

    return result.mapValues { (_, weights) ->
        val avg = weights.average()
        if (weights.size == 1) String.format(Locale.CHINA, "%.1f", weights[0])
        else String.format(Locale.CHINA, "%.1f", avg)
    }
}
