package com.weightcount.app.ui.report

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.weightcount.app.data.entity.Tag
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(
    viewModel: ReportViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsState()
    var showStartPicker by remember { mutableStateOf(false) }
    var showEndPicker by remember { mutableStateOf(false) }
    var selectedPointIndex by remember { mutableStateOf(-1) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.6f),
            contentAlignment = Alignment.Center
        ) {
            if (state.chartData.isEmpty()) {
                Text(
                    text = "暂无数据",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                val sorted = state.chartData.sortedBy { it.timestamp }
                val df = remember { SimpleDateFormat("yyyy.M.d", Locale.CHINA) }
                val headerRange = if (state.useCustomRange && state.rangeStart != null && state.rangeEnd != null) {
                    "${df.format(Date(state.rangeStart!!))} - ${df.format(Date(state.rangeEnd!!))}"
                } else {
                    "${df.format(Date(sorted.first().timestamp))} - ${df.format(Date(sorted.last().timestamp))}"
                }
                WeightChart(
                    chartData = sorted,
                    lineColor = MaterialTheme.colorScheme.primary,
                    gridColor = MaterialTheme.colorScheme.outlineVariant,
                    labelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    selectedPointIndex = selectedPointIndex,
                    onPointIndexChange = { selectedPointIndex = it },
                    dateRangeText = headerRange
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        val scrollState = rememberScrollState()
        Column(modifier = Modifier.verticalScroll(scrollState)) {
            // Period selector: chips row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ChartPeriod.entries.forEach { period ->
                    val label = state.periodLabels[period.key] ?: period.defaultLabel
                    FilterChip(
                        selected = state.selectedPeriod == period && !state.useCustomRange,
                        onClick = { viewModel.selectPeriod(period) },
                        modifier = Modifier.padding(horizontal = 4.dp),
                        label = { Text(label, style = MaterialTheme.typography.labelSmall) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Tag filter chips row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilterChip(
                    selected = state.selectedTagIds.isEmpty(),
                    onClick = { viewModel.selectAllTags() },
                    label = { Text("全部", style = MaterialTheme.typography.labelSmall) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                        selectedLabelColor = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                )
                state.allTags.forEach { tag ->
                    FilterChip(
                        selected = tag.id in state.selectedTagIds,
                        onClick = { viewModel.toggleTag(tag.id) },
                        label = { Text(tag.name, style = MaterialTheme.typography.labelSmall) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            selectedLabelColor = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Date range selector (static buttons with fixed widths)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = { showStartPicker = true },
                    modifier = Modifier.weight(0.35f)
                ) {
                    Text("起始日期", style = MaterialTheme.typography.bodySmall)
                }
                Spacer(modifier = Modifier.weight(0.1f))
                OutlinedButton(
                    onClick = { showEndPicker = true },
                    modifier = Modifier.weight(0.35f)
                ) {
                    Text("结束日期", style = MaterialTheme.typography.bodySmall)
                }
            }

            // Summary stats
            if (state.chartData.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                val minW = state.chartData.minOf { it.weight }
                val maxW = state.chartData.maxOf { it.weight }
                val avgW = state.chartData.map { it.weight }.average()

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    StatItem("最低", String.format("%.1f", minW))
                    StatItem("平均", String.format("%.1f", avgW))
                    StatItem("最高", String.format("%.1f", maxW))
                }
            }
        }
    }

    if (showStartPicker) {
        val startPs = rememberDatePickerState(initialSelectedDateMillis = state.rangeStart)
        DatePickerDialog(
            onDismissRequest = { showStartPicker = false },
            confirmButton = {
                TextButton(onClick = {
                    startPs.selectedDateMillis?.let { viewModel.setCustomRangeStart(it) }
                    showStartPicker = false
                }) { Text("确定") }
            },
            dismissButton = {
                TextButton(onClick = { showStartPicker = false }) { Text("取消") }
            }
        ) { DatePicker(state = startPs) }
    }

    if (showEndPicker) {
        val endPs = rememberDatePickerState(initialSelectedDateMillis = state.rangeEnd)
        DatePickerDialog(
            onDismissRequest = { showEndPicker = false },
            confirmButton = {
                TextButton(onClick = {
                    endPs.selectedDateMillis?.let { viewModel.setCustomRangeEnd(it) }
                    showEndPicker = false
                }) { Text("确定") }
            },
            dismissButton = {
                TextButton(onClick = { showEndPicker = false }) { Text("取消") }
            }
        ) { DatePicker(state = endPs) }
    }
}

@Composable
private fun StatItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = "$value kg",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun WeightChart(
    chartData: List<ChartDataPoint>,
    lineColor: Color,
    gridColor: Color,
    labelColor: Color,
    selectedPointIndex: Int,
    onPointIndexChange: (Int) -> Unit,
    dateRangeText: String = "",
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    val textPaint = remember {
        android.graphics.Paint().apply {
            color = android.graphics.Color.parseColor("#9E9E9E")
            textSize = with(density) { 10.sp.toPx() }
            textAlign = android.graphics.Paint.Align.CENTER
        }
    }
    val yLabelPaint = remember {
        android.graphics.Paint().apply {
            color = android.graphics.Color.parseColor("#9E9E9E")
            textSize = with(density) { 10.sp.toPx() }
            textAlign = android.graphics.Paint.Align.RIGHT
        }
    }

    val sortedData = remember(chartData) { chartData.sortedBy { it.timestamp } }
    val dateFormat = remember { SimpleDateFormat("M/d", Locale.CHINA) }
    val dayFormat = remember { SimpleDateFormat("yyyy年M月d日", Locale.CHINA) }
    val timeFormat = remember { SimpleDateFormat("HH:mm", Locale.CHINA) }
    val tipTextPaint = remember {
        android.graphics.Paint().apply {
            color = android.graphics.Color.WHITE
            textSize = with(density) { 10.sp.toPx() }
            textAlign = android.graphics.Paint.Align.CENTER
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        // Navigation row with arrows and date range
        if (sortedData.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    val curr = selectedPointIndex
                    if (curr >= 0) {
                        onPointIndexChange((curr - 1 + sortedData.size) % sortedData.size)
                    }
                }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "上一个")
                }
                Text(
                    text = dateRangeText,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                IconButton(onClick = {
                    val curr = selectedPointIndex
                    if (curr >= 0) {
                        onPointIndexChange((curr + 1) % sortedData.size)
                    }
                }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "下一个")
                }
            }
        }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp, end = 8.dp)
            .pointerInput(sortedData) {
                detectTapGestures { offset ->
                    if (sortedData.size == 1) {
                        onPointIndexChange(if (selectedPointIndex == -1) 0 else -1)
                        return@detectTapGestures
                    }
                    if (sortedData.size < 2) return@detectTapGestures

                    val leftPadding = 48.dp.toPx()
                    val bottomPadding = 32.dp.toPx()
                    val topPadding = 16.dp.toPx()
                    val chartWidth = size.width - leftPadding - 16.dp.toPx()
                    val chartHeight = size.height - bottomPadding - topPadding

                    val tapX = offset.x - leftPadding
                    val closest = sortedData.indices.minByOrNull { i ->
                        val x = ((sortedData[i].timestamp - sortedData.first().timestamp).toFloat() /
                                (sortedData.last().timestamp - sortedData.first().timestamp).coerceAtLeast(1)) * chartWidth
                        kotlin.math.abs(tapX - x)
                    } ?: -1

                    onPointIndexChange(if (closest == selectedPointIndex) -1 else closest)
                }
            }
    ) {
        val leftPadding = 48.dp.toPx()
        val bottomPadding = 48.dp.toPx()
        val topPadding = 16.dp.toPx()
        val rightPadding = 16.dp.toPx()
        val chartWidth = size.width - leftPadding - rightPadding
        val chartHeight = size.height - bottomPadding - topPadding

        val minWeight = sortedData.minOfOrNull { it.weight } ?: 0.0
        val maxWeight = sortedData.maxOfOrNull { it.weight } ?: 100.0
        val weightRange = if (maxWeight - minWeight < 0.1) 1.0 else maxWeight - minWeight

        val yPadding = weightRange * 0.1
        val yMin = minWeight - yPadding
        val yMax = maxWeight + yPadding
        val yRange = yMax - yMin

        // Horizontal grid lines (5 lines)
        val gridLineCount = 5
        for (i in 0..gridLineCount) {
            val y = topPadding + chartHeight * i / gridLineCount
            drawLine(
                color = gridColor,
                start = Offset(leftPadding, y),
                end = Offset(leftPadding + chartWidth, y),
                strokeWidth = 0.5f
            )

            val weightValue = yMax - (yRange * i / gridLineCount)
            val label = String.format(Locale.CHINA, "%.1f", weightValue)
            drawContext.canvas.nativeCanvas.drawText(
                label,
                leftPadding - 4.dp.toPx(),
                y + 4.dp.toPx(),
                yLabelPaint
            )
        }

        if (sortedData.size < 2) {
            if (sortedData.size == 1) {
                val point = sortedData.first()
                val cx = leftPadding + chartWidth / 2
                val cy = topPadding + chartHeight * ((yMax - point.weight) / yRange).toFloat()

                // Single data point circle
                drawCircle(color = Color.White, radius = 5.dp.toPx(), center = Offset(cx, cy))
                drawCircle(color = lineColor, radius = 4.dp.toPx(), center = Offset(cx, cy))

                // Date label
                drawContext.canvas.nativeCanvas.drawText(
                    dateFormat.format(Date(point.timestamp)),
                    cx,
                    size.height - 12.dp.toPx(),
                    textPaint
                )

                // Tooltip if selected
                if (selectedPointIndex == 0) {
                    val dateLine = dayFormat.format(Date(point.timestamp))
                    val timeLine = String.format(Locale.CHINA, "%s  %.1f kg", timeFormat.format(Date(point.timestamp)), point.weight)
                    val tagLines = buildTagLines(point.tags)
                    val lineHeight = 14.dp.toPx()
                    val totalLines = 2 + tagLines.size
                    val tooltipWidth = 140.dp.toPx()
                    val tooltipHeight = 8.dp.toPx() + lineHeight * (totalLines + 0.5f)
                    var tooltipX = cx - tooltipWidth / 2
                    val tooltipY = (cy - tooltipHeight - 12.dp.toPx()).coerceAtLeast(0f)
                    tooltipX = tooltipX.coerceIn(0f, size.width - tooltipWidth)

                    drawRoundRect(
                        color = Color(0xFF3C3C3C),
                        topLeft = Offset(tooltipX, tooltipY),
                        size = androidx.compose.ui.geometry.Size(tooltipWidth, tooltipHeight),
                        cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
                    )

                    drawContext.canvas.nativeCanvas.drawText(dateLine, tooltipX + tooltipWidth / 2, tooltipY + 6.dp.toPx() + lineHeight, tipTextPaint)
                    drawContext.canvas.nativeCanvas.drawText(timeLine, tooltipX + tooltipWidth / 2, tooltipY + 6.dp.toPx() + lineHeight * 2, tipTextPaint)
                    tagLines.forEachIndexed { i, line ->
                        drawContext.canvas.nativeCanvas.drawText(line, tooltipX + tooltipWidth / 2, tooltipY + 6.dp.toPx() + (2 + i) * lineHeight, tipTextPaint)
                    }

                    drawCircle(color = lineColor, radius = 8.dp.toPx(), center = Offset(cx, cy))
                    drawCircle(color = Color.White, radius = 4.dp.toPx(), center = Offset(cx, cy))
                }
            }
            return@Canvas
        }

        val timeMin = sortedData.first().timestamp
        val timeRange = (sortedData.last().timestamp - timeMin).coerceAtLeast(1L).toFloat()

        val points = sortedData.map { point ->
            val x = leftPadding + ((point.timestamp - timeMin) / timeRange) * chartWidth
            val y = topPadding + chartHeight * ((yMax - point.weight) / yRange).toFloat()
            Offset(x, y)
        }

        // Draw line
        if (points.size >= 2) {
            val path = Path()
            path.moveTo(points[0].x, points[0].y)
            for (i in 1 until points.size) {
                path.lineTo(points[i].x, points[i].y)
            }
            drawPath(
                path = path,
                color = lineColor,
                style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
            )
        }

        // Draw data point circles
        points.forEach { point ->
            drawCircle(color = Color.White, radius = 5.dp.toPx(), center = point)
            drawCircle(color = lineColor, radius = 4.dp.toPx(), center = point)
        }

        // Evenly-distributed x-axis labels across time range
        val timeMax = sortedData.last().timestamp
        val timeSpanDays = (timeMax - timeMin) / 86400000f
        val labelCount = when {
            timeSpanDays <= 10 -> 5
            timeSpanDays <= 60 -> 6
            else -> 8
        }.coerceAtMost(sortedData.size)
        val labelTimeStep = (timeMax - timeMin).toFloat() / (labelCount - 1).coerceAtLeast(1)

        for (i in 0 until labelCount) {
            val t = timeMin + (labelTimeStep * i).toLong()
            val x = leftPadding + ((t - timeMin) / (timeMax - timeMin).coerceAtLeast(1L).toFloat()) * chartWidth
            val label = dateFormat.format(Date(t))
            drawContext.canvas.nativeCanvas.drawText(
                label,
                x,
                size.height - 12.dp.toPx(),
                textPaint
            )
        }

        // Draw multi-line tooltip
        if (selectedPointIndex in sortedData.indices) {
            val tipPoint = sortedData[selectedPointIndex]
            val tipPos = points[selectedPointIndex]

            val dateLine = dayFormat.format(Date(tipPoint.timestamp))
            val timeLine = String.format(Locale.CHINA, "%s  %.1f kg", timeFormat.format(Date(tipPoint.timestamp)), tipPoint.weight)
            val tagLines = buildTagLines(tipPoint.tags)

            val lineHeight = 14.dp.toPx()
            val totalLines = 2 + tagLines.size
            val tooltipWidth = 140.dp.toPx()
            val tooltipHeight = 8.dp.toPx() + lineHeight * (totalLines + 0.5f)
            val textStartY = 6.dp.toPx() + lineHeight

            var tooltipX = tipPos.x - tooltipWidth / 2
            val tooltipY = (tipPos.y - tooltipHeight - 12.dp.toPx()).coerceAtLeast(0f)
            tooltipX = tooltipX.coerceIn(0f, size.width - tooltipWidth)

            drawRoundRect(
                color = Color(0xFF3C3C3C),
                topLeft = Offset(tooltipX, tooltipY),
                size = androidx.compose.ui.geometry.Size(tooltipWidth, tooltipHeight),
                cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
            )

            drawContext.canvas.nativeCanvas.drawText(
                dateLine,
                tooltipX + tooltipWidth / 2,
                tooltipY + textStartY,
                tipTextPaint
            )
            drawContext.canvas.nativeCanvas.drawText(
                timeLine,
                tooltipX + tooltipWidth / 2,
                tooltipY + textStartY + lineHeight,
                tipTextPaint
            )
            tagLines.forEachIndexed { i, line ->
                drawContext.canvas.nativeCanvas.drawText(
                    line,
                    tooltipX + tooltipWidth / 2,
                    tooltipY + textStartY + (2 + i) * lineHeight,
                    tipTextPaint
                )
            }

            drawCircle(color = lineColor, radius = 8.dp.toPx(), center = tipPos)
            drawCircle(color = Color.White, radius = 4.dp.toPx(), center = tipPos)
        }
    }
    }
}

private fun buildTagLines(tags: List<Tag>): List<String> {
    if (tags.isEmpty()) return emptyList()
    val lines = mutableListOf<String>()
    val current = StringBuilder()
    tags.forEachIndexed { index, tag ->
        if (index > 0 && index % 2 == 0) {
            lines.add(current.toString().trim())
            current.clear()
        }
        if (current.isNotEmpty()) current.append("  ")
        current.append(tag.name)
    }
    if (current.isNotEmpty()) lines.add(current.toString().trim())
    return lines
}
