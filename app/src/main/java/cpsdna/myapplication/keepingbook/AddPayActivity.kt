package cpsdna.myapplication.keepingbook

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import cpsdna.myapplication.keepingbook.adapter.TagListAdapter
import cpsdna.myapplication.keepingbook.bean.SubPay
import cpsdna.myapplication.keepingbook.bean.TAG
import cpsdna.myapplication.keepingbook.ui.BaseActivity
import cpsdna.myapplication.keepingbook.ui.CreateTagDialog
import cpsdna.myapplication.keepingbook.ui.view.CreateTagDialog2
import cpsdna.myapplication.keepingbook.util.convertTime
import cpsdna.myapplication.keepingbook.util.logThis
import cpsdna.myapplication.keepingbook.viewmodel.AccountBookViewModel
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.fragment_tag_bottom_layout.*

class AddPayActivity : BaseActivity() {
    var i = 0
    lateinit var viewModel: AccountBookViewModel
    lateinit var bottomBehavior: BottomSheetBehavior<*>
    lateinit var tagAdapter:TagListAdapter
    override fun onActivityCreate() {
        viewModel = ViewModelProviders.of(this, ViewModelProvider.AndroidViewModelFactory(application))
            .get(AccountBookViewModel::class.java)
        //init()
        addBtn.setOnClickListener {

            var k = testData[i % testData.size]
            viewModel.insertPay(k.time, k.value)
            setResult(Activity.RESULT_OK)
            i++
        }

        testChooseTag.tagText="test data"
        filterview.text="test data"
        testChooseTag.setOnClickListener {
            testChooseTag.animateProgress();
        }
    }




    override fun getLayoutind(): Int = R.layout.activity_main2


}

var testTag = listOf(
    TAG(0xFF0078ff.toInt(), "吃饭"),
    TAG(0xFFafff00.toInt(), "睡觉"),
    TAG(0xff3F0240.toInt(), "大保健"),
    TAG(0xff990424.toInt(), "大保健"),
    TAG(0xff682522.toInt(), "睡觉"),
    TAG(0xffccccff.toInt(), "游戏")
)
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