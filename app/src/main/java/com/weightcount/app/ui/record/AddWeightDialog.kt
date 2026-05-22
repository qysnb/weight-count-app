package com.weightcount.app.ui.record

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.weightcount.app.ui.components.TagChipGroup
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWeightDialog(
    weightInput: String,
    selectedTagIds: Set<Long>,
    selectedTimestamp: Long,
    tags: List<com.weightcount.app.data.entity.Tag>,
    weightError: String?,
    onWeightChange: (String) -> Unit,
    onTagToggle: (Long) -> Unit,
    onTimestampChange: (Long) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    showDelete: Boolean = false,
    onDelete: () -> Unit = {}
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier.fillMaxWidth(0.85f),
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp
        ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(
                text = "记录体重",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = weightInput,
                onValueChange = onWeightChange,
                label = { Text("体重（公斤）") },
                isError = weightError != null,
                supportingText = weightError?.let { { Text(it) } },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            val cal = Calendar.getInstance(Locale.CHINA).apply { timeInMillis = selectedTimestamp }
            val year = cal.get(Calendar.YEAR)
            val monthDay = String.format(Locale.CHINA, "%d月%d日", cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH))
            val timeStr = String.format(Locale.CHINA, "%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = { showDatePicker = true },
                    modifier = Modifier.weight(1f)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${year}年",
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = monthDay,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                OutlinedButton(
                    onClick = { showTimePicker = true },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = timeStr,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "标签",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            TagChipGroup(
                tags = tags,
                selectedTagIds = selectedTagIds,
                onTagToggle = onTagToggle
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (showDelete) {
                    OutlinedButton(onClick = onDelete) {
                        Text("删除", color = MaterialTheme.colorScheme.error)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                }
                TextButton(onClick = onDismiss) {
                    Text("取消")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = onConfirm) {
                    Text("确定")
                }
            }
        }
        }
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = selectedTimestamp
        )
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val cal = Calendar.getInstance(Locale.CHINA).apply { timeInMillis = millis }
                        val current = Calendar.getInstance(Locale.CHINA).apply { timeInMillis = selectedTimestamp }
                        cal.set(Calendar.HOUR_OF_DAY, current.get(Calendar.HOUR_OF_DAY))
                        cal.set(Calendar.MINUTE, current.get(Calendar.MINUTE))
                        cal.set(Calendar.SECOND, current.get(Calendar.SECOND))
                        onTimestampChange(cal.timeInMillis)
                    }
                    showDatePicker = false
                }) {
                    Text("确定")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("取消")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    if (showTimePicker) {
        val timeState = rememberTimePickerState(
            initialHour = Calendar.getInstance(Locale.CHINA).apply { timeInMillis = selectedTimestamp }.get(Calendar.HOUR_OF_DAY),
            initialMinute = Calendar.getInstance(Locale.CHINA).apply { timeInMillis = selectedTimestamp }.get(Calendar.MINUTE),
            is24Hour = true
        )
        Dialog(onDismissRequest = { showTimePicker = false }) {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                TimePicker(state = timeState)
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = { showTimePicker = false }) {
                        Text("取消")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = {
                        val cal = Calendar.getInstance(Locale.CHINA).apply { timeInMillis = selectedTimestamp }
                        cal.set(Calendar.HOUR_OF_DAY, timeState.hour)
                        cal.set(Calendar.MINUTE, timeState.minute)
                        cal.set(Calendar.SECOND, 0)
                        onTimestampChange(cal.timeInMillis)
                        showTimePicker = false
                    }) {
                        Text("确定")
                    }
                }
            }
        }
    }
}
