package vnjp.monstarlaplifetime.studentmanager.data.repository


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vnjp.monstarlaplifetime.studentmanager.data.api.ApiService
import vnjp.monstarlaplifetime.studentmanager.data.reponse.Student
import vnjp.monstarlaplifetime.studentmanager.data.reponse.StudentResponse

class StudentRepository(private val apiService: ApiService) {

    fun getAllStudents(onDataLoaded: (List<StudentResponse>?) -> Unit, ex: (String?) -> Unit) {
        val response = apiService.getAllStudents()
        response.enqueue(object : Callback<List<StudentResponse>> {
            override fun onResponse(
                call: Call<List<StudentResponse>>,
                response: Response<List<StudentResponse>>
            ) {
                if (response.isSuccessful) {
                    onDataLoaded.invoke(response.body())
                }
            }

            override fun onFailure(call: Call<List<StudentResponse>>, t: Throwable) {
                ex.invoke(t.message.toString())
            }
        })

    }

    fun getStudentById(
        id: Int,
        onDataLoaded: (StudentResponse?) -> Unit,
        ex: (String?) -> Unit
    ) {
        val response = apiService.getStudentById(id)
        response.enqueue(object : Callback<StudentResponse?> {
            override fun onResponse(
                call: Call<StudentResponse?>,
                response: Response<StudentResponse?>
            ) {
                if (response.isSuccessful) {
                    onDataLoaded.invoke(response.body())
                }
            }

            override fun onFailure(call: Call<StudentResponse?>, t: Throwable) {
                ex.invoke(t.message.toString())
            }
        })

    }

    fun addStudents(
        student: Student,
        onDataLoaded: (StudentResponse?) -> Unit,
        ex: (String?) -> Unit
    ) {
        val response = apiService.addStudent(student)
        response.enqueue(object : Callback<StudentResponse?> {
            override fun onResponse(
                call: Call<StudentResponse?>,
                response: Response<StudentResponse?>
            ) {
                if (response.isSuccessful) {
                    onDataLoaded.invoke(response.body())
                }
            }

            override fun onFailure(call: Call<StudentResponse?>, t: Throwable) {
                ex.invoke(t.message.toString())
            }
        })

    }

    fun updateStudents(
        id: Int,
        student: Student,
        onDataLoaded: (StudentResponse?) -> Unit,
        ex: (String?) -> Unit
    ) {
        val response = apiService.updateStudentById(id, student)
        response.enqueue(object : Callback<StudentResponse?> {
            override fun onResponse(
                call: Call<StudentResponse?>,
                response: Response<StudentResponse?>
            ) {
                if (response.isSuccessful) {
                    onDataLoaded.invoke(response.body())
                }
            }

            override fun onFailure(call: Call<StudentResponse?>, t: Throwable) {
                ex.invoke(t.message.toString())
            }
        })

    }

    fun deleteStudentId(
        id: Int,
        onDataLoaded: (Unit?) -> Unit,
        ex: (String?) -> Unit
    ) {
        val response = apiService.deleteStudentById(id)
        response.enqueue(object : Callback<Unit?> {
            override fun onResponse(
                call: Call<Unit?>,
                response: Response<Unit?>
            ) {
                if (response.isSuccessful) {
                    onDataLoaded.invoke(response.body())
                }
            }
            override fun onFailure(call: Call<Unit?>, t: Throwable) {
                ex.invoke(t.message.toString())
            }
        })

    }
}