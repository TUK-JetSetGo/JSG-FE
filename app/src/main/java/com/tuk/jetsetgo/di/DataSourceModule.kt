package com.tuk.jetsetgo.di

import com.tuk.jetsetgo.data.datasource.addTravel.AddTravelDataSource
import com.tuk.jetsetgo.data.datasourceImpl.addTravel.AddTravelDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Provides
    @Singleton
    fun provideAddTravelDataSource(addTravelDataSourceImpl: AddTravelDataSourceImpl): AddTravelDataSource =
        addTravelDataSourceImpl

}