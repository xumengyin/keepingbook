package cpsdna.myapplication.keepingbook.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnNextLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import cpsdna.myapplication.keepingbook.AddPayActivity
import cpsdna.myapplication.keepingbook.R
import cpsdna.myapplication.keepingbook.adapter.BookPayAdapter
import cpsdna.myapplication.keepingbook.adapter.MainPayAdapter
import cpsdna.myapplication.keepingbook.bean.SubPay
import cpsdna.myapplication.keepingbook.ui.ScheduleTimeHeadersDecoration
import cpsdna.myapplication.keepingbook.util.clearDecorations
import cpsdna.myapplication.keepingbook.util.convertTime
import cpsdna.myapplication.keepingbook.viewmodel.AccountBookViewModel
import kotlinx.android.synthetic.main.book_list_layout.*
import kotlinx.android.synthetic.main.layout_no_data.*

class BookingFragment : Fragment() {

    companion object {
        val DEFAULT_START_TIME = "2010-12-12 12:12:12"
    }

    // lateinit var vadapter: BookPayAdapter
    lateinit var vadapter: MainPayAdapter
    lateinit var bookViewModel: AccountBookViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.book_list_layout, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        vadapter = MainPayAdapter()
        bookViewModel = ViewModelProviders.of(this, ViewModelProvider.AndroidViewModelFactory(activity!!.application))
            .get(AccountBookViewModel::class.java)
        recycleView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = vadapter
            addItemDecoration(ScheduleTimeHeadersDecoration(context, mutableListOf()))
        }
        bookViewModel.timePay.observe(viewLifecycleOwner, Observer { data ->
            initListData(data.data)
        })
        bookViewModel.getData(DEFAULT_START_TIME.convertTime())
    }

    private fun initListData(data: List<SubPay>?) {
        if (data == null) {
            noData.visibility = View.VISIBLE
        } else {
            noData.visibility = View.GONE
            vadapter.submitList(data)
            recycleView.run {
                doOnNextLayout {
                    clearDecorations()
                    if (data.isNotEmpty()) {
                        addItemDecoration(
                            ScheduleTimeHeadersDecoration(context, data)
                        )
                    }

                }
            }

        }
    }

    private fun initView() {
        setHasOptionsMenu(true)
        toolbar.title = "记账本"
        var a = activity as AppCompatActivity
        a.setSupportActionBar(toolbar)
        toolbar.inflateMenu(R.menu.paymenu)
        fab.setOnClickListener {
            startActivityForResult(Intent(activity, AddPayActivity::class.java), 101)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {

        inflater?.inflate(R.menu.paymenu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
       // bookViewModel.getData(DEFAULT_START_TIME.convertTime())
    }
}