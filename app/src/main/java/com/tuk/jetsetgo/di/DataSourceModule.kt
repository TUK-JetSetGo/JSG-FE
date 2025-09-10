package com.tuk.jetsetgo.di

import com.tuk.jetsetgo.data.datasource.addTravel.AddTravelDataSource
import com.tuk.jetsetgo.data.datasource.login.LoginDataSource
import com.tuk.jetsetgo.data.datasource.myTravel.MyTravelDataSource
import com.tuk.jetsetgo.data.datasource.myTravel.OdsayDataSource
import com.tuk.jetsetgo.data.datasource.myTravel.OsrmDataSource
import com.tuk.jetsetgo.data.datasource.mypage.MypageDataSource
import com.tuk.jetsetgo.data.datasource.review.ReviewDataSource
import com.tuk.jetsetgo.data.datasourceImpl.addTravel.AddTravelDataSourceImpl
import com.tuk.jetsetgo.data.datasourceImpl.login.LoginDataSourceImpl
import com.tuk.jetsetgo.data.datasourceImpl.myTravel.MyTravelDataSourceImpl
import com.tuk.jetsetgo.data.datasourceImpl.myTravel.OdsayDataSourceImpl
import com.tuk.jetsetgo.data.datasourceImpl.myTravel.OsrmDataSourceImpl
import com.tuk.jetsetgo.data.datasourceImpl.mypage.MypageDataSourceImpl
import com.tuk.jetsetgo.data.datasourceImpl.review.ReviewDataSourceImpl
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

    @Provides
    @Singleton
    fun provideLoginDataSource(loginDataSourceImpl: LoginDataSourceImpl): LoginDataSource =
        loginDataSourceImpl

    @Provides
    @Singleton
    fun provideMyTravelDataSource(myTravelDataSourceImpl: MyTravelDataSourceImpl): MyTravelDataSource =
        myTravelDataSourceImpl

    @Provides
    @Singleton
    fun provideMypageDataSource(mypageDataSourceImpl: MypageDataSourceImpl): MypageDataSource =
        mypageDataSourceImpl

    @Provides
    @Singleton
    fun provideReviewDataSource(reviewDataSourceImpl: ReviewDataSourceImpl): ReviewDataSource =
        reviewDataSourceImpl

    @Provides
    @Singleton
    fun provideOsrmDataSource(osrmDataSourceImpl: OsrmDataSourceImpl): OsrmDataSource =
        osrmDataSourceImpl

    @Provides
    @Singleton
    fun provideOdsayDataSource(odsayDataSourceImpl: OdsayDataSourceImpl): OdsayDataSource =
        odsayDataSourceImpl

}