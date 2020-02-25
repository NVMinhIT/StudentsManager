package vnjp.monstarlaplifetime.studentmanager.ui.screen.addstudent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_add_student.*
import vnjp.monstarlablifetime.mochichat.data.base.BaseActivity
import vnjp.monstarlaplifetime.studentmanager.R
import vnjp.monstarlaplifetime.studentmanager.data.response.Student
import vnjp.monstarlaplifetime.studentmanager.util.Common

@Suppress("DEPRECATION")
class AddStudentActivity : BaseActivity(), View.OnClickListener {
    private lateinit var toolbar: Toolbar
    private lateinit var addStudentViewModel: AddStudentViewModel
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
        addStudentViewModel.isLoading.observe(this, Observer {
            showDialog(it)
        })
        addStudentViewModel.students.observe(this, Observer {
            if (it.address.isNotEmpty()) {
                finish()
                Common.showToastSuccess(R.string.add_success)
            }

        })
        addStudentViewModel.isException.observe(this, Observer {
            showDialog(false)
            Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()

        })

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun initView() {
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
                            1,
                            edtName.text.toString(),
                            edtAge.text.toString().toInt(),
                            edtAddress.text.toString(),
                            edtPhone.text.toString()

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
