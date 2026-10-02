package com.clashiq.nativelab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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

// Colors aligned with the current Clash IQ dark/gold overview theme.
private val Ink = Color(0xFF07090D)
private val Panel = Color(0xFF11151C)
private val Gold = Color(0xFFFBBF24)
private val Muted = Color(0xFF9299A5)
private val Border = Color(0xFF252B34)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { ClashIQNativeApp() }
    }
}

@Composable
fun ClashIQNativeApp() {
    var selected by remember { mutableStateOf("Overview") }
    MaterialTheme(colorScheme = darkColorScheme(
        background = Ink, surface = Panel, primary = Gold,
        onBackground = Color.White, onSurface = Color.White,
        secondary = Color(0xFF27313E)
    )) {
        Scaffold(
            containerColor = Ink,
            bottomBar = {
                NavigationBar(containerColor = Color(0xFF0B0E13), contentColor = Gold) {
                    val items = listOf("Overview", "War", "Planner", "AI Coach", "Profile")
                    val icons = listOf(Icons.Default.Dashboard, Icons.Default.Shield, Icons.Default.EventNote, Icons.Default.AutoAwesome, Icons.Default.Person)
                    items.forEachIndexed { i, item ->
                        NavigationBarItem(
                            selected = selected == item,
                            onClick = { selected = item },
                            icon = { Icon(icons[i], contentDescription = item) },
                            label = { Text(item, fontSize = 10.sp) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Gold, selectedTextColor = Gold,
                                indicatorColor = Color(0xFF332911),
                                unselectedIconColor = Muted, unselectedTextColor = Muted
                            )
                        )
                    }
                }
            }
        ) { padding ->
            LazyColumn(
                Modifier.fillMaxSize().padding(padding).padding(horizontal = 18.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Row(
                        Modifier.fillMaxWidth().padding(top = 22.dp, bottom = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text("CLASH IQ  /  COMMAND CENTER", color = Gold, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.8.sp)
                            Text(selected, color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                            Text("Your clan. Your strategy. One view.", color = Muted, fontSize = 12.sp)
                        }
                        IconButton(onClick = {}) { Icon(Icons.Default.NotificationsNone, contentDescription = "Notifications", tint = Color.White) }
                    }
                }
                item {
                    Surface(
                        color = Panel, shape = RoundedCornerShape(20.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Border),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text("CLAN OVERVIEW", color = Muted, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.6.sp)
                                    Text("BHABE DHEMONS", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                                    Text("Level 24  •  #2Q0Q82C9R", color = Muted, fontSize = 12.sp)
                                }
                                Surface(color = Color(0xFF30250D), shape = RoundedCornerShape(14.dp)) {
                                    Icon(Icons.Default.Groups, contentDescription = null, tint = Gold, modifier = Modifier.padding(14.dp).size(28.dp))
                                }
                            }
                            HorizontalDivider(color = Border)
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Metric("WAR WINS", "128")
                                Metric("WIN RATE", "72%")
                                Metric("MEMBERS", "48/50")
                            }
                        }
                    }
                }
                item {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("War Center", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                        Text("VIEW ALL  →", color = Gold, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                    }
                    Surface(
                        color = Panel, shape = RoundedCornerShape(18.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Border),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            Surface(color = Color(0xFF17271D), shape = RoundedCornerShape(12.dp)) {
                                Icon(Icons.Default.Shield, contentDescription = null, tint = Color(0xFF80C58A), modifier = Modifier.padding(12.dp).size(24.dp))
                            }
                            Column(Modifier.weight(1f).padding(start = 12.dp), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                                Text("No active war", color = Color.White, fontWeight = FontWeight.SemiBold)
                                Text("Your next battle starts here", color = Muted, fontSize = 12.sp)
                            }
                            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Muted)
                        }
                    }
                }
                item {
                    Text("Quick access", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        QuickCard("War Planner", Icons.Default.CalendarMonth, Modifier.weight(1f)) { selected = "Planner" }
                        QuickCard("AI Coach", Icons.Default.AutoAwesome, Modifier.weight(1f)) { selected = "AI Coach" }
                    }
                }
                item { Spacer(Modifier.height(8.dp)) }
            }
        }
    }
}

@Composable
private fun Metric(label: String, value: String) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(label, color = Muted, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 0.8.sp)
        Text(value, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun QuickCard(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector, modifier: Modifier, onClick: () -> Unit) {
    Surface(
        onClick = onClick, color = Panel, shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Border), modifier = modifier
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            Icon(icon, contentDescription = null, tint = Gold, modifier = Modifier.size(23.dp))
            Text(title, color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF07090D)
@Composable
fun ClashIQNativePreview() {
    ClashIQNativeApp()
}
