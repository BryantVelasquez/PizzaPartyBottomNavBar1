package edu.farmingdale.pizzapartybottomnavbar

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun NavigationGraph(navController: NavHostController, onBottomBarVisibilityChanged: (Boolean) -> Unit) {

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(navController, coroutineScope, drawerState)
        }
    ) {
        NavHost(navController, startDestination = BottomNavigationItems.Welcome.route) {
            composable(BottomNavigationItems.Welcome.route) {
                onBottomBarVisibilityChanged(false)
                SplashScreen(navController = navController)
            }
            composable(BottomNavigationItems.PizzaScreen.route) {
                onBottomBarVisibilityChanged(true)
                PizzaPartyScreen()
            }
            composable(BottomNavigationItems.GpaAppScreen.route) {
                onBottomBarVisibilityChanged(true)
                GpaAppScreen()
            }
            composable(BottomNavigationItems.Screen3.route) {
                onBottomBarVisibilityChanged(true)
                Screen3()
            }
        }
    }
}

@Composable
fun DrawerContent(
    navController: NavHostController,
    coroutineScope: CoroutineScope,
    drawerState: DrawerState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Navigation", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))


        NavigationItem(navController, "Welcome", BottomNavigationItems.Welcome.route, coroutineScope, drawerState)
        NavigationItem(navController, "Pizza Party", BottomNavigationItems.PizzaScreen.route, coroutineScope, drawerState)
        NavigationItem(navController, "GPA App", BottomNavigationItems.GpaAppScreen.route, coroutineScope, drawerState)
        NavigationItem(navController, "Screen 3", BottomNavigationItems.Screen3.route, coroutineScope, drawerState)
    }
}

@Composable
fun NavigationItem(
    navController: NavHostController,
    label: String,
    route: String,
    coroutineScope: CoroutineScope,
    drawerState: DrawerState
) {
    Button(onClick = {
        navController.navigate(route)
        coroutineScope.launch {
            drawerState.close()
        }
    }, modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = label)
    }
}
