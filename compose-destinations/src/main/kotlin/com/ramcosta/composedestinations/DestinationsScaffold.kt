package com.ramcosta.composedestinations

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController


@Composable
fun DestinationsScaffold(
    destinations: Map<String, Destination>,
    startDestination: Destination,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    topBar: (@Composable (Destination) -> Unit) = {},
    bottomBar: @Composable (Destination) -> Unit = {},
    snackbarHost: @Composable (SnackbarHostState) -> Unit = { SnackbarHost(it) },
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    isFloatingActionButtonDocked: Boolean = false,
    drawerContent: @Composable (ColumnScope.() -> Unit)? = null,
    drawerGesturesEnabled: Boolean = true,
    drawerShape: Shape = MaterialTheme.shapes.large,
    drawerElevation: Dp = DrawerDefaults.Elevation,
    drawerBackgroundColor: Color = MaterialTheme.colors.surface,
    drawerContentColor: Color = contentColorFor(drawerBackgroundColor),
    drawerScrimColor: Color = DrawerDefaults.scrimColor,
    backgroundColor: Color = MaterialTheme.colors.background,
    contentColor: Color = contentColorFor(backgroundColor),
    modifierForPaddingValues: (Destination, PaddingValues) -> Modifier = { _, _ -> Modifier }
) {
    val currentBackStackEntryAsState by navController.currentBackStackEntryAsState()
    val route = currentBackStackEntryAsState?.destination?.route

    Scaffold(
        modifier,
        scaffoldState,
        route?.let { { topBar(destinations[route]!!) } } ?: {},
        route?.let { { bottomBar(destinations[route]!!) } } ?: {},
        snackbarHost,
        floatingActionButton,
        floatingActionButtonPosition,
        isFloatingActionButtonDocked,
        drawerContent,
        drawerGesturesEnabled,
        drawerShape,
        drawerElevation,
        drawerBackgroundColor,
        drawerContentColor,
        drawerScrimColor,
        backgroundColor,
        contentColor,
    ) { paddingValues ->
        DestinationsNavHost(
            modifier = route?.let { modifierForPaddingValues(destinations[route]!!, paddingValues) } ?: Modifier,
            destinations = destinations.values,
            navController = navController,
            startDestination = startDestination,
            scaffoldState = scaffoldState
        )
    }
}