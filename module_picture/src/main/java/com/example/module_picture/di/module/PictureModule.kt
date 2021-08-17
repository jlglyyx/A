package com.example.module_picture.di.module
import android.app.Application
import androidx.lifecycle.ViewModelStoreOwner
import com.example.lib_common.scope.ActivityScope
import com.example.lib_common.scope.ModelWithFactory
import com.example.lib_common.util.getViewModel
import com.example.module_picture.api.PictureApiService
import com.example.module_picture.di.factory.PictureViewModelFactory
import com.example.module_picture.repository.PictureRepository
import com.example.module_picture.viewmodel.PictureViewModel
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit


@Module
class PictureModule(private val viewModelStoreOwner: ViewModelStoreOwner) {


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

    @ActivityScope
    @Provides
    @ModelWithFactory
    fun provideModelWithFactory(
        pictureViewModelFactory: PictureViewModelFactory
    ): PictureViewModel =
        getViewModel(viewModelStoreOwner, pictureViewModelFactory, PictureViewModel::class.java)

//    @ActivityScope
//    @Provides
//    @ModelNoFactory
//    fun provideModelNoFactory(
//    ): PictureViewModel = getViewModel(viewModelStoreOwner,PictureViewModel::class.java)
}