package com.clashiq.nativelab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

private val Ink = Color(0xFF101114)
private val Panel = Color(0xFF1B1D22)
private val Gold = Color(0xFFE5B84B)
private val Muted = Color(0xFF9A9DA5)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { ClashIQNativeApp() }
    }
}

@Composable
fun ClashIQNativeApp() {
    var selected by remember { mutableStateOf("Home") }
    MaterialTheme(colorScheme = darkColorScheme(background = Ink, surface = Panel, primary = Gold)) {
        Scaffold(
            containerColor = Ink,
            bottomBar = {
                NavigationBar(containerColor = Panel, contentColor = Gold) {
                    val items = listOf("Home", "War", "Planner", "Coach", "Profile")
                    val icons = listOf(Icons.Default.Home, Icons.Default.Shield, Icons.Default.EventNote, Icons.Default.AutoAwesome, Icons.Default.Person)
                    items.forEachIndexed { i, item ->
                        NavigationBarItem(
                            selected = selected == item,
                            onClick = { selected = item },
                            icon = { Icon(icons[i], contentDescription = item) },
                            label = { Text(item, fontSize = 10.sp) },
                            colors = NavigationBarItemDefaults.colors(selectedIconColor = Gold, selectedTextColor = Gold, indicatorColor = Color(0xFF393126), unselectedIconColor = Muted, unselectedTextColor = Muted)
                        )
                    }
                }
            }
        ) { padding ->
            LazyColumn(Modifier.fillMaxSize().padding(padding).padding(horizontal = 18.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                item {
                    Row(Modifier.fillMaxWidth().padding(top = 20.dp, bottom = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                        Column(Modifier.weight(1f)) {
                            Text("CLASH IQ", color = Gold, fontSize = 12.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
                            Text(selected, color = Color.White, fontSize = 27.sp, fontWeight = FontWeight.Bold)
                        }
                        IconButton(onClick = {}) { Icon(Icons.Default.NotificationsNone, contentDescription = "Notifications", tint = Color.White) }
                    }
                }
                item {
                    Surface(color = Panel, shape = RoundedCornerShape(22.dp), modifier = Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Column(Modifier.weight(1f)) {
                                    Text("CLAN OVERVIEW", color = Muted, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.4.sp)
                                    Text("BHABE DHEMONS", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                                    Text("Level 24  •  #2Q0Q82C9R", color = Muted, fontSize = 12.sp)
                                }
                                Surface(color = Color(0xFF332B1C), shape = RoundedCornerShape(14.dp)) {
                                    Icon(Icons.Default.Groups, contentDescription = null, tint = Gold, modifier = Modifier.padding(14.dp).size(28.dp))
                                }
                            }
                            HorizontalDivider(color = Color(0xFF303239))
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Metric("WINS", "128")
                                Metric("WIN RATE", "72%")
                                Metric("MEMBERS", "48/50")
                            }
                        }
                    }
                }
                item {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("War Center", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                        Text("VIEW ALL", color = Gold, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                    }
                    Spacer(Modifier.height(10.dp))
                    Surface(color = Panel, shape = RoundedCornerShape(18.dp), modifier = Modifier.fillMaxWidth()) {
                        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            Surface(color = Color(0xFF28352C), shape = RoundedCornerShape(12.dp)) {
                                Icon(Icons.Default.Shield, contentDescription = null, tint = Color(0xFF80C58A), modifier = Modifier.padding(12.dp).size(24.dp))
                            }
                            Column(Modifier.weight(1f).padding(start = 12.dp)) {
                                Text("No active war", color = Color.White, fontWeight = FontWeight.SemiBold)
                                Text("Your next battle starts here", color = Muted, fontSize = 12.sp)
                            }
                            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Muted)
                        }
                    }
                }
                item {
                    Text("Quick access", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(10.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        QuickCard("War Planner", Icons.Default.CalendarMonth, Modifier.weight(1f)) { selected = "Planner" }
                        QuickCard("AI Coach", Icons.Default.AutoAwesome, Modifier.weight(1f)) { selected = "Coach" }
                    }
                }
                item { Spacer(Modifier.height(8.dp)) }
            }
        }
    }
}

@Composable
private fun Metric(label: String, value: String) {
    Column(horizontalAlignment = Alignment.Start) {
        Text(label, color = Muted, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 0.8.sp)
        Text(value, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun QuickCard(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector, modifier: Modifier, onClick: () -> Unit) {
    Surface(onClick = onClick, color = Panel, shape = RoundedCornerShape(16.dp), modifier = modifier) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            Icon(icon, contentDescription = null, tint = Gold, modifier = Modifier.size(23.dp))
            Text(title, color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFF101114)
@Composable
fun ClashIQNativePreview() {
    ClashIQNativeApp()
}
