package cpsdna.myapplication.keepingbook

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import cpsdna.myapplication.keepingbook.ui.BaseActivity
import cpsdna.myapplication.keepingbook.ui.fragment.BookingFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                viewPager.setCurrentItem(0, false)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                viewPager.setCurrentItem(1, false)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                viewPager.setCurrentItem(2, false)
                return@OnNavigationItemSelectedListener true
            }
        }
        return@OnNavigationItemSelectedListener false
    }


    override fun onActivityCreate() {
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        viewPager.apply {
            adapter = PageFragmentApdater(supportFragmentManager)
            addOnAdapterChangeListener { viewPager, oldAdapter, newAdapter ->

            }
            addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
                override fun onPageSelected(position: Int) {
                    navigation.selectedItemId = when (position) {
                         0 -> R.id.navigation_home
                        1->R.id.navigation_dashboard
                        2->R.id.navigation_notifications
                        else -> 0
                    }
                }
            })
        }

    }

    override fun getLayoutind(): Int = R.layout.activity_main

    inner class PageFragmentApdater(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        val list = mutableListOf<Fragment>(BookingFragment(), BookingFragment(), BookingFragment())
        override fun getItem(position: Int): Fragment {
            return BookingFragment()
        }

        override fun getCount(): Int {
            return list.size
        }

    }
}
