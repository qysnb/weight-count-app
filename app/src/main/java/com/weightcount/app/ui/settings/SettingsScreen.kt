package com.weightcount.app.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.weightcount.app.data.entity.Tag
import com.weightcount.app.data.datastore.PeriodConfig

@Composable
fun SettingsScreen(
    onNavigateToAbout: () -> Unit,
    viewModel: SettingsViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        item {
            Text(
                text = "设置",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        // Custom Tags
        item {
            SettingsSectionHeader("自定义标签")
        }

        item {
            TagListSection(
                tags = state.tags,
                onAddClick = viewModel::showAddTagDialog,
                onRenameClick = viewModel::showRenameTagDialog,
                onDeleteClick = viewModel::showDeleteTagConfirm
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            SettingsSectionHeader("报表自定义")
        }

        item {
            PeriodCustomizer(
                configs = state.periodConfigs,
                onEditPeriod = viewModel::showEditPeriodDialog
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            SettingsSectionHeader("其他")
        }

        item {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigateToAbout() },
                shape = MaterialTheme.shapes.medium,
                tonalElevation = 1.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("关于", style = MaterialTheme.typography.bodyLarge)
                    Icon(
                        Icons.Default.ChevronRight,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }

    if (state.showAddTagDialog) {
        TagNameDialog(
            title = "添加标签",
            initialValue = state.newTagName,
            error = state.tagError,
            onValueChange = viewModel::setNewTagName,
            onConfirm = viewModel::confirmAddTag,
            onDismiss = viewModel::dismissAddTagDialog
        )
    }

    if (state.showRenameTagDialog) {
        TagNameDialog(
            title = "重命名标签",
            initialValue = state.newTagName,
            error = state.tagError,
            onValueChange = viewModel::setNewTagName,
            onConfirm = viewModel::confirmRenameTag,
            onDismiss = viewModel::dismissRenameTagDialog
        )
    }

    if (state.showDeleteTagConfirm) {
        AlertDialog(
            onDismissRequest = viewModel::dismissDeleteTagConfirm,
            title = { Text("删除标签") },
            text = { Text("删除标签「${state.editingTag?.name}」？") },
            confirmButton = {
                Button(onClick = viewModel::confirmDeleteTag) {
                    Text("删除")
                }
            },
            dismissButton = {
                TextButton(onClick = viewModel::dismissDeleteTagConfirm) {
                    Text("取消")
                }
            }
        )
    }

    if (state.showEditPeriodDialog) {
        AlertDialog(
            onDismissRequest = viewModel::dismissEditPeriodDialog,
            title = { Text("编辑周期") },
            text = {
                Column {
                    OutlinedTextField(
                        value = state.editingPeriodName,
                        onValueChange = viewModel::setEditingPeriodName,
                        label = { Text("名称") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = state.editingPeriodValue,
                        onValueChange = viewModel::setEditingPeriodValue,
                        label = { Text("天数") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(onClick = viewModel::confirmEditPeriod) {
                    Text("确定")
                }
            },
            dismissButton = {
                TextButton(onClick = viewModel::dismissEditPeriodDialog) {
                    Text("取消")
                }
            }
        )
    }
}

@Composable
private fun SettingsSectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
private fun TagListSection(
    tags: List<Tag>,
    onAddClick: () -> Unit,
    onRenameClick: (Tag) -> Unit,
    onDeleteClick: (Tag) -> Unit
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        tonalElevation = 1.dp
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("标签列表", style = MaterialTheme.typography.bodyMedium)
                IconButton(onClick = onAddClick) {
                    Icon(Icons.Default.Add, contentDescription = "添加")
                }
            }

            tags.forEach { tag ->
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = tag.name,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onRenameClick(tag) }
                            .padding(horizontal = 8.dp)
                    )
                    TextButton(onClick = { onDeleteClick(tag) }) {
                        Text("删除", color = MaterialTheme.colorScheme.error)
                    }
                }
            }

            if (tags.isEmpty()) {
                Text(
                    text = "暂无标签",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
private fun PeriodCustomizer(
    configs: Map<String, PeriodConfig>,
    onEditPeriod: (String, PeriodConfig) -> Unit
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        tonalElevation = 1.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            val entries = listOf("7d" to "7日", "30d" to "30日", "90d" to "90日", "180d" to "180日", "year" to "年度")
            entries.forEach { (key, _) ->
                val config = configs[key] ?: PeriodConfig(key, 7)
                PeriodRow(
                    label = config.label,
                    days = config.days
                ) { onEditPeriod(key, config) }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun PeriodRow(
    label: String,
    days: Int,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.small,
        tonalElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(label, style = MaterialTheme.typography.bodyMedium)
            Text(
                "${days}天",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun TagNameDialog(
    title: String,
    initialValue: String,
    error: String?,
    onValueChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            OutlinedTextField(
                value = initialValue,
                onValueChange = onValueChange,
                label = { Text("输入标签名称") },
                isError = error != null,
                supportingText = error?.let { { Text(it) } },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text("确定")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("取消")
            }
        }
    )
}
