package vnjp.monstarlaplifetime.studentmanager.ui.screen.addstudent

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_add_student.*
import vnjp.monstarlaplifetime.studentmanager.R
import vnjp.monstarlaplifetime.studentmanager.data.reponse.Student
import vnjp.monstarlaplifetime.studentmanager.util.CommonF

@Suppress("DEPRECATION")
class AddStudentActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var toolbar: Toolbar
    private lateinit var addStudentViewModel: AddStudentViewModel
    private lateinit var progress: ProgressBar
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)
        addStudentViewModel = ViewModelProviders.of(this).get(AddStudentViewModel::class.java)
        initView()
        initEvent()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun initEvent() {
        toolbar.setNavigationOnClickListener {
            finish()
        }
        btCancel.setOnClickListener(this)
        btAdd.setOnClickListener(this)
    }

    private fun observable() {
        addStudentViewModel.students.observe(this, Observer {
            if (it.address.isNotEmpty()) {
                finish()
                CommonF.showToastSuccess(R.string.add_success)

            }

        })
        addStudentViewModel.isException.observe(this, Observer {

        })
        addStudentViewModel.isLoading.observe(this, Observer {
            if (it) {
                if (it) {
                    progress.visibility = View.VISIBLE
                } else {
                    progress.visibility = View.INVISIBLE
                }
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun initView() {
        progress = findViewById(R.id.progressOnLoading)
        toolbar = findViewById(R.id.toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_feather_arrow_left__white)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btAdd -> {
                val edtName = findViewById<EditText>(R.id.edtName)
                val edtAge = findViewById<EditText>(R.id.edtAge)
                val edtPhone = findViewById<EditText>(R.id.edtPhone)
                val edtAddress = findViewById<EditText>(R.id.edtAddress)
                if (edtName.text.toString().isEmpty()) {
                    edtName.setError("Please Enter Name")
                } else if (edtAge.text.toString().isEmpty()) {
                    edtAge.setError("Please Enter Age")
                } else if (edtPhone.text.toString().isEmpty()) {
                    edtPhone.setError("Please Enter Phone")
                } else if (edtAddress.text.toString().isEmpty()) {
                    edtAddress.setError("Please Enter Address")
                } else {
                    addStudentViewModel.addStudents(
                        Student(
                            edtAddress.text.toString(),
                            edtAge.text.toString().toInt(),
                            1,
                            edtPhone.text.toString(),
                            edtName.text.toString()
                        )
                    )
                    observable()
                }
            }
            R.id.btCancel -> {
                finish()
            }

        }
    }
}
