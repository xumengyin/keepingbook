package cpsdna.myapplication.keepingbook.adapter

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import cpsdna.myapplication.keepingbook.R
import cpsdna.myapplication.keepingbook.bean.DayPay
import cpsdna.myapplication.keepingbook.bean.MonthPay

class BookPayAdapter(data: MutableList<MultiItemEntity>?) :
    BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder>(data) {
    companion object {
        public const val TYPE_LEVEL_MONTH = 0
        public const val TYPE_LEVEL_DAYS = 1
    }

    init {

        addItemType(TYPE_LEVEL_MONTH, R.layout.month_book_layout)
        addItemType(TYPE_LEVEL_DAYS, R.layout.day_book_layout)

    }

    override fun convert(helper: BaseViewHolder?, item: MultiItemEntity?) {
        when (item?.itemType) {
            TYPE_LEVEL_MONTH -> {
                val monthData = item as MonthPay
                helper?.setText(R.id.timeMonth, monthData.title)
                helper?.setText(R.id.monthMoney, monthData.payment)
                helper?.setImageResource(R.id.arrow, if (monthData.isExpanded) R.mipmap.arrow_b else R.mipmap.arrow_r)
            }
            TYPE_LEVEL_DAYS -> {
                val dayData = item as DayPay
                helper?.setText(R.id.timeDay, dayData.dayTitle)
                helper?.setText(R.id.monthMoney, dayData.payment)
            }
        }

    }

}