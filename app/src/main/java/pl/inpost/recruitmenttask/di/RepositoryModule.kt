package pl.inpost.recruitmenttask.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.inpost.recruitmenttask.data.source.DefaultShipmentsRepository
import pl.inpost.recruitmenttask.data.source.local.LocalDataSource
import pl.inpost.recruitmenttask.data.source.remote.RemoteDataSource
import pl.inpost.recruitmenttask.data.source.repository.ShipmentsRepository
import pl.inpost.recruitmenttask.data.source.local.ShipmentsDao
import pl.inpost.recruitmenttask.data.source.local.ShipmentsDataLocalSource
import pl.inpost.recruitmenttask.data.source.remote.ShipmentApi
import pl.inpost.recruitmenttask.data.source.remote.ShipmentsRemoteDataSource

@Module
@InstallIn(SingletonComponent::class)

object RepositoryModule {

    @Provides
    fun localDataSource(shipmentsDao: ShipmentsDao): LocalDataSource {
        return ShipmentsDataLocalSource(shipmentsDao)
    }

    @Provides
    fun remoteDataSource(api: ShipmentApi): RemoteDataSource {
        return ShipmentsRemoteDataSource(api)
    }

    @Provides
    fun repository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): ShipmentsRepository {
        return DefaultShipmentsRepository(localDataSource, remoteDataSource)
    }
}