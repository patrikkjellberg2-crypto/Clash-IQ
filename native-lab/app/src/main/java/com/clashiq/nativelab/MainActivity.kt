package com.clashiq.nativelab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.EventNote
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import kotlinx.coroutines.launch

private val Ink = Color(0xFF050608)
private val Panel = Color(0xFF10141B)
private val Gold = Color(0xFFFFC43D)
private val GoldSoft = Color(0xFFFFE7A3)
private val Muted = Color(0xFFB1B6C1)
private val Border = Color(0xFF2B313B)

private const val GOOGLE_WEB_CLIENT_ID = ""
private const val GOOGLE_TOKEN_TYPE =
    "com.google.android.libraries.identity.googleid.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { ClashIQNativeApp() }
    }
}

@Composable
fun ClashIQNativeApp() {
    var signedIn by remember { mutableStateOf(false) }

    MaterialTheme(
        colorScheme = androidx.compose.material3.darkColorScheme(
            background = Ink,
            surface = Panel,
            primary = Gold,
            onBackground = Color.White,
            onSurface = Color.White
        )
    ) {
        if (signedIn) {
            DashboardScreen()
        } else {
            WelcomeScreen(onSignedIn = { signedIn = true })
        }
    }
}

@Composable
private fun WelcomeScreen(onSignedIn: () -> Unit) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val scope = rememberCoroutineScope()
    val credentialManager = remember { CredentialManager.create(context) }
    var showIntroContent by remember { mutableStateOf(false) }
    var isSigningIn by remember { mutableStateOf(false) }
    var loginMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(350)
        showIntroContent = true
    }

    val introScale by animateFloatAsState(
        targetValue = if (showIntroContent) 1f else 0.86f,
        animationSpec = tween(1100, easing = FastOutSlowInEasing),
        label = "introScale"
    )
    val introAlpha by animateFloatAsState(
        targetValue = if (showIntroContent) 1f else 0f,
        animationSpec = tween(900),
        label = "introAlpha"
    )

    val infinite = rememberInfiniteTransition(label = "heroMotion")
    val bgScale by infinite.animateFloat(
        initialValue = 1.02f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(9000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bgScale"
    )
    val glowPulse by infinite.animateFloat(
        initialValue = 0.42f,
        targetValue = 0.72f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glowPulse"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Ink)
    ) {
        Image(
            painter = painterResource(id = R.drawable.clash_iq_barbarian),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    scaleX = bgScale
                    scaleY = bgScale
                },
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.Black.copy(alpha = 0.18f),
                            Color.Black.copy(alpha = 0.48f),
                            Ink.copy(alpha = 0.98f)
                        )
                    )
                )
        )

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .alpha(glowPulse)
        ) {
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Gold.copy(alpha = 0.32f), Color.Transparent)
                ),
                radius = size.minDimension * 0.46f,
                center = Offset(size.width / 2f, size.height * 0.30f)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp)
                .padding(
                    top = 34.dp,
                    bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() + 26.dp
                )
                .alpha(introAlpha)
                .scale(introScale),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "NATIVE TEST LAB",
                    color = Gold,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 3.sp
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "COMMAND CENTER",
                    color = Color.White.copy(alpha = 0.82f),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 2.sp
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(170.dp)
                        .shadow(28.dp, RoundedCornerShape(42.dp))
                        .background(
                            brush = Brush.radialGradient(
                                listOf(
                                    Color.White.copy(alpha = 0.13f),
                                    Gold.copy(alpha = 0.08f),
                                    Color.Transparent
                                )
                            ),
                            shape = RoundedCornerShape(42.dp)
                        )
                        .padding(14.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.clash_iq_icon),
                        contentDescription = "Clash IQ",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.clash_iq_logo),
                    contentDescription = "Clash IQ",
                    modifier = Modifier
                        .fillMaxWidth(0.70f)
                        .height(46.dp),
                    contentScale = ContentScale.Fit
                )

                Text(
                    text = "Your clan. Your strategy. One view.",
                    color = Color.White.copy(alpha = 0.88f),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                AnimatedVisibility(
                    visible = showIntroContent,
                    enter = fadeIn(tween(800)) + scaleIn(tween(800), initialScale = 0.96f)
                ) {
                    Button(
                        onClick = {
                            loginMessage = null
                            if (GOOGLE_WEB_CLIENT_ID.isBlank()) {
                                loginMessage = "Google-knappen är ansluten. Web Client ID behöver läggas in för riktig inloggning."
                                return@Button
                            }

                            scope.launch {
                                isSigningIn = true
                                try {
                                    val googleOption = GetGoogleIdOption.Builder()
                                        .setServerClientId(GOOGLE_WEB_CLIENT_ID)
                                        .setFilterByAuthorizedAccounts(false)
                                        .setAutoSelectEnabled(false)
                                        .build()

                                    val request = GetCredentialRequest.Builder()
                                        .addCredentialOption(googleOption)
                                        .build()

                                    val result = credentialManager.getCredential(
                                        context = context,
                                        request = request
                                    )
                                    val credential = result.credential

                                    if (
                                        credential is CustomCredential &&
                                        credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
                                    ) {
                                        GoogleIdTokenCredential.createFrom(credential.data)
                                        onSignedIn()
                                    } else if (credential.type == GOOGLE_TOKEN_TYPE) {
                                        onSignedIn()
                                    } else {
                                        loginMessage = "Google-inloggningen kunde inte verifieras."
                                    }
                                } catch (error: Exception) {
                                    loginMessage = error.message ?: "Google-inloggningen avbröts."
                                } finally {
                                    isSigningIn = false
                                }
                            }
                        },
                        enabled = !isSigningIn,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(58.dp),
                        shape = RoundedCornerShape(18.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color(0xFF161616)
                        )
                    ) {
                        if (isSigningIn) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(22.dp),
                                color = Color(0xFF161616),
                                strokeWidth = 2.5.dp
                            )
                        } else {
                            Text(
                                text = "Fortsätt med Google",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Text(
                    text = loginMessage ?: "Säker inloggning • Native Android",
                    color = loginMessage?.let { Color(0xFFFFD1D1) } ?: Color.White.copy(alpha = 0.65f),
                    fontSize = 11.sp,
                    lineHeight = 16.sp
                )
            }
        }
    }
}

