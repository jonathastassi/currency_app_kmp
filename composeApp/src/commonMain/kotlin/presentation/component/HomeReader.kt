package presentation.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import currencyapp.composeapp.generated.resources.Res
import currencyapp.composeapp.generated.resources.exchange_illustration
import currencyapp.composeapp.generated.resources.refresh_ic
import currencyapp.composeapp.generated.resources.switch_ic
import domain.model.Currency
import domain.model.CurrencyCode
import domain.model.RateStatus
import domain.model.RequestState
import org.jetbrains.compose.resources.painterResource
import ui.theme.headerColor
import ui.theme.staleColor
import util.displayCurrentDateTime

@Composable
fun HomeReader(
    status: RateStatus,
    source: RequestState<Currency>,
    target: RequestState<Currency>,
    amount: Double,
    onAmountChange: (Double) -> Unit,
    onRatesRefresh: () -> Unit,
    onSwitchClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp))
            .background(headerColor)
            .padding(all = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        RateStatus(
            status = status,
            onRatesRefresh = onRatesRefresh
        )
        Spacer(modifier = Modifier.height(24.dp))
        CurrencyInputs(
            source = source,
            target = target,
            onSwitchClick = onSwitchClick
        )
        Spacer(modifier = Modifier.height(24.dp))
        AmountInput(
            amount = amount,
            onAmountChange = onAmountChange
        )
    }
}

@Composable
fun RateStatus(
    status: RateStatus,
    onRatesRefresh: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Image(
                modifier = Modifier.size(50.dp),
                painter = painterResource(Res.drawable.exchange_illustration),
                contentDescription = "Exchange Rate Illustration"
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = displayCurrentDateTime(),
                    color = Color.White
                )
                Text(
                    text = status.title,
                    fontSize = MaterialTheme.typography.body2.fontSize,
                    color = status.color
                )
            }
        }
        if (status == RateStatus.Stale) {
            IconButton(
                onClick = onRatesRefresh
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(Res.drawable.refresh_ic),
                    contentDescription = "Refresh Icon",
                    tint = staleColor
                )
            }
        }

    }
}

@Composable
fun CurrencyInputs(
    source: RequestState<Currency>,
    target: RequestState<Currency>,
    onSwitchClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CurrencyView(
            placeholder = "from",
            currency = source,
            onClick = {}
        )
        Spacer(Modifier.height(14.dp))
        IconButton(
            modifier = Modifier.padding(top = 24.dp),
            onClick = onSwitchClick
        ) {
            Icon(
                painter = painterResource(Res.drawable.switch_ic),
                contentDescription = "Switch Icon",
                tint = Color.White
            )
        }
        Spacer(Modifier.height(14.dp))
        CurrencyView(
            placeholder = "to",
            currency = target,
            onClick = {}
        )
    }
}

@Composable
fun RowScope.CurrencyView(
    placeholder: String,
    currency: RequestState<Currency>,
    onClick: () -> Unit
) {
    Column(modifier = Modifier.weight(1f)) {
        Text(
            modifier = Modifier.padding(start = 12.dp),
            text = placeholder,
            fontSize = MaterialTheme.typography.body1.fontSize,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(size = 8.dp))
                .background(Color.White.copy(alpha = 0.05f))
                .height(54.dp)
                .clickable { onClick() },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (currency.isSuccess()) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(
                        CurrencyCode.valueOf(currency.getSuccessData().code).flag
                    ),
                    tint = Color.Unspecified,
                    contentDescription = "Country Flag"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = CurrencyCode.valueOf(currency.getSuccessData().code).name,
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.h1.fontSize,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun AmountInput(
    amount: Double,
    onAmountChange: (Double) -> Unit
) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(size = 8.dp))
            .animateContentSize()
            .height(54.dp),
        value = "$amount",
        onValueChange = { onAmountChange(it.toDouble()) },
//        colors = TextFieldDefaults.outlinedTextFieldColors(
//            focusedContainerColor = Color.White.copy(alpha = 0.05f),
//            unfocusedContainerColor = Color.White.copy(alpha = 0.05f),
//            disabledContainerColor = Color.White.copy(alpha = 0.05f),
//            errorContainerColor = Color.White.copy(alpha = 0.05f),
//            focusedIndicatorColor = Color.Transparent,
//            disabledIndicatorColor = Color.Transparent,
//            unfocusedIndicatorColor = Color.Transparent,
//            cursorColor = Color.White
//        ),
        textStyle = TextStyle(
            color = Color.White,
            fontSize = MaterialTheme.typography.h5.fontSize,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal
        )
    )
}
