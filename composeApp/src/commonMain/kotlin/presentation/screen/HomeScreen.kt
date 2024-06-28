package presentation.component.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import presentation.component.HomeReader
import presentation.screen.HomeUiEvent
import presentation.screen.HomeViewModel
import ui.theme.surfaceColor

class HomeScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel = koinScreenModel<HomeViewModel>()
        val rateStatus by viewModel.rateStatus
        val targetCurrency by viewModel.targetCurrency
        val sourceCurrency by viewModel.sourceCurrency

        var amount by rememberSaveable { mutableStateOf(0.0) }

        Column(
            modifier = Modifier.fillMaxSize().background(surfaceColor)
        ) {
            HomeReader(status = rateStatus,
                source = sourceCurrency,
                target = targetCurrency,
                amount = amount,
                onAmountChange = { amount = it },
                onSwitchClick = { viewModel.sendEvent(HomeUiEvent.SwitchCurrencies) },
                onRatesRefresh = {
                    viewModel.sendEvent(
                        HomeUiEvent.RefreshRates
                    )
                })
        }
    }
}