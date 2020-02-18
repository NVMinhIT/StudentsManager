package vnjp.monstarlaplifetime.studentmanager.ui.screen.addstudent

import android.os.Build
import android.os.Bundle
import android.widget.Toolbar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import vnjp.monstarlaplifetime.studentmanager.R

class AddStudentActivity : AppCompatActivity() {
    lateinit var toolbar: Toolbar
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)
        initView()
        observable()
        initEvent()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun initEvent() {
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun observable() {

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun initView() {
        toolbar = findViewById(R.id.toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_feather_arrow_left__white)

    }
}
