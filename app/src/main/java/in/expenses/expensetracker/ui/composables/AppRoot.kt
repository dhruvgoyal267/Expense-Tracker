package `in`.expenses.expensetracker.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import `in`.expenses.expensetracker.R
import `in`.expenses.expensetracker.model.NavScreens
import `in`.expenses.expensetracker.model.TransactionSelector
import `in`.expenses.expensetracker.ui.MainViewModel


@Composable
fun AppRoot(viewModel: MainViewModel) {
    val snackbarHostState = remember { SnackbarHostState() }
    val navController = rememberNavController()
    LaunchedEffect(viewModel) {
        println("Dhruv: Lauching")
        viewModel.toastMsg.collect { msg ->
            println("Dhruv: $msg")
            snackbarHostState.showSnackbar(msg)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .background(colorResource(id = R.color.app_bg))
        ) {
            AppNavGraph(
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun AppNavGraph(navController: NavHostController, viewModel: MainViewModel) {
    NavHost(
        navController = navController,
        startDestination = NavScreens.HOME.name
    ) {
        composable(NavScreens.HOME.name) {
            HomeScreenUi(viewModel = viewModel) { selector ->
                navController.navigate("${NavScreens.VIEW_ALL_TRANSACTION.name}/${selector.key}")
            }
        }
        composable("${NavScreens.VIEW_ALL_TRANSACTION.name}/{selectedOption}") { backStackEntry ->
            val selectedOption =
                backStackEntry.arguments?.getString("selectedOption")
            ViewAllTransactionsUI(
                viewModel = viewModel,
                selectedOption = TransactionSelector.getTransactionSelector(
                    selectedOption
                )
            ) {
                viewModel.stopListeningAllTransaction()
                navController.popBackStack()
            }
        }
    }
}