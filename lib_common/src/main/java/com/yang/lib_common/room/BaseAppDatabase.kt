package com.yang.lib_common.room

import android.os.Environment
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yang.lib_common.app.BaseApplication
import com.yang.lib_common.room.dao.ImageTypeDao
import com.yang.lib_common.room.dao.SearchHistoryDao
import com.yang.lib_common.room.dao.VideoTypeDao
import com.yang.lib_common.room.entity.ImageTypeData
import com.yang.lib_common.room.entity.SearchData
import com.yang.lib_common.room.entity.VideoTypeData
import java.io.File


/**
 * @Author Administrator
 * @ClassName BaseAppDate
 * @Description
 * @Date 2021/8/5 14:22
 */
@Database(entities = [ImageTypeData::class,VideoTypeData::class,SearchData::class],version = 1,exportSchema = true)
abstract class BaseAppDatabase : RoomDatabase() {

    abstract fun imageTypeDao(): ImageTypeDao

    abstract fun videoTypeDao(): VideoTypeDao

    abstract fun searchHistoryDao(): SearchHistoryDao

    companion object {
        val instance: BaseAppDatabase by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            getBaseAppDatabase()
        }

        private fun getBaseAppDatabase(): BaseAppDatabase {
            return Room.databaseBuilder(
                BaseApplication.baseApplication,
                BaseAppDatabase::class.java,
                "${createFile()}/app.db"
            ).allowMainThreadQueries().build()
        }

        private fun createFile():String{
            val file = File("${Environment.getExternalStorageDirectory()}/MFiles/db")
            if (!file.exists()){
                file.mkdirs()
            }
            return file.path
        }
    }


}