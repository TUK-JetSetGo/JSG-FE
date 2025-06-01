package com.tuk.jetsetgo.di

import android.app.Application
import android.content.Context
import com.tuk.jetsetgo.data.repositoryImpl.TestRepositoryImpl
import com.tuk.jetsetgo.data.repositoryImpl.addTravel.AddTravelRepositoryImpl
import com.tuk.jetsetgo.data.repositoryImpl.login.LoginRepositoryImpl
import com.tuk.jetsetgo.data.repositoryImpl.myTravel.MyTravelRepositoryImpl
import com.tuk.jetsetgo.data.repositoryImpl.myTravel.OsrmRepositoryImpl
import com.tuk.jetsetgo.data.repositoryImpl.mypage.MypageRepositoryImpl
import com.tuk.jetsetgo.data.service.TestService
import com.tuk.jetsetgo.data.service.addTravel.AddTravelService
import com.tuk.jetsetgo.domain.repository.TestRepository
import com.tuk.jetsetgo.domain.repository.addTravel.AddTravelRepository
import com.tuk.jetsetgo.domain.repository.login.LoginRepository
import com.tuk.jetsetgo.domain.repository.myTravel.MyTravelRepository
import com.tuk.jetsetgo.domain.repository.myTravel.OsrmRepository
import com.tuk.jetsetgo.domain.repository.mypage.MypageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideApplicationContext(application: Application): Context = application

    // 스코프 애노테이션이 있음
    // 해당하는 Hilt 컴포넌트의 수명동안 매 요청에 동일 인스턴스를 반환
    // 다음의 경우 viewModel의 수명동안 동일 인스턴스를 반환
    @Singleton
    @Provides
    fun providesTestRepository(
        testService: TestService
    ): TestRepository = TestRepositoryImpl(testService)

    @Singleton
    @Provides
    fun providesAddTravelRepository(
        addTravelRepositoryImpl: AddTravelRepositoryImpl
    ): AddTravelRepository = addTravelRepositoryImpl

    @Singleton
    @Provides
    fun providesLoginRepository(
        loginRepositoryImpl: LoginRepositoryImpl
    ): LoginRepository = loginRepositoryImpl

    @Singleton
    @Provides
    fun providesMyTravelRepository(
        myTravelRepositoryImpl: MyTravelRepositoryImpl
    ): MyTravelRepository = myTravelRepositoryImpl

    @Singleton
    @Provides
    fun providesMypageRepository(
        mypageRepositoryImpl: MypageRepositoryImpl
    ): MypageRepository = mypageRepositoryImpl

    @Singleton
    @Provides
    fun providesOsrmRepository(
        osrmRepositoryImpl: OsrmRepositoryImpl
    ): OsrmRepository = osrmRepositoryImpl
}
