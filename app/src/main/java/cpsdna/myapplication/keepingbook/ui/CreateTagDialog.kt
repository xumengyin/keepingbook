package cpsdna.myapplication.keepingbook.ui

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.forEach
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import cpsdna.myapplication.keepingbook.R
import cpsdna.myapplication.keepingbook.bean.TAG
import cpsdna.myapplication.keepingbook.ui.view.TagColorView
import cpsdna.myapplication.keepingbook.viewmodel.AccountBookViewModel

class CreateTagDialog() : DialogFragment() {


    lateinit var viewModel: AccountBookViewModel

    companion object {

        fun create(manager: FragmentManager) {
            val dialog = CreateTagDialog()
            dialog.show(manager, "tag")
        }

    }

    val MATERIAL_COLORS = intArrayOf(
        0xbbcca, // RED 500
        0x16e19d, // PINK 500
        0xd36d, // LIGHT PINK 500
        0x63d850, // PURPLE 500
        0x98c549, // DEEP PURPLE 500
        0xc0ae4b, // INDIGO 500
        0xde690d, // BLUE 500
        0xfc560c, // LIGHT BLUE 500
        0xff432c, // CYAN 500
        0xff6978, // TEAL 500
        0xb350b0, // GREEN 500
        0x743cb6, // LIGHT GREEN 500
        0x3223c7, // LIME 500
        0x14c5, // YELLOW 500
        0x3ef9, // AMBER 500
        0x6800, // ORANGE 500
        0x86aab8, // BROWN 500
        0x9f8275, // BLUE GREY 500
        0x616162,
        0x347590
    )// GREY 500

    val tagList = MATERIAL_COLORS.map {
        val red = Color.red(it)
        val green = Color.green(it)
        val blue = Color.blue(it)
        TAG(Color.argb(255, red, green, blue))
    }.toList()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(requireContext())
        builder.setView(createTagLayout())
        builder.setTitle("创建标签")
        builder.setPositiveButton("创建", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                val desc = editText.text.toString()
                if (!desc.isNullOrEmpty())
                    viewModel.insertTag(TAG(tagList.get(chooseIndex).color, editText.text.toString()))
            }

        })
        return builder.create()
    }

    lateinit var editText: EditText
    lateinit var recyclerView: RecyclerView
    var chooseIndex = -1
    private val click: BaseQuickAdapter.OnItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
        val tagView = view.findViewById<TagColorView>(R.id.tag_item)
        val index = position
        if (index == chooseIndex) {
            tagView.animateChoose(false)
            chooseIndex = -1
            editText.visibility = View.GONE
            return@OnItemClickListener
        }
        recyclerView.forEach {
            if (it is ViewGroup) {
                it.forEach { view ->
                    if (view is TagColorView) {
                        if (view.isCheck()) {
                            view.animateChoose(false)
                        }
                    }
                }
            }
        }
        tagView.animateChoose(true)
        chooseIndex = index
        editText.visibility = View.VISIBLE
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog as AlertDialog
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        )
            .get(AccountBookViewModel::class.java)
    }

    private fun createTagLayout(): View {
        val view: View = LayoutInflater.from(requireContext()).inflate(R.layout.create_tag_layout, null, false)
        recyclerView = view.findViewById(R.id.createTagRv) as RecyclerView
        editText = view.findViewById(R.id.tagEdittext) as EditText
        val vAdapter = createTagAdapter(tagList)
        vAdapter.setOnItemClickListener(click)
        recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 4)
            // adapter=vAdapter
            vAdapter.bindToRecyclerView(this)

        }
        return view
    }

    private fun createMdColorList(): List<TAG> {
        return MATERIAL_COLORS.map {
            val red = Color.red(it)
            val green = Color.green(it)
            val blue = Color.blue(it)
            TAG(Color.argb(255, red, green, blue))
        }.toList()
    }

    class createTagAdapter(data: List<TAG>) : BaseQuickAdapter<TAG, BaseViewHolder>(R.layout.create_tag_item, data) {
        override fun convert(helper: BaseViewHolder?, item: TAG?) {
            val view = helper?.getView<View>(R.id.tag_item) as TagColorView
            item?.let {
                view.setTagColorValue(item.color)

            }

        }

    }
}
