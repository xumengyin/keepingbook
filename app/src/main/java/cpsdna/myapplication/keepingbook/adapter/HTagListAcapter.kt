package cpsdna.myapplication.keepingbook.adapter

import android.content.res.ColorStateList
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import cpsdna.myapplication.keepingbook.R
import cpsdna.myapplication.keepingbook.bean.TAG

class HTagListAcapter(data:List<TAG>):BaseQuickAdapter<TAG, BaseViewHolder>(R.layout.tag_list_layout1,data) {
    override fun convert(helper: BaseViewHolder?, item: TAG?) {

        item?.apply {
            val text= helper?.getView<TextView>(R.id.tagText)
            text?.text=desc
            text?.backgroundTintList= ColorStateList.valueOf(color)
        }

    }
}