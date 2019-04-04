package cpsdna.myapplication.keepingbook.bean

import com.chad.library.adapter.base.entity.AbstractExpandableItem
import com.chad.library.adapter.base.entity.MultiItemEntity
import cpsdna.myapplication.keepingbook.adapter.BookPayAdapter
import java.text.DecimalFormat

data class MonthPay(val month: Int, val pay: Double) : AbstractExpandableItem<DayPay>(), MultiItemEntity {
    override fun getLevel(): Int {
        return 0
    }

    val title: String
        get() {
            return getMonth(month)
        }
    val payment: String
        get() {
            val f = DecimalFormat("#.##")
            return f.format(pay).plus("元")
        }

    override fun getItemType(): Int {
        return BookPayAdapter.TYPE_LEVEL_MONTH
    }
}

data class DayPay(val day: Int, val pay: Double) : MultiItemEntity {
    val dayTitle: String
        get() {
            return day.toString() + "日"
        }
    val payment: String
        get() {
            val f = DecimalFormat("#.##")
            return f.format(pay).plus("元")
        }

    override fun getItemType(): Int {
        return BookPayAdapter.TYPE_LEVEL_DAYS
    }
}


fun getMonth(month: Int): String {
    return when (month) {
        1 -> "1月"
        2 -> "2月"
        3 -> "3月"
        4 -> "4月"
        5 -> "5月"
        6 -> "6月"
        7 -> "7月"
        8 -> "8月"
        9 -> "9月"
        10 -> "10月"
        11 -> "11月"
        12 -> "12月"
        else -> ""
    }
}