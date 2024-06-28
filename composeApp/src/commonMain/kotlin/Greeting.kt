import com.russhwolf.settings.Settings
import data.local.PreferencesRepositoryImpl
import data.remote.api.CurrencyApiServiceImpl
import domain.model.Currency
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class Greeting {

    suspend fun greeting(): List<Currency> {
        val response = CurrencyApiServiceImpl(
            preferencesRepository = PreferencesRepositoryImpl(
                settings = Settings()
            )
        ).getLatestExchangeRates()
        return response.getSuccessData()
    }
}