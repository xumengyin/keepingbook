package cpsdna.myapplication.keepingbook.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import cpsdna.myapplication.keepingbook.R
import cpsdna.myapplication.keepingbook.viewmodel.AccountBookViewModel
import kotlinx.android.synthetic.main.book_list_layout.*

class BookingFragment : Fragment() {
    lateinit var  bookViewModel:AccountBookViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.book_list_layout,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.title="记账本"
        bookViewModel=ViewModelProviders.of(this).get(AccountBookViewModel::class.java)

    }
}