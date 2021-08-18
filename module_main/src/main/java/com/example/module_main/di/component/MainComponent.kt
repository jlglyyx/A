package com.example.module_main.di.component

import com.example.lib_common.remote.di.component.RemoteComponent
import com.example.lib_common.scope.ActivityScope
import com.example.module_main.di.module.MainModule
import com.example.module_main.ui.main.activity.AddDynamicActivity
import com.example.module_main.ui.main.activity.DynamicDetailActivity
import com.example.module_main.ui.main.activity.MainActivity
import com.example.module_main.ui.main.fragment.MainFragment
import com.example.module_main.ui.menu.activity.MyPushActivity
import dagger.Component


@ActivityScope
@Component(modules = [MainModule::class] ,dependencies = [RemoteComponent::class])
interface MainComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(mainFragment: MainFragment)
    fun inject(myPushActivity: MyPushActivity)
    fun inject(addDynamicActivity: AddDynamicActivity)
    fun inject(dynamicDetailActivity: DynamicDetailActivity)
}