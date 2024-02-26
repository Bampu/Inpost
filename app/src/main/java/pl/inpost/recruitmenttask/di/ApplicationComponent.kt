package pl.inpost.recruitmenttask.di

import dagger.Component

@Component(modules = [DaoModule::class, RepositoryModule::class, NetworkAndroidModule::class])
interface ApplicationComponent