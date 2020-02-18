package vnjp.monstarlaplifetime.studentmanager.ui.screen.liststudent

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import vnjp.monstarlablifetime.mochichat.data.model.OptionalButton
import vnjp.monstarlablifetime.mochichat.data.model.OptionalClickListener
import vnjp.monstarlablifetime.mochichat.screen.SwipeHelper
import vnjp.monstarlaplifetime.studentmanager.R
import vnjp.monstarlaplifetime.studentmanager.ui.screen.detailstudent.DetailStudentActivity

@Suppress("DEPRECATION")
class ListStudentActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: ListStudentViewModel
    private lateinit var listAdapterStudent: ListAdapterStudent
    private lateinit var progress: ProgressBar
    private var swipeRefreshLayout: SwipeRefreshLayout? = null

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

    fun observerList() {
        viewModel.delStudent.observe(this, Observer {
            listAdapterStudent.notifyDataSetChanged()
        })
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ListStudentViewModel::class.java)
    }

    private fun initEvent() {
        recyclerView = findViewById(R.id.rvListStudent)
        recyclerView.layoutManager = LinearLayoutManager(this)
        listAdapterStudent = ListAdapterStudent(this) {
            val intent = Intent(this, DetailStudentActivity::class.java)
            intent.putExtra(BUNDLE_STUDENT_ID, listAdapterStudent.getPosition(it).id)
            startActivity(intent)
        }
        recyclerView.adapter = listAdapterStudent
        swipeRefreshLayout?.setOnRefreshListener {
            viewModel.getAllStudent()
            swipeRefreshLayout!!.isRefreshing = false
        }
        val swipeHelper = object : SwipeHelper(this, recyclerView, 200) {
            override fun initOptionalButton(
                viewHolder: RecyclerView.ViewHolder,
                buffer: MutableList<OptionalButton>
            ) {
                buffer.add(
                    OptionalButton(applicationContext,
                        "Has Seen",
                        30,
                        R.drawable.ic_bin,
                        Color.parseColor("#F44336"),
                        object : OptionalClickListener {
                            override fun onClick(pos: Int) {
//                                Toast.makeText(
//                                    applicationContext,
//                                    listAdapterStudent.getPosition(pos).id.toString(),
//                                    Toast.LENGTH_SHORT
//                                )
//                                    .show()
                                //"on click check $pos"
                                viewModel.deleteStudent(listAdapterStudent.getPosition(pos).id)
                                observerList()
                            }
                        }
                    )
                )
            }

        }


    }

    private fun initView() {
        progress = findViewById(R.id.progressOnLoading)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)

    }
}
