package pl.inpost.recruitmenttask.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.inpost.recruitmenttask.data.source.local.CustomSharedPreferences
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class StoreDataModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(application: Application): CustomSharedPreferences {
        return CustomSharedPreferences(application)
    }
}