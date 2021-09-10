package com.yang.module_mine.di.component

import com.yang.lib_common.remote.di.component.RemoteComponent
import com.yang.lib_common.scope.ActivityScope
import com.yang.module_mine.di.module.MineModule
import com.yang.module_mine.ui.activity.*
import com.yang.module_mine.ui.fragment.MineFragment
import dagger.Component


@ActivityScope
@Component(modules = [MineModule::class] ,dependencies = [RemoteComponent::class])
interface MineComponent {
    fun inject(changeUserInfoActivity: ChangeUserInfoActivity)
    fun inject(otherPersonInfoActivity: OtherPersonInfoActivity)
    fun inject(viewHistoryActivity: ViewHistoryActivity)
    fun inject(mineObtainActivity: MineObtainActivity)
    fun inject(mineSignActivity: MineSignActivity)
    fun inject(mineExtensionActivity: MineExtensionActivity)
    fun inject(mineLimitTimeExtensionActivity: MineLimitTimeExtensionActivity)

    fun inject(mineFragment: MineFragment)
}