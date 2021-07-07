package com.example.module_video.di.module
import android.app.Application
import com.example.lib_common.scope.ActivityScope
import com.example.module_video.api.VideoApiService
import com.example.module_video.di.factory.VideoViewModelFactory
import com.example.module_video.repository.VideoRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit


@Module
class VideoModule {


    @ActivityScope
    @Provides
    fun provideVideoApi(retrofit: Retrofit): VideoApiService = retrofit.create(
        VideoApiService::class.java)


    @ActivityScope
    @Provides
    fun provideVideoRepository(mainApiService: VideoApiService): VideoRepository =
        VideoRepository(mainApiService)

    @ActivityScope
    @Provides
    fun provideVideoViewModelFactory(
        application: Application,
        videoRepository: VideoRepository
    ): VideoViewModelFactory =
        VideoViewModelFactory(
            application,
            videoRepository
        )


}