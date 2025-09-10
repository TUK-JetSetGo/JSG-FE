package com.tuk.jetsetgo.di

import com.tuk.jetsetgo.data.service.TestService
import com.tuk.jetsetgo.data.service.addTravel.AddTravelService
import com.tuk.jetsetgo.data.service.login.LoginService
import com.tuk.jetsetgo.data.service.myTravel.MyTravelService
import com.tuk.jetsetgo.data.service.myTravel.OdsayService
import com.tuk.jetsetgo.data.service.myTravel.OsrmService
import com.tuk.jetsetgo.data.service.mypage.MypageService
import com.tuk.jetsetgo.data.service.review.ReviewService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    private inline fun <reified T> Retrofit.buildService(): T {
        return this.create(T::class.java)
    }

    @Provides
    @Singleton
    fun provideTestService(retrofit: Retrofit): TestService {
        return retrofit.buildService()
    }

    @Provides
    @Singleton
    fun provideAddTravelService(retrofit: Retrofit): AddTravelService {
        return retrofit.buildService()
    }

    @Provides
    @Singleton
    fun provideLoginService(retrofit: Retrofit): LoginService {
        return retrofit.buildService()
    }

    @Provides
    @Singleton
    fun provideMyTravelService(retrofit: Retrofit): MyTravelService {
        return retrofit.buildService()
    }

    @Provides
    @Singleton
    fun provideMypageService(retrofit: Retrofit): MypageService {
        return retrofit.buildService()
    }

    @Provides
    @Singleton
    fun provideReviewService(retrofit: Retrofit): ReviewService {
        return retrofit.buildService()
    }

    @Provides
    @Singleton
    fun provideOsrmService(
        @Named("osrm") retrofit: Retrofit): OsrmService {
        return retrofit.create(OsrmService::class.java)
    }

    @Provides
    @Singleton
    fun provideOdsayService(
        @Named("odsay") retrofit: Retrofit): OdsayService {
        return retrofit.create(OdsayService::class.java)
    }


}