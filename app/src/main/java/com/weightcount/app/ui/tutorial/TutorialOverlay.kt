package com.weightcount.app.ui.tutorial

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.weightcount.app.navigation.Screen

private data class TutorialStep(
    val title: String,
    val description: String,
    val navigateTo: String? = null
)

private val steps = listOf(
    TutorialStep(
        title = "欢迎使用体重记录",
        description = "本应用包含三个主要功能模块：\n\n【记录】- 记录每天的体重数据\n【报表】- 查看体重变化图表\n【设置】- 管理标签和自定义报表\n\n让我们一步步了解如何使用。\n\n您可以随时点击教程左上角的 - 按钮暂时最小化教程，随后通过右下角 ··· 按钮返回教程。",
        navigateTo = null
    ),
    TutorialStep(
        title = "日历导航",
        description = "在【记录】页面中，您可以使用日历上方的四个箭头按钮切换年月：\n• ←上月→下月\n• < 上年 > 下年\n\n通过切换可以查看不同日期的记录。",
        navigateTo = Screen.Record.route
    ),
    TutorialStep(
        title = "添加记录",
        description = "这是【记录】页面。\n\n点击下方中间的 + 按钮，填写体重、日期和标签信息，添加体重记录。\n\n请现在尝试自行添加两条记录。",
        navigateTo = Screen.Record.route
    ),
    TutorialStep(
        title = "查看报表",
        description = "点击底部导航栏的【报表】标签页，可以查看体重变化图表。\n\n您可以使用以下功能：\n• 切换周期（7日/30日/90日等）\n• 选择查看的具体日期范围\n• 按标签筛选数据",
        navigateTo = Screen.Report.route
    ),
    TutorialStep(
        title = "图表数据点",
        description = "在【报表】页面中，您可以点击图表上的数据点来选中它，查看该点的详细信息。\n\n点击数据点上方的 ← → 箭头可以切换查看相邻的数据点。",
        navigateTo = Screen.Report.route
    ),
    TutorialStep(
        title = "设置与自定义",
        description = "点击底部导航栏的【设置】标签页，您可以：\n\n• 添加/编辑/删除自定义标签\n• 自定义报表周期名称和天数\n• 再次体验本教程",
        navigateTo = Screen.Settings.route
    ),
    TutorialStep(
        title = "删除记录",
        description = "回到【记录】页面，您可以点击一条已有记录，在弹出的操作中选择删除。\n\n定期清理不需要的记录有助于保持数据整洁。",
        navigateTo = Screen.Record.route
    ),
    TutorialStep(
        title = "教程完成",
        description = "教程已完成！现在您可以自由使用本应用了。\n\n如有需要，可以随时在设置页面再次使用本教程。",
        navigateTo = null
    )
)

@Composable
fun TutorialOverlay(
    visible: Boolean,
    onComplete: () -> Unit,
    onNavigate: (String) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    var currentStep by remember { mutableIntStateOf(0) }
    var isCollapsed by remember { mutableStateOf(false) }

    LaunchedEffect(currentStep) {
        if (visible) {
            isCollapsed = false
            steps[currentStep].navigateTo?.let { onNavigate(it) }
        }
    }

    LaunchedEffect(visible) {
        if (visible) {
            currentStep = 0
            isCollapsed = false
        }
    }

    val dimAlpha by animateFloatAsState(
        targetValue = if (visible && !isCollapsed) 0.6f else 0f,
        label = "dimAlpha"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        if (visible) {
            if (!isCollapsed) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = dimAlpha))
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) { }
                )

                BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                    val bottomMargin = maxHeight * 0.1f

                    Card(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .padding(start = 24.dp, end = 24.dp, bottom = bottomMargin),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                    ) {
                        Box {
                            IconButton(
                                onClick = { isCollapsed = true },
                                modifier = Modifier.align(Alignment.TopStart)
                            ) {
                                Text(
                                    text = "_",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(24.dp)
                                    .verticalScroll(rememberScrollState()),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = "第 ${currentStep + 1}/${steps.size} 步",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                Text(
                                    text = steps[currentStep].title,
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                Text(
                                    text = steps[currentStep].description,
                                    style = MaterialTheme.typography.bodyLarge,
                                    textAlign = TextAlign.Start,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )

                                Spacer(modifier = Modifier.height(24.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    TextButton(onClick = { onComplete() }) {
                                        Text("跳过")
                                    }

                                    Row {
                                        if (currentStep > 0) {
                                            TextButton(onClick = { currentStep-- }) {
                                                Text("上一步")
                                            }
                                        }

                                        Spacer(modifier = Modifier.padding(horizontal = 4.dp))

                                        Button(onClick = {
                                            if (currentStep < steps.size - 1) {
                                                currentStep++
                                            } else {
                                                onComplete()
                                            }
                                        }) {
                                            Text(if (currentStep < steps.size - 1) "下一步" else "完成教程")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (isCollapsed) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(24.dp)
                        .size(48.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        )
                        .clickable { isCollapsed = false },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "···",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}
