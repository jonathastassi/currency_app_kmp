import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import di.initializeKoin
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.component.screen.HomeScreen

@Composable
@Preview
fun App() {
    initializeKoin()

    MaterialTheme {
        Navigator(HomeScreen())
    }
}
