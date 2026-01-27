package wolf.north.parcelscannerapp.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import wolf.north.parcelscannerapp.navigation.Screen



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    title: String,
    currentRoute: String?,
    navController: NavHostController,
    showBars: Boolean = true,
    content: @Composable (PaddingValues) -> Unit
) {

    //Material theme val for color scheme
    val colorScheme = MaterialTheme.colorScheme

    Scaffold(
        topBar = {
            if (showBars) {
                TopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.SemiBold,
                                color = colorScheme.onSurface
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = colorScheme.surface,
                        titleContentColor = colorScheme.onSurface,
                    )
                )
            }
        },
        bottomBar = {
            if (showBars) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(horizontal = 24.dp, vertical = 12.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    NavigationBar(
                        modifier = Modifier
                            .height(64.dp)
                            .clip(RoundedCornerShape(32.dp)),
                        containerColor = colorScheme.surfaceContainer,
                        tonalElevation = 3.dp
                    ) {
                        // Home
                        NavigationBarItem(
                            selected = currentRoute == Screen.Home.route,
                            onClick = {
                                if (currentRoute != Screen.Home.route) {
                                    navController.navigate(Screen.Home.route) {
                                        popUpTo(Screen.Home.route) { inclusive = true }
                                    }
                                }
                            },
                            icon = {
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.Default.Home,
                                        contentDescription = "Home",
                                        modifier = Modifier.size(22.dp)
                                    )
                                    // Animated text near bottom bar icons
                                    AnimatedVisibility(
                                        visible = currentRoute == Screen.Home.route,
                                        enter = fadeIn(
                                            animationSpec = spring(
                                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                                stiffness = Spring.StiffnessLow
                                            )
                                        ) + expandHorizontally(
                                            animationSpec = spring(
                                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                                stiffness = Spring.StiffnessLow
                                            )
                                        ),
                                        exit = fadeOut() + shrinkHorizontally()
                                    ) {
                                        Row {
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text(
                                                "Home",
                                                style = MaterialTheme.typography.labelMedium,
                                                fontWeight = FontWeight.Medium
                                            )
                                        }
                                    }
                                }
                            },
                            label = null,
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = colorScheme.onPrimaryContainer,
                                selectedTextColor = colorScheme.onPrimaryContainer,
                                indicatorColor = colorScheme.primaryContainer,
                                unselectedIconColor = colorScheme.onSurfaceVariant
                            )
                        )

                        // History
                        NavigationBarItem(
                            selected = currentRoute == Screen.History.route,
                            onClick = {
                                if (currentRoute != Screen.History.route) {
                                    navController.navigate(Screen.History.route) {
                                        popUpTo(Screen.Home.route)
                                    }
                                }
                            },
                            icon = {
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.AutoMirrored.Filled.List,
                                        contentDescription = "History",
                                        modifier = Modifier.size(22.dp)
                                    )
                                    AnimatedVisibility(
                                        visible = currentRoute == Screen.History.route,
                                        enter = fadeIn(
                                            animationSpec = spring(
                                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                                stiffness = Spring.StiffnessLow
                                            )
                                        ) + expandHorizontally(
                                            animationSpec = spring(
                                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                                stiffness = Spring.StiffnessLow
                                            )
                                        ),
                                        exit = fadeOut() + shrinkHorizontally()
                                    ) {
                                        Row {
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text(
                                                "History",
                                                style = MaterialTheme.typography.labelMedium,
                                                fontWeight = FontWeight.Medium
                                            )
                                        }
                                    }
                                }
                            },
                            label = null,
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = colorScheme.onPrimaryContainer,
                                selectedTextColor = colorScheme.onPrimaryContainer,
                                indicatorColor = colorScheme.primaryContainer,
                                unselectedIconColor = colorScheme.onSurfaceVariant
                            )
                        )

                        // Profile
                        NavigationBarItem(
                            selected = currentRoute == Screen.Profile.route,
                            onClick = {
                                if (currentRoute != Screen.Profile.route) {
                                    navController.navigate(Screen.Profile.route) {
                                        popUpTo(Screen.Home.route)
                                    }
                                }
                            },
                            icon = {
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.Default.Person,
                                        contentDescription = "Profile",
                                        modifier = Modifier.size(22.dp)
                                    )
                                    AnimatedVisibility(
                                        visible = currentRoute == Screen.Profile.route,
                                        enter = fadeIn(
                                            animationSpec = spring(
                                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                                stiffness = Spring.StiffnessLow
                                            )
                                        ) + expandHorizontally(
                                            animationSpec = spring(
                                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                                stiffness = Spring.StiffnessLow
                                            )
                                        ),
                                        exit = fadeOut() + shrinkHorizontally()
                                    ) {
                                        Row {
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text(
                                                "Profile",
                                                style = MaterialTheme.typography.labelMedium,
                                                fontWeight = FontWeight.Medium
                                            )
                                        }
                                    }
                                }
                            },
                            label = null,
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = colorScheme.onPrimaryContainer,
                                selectedTextColor = colorScheme.onPrimaryContainer,
                                indicatorColor = colorScheme.primaryContainer,
                                unselectedIconColor = colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }
            }
        }

    ) { paddingValues ->
        content(paddingValues)
    }
}

