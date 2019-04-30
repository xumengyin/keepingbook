package cpsdna.myapplication.keepingbook.adapter

import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity

abstract class BaseDiffAdapter<K : MultiItemEntity, T : BaseViewHolder>(
    data: List<K>,
    diffCallback: ItemCallback<K>
) :
    BaseMultiItemQuickAdapter<K, T>(data) {
    var mHelper: AsyncListDiffer<K>

    init {
        mHelper=AsyncListDiffer(AdapterUpdateCallBack(this), AsyncDifferConfig.Builder(diffCallback).build())
    }
}