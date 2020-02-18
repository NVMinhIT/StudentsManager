package vnjp.monstarlaplifetime.studentmanager.ui.screen.liststudent

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vnjp.monstarlaplifetime.studentmanager.R
import vnjp.monstarlaplifetime.studentmanager.data.reponse.Student

class ListAdapterStudent(
    private val context: Context, private val itemClick: (Int) -> Unit
) : RecyclerView.Adapter<ListAdapterStudent.MyViewHolder>() {
    private var listStudent: List<Student> = emptyList()

    fun setListStudent(list: List<Student>) {
        listStudent = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_list_student, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listStudent.size
    }

    fun getPosition(position: Int): Student {
        return listStudent.get(position)

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

        fun bindData(student: Student) {
            nameStudent.text = student.name
            ageStudent.text = student.age.toString()
        }

    }

}