package cpsdna.myapplication.keepingbook.adapter

import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import cpsdna.myapplication.keepingbook.R
import cpsdna.myapplication.keepingbook.bean.TAG

class TagListAdapter(data: List<TAG>) : BaseQuickAdapter<TAG, BaseViewHolder>(R.layout.tag_layout, data) {
    override fun convert(helper: BaseViewHolder?, item: TAG?) {
        val tagText = helper?.getView(R.id.tagText) as TextView
        tagText.apply {
            text = item?.desc
            if (item != null)
                compoundDrawablesRelative[0]?.setTint(item.color)
        }
    }
}