package com.weightcount.app.ui.record

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.util.Calendar
import java.util.Locale

data class CalendarDay(
    val day: Int,
    val timestamp: Long,
    val weightSummary: String?,
    val isToday: Boolean,
    val isSelected: Boolean
)

@Composable
fun MonthYearSelector(
    displayYear: Int,
    displayMonth: Int,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit,
    onPreviousYear: () -> Unit,
    onNextYear: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onPreviousYear) {
            Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "上年")
        }
        IconButton(onClick = onPreviousMonth) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "上月")
        }
        Text(
            text = String.format(Locale.CHINA, "%d年%d月", displayYear, displayMonth + 1),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = onNextMonth) {
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "下月")
        }
        IconButton(onClick = onNextYear) {
            Icon(Icons.Default.KeyboardArrowRight, contentDescription = "下年")
        }
    }
}

@Composable
fun CalendarHeader(
    selectedDate: Long,
    displayYear: Int,
    displayMonth: Int,
    weightByDay: Map<Long, String>,
    onDaySelected: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val todayCal = Calendar.getInstance(Locale.CHINA)

    val days = buildMonthDays(displayYear, displayMonth, selectedDate, todayCal.timeInMillis, weightByDay)

    Column(modifier = modifier.fillMaxWidth()) {
        // Weekday headers
        val weekDays = listOf("一", "二", "三", "四", "五", "六", "日")
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp),
            userScrollEnabled = false
        ) {
            items(weekDays) { day ->
                Text(
                    text = day,
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Day grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp),
            userScrollEnabled = false
        ) {
            items(days) { day ->
                CalendarDayCell(
                    day = day,
                    isSelected = day.isSelected,
                    onClick = { if (day.day > 0) onDaySelected(day.timestamp) }
                )
            }
        }
    }
}

@Composable
private fun CalendarDayCell(
    day: CalendarDay,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val bgColor = if (isSelected) {
        MaterialTheme.colorScheme.primaryContainer
    } else if (day.isToday) {
        MaterialTheme.colorScheme.surfaceVariant
    } else {
        MaterialTheme.colorScheme.surface
    }

    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(bgColor)
            .clickable(enabled = day.day > 0) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (day.day > 0) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = day.day.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer
                    else MaterialTheme.colorScheme.onSurface
                )
                day.weightSummary?.let { summary ->
                    Text(
                        text = summary,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

private fun buildMonthDays(
    year: Int,
    month: Int,
    selectedDateMillis: Long,
    todayMillis: Long,
    weightByDay: Map<Long, String>
): List<CalendarDay> {
    val cal = Calendar.getInstance(Locale.CHINA)
    cal.set(year, month, 1)
    val firstDayOfWeek = cal.get(Calendar.DAY_OF_WEEK)
    val leadingBlanks = (firstDayOfWeek + 5) % 7
    val maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH)

    val todayCal = Calendar.getInstance(Locale.CHINA)
    todayCal.set(Calendar.HOUR_OF_DAY, 0)
    todayCal.set(Calendar.MINUTE, 0)
    todayCal.set(Calendar.SECOND, 0)
    todayCal.set(Calendar.MILLISECOND, 0)
    val todayStart = todayCal.timeInMillis

    val selectedCal = Calendar.getInstance(Locale.CHINA)
    selectedCal.timeInMillis = selectedDateMillis
    selectedCal.set(Calendar.HOUR_OF_DAY, 0)
    selectedCal.set(Calendar.MINUTE, 0)
    selectedCal.set(Calendar.SECOND, 0)
    selectedCal.set(Calendar.MILLISECOND, 0)
    val selectedStart = selectedCal.timeInMillis

    val days = mutableListOf<CalendarDay>()

    repeat(leadingBlanks) {
        days.add(CalendarDay(day = -1, timestamp = 0, weightSummary = null, isToday = false, isSelected = false))
    }

    for (d in 1..maxDay) {
        cal.set(year, month, d)
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        val dayStart = cal.timeInMillis

        val summary = weightByDay[dayStart]
        val isToday = dayStart == todayStart
        val isSelected = dayStart == selectedStart

        days.add(CalendarDay(day = d, timestamp = dayStart, weightSummary = summary, isToday = isToday, isSelected = isSelected))
    }

    return days
}
