package cpsdna.myapplication.keepingbook.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cpsdna.myapplication.keepingbook.bean.SubPay
import cpsdna.myapplication.keepingbook.bean.TAG

@Database(entities = [SubPay::class, TAG::class], version = 2)
abstract class AppDataBase() : RoomDatabase() {

    abstract fun payDay(): AccountPayDao
    abstract fun tagDao(): TagDao

    companion object {
        private var base: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase {
            if (base == null) {
                base = Room.databaseBuilder(context.applicationContext, AppDataBase::class.java, "appData.db")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return base!!
        }
    }
}