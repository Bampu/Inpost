package pl.inpost.recruitmenttask.di

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import pl.inpost.recruitmenttask.data.source.local.SharedPreferencesManager
import pl.inpost.recruitmenttask.data.source.repository.ShipmentsRepository
import pl.inpost.recruitmenttask.ui.shipment.ShipmentListViewModel

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideYourViewModel(
        shipmentsRepository: ShipmentsRepository,
        sharedPreferencesManager: SharedPreferencesManager
    ): ViewModel {
        return ShipmentListViewModel(shipmentsRepository, sharedPreferencesManager)
    }
}