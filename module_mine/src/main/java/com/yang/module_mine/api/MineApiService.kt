package com.yang.module_mine.api

import com.yang.lib_common.api.BaseApiService
import com.yang.lib_common.data.UserInfoData
import com.yang.lib_common.remote.di.response.MResult
import com.yang.module_mine.data.MineTurnoverData
import com.yang.module_mine.data.ViewHistoryData
import okhttp3.RequestBody
import retrofit2.http.*

interface MineApiService : BaseApiService {

    @POST("user/login")
    suspend fun login(@Query("userAccount") userAccount:String, @Query("password") password:String): MResult<UserInfoData>

    @POST("user/register")
    suspend fun register(@Body userInfoData: UserInfoData): MResult<UserInfoData>

    @Multipart
    @POST("/uploadFile")
    suspend fun uploadFile(@PartMap file: MutableMap<String, RequestBody>): MResult<MutableList<String>>

    @POST("user/changePassword")
    suspend fun changePassword(@Query("password") password:String): MResult<String>

    @POST("user/changeUserInfo")
    suspend fun changeUserInfo(@Body userInfoData: UserInfoData): MResult<UserInfoData>

    @POST("user/queryViewHistory")
    suspend fun queryViewHistory(): MResult<MutableList<ViewHistoryData>>

    @GET("article/list/{pageNum}/json?page_size=40")
    suspend fun queryObtainTurnover(@Path("pageNum") pageNum:Int): MResult<MutableList<MineTurnoverData>>

    @POST("user/querySignTurnover")
    suspend fun querySignTurnover(): MResult<MutableList<MineTurnoverData>>

    @POST("user/queryExtensionTurnover")
    suspend fun queryExtensionTurnover(): MResult<MutableList<MineTurnoverData>>

}