package cpsdna.myapplication.keepingbook.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import cpsdna.myapplication.keepingbook.bean.SubPay

@Dao
abstract class AccountPayDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertDayPay(dayPay: SubPay):Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertDayPays(dayPays: List<SubPay>)

    @Update
    abstract fun updateDayPays(vararg dayPays: SubPay)

    @Delete
    abstract fun deleteDayPays(vararg dayPays: SubPay)

    @Query("SELECT * FROM SubPay ORDER BY time")
    abstract fun loadAllPays(): LiveData<List<SubPay>>

    @Query("SELECT * FROM SubPay WHERE time BETWEEN :start AND :end ORDER BY time" )
    abstract fun loadRangePays(start: Long, end: Long = System.currentTimeMillis()): LiveData<List<SubPay>>
}