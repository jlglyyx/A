package com.example.module_picture.di.module
import android.app.Application
import com.example.lib_common.scope.ActivityScope
import com.example.module_picture.api.PictureApiService
import com.example.module_picture.di.factory.PictureViewModelFactory
import com.example.module_picture.repository.PictureRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit


@Module
class PictureModule {


    @ActivityScope
    @Provides
    fun providePictureApi(retrofit: Retrofit): PictureApiService = retrofit.create(
        PictureApiService::class.java)


    @ActivityScope
    @Provides
    fun providePictureRepository(mainApiService: PictureApiService): PictureRepository =
        PictureRepository(mainApiService)

    @ActivityScope
    @Provides
    fun providePictureViewModelFactory(
        application: Application,
        pictureRepository: PictureRepository
    ): PictureViewModelFactory =
        PictureViewModelFactory(
            application,
            pictureRepository
        )


}