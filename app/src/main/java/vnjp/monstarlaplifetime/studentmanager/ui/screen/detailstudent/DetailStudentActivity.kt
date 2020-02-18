package vnjp.monstarlaplifetime.studentmanager.ui.screen.detailstudent

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import vnjp.monstarlaplifetime.studentmanager.R
import vnjp.monstarlaplifetime.studentmanager.ui.screen.liststudent.ListStudentActivity

@Suppress("DEPRECATION")
class DetailStudentActivity : AppCompatActivity() {
    lateinit var toolbar: Toolbar
    private var idStudent: Int? = null
    private lateinit var viewModel: DetailStudentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        intent.extras.let {
            idStudent = it?.getInt(ListStudentActivity.BUNDLE_STUDENT_ID)
        }
        setContentView(R.layout.activity_detail_student)
        initView()
        viewModel = ViewModelProviders.of(this).get(DetailStudentViewModel::class.java)
        initEvent()
        observable()
    }

    private fun initEvent() {
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun observable() {
        val tvNameProfile = findViewById<TextView>(R.id.tvNameProfile)
        val tvAgeProfile = findViewById<TextView>(R.id.tvAgeProfile)
        val tvPhone = findViewById<TextView>(R.id.tvPhone)
        val tvAddress = findViewById<TextView>(R.id.tvAddress)
        idStudent?.let { viewModel.getStudentById(it) }
        viewModel.student.observe(this, Observer {
            tvNameProfile.text = it.name
            tvAgeProfile.text = it.age.toString()
            tvPhone.text = it.mobile
            tvAddress.text = it.address
        })
    }

    private fun initView() {
        toolbar = findViewById(R.id.toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_feather_arrow_left__white)

    }

}
