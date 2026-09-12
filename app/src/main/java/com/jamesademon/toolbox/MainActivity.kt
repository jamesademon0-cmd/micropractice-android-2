package com.jamesademon.toolbox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState); setContent { ToolBoxApp() } }
}

data class Tool(val name: String, val desc: String, val icon: ImageVector, val category: String)

private val tools = listOf(
    Tool("随机密码", "生成安全随机密码", Icons.Default.Password, "日常"),
    Tool("JSON 格式化", "格式化与校验 JSON", Icons.Default.Code, "编程"),
    Tool("文本统计", "字数、行数、字符统计", Icons.Default.TextFields, "文字"),
    Tool("单位转换", "长度、重量、温度等", Icons.Default.SwapHoriz, "转换"),
    Tool("二维码", "生成或识别二维码", Icons.Default.QrCode2, "日常"),
    Tool("OCR 文字识别", "图片提取文字", Icons.Default.DocumentScanner, "智能"),
    Tool("图片压缩", "快速降低图片体积", Icons.Default.Compress, "图片"),
    Tool("PDF 工具", "合并、拆分、转换", Icons.Default.PictureAsPdf, "文档"),
    Tool("番茄专注", "专注计时与任务管理", Icons.Default.Timer, "日常"),
    Tool("白噪音", "专注与睡眠声音", Icons.Default.GraphicEq, "音频"),
    Tool("视频转 GIF", "视频片段转换为 GIF", Icons.Default.Movie, "视频"),
    Tool("正则测试", "在线测试正则表达式", Icons.Default.Rule, "编程")
)

@Composable fun ToolBoxApp() {
    var selected by remember { mutableIntStateOf(0) }
    var query by remember { mutableStateOf("") }
    val categories = listOf("全部", "日常", "图片", "视频", "文字", "文档", "智能", "编程")
    val filtered = tools.filter { (query.isBlank() || it.name.contains(query, true)) && (selected == 0 || it.category == categories[selected]) }
    MaterialTheme(colorScheme = lightColorScheme(primary = Color(0xFF4D73FF))) {
        Box(Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color(0xFFF4F7FF), Color(0xFFF9FAFC))))) {
            Column(Modifier.fillMaxSize().statusBarsPadding()) {
                Row(Modifier.padding(horizontal = 20.dp, vertical = 14.dp), verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) { Text("工具箱", fontSize = 30.sp, fontWeight = FontWeight.Bold); Text("一站式解决日常小需求", color = Color.Gray) }
                    IconButton(onClick = {}) { Icon(Icons.Default.Settings, "设置") }
                }
                OutlinedTextField(query, { query = it }, Modifier.fillMaxWidth().padding(horizontal = 16.dp), placeholder = { Text("搜索工具") }, leadingIcon = { Icon(Icons.Default.Search, null) }, singleLine = true, shape = RoundedCornerShape(22.dp))
                Spacer(Modifier.height(14.dp))
                LazyRow(contentPadding = PaddingValues(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) { items(categories) { category -> FilterChip(selected = categories[selected] == category, onClick = { selected = categories.indexOf(category) }, label = { Text(category) }) } }
                Spacer(Modifier.height(10.dp))
                LazyVerticalGrid(columns = GridCells.Fixed(2), contentPadding = PaddingValues(16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp), verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxSize()) {
                    items(filtered) { tool -> ToolCard(tool) }
                }
            }
        }
    }
}

@Composable private fun ToolCard(tool: Tool) {
    Card(Modifier.fillMaxWidth().height(142.dp).clickable { }, shape = RoundedCornerShape(22.dp), colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = .84f)), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Box(Modifier.size(42.dp).background(Color(0xFFEAF0FF), RoundedCornerShape(14.dp)), contentAlignment = Alignment.Center) { Icon(tool.icon, null, tint = Color(0xFF4D73FF)) }
            Text(tool.name, fontWeight = FontWeight.SemiBold, fontSize = 17.sp)
            Text(tool.desc, color = Color.Gray, fontSize = 12.sp, maxLines = 1)
        }
    }
}
