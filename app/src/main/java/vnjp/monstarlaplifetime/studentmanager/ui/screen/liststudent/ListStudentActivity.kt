@file:Suppress("DEPRECATION")

package vnjp.monstarlaplifetime.studentmanager.ui.screen.liststudent

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_list_student.*
import vnjp.monstarlaplifetime.studentmanager.R
import vnjp.monstarlaplifetime.studentmanager.data.reponse.StudentResponse
import vnjp.monstarlaplifetime.studentmanager.ui.screen.addstudent.AddStudentActivity
import vnjp.monstarlaplifetime.studentmanager.ui.screen.detailstudent.DetailStudentActivity
import vnjp.monstarlaplifetime.studentmanager.util.CommonF

@Suppress("DEPRECATION")
class ListStudentActivity : AppCompatActivity(), View.OnClickListener,
    ListAdapterStudent.ILongClickItemListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: ListStudentViewModel
    private lateinit var listAdapterStudent: ListAdapterStudent
    private lateinit var progress: ProgressBar
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
        obServable()
        initEvent()
    }

    private fun obServable() {
        viewModel.getAllStudent()
        viewModel.isLoading.observe(this, Observer {
            if (it) {
                progress.visibility = View.VISIBLE
            } else {
                progress.visibility = View.INVISIBLE
            }
        })
        viewModel.students.observe(this, Observer {
            listAdapterStudent.setListStudent(it)
        })


    }

    fun observerDelete() {
//        viewModel.delStudent.observe(this, Observer {
////
////            Log.d("HEADER",it.toString())
////            Toast.makeText(this,it.toString(),Toast.LENGTH_LONG).show()
////        })
        viewModel.delStudent.observe(this, Observer {
            viewModel.getAllStudent()
            CommonF.showToastSuccess(R.string.delete_success)
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
                if (CommonF.isNullOrEmpty(s.toString())) {
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
        listAdapterStudent = ListAdapterStudent(this) {
            val intent = Intent(this, DetailStudentActivity::class.java)
            intent.putExtra(BUNDLE_STUDENT_ID, listAdapterStudent.getPosition(it).id)
            startActivity(intent)
        }
        recyclerView.adapter = listAdapterStudent
        listAdapterStudent.setLongClickItemListener(this)
        floatingActionButton = findViewById(R.id.fab)
        progress = findViewById(R.id.progressOnLoading)
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
        dialog.setMessage("You want delete student?")
        dialog.setPositiveButton(
            "Yes"
        ) { dialogInterface, i ->
            student?.id?.let {
                viewModel.deleteStudentById(it) }
           // observerDelete()
            observerDelete()
        }
        dialog.setNegativeButton(
            "No"
        ) { dialogInterface, i ->

        }
        dialog.show()
    }
}
