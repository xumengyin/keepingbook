package cpsdna.myapplication.keepingbook.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import cpsdna.myapplication.keepingbook.R

class BookPayActivity : BaseActivity() {
    override fun getLayoutind(): Int {
        return R.layout.activity_book_pay
    }

    override fun onActivityCreate() {
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
    }
}
