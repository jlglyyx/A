package com.example.module_main.di.component

import com.example.lib_common.base.di.component.BaseComponent
import com.example.lib_common.scope.ActivityScope
import com.example.module_main.di.module.MainModule
import com.example.module_main.ui.activity.MainActivity
import com.example.module_main.ui.fragment.MainFragment
import dagger.Component


@ActivityScope
@Component(modules = [MainModule::class] ,dependencies = [BaseComponent::class])
interface MainComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(mainFragment: MainFragment)
}