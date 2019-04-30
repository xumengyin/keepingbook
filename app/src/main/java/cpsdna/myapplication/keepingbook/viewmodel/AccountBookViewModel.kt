package cpsdna.myapplication.keepingbook.viewmodel

import android.app.Application
import androidx.annotation.ColorInt
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.chad.library.adapter.base.entity.MultiItemEntity
import cpsdna.myapplication.keepingbook.AppExecutors
import cpsdna.myapplication.keepingbook.bean.*
import cpsdna.myapplication.keepingbook.repo.AccountBookRepo
import cpsdna.myapplication.keepingbook.util.toConvertDay
import cpsdna.myapplication.keepingbook.util.toConvertMonth
import cpsdna.myapplication.keepingbook.util.toConvertYear

class AccountBookViewModel(app: Application) : AndroidViewModel(app) {
    //转换俩次??
    val repo = AccountBookRepo(app)
    private val _timePay = MutableLiveData<PayTime>()
    val timePay: LiveData<Resource<List<SubPay>>> = Transformations.switchMap(_timePay) {

        repo.getRangePay(it.start, it.end)
    }
    private val _dataLiveData = MutableLiveData<List<MultiItemEntity>>()


    fun getData(start: Long, end: Long = System.currentTimeMillis()) {
        _timePay.value = PayTime(start, end)
    }

    private fun postValue(data: List<MultiItemEntity>) {
        if (this._dataLiveData.value != data)
            this._dataLiveData.postValue(data)
    }

    fun insertPay(time: Long, pay: Float,desc:String="",position:String="") {
        AppExecutors.instance.diskIO().execute {
            val pay=SubPay(pay,time = time)
            repo.insertPay(pay)
        }

    }

    fun insertTag(tag:TAG)
    {
        AppExecutors.instance.diskIO().execute {
            repo.insertTAG(tag)
        }
    }
    fun loadAllTag():LiveData<List<TAG>>
    {
       return repo.getAllTag()
    }
    private fun filterData(data: List<SubPay>) {
        AppExecutors.instance.networkIO().execute {
            var cacheData = mutableListOf<MultiItemEntity>()
            var year: String = ""
            var month: String = ""
            var day: String = ""
            var yearData: YearPay? = null
            var monthData: MonthPay? = null
            var dayData: DayPay? = null
            data.forEach {
                val tempYear = it.time.toConvertYear()
                val tempMonth = it.time.toConvertMonth()
                val tempDay = it.time.toConvertDay()
                if (tempYear == year) {
                    yearData?.pay = yearData?.pay!! + (it.value)
                    if (tempMonth == month) {
                        monthData?.pay = monthData?.pay!! + it.value
                        if (tempDay == day) {
                            dayData?.pay = dayData?.pay!! + it.value
                        } else {
                            dayData = DayPay(tempDay, it.value)
                            monthData?.addSubItem(dayData)
                            dayData?.addSubItem(it)
                        }
                    } else {
                        monthData = MonthPay(tempMonth, it.value)
                        dayData = DayPay(tempDay, it.value)
                        yearData?.addSubItem(monthData)
                        monthData?.addSubItem(dayData)
                        dayData?.addSubItem(it)
                    }
                } else {
                    yearData = YearPay(tempYear, it.value)
                    monthData = MonthPay(tempMonth, it.value)
                    dayData = DayPay(tempDay, it.value)
                    yearData?.addSubItem(monthData)
                    monthData?.addSubItem(dayData)
                    dayData?.addSubItem(it)
                    cacheData.add(yearData!!)
                }

//                month=it.time.toConvertMonth()
//                monthData= cacheData.get(generateKey(year,month)) as MonthPay? ?:YearPay(year,0f)

                year = tempYear
                month = tempMonth
                day = tempDay
            }
            postValue(cacheData)
        }

    }

    data class PayTime(val start: Long, val end: Long)

}