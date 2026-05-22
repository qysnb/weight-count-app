package com.weightcount.app.ui.record

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EditWeightDialog(
    weightInput: String,
    selectedTagIds: Set<Long>,
    selectedTimestamp: Long,
    tags: List<com.weightcount.app.data.entity.Tag>,
    weightError: String?,
    showDeleteConfirm: Boolean,
    onWeightChange: (String) -> Unit,
    onTagToggle: (Long) -> Unit,
    onTimestampChange: (Long) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    onDelete: () -> Unit,
    onDeleteConfirm: () -> Unit,
    onDeleteDismiss: () -> Unit
) {
    AddWeightDialog(
        weightInput = weightInput,
        selectedTagIds = selectedTagIds,
        selectedTimestamp = selectedTimestamp,
        tags = tags,
        weightError = weightError,
        onWeightChange = onWeightChange,
        onTagToggle = onTagToggle,
        onTimestampChange = onTimestampChange,
        onConfirm = onConfirm,
        onDismiss = onDismiss,
        showDelete = true,
        onDelete = onDelete
    )

    // Delete confirmation dialog
    if (showDeleteConfirm) {
        AlertDialog(
            onDismissRequest = onDeleteDismiss,
            title = { Text("删除记录") },
            text = { Text("确定删除此条记录？") },
            confirmButton = {
                Button(onClick = onDeleteConfirm) {
                    Text("确定")
                }
            },
            dismissButton = {
                TextButton(onClick = onDeleteDismiss) {
                    Text("取消")
                }
            }
        )
    }
}