@Composable
private fun DashboardScreen() {
    var selected by remember { mutableStateOf("Overview") }

    Scaffold(
        containerColor = Ink,
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFF0B0E13),
                contentColor = Gold
            ) {
                val items = listOf("Overview", "War", "Planner", "AI Coach", "Profile")
                val icons = listOf(
                    Icons.Default.Dashboard,
                    Icons.Default.Shield,
                    Icons.Default.EventNote,
                    Icons.Default.AutoAwesome,
                    Icons.Default.Person
                )
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selected == item,
                        onClick = { selected = item },
                        icon = { Icon(icons[index], contentDescription = item) },
                        label = { Text(item, fontSize = 10.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Gold,
                            selectedTextColor = Gold,
                            indicatorColor = Color(0xFF332911),
                            unselectedIconColor = Muted,
                            unselectedTextColor = Muted
                        )
                    )
                }
            }
        }
    ) { padding ->
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 18.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 22.dp, bottom = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            "CLASH IQ / COMMAND CENTER",
                            color = Gold,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.8.sp
                        )
                        Text(
                            selected,
                            color = Color.White,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "Your clan. Your strategy. One view.",
                            color = Muted,
                            fontSize = 12.sp
                        )
                    }
                    IconButton(onClick = {}) {
                        Icon(
                            Icons.Default.NotificationsNone,
                            contentDescription = "Notifications",
                            tint = Color.White
                        )
                    }
                }
            }

            item {
                Surface(
                    color = Panel,
                    shape = RoundedCornerShape(20.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Border),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Column(
                                Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    "CLAN OVERVIEW",
                                    color = Muted,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.6.sp
                                )
                                Text(
                                    "BHABE DHEMONS",
                                    color = Color.White,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    "Level 24 • #2Q0Q82C9R",
                                    color = Muted,
                                    fontSize = 12.sp
                                )
                            }
                            Surface(
                                color = Color(0xFF30250D),
                                shape = RoundedCornerShape(14.dp)
                            ) {
                                Icon(
                                    Icons.Default.Groups,
                                    contentDescription = null,
                                    tint = Gold,
                                    modifier = Modifier
                                        .padding(14.dp)
                                        .size(28.dp)
                                )
                            }
                        }
                        androidx.compose.material3.HorizontalDivider(color = Border)
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Metric("WAR WINS", "128")
                            Metric("WIN RATE", "72%")
                            Metric("MEMBERS", "48/50")
                        }
                    }
                }
            }

            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "War Center",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        "VIEW ALL →",
                        color = Gold,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }
                Surface(
                    color = Panel,
                    shape = RoundedCornerShape(18.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Border),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = Color(0xFF17271D),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(
                                Icons.Default.Shield,
                                contentDescription = null,
                                tint = Color(0xFF80C58A),
                                modifier = Modifier
                                    .padding(12.dp)
                                    .size(24.dp)
                            )
                        }
                        Column(
                            Modifier
                                .weight(1f)
                                .padding(start = 12.dp),
                            verticalArrangement = Arrangement.spacedBy(3.dp)
                        ) {
                            Text(
                                "No active war",
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                "Your next battle starts here",
                                color = Muted,
                                fontSize = 12.sp
                            )
                        }
                        Icon(
                            Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = Muted
                        )
                    }
                }
            }

            item {
                Text(
                    "Quick access",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    QuickCard(
                        "War Planner",
                        Icons.Default.CalendarMonth,
                        Modifier.weight(1f)
                    ) { selected = "Planner" }
                    QuickCard(
                        "AI Coach",
                        Icons.Default.AutoAwesome,
                        Modifier.weight(1f)
                    ) { selected = "AI Coach" }
                }
            }

            item { Spacer(Modifier.height(8.dp)) }
        }
    }
}

@Composable
private fun Metric(label: String, value: String) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            label,
            color = Muted,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.8.sp
        )
        Text(
            value,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun QuickCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        color = Panel,
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Border),
        modifier = modifier
    ) {
        Column(
            Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = Gold,
                modifier = Modifier.size(23.dp)
            )
            Text(
                title,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp
            )
        }
    }
}
