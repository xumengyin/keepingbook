package cpsdna.myapplication.keepingbook.util

import java.text.SimpleDateFormat

class Utils {
    companion object {
        val yearFotmat= SimpleDateFormat("yyyy")
        val monthFotmat= SimpleDateFormat("MM")
        val dayFotmat= SimpleDateFormat("dd")
        val monthDayFotmat= SimpleDateFormat("MM月dd日")
        val timeFormat= SimpleDateFormat("HH:mm")
    }
}