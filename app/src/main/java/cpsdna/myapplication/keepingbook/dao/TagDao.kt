package cpsdna.myapplication.keepingbook.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import cpsdna.myapplication.keepingbook.bean.SubPay
import cpsdna.myapplication.keepingbook.bean.TAG

@Dao
abstract class TagDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertTag(tag: TAG):Long


    @Update
    abstract fun updateTag(vararg tags: TAG)

    @Delete
    abstract fun deleteTag(vararg tags: TAG)

    @Query("SELECT * FROM TAG")
    abstract fun loadAllTags(): LiveData<List<TAG>>

}