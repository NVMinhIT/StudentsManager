@file:Suppress("DEPRECATION")

package vnjp.monstarlaplifetime.studentmanager.ui.screen.liststudent

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_list_student.*
import vnjp.monstarlablifetime.mochichat.data.base.BaseActivity
import vnjp.monstarlaplifetime.studentmanager.R
import vnjp.monstarlaplifetime.studentmanager.data.reponse.StudentResponse
import vnjp.monstarlaplifetime.studentmanager.ui.screen.addstudent.AddStudentActivity
import vnjp.monstarlaplifetime.studentmanager.ui.screen.detailstudent.DetailStudentActivity
import vnjp.monstarlaplifetime.studentmanager.util.Common

@Suppress("DEPRECATION")
class ListStudentActivity : BaseActivity(), View.OnClickListener,
    ListStudentAdapter.ILongClickItemListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: ListStudentViewModel
    private lateinit var listAdapterStudent: ListStudentAdapter
    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    private lateinit var floatingActionButton: FloatingActionButton
    private var isFirst = true

    companion object {
        const val BUNDLE_STUDENT_ID = "BUNDLE_STUDENT_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_student)
        initView()
        initViewModel()
        observable()
        initEvent()
    }

    private fun observable() {
        viewModel.getAllStudent()
        viewModel.isLoading.observe(this, Observer {
            if (it) {
                showDialog(true)
            } else {
                showDialog(false)
            }
        })
        viewModel.students.observe(this, Observer {
            listAdapterStudent.setListStudent(it)
        })
        viewModel.isException.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            showDialog(false)
        })

    }

    private fun observableResponse() {
        viewModel.isLoading.observe(this, Observer {
            if (it) {
                showDialog(true)
            } else {
                showDialog(false)
            }
        })
        viewModel.delStudent.observe(this, Observer {
            Common.showToastSuccess(R.string.delete_success)
            viewModel.getAllStudent()
        })
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ListStudentViewModel::class.java)
    }

    private fun initEvent() {
        floatingActionButton.setOnClickListener(this)
        swipeRefreshLayout?.setOnRefreshListener {
            viewModel.getAllStudent()
            swipeRefreshLayout!!.isRefreshing = false
        }
        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                listAdapterStudent.filter(s.toString())
                if (Common.isNullOrEmpty(s.toString())) {
                    viewModel.getAllStudent()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
        })
    }

    private fun initView() {
        recyclerView = findViewById(R.id.rvListStudent)
        recyclerView.layoutManager = LinearLayoutManager(this)
        listAdapterStudent = ListStudentAdapter(this) {
            val intent = Intent(this, DetailStudentActivity::class.java)
            intent.putExtra(BUNDLE_STUDENT_ID, listAdapterStudent.getPosition(it).id)
            startActivity(intent)
        }
        recyclerView.adapter = listAdapterStudent
        listAdapterStudent.setLongClickItemListener(this)
        floatingActionButton = findViewById(R.id.fab)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fab -> {
                val intent = Intent(this, AddStudentActivity::class.java)
                startActivity(intent)
            }

        }
    }

    override fun onResume() {
        super.onResume()
        if (!isFirst) {
            viewModel.getAllStudent()

        } else {
            isFirst = false
        }
    }

    override fun onLongClickItemStudent(student: StudentResponse?) {
        showDialogDelete(student)
    }

    private fun showDialogDelete(student: StudentResponse?) {
        val dialog = AlertDialog.Builder(this, R.style.AlertDialog)
        dialog.setMessage("You want delete ${student?.name}?")
        dialog.setPositiveButton(
            "Yes"
        ) { dialogInterface, i ->
            student?.id?.let {
                viewModel.deleteStudentById(it)
            }
            observableResponse()
        }
        dialog.setNegativeButton(
            "No"
        ) { dialogInterface, i ->

        }
        dialog.show()
    }
}
