package pl.inpost.recruitmenttask.di

import dagger.Component

@Component(modules = [DaoModule::class, RepositoryModule::class, NetworkAndroidModule::class, StoreDataModule::class])
interface ApplicationComponent