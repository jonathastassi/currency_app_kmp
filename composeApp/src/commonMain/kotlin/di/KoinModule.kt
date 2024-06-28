package di

import com.russhwolf.settings.Settings
import data.local.MongoImpl
import data.local.PreferencesRepositoryImpl
import data.remote.api.CurrencyApiServiceImpl
import domain.CurrencyApiService
import domain.MongoRepository
import domain.PreferencesRepository
import org.koin.core.context.startKoin
import org.koin.dsl.module
import presentation.screen.HomeViewModel

val appModule = module {
    single { Settings() }
    single<MongoRepository> { MongoImpl() }
    single<PreferencesRepository> { PreferencesRepositoryImpl(settings = get()) }
    single<CurrencyApiService> { CurrencyApiServiceImpl(preferencesRepository = get()) }
    factory {
        HomeViewModel(
            preferencesRepository = get(),
            api = get(),
            mongoDb = get()
        )
    }
}

fun initializeKoin() {
    startKoin {
        modules(appModule)
    }
}