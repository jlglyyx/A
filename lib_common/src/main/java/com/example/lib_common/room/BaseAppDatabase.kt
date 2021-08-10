package com.example.lib_common.room

import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lib_common.app.BaseApplication
import com.example.lib_common.room.dao.ImageTypeDao


/**
 * @Author Administrator
 * @ClassName BaseAppDate
 * @Description
 * @Date 2021/8/5 14:22
 */
//@Database(entities = [ImageTypeData::class],version = 1,exportSchema = false)
abstract class BaseAppDatabase : RoomDatabase() {

    abstract fun imageTypeDao(): ImageTypeDao

    companion object {
        val instance: BaseAppDatabase by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            getBaseAppDatabase()
        }

        private fun getBaseAppDatabase(): BaseAppDatabase {
            return Room.databaseBuilder(
                BaseApplication.baseApplication,
                BaseAppDatabase::class.java,
                "user.db"
            ).allowMainThreadQueries().build()
        }
    }


}