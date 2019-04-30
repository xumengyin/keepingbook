package cpsdna.myapplication.keepingbook.bean

import androidx.annotation.ColorInt
import androidx.room.*
import com.chad.library.adapter.base.entity.AbstractExpandableItem
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.google.gson.annotations.SerializedName
import cpsdna.myapplication.keepingbook.adapter.BookPayAdapter
import cpsdna.myapplication.keepingbook.util.toConverPay
import cpsdna.myapplication.keepingbook.util.toConvertDay
import java.text.DecimalFormat

data class YearPay(val year: String, var pay: Float) : AbstractExpandableItem<MonthPay>(), MultiItemEntity {
    override fun getLevel(): Int {
        return BookPayAdapter.TYPE_LEVEL_YEAR
    }

    val title: String
        get() {
            return year
        }
    val payment: String
        get() {
            val f = DecimalFormat("#.##")
            return f.format(pay).plus("元")
        }

    override fun getItemType(): Int {
        return BookPayAdapter.TYPE_LEVEL_YEAR
    }
}

data class MonthPay(val month: String, var pay: Float) : AbstractExpandableItem<DayPay>(), MultiItemEntity {
    override fun getLevel(): Int {
        return BookPayAdapter.TYPE_LEVEL_MONTH
    }

    val title: String
        get() {
            return month
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


data class DayPay(val day: String, var pay: Float) : AbstractExpandableItem<SubPay>(),
    MultiItemEntity {
    override fun getLevel(): Int {
        return BookPayAdapter.TYPE_LEVEL_DAYS
    }

    val dayTitle: String
        get() {

            return day.toString() + "日"
        }
    val payment: String
        get() {
            return pay.toConverPay().plus("元")
        }

    override fun getItemType(): Int {
        return BookPayAdapter.TYPE_LEVEL_DAYS
    }
}

@Entity(primaryKeys = ["time"])
data class SubPay(
    @field:SerializedName("payValue") val value: Float,
    @field:SerializedName("time") val time: Long,
    @field:SerializedName("desc") val desc: String? = "",
    @field:SerializedName("position") val position: String? = ""
) :
    MultiItemEntity {
    override fun getItemType(): Int {
        return BookPayAdapter.TYPE_LEVEL_SUB
    }


}

fun SubPay.dayTitle(): String {
    return time.toConvertDay() + "日"
}

fun SubPay.payment(): String {
    return value.toConverPay() + "元"
}

@Entity
data class TAG(
    @ColumnInfo(name = "color") @ColorInt val color: Int,
    @ColumnInfo(name = "desc") val desc: String=""
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int=0

    @Ignore
    var choose:Boolean=false
}
