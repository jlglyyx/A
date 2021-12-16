package com.yang.module_mine.di.component

import com.yang.lib_common.remote.di.component.RemoteComponent
import com.yang.lib_common.scope.ActivityScope
import com.yang.module_mine.di.module.MineModule
import com.yang.module_mine.ui.activity.*
import com.yang.module_mine.ui.fragment.MineFragment
import com.yang.module_mine.ui.obtain.activity.*
import com.yang.module_mine.ui.obtain.fragment.MineExchangeStatusFragment
import dagger.Component


@ActivityScope
@Component(modules = [MineModule::class] ,dependencies = [RemoteComponent::class])
interface MineComponent {
    fun inject(changeUserInfoActivity: ChangeUserInfoActivity)
    fun inject(otherPersonInfoActivity: OtherPersonInfoActivity)
    fun inject(viewHistoryActivity: ViewHistoryActivity)
    fun inject(mineObtainActivity: MineObtainActivity)
    fun inject(mineObtainExchangeActivity: MineObtainExchangeActivity)
    fun inject(mineExchangeActivity: MineExchangeActivity)
    fun inject(mineExchangeGoodsDetailActivity: MineExchangeGoodsDetailActivity)
    fun inject(mineSignActivity: MineSignActivity)
    fun inject(mineExtensionActivity: MineExtensionActivity)
    fun inject(mineLimitTimeExtensionActivity: MineLimitTimeExtensionActivity)
    fun inject(mineAddAddressActivity: MineAddAddressActivity)
    fun inject(changePasswordActivity: ChangePasswordActivity)

    fun inject(mineFragment: MineFragment)
    fun inject(mineExchangeStatusFragment: MineExchangeStatusFragment)
}