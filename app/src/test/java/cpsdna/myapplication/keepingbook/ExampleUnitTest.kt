package cpsdna.myapplication.keepingbook

import com.chad.library.adapter.base.entity.MultiItemEntity
import cpsdna.myapplication.keepingbook.bean.DayPay
import cpsdna.myapplication.keepingbook.bean.MonthPay
import cpsdna.myapplication.keepingbook.bean.SubPay
import cpsdna.myapplication.keepingbook.bean.YearPay
import cpsdna.myapplication.keepingbook.util.*
import org.junit.Test

import org.junit.Assert.*
import java.text.SimpleDateFormat

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    var testData: List<SubPay> = listOf(
        SubPay(100f, "2013-12-12 12:22:13".convertTime()),
        SubPay(100f, "2014-01-12 12:22:13".convertTime()),
        SubPay(100f, "2014-01-13 12:22:13".convertTime()),
        SubPay(100f, "2014-01-14 12:22:13".convertTime()),
        SubPay(100f, "2014-01-15 12:22:13".convertTime()),
        SubPay(100f, "2014-02-12 12:22:13".convertTime()),
        SubPay(100f, "2014-04-12 12:22:13".convertTime()),
        SubPay(100f, "2014-05-12 12:22:13".convertTime()),
        SubPay(100f, "2014-07-12 12:22:13".convertTime()),
        SubPay(100f, "2014-08-12 12:22:13".convertTime()),
        SubPay(100f, "2014-09-12 12:22:13".convertTime()),
        SubPay(100f, "2014-09-12 12:22:13".convertTime()),
        SubPay(100f, "2014-10-12 12:22:13".convertTime()),
        SubPay(100f, "2014-11-12 12:22:13".convertTime()),
        SubPay(100f, "2014-12-12 12:22:13".convertTime()),
        SubPay(100f, "2015-01-12 12:22:13".convertTime()),
        SubPay(100f, "2015-02-12 12:22:13".convertTime()),
        SubPay(100f, "2015-02-12 12:22:13".convertTime()),
        SubPay(100f, "2015-02-12 12:22:13".convertTime())

    )

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


    @Test
    fun testData() {
        filterData(testData)
    }

    private fun filterData(data: List<SubPay>) {


        var cacheData = mutableListOf<MultiItemEntity>()
        var year: String = ""
        var month: String = ""
        var day: String = ""
        var yearData: YearPay?=null
        var monthData: MonthPay? = null
        var dayData: DayPay? = null
        data.forEach {
            val tempYear = it.time.toConvertYear()
            val tempMonth = it.time.toConvertMonth()
            val tempDay = it.time.toConvertDay()
            if (tempYear == year) {
                yearData?.pay = yearData?.pay!!+(it.value)
                if (tempMonth == month) {
                    monthData?.pay = monthData?.pay!!+it.value
                    if (tempDay == day) {
                        dayData?.pay = dayData?.pay!!+it.value
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
       // cacheData.logThis("xuxux")
        System.out.print(cacheData)

    }
}
