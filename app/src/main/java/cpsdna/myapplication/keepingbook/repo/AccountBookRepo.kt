package cpsdna.myapplication.keepingbook.repo

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import cpsdna.myapplication.keepingbook.ApiResponse
import cpsdna.myapplication.keepingbook.AppExecutors
import cpsdna.myapplication.keepingbook.NetworkBoundResource
import cpsdna.myapplication.keepingbook.bean.Resource
import cpsdna.myapplication.keepingbook.bean.SubPay
import cpsdna.myapplication.keepingbook.bean.TAG
import cpsdna.myapplication.keepingbook.dao.AppDataBase

class AccountBookRepo(context: Context) {


    val payDao by lazy {
        AppDataBase.getInstance(context)
    }

    init {

    }

    fun insertPay(dayPay: SubPay) {
        payDao.payDay().insertDayPay(dayPay)
    }

    fun insertTAG(tag: TAG) {
        payDao.tagDao().insertTag(tag)
    }

    fun getAllTag():LiveData<List<TAG>>
    {
       return payDao.tagDao().loadAllTags()
    }
    fun getAllPay(): LiveData<Resource<List<SubPay>>> {
        return object : NetworkBoundResource<List<SubPay>, List<SubPay>>(AppExecutors.instance) {
            override fun saveCallResult(item: List<SubPay>) {

            }

            override fun shouldFetch(data: List<SubPay>?): Boolean {
                return false
            }

            override fun loadFromDb(): LiveData<List<SubPay>> {
                val parDao = payDao.payDay()
                return parDao.loadAllPays()
            }

            override fun createCall(): LiveData<ApiResponse<List<SubPay>>> {
                return MediatorLiveData<ApiResponse<List<SubPay>>>()
            }

        }.asLiveData()
    }

    fun getRangePay(start: Long, end: Long): LiveData<Resource<List<SubPay>>> {
        return object : NetworkBoundResource<List<SubPay>, List<SubPay>>(AppExecutors.instance) {
            override fun saveCallResult(item: List<SubPay>) {

            }

            override fun shouldFetch(data: List<SubPay>?): Boolean {
                return false
            }

            override fun loadFromDb(): LiveData<List<SubPay>> {
                val parDao = payDao.payDay()
                return parDao.loadRangePays(start, end)
            }

            override fun createCall(): LiveData<ApiResponse<List<SubPay>>> {
                return MediatorLiveData<ApiResponse<List<SubPay>>>()
            }

        }.asLiveData()
    }

}