package cpsdna.myapplication.keepingbook.ui.fragment

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import cpsdna.myapplication.keepingbook.R
import cpsdna.myapplication.keepingbook.adapter.HTagListAcapter
import cpsdna.myapplication.keepingbook.adapter.TagListAdapter
import cpsdna.myapplication.keepingbook.ui.CreateTagDialog
import cpsdna.myapplication.keepingbook.util.BottomSheetBehavior
import cpsdna.myapplication.keepingbook.viewmodel.AccountBookViewModel
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.fragment_tag_bottom_layout.*

class AddTagBottomFragment():Fragment()
{
    private lateinit var bottomBehavior: BottomSheetBehavior<*>
    private lateinit var tagAdapter:TagListAdapter
    private lateinit var tagAdapterH: HTagListAcapter
    lateinit var viewModel: AccountBookViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tag_bottom_layout,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()

    }
    private fun init() {
        viewModel = ViewModelProviders.of(this, ViewModelProvider.AndroidViewModelFactory(context?.applicationContext as Application))
            .get(AccountBookViewModel::class.java)
        bottomBehavior = BottomSheetBehavior.from(view!!)
        bottomBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback {
            override fun onSlide(p0: View, p1: Float) {
                Log.d("BottomSheetBehavior", "on slide: ${p1}")
                offsetAlpha(p1)
            }

            override fun onStateChanged(p0: View, state: Int) {
                Log.d("BottomSheetBehavior", "onStateChanged: ${state}")
                setMenuClickAble()
                when (state) {
                    BottomSheetBehavior.STATE_EXPANDED->
                    {
                        tagMenuLayou.alpha=1f
                        tagHRecycleview.alpha=0f
                    }
                    BottomSheetBehavior.STATE_COLLAPSED ->
                    {
                        tagMenuLayou.alpha=0f
                        tagHRecycleview.alpha=1f
                    }

                }
            }

        })

        initRecycleView()
        addTag.setOnClickListener {
            CreateTagDialog.create(childFragmentManager)
            //CreateTagDialog2.create(this)
            //bottomBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }
        observeTag()
    }
    private fun setMenuClickAble()
    {
        if(tagMenuLayou.alpha>0)
        {
            addTag.isClickable=true
            deleteTag.isClickable=true
        }else
        {
            addTag.isClickable=false
            deleteTag.isClickable=false
        }
    }
    val startAlpha=0.5f
    private fun offsetAlpha(offset:Float)
    {
        tagMenuLayou.alpha=(offset-startAlpha)/(1-startAlpha).coerceIn(0f,1f)
        tagHRecycleview.alpha=(1-offset-startAlpha)/(1-startAlpha).coerceIn(0f,1f)
    }
    private fun initRecycleView()
    {
        val mamager = FlexboxLayoutManager(requireContext(), FlexDirection.ROW).apply {
            flexWrap = FlexWrap.WRAP
            alignItems = AlignItems.STRETCH
            flexDirection = FlexDirection.ROW
        }
        tagRecycleview.apply {
            layoutManager = GridLayoutManager(requireContext(),2)
            tagAdapter= TagListAdapter(listOf())
            adapter = tagAdapter
            isNestedScrollingEnabled=true
        }
        tagHRecycleview.apply {
            layoutManager=LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
            tagAdapterH=HTagListAcapter(listOf())
            adapter=tagAdapterH
        }
    }
    private fun observeTag()
    {
        viewModel.loadAllTag().observe(this, Observer {
            if(it.size>0)
            {
                tagAdapter.setNewData(it)
                tagAdapterH.setNewData(it)
                nodataTip.visibility=View.GONE

            }else
            {
                nodataTip.visibility=View.VISIBLE
            }
           // bottomBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        })
    }
}