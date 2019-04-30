package cpsdna.myapplication.keepingbook.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cpsdna.myapplication.keepingbook.R
import cpsdna.myapplication.keepingbook.bean.SubPay
import cpsdna.myapplication.keepingbook.bean.payment
import cpsdna.myapplication.keepingbook.util.toConvertMonth
import cpsdna.myapplication.keepingbook.util.toConvertTime

class MainPayAdapter() : ListAdapter<SubPay, PayViewHolder>(diffCallBack) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PayViewHolder {

        return  PayViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.day_book_layout,parent,false))
    }

    override fun onBindViewHolder(holder: PayViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    object diffCallBack : DiffUtil.ItemCallback<SubPay>() {
        override fun areItemsTheSame(oldItem: SubPay, newItem: SubPay): Boolean {
            return false
        }

        override fun areContentsTheSame(oldItem: SubPay, newItem: SubPay): Boolean {
            return false
        }

    }
}

class PayViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val timeText:TextView
    val moneyText:TextView
    init {
        timeText=view.findViewById(R.id.timeDay)
        moneyText=view.findViewById(R.id.dayMoney)
    }
    fun bind(subpay:SubPay)
    {
        timeText.text=subpay.time.toConvertTime()
        moneyText.text=subpay.payment()
    }
}