package pl.inpost.recruitmenttask.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.inpost.recruitmenttask.data.source.remote.ShipmentApi
import pl.inpost.recruitmenttask.utils.RetrofitInstance
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkAndroidModule {

    @Provides
    @Singleton
    fun provideApiService(): ShipmentApi {
        return RetrofitInstance.apiService
    }
}
