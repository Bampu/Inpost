package pl.inpost.recruitmenttask.data.source

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class NetworkAndroidModule {

    @Provides
    fun shipmentApi(@ApplicationContext context: Context, apiTypeAdapter: ApiTypeAdapter): ShipmentApi = MockShipmentApi(context, apiTypeAdapter)
}
