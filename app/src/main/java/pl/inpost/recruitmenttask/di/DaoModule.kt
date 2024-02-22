package pl.inpost.recruitmenttask.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pl.inpost.recruitmenttask.data.source.local.ShipmentDataBase
import pl.inpost.recruitmenttask.data.source.local.ShipmentsDao
import pl.inpost.recruitmenttask.utils.SHIPMENT_DB
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DaoModule {
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): ShipmentDataBase {
        return Room.databaseBuilder(
            context,
            ShipmentDataBase::class.java,
            SHIPMENT_DB
        ).build()
    }

    @Provides
    @Singleton
    fun provideYourDao(appDatabase: ShipmentDataBase): ShipmentsDao {
        return appDatabase.shipmentDao()
    }
}