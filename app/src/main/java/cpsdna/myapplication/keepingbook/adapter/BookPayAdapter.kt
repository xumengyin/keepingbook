package cpsdna.myapplication.keepingbook.adapter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.AbstractExpandableItem
import com.chad.library.adapter.base.entity.MultiItemEntity
import cpsdna.myapplication.keepingbook.R
import cpsdna.myapplication.keepingbook.bean.*

@Deprecated("分组adapter")
class BookPayAdapter(data: List<MultiItemEntity>) :
    BaseDiffAdapter<MultiItemEntity, BaseViewHolder>(data,diffCallback= object: ItemCallback<MultiItemEntity>() {
        override fun areItemsTheSame(oldItem: MultiItemEntity, newItem: MultiItemEntity): Boolean {
            if(oldItem is YearPay && newItem is YearPay)
            {
                return (oldItem.itemType==newItem.itemType)&&(oldItem.year == newItem.year)
            }
            return false
        }

        override fun areContentsTheSame(oldItem: MultiItemEntity, newItem: MultiItemEntity): Boolean {
            if(oldItem is YearPay && newItem is YearPay)
            {

                return (oldItem.itemType==newItem.itemType)&&(oldItem.year == newItem.year)
            }
            return false
        }

    }) {
    companion object {
        public const val TYPE_LEVEL_YEAR = 0
        public const val TYPE_LEVEL_MONTH = 1
        public const val TYPE_LEVEL_DAYS = 2
        public const val TYPE_LEVEL_SUB = 3
    }

    init {
        addItemType(TYPE_LEVEL_YEAR, R.layout.month_book_layout)
        addItemType(TYPE_LEVEL_MONTH, R.layout.month_book_layout)
        addItemType(TYPE_LEVEL_DAYS, R.layout.month_book_layout)
        addItemType(TYPE_LEVEL_SUB, R.layout.day_book_layout)
    }

    override fun convert(helper: BaseViewHolder?, item: MultiItemEntity?) {
        when (item?.itemType) {
            TYPE_LEVEL_YEAR -> {
                val yearData = item as YearPay
                helper?.setText(R.id.timeMonth, yearData.title)
                helper?.setText(R.id.monthMoney, yearData.payment)
                helper?.setImageResource(R.id.arrow, if (yearData.isExpanded) R.mipmap.arrow_b else R.mipmap.arrow_r)
            }
            TYPE_LEVEL_MONTH -> {
                val monthData = item as MonthPay
                helper?.setText(R.id.timeMonth, monthData.title)
                helper?.setText(R.id.monthMoney, monthData.payment)
                helper?.setImageResource(R.id.arrow, if (monthData.isExpanded) R.mipmap.arrow_b else R.mipmap.arrow_r)
            }
            TYPE_LEVEL_DAYS -> {
                val dayData = item as DayPay
                helper?.setText(R.id.timeMonth, dayData.dayTitle)
                helper?.setText(R.id.monthMoney, dayData.payment)
            }
            TYPE_LEVEL_SUB -> {
                val dayData = item as SubPay
                helper?.setText(R.id.timeDay, dayData.dayTitle())
                helper?.setText(R.id.dayMoney, dayData.payment())
            }
        }
       if(item is AbstractExpandableItem<*>)
       {
           helper?.itemView?.setOnClickListener {
               if(item.isExpanded)
               {
                   collapse( helper.getAdapterPosition(),false)
               }else
               {
                   expand(helper.getAdapterPosition(), false)
               }
           }
       }

    }

}