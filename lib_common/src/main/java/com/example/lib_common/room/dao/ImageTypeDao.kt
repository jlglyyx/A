package com.example.lib_common.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.lib_common.room.entity.ImageTypeData

/**
 * @Author Administrator
 * @ClassName ImageTypeDao
 * @Description
 * @Date 2021/8/5 14:28
 */
@Dao
interface ImageTypeDao {

    @Query("SELECT  * FROM image_type")
    fun getAllData():MutableList<ImageTypeData>

    @Insert
    fun insertData(imageTypeData:ImageTypeData)

    @Insert
    fun insertData(imageTypeData:MutableList<ImageTypeData>)
}