@file:Suppress("DEPRECATION")

package vnjp.monstarlaplifetime.studentmanager.ui.screen.updatestudent

import android.os.Build
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_update_student.*
import vnjp.monstarlablifetime.mochichat.data.base.BaseActivity
import vnjp.monstarlaplifetime.studentmanager.R
import vnjp.monstarlaplifetime.studentmanager.data.reponse.Student
import vnjp.monstarlaplifetime.studentmanager.ui.screen.detailstudent.DetailStudentActivity
import vnjp.monstarlaplifetime.studentmanager.util.Common

@Suppress("DEPRECATION")
class UpdateStudentActivity : BaseActivity() {
    private var idNew: Int? = null
    private lateinit var edtUpdateName: EditText
    private lateinit var edtUpdateAge: EditText
    private lateinit var edtUpdatePhone: EditText
    private lateinit var edtUpdateAddress: EditText
    private lateinit var toolbar: Toolbar
    private lateinit var updateStudentViewModel: UpdateStudentViewModel
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_student)
        intent?.extras.let {
            idNew = it?.getInt(DetailStudentActivity.ID_UPDATE)
        }
        updateStudentViewModel = ViewModelProviders.of(this).get(UpdateStudentViewModel::class.java)
        initView()
        observablesById()
        initEvent()
    }

    private fun observablesUpdate() {
        idNew?.let {
            Student(
                edtUpdateAddress.text.toString(),
                edtUpdateAge.text.toString().toInt(), it, edtUpdatePhone.text.toString(),
                edtUpdateName.text.toString()
            )
        }?.let {
            updateStudentViewModel.updateStudent(
                idNew!!, it
            )
        }
        updateStudentViewModel.isLoading.observe(this, Observer {
            if (it) {
                if (it) {
                    showDialog(true)
                } else {
                    showDialog(false)
                }
            }
        })
        updateStudentViewModel.updateStudent.observe(this, Observer {
            if (it.address.isNotEmpty()) {
                finish()
                Common.showToastSuccess(R.string.edit_success)
            }
        })
        updateStudentViewModel.isException.observe(this, Observer {
            Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
        })

    }

    private fun observablesById() {
        idNew?.let { updateStudentViewModel.getStudentId(it) }
        updateStudentViewModel.student.observe(this, Observer {
            edtUpdateName.setText(it.name)
            edtUpdateAddress.setText(it.address)
            edtUpdateAge.setText(it.age.toString())
            edtUpdatePhone.setText(it.mobile)
        })
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun initEvent() {
        tvEdit.setOnClickListener {
            observablesUpdate()
        }
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun initView() {
        toolbar = findViewById(R.id.toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_feather_arrow_left__white)
        edtUpdateAddress = findViewById(R.id.edtUpdateAddress)
        edtUpdatePhone = findViewById(R.id.edtUpdatePhone)
        edtUpdateAge = findViewById(R.id.edtUpdateAge)
        edtUpdateName = findViewById(R.id.edtUpdateName)
    }

}
