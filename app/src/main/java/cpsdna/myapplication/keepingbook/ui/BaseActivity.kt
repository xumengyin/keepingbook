package cpsdna.myapplication.keepingbook.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

public abstract class BaseActivity :AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutind())
        onActivityCreate()
    }
    abstract fun getLayoutind():Int
    abstract fun onActivityCreate()
}