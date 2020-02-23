package vnjp.monstarlaplifetime.studentmanager.ui.screen.liststudent
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vnjp.monstarlaplifetime.studentmanager.R
import vnjp.monstarlaplifetime.studentmanager.data.reponse.StudentResponse
import vnjp.monstarlaplifetime.studentmanager.util.Common
class ListStudentAdapter(
    private val context: Context, private val itemClick: (Int) -> Unit
) : RecyclerView.Adapter<ListStudentAdapter.MyViewHolder>() {
    private var listStudent: List<StudentResponse> = emptyList()
    private var longClickItemListener: ILongClickItemListener? = null
    fun setListStudent(list: List<StudentResponse>) {
        listStudent = list
        notifyDataSetChanged()
    }

    fun setLongClickItemListener(studentListener: ILongClickItemListener) {
        longClickItemListener = studentListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_list_student, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listStudent.size
    }

    fun getPosition(position: Int): StudentResponse {
        return listStudent.get(position)

    }
    fun filter(name: String) {
        if (Common.isNullOrEmpty(name)) {
            listStudent.let {
                setListStudent(it)
            }
        } else {
            val orderList: ArrayList<StudentResponse> =
                java.util.ArrayList<StudentResponse>()
            for (item in this.listStudent) {
                if (item.name.contains(name)) {
                    orderList.add(item)
                }
            }
            setListStudent(orderList)
        }

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val current = listStudent[position]
        holder.bindData(current)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var nameStudent: TextView = itemView.findViewById(R.id.tvNameStudent)
        private var ageStudent: TextView = itemView.findViewById(R.id.tvAgeStudent)

        init {
            itemView.setOnClickListener {
                itemClick(adapterPosition)
            }
        }

        fun bindData(student: StudentResponse) {
            nameStudent.text = student.name
            ageStudent.text = student.age.toString()
            this.itemView.setOnLongClickListener {
                longClickItemListener?.onLongClickItemStudent(student)
                true
            }

        }

    }

    interface ILongClickItemListener {
        fun onLongClickItemStudent(
            student: StudentResponse?
        )
    }
}