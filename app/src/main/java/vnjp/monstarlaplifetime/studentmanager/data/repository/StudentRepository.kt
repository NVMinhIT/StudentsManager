package vnjp.monstarlaplifetime.studentmanager.data.repository


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import vnjp.monstarlaplifetime.studentmanager.data.api.ApiService
import vnjp.monstarlaplifetime.studentmanager.data.api.Result
import vnjp.monstarlaplifetime.studentmanager.data.reponse.Student
import vnjp.monstarlaplifetime.studentmanager.data.reponse.StudentResponse

class StudentRepository(private val apiService: ApiService) {

    suspend fun getAllStudents(): Result<List<StudentResponse>?> {

        return withContext(Dispatchers.IO) {

            val response = apiService.getAllStudents()
            if (response.isSuccessful) {
                return@withContext Result.Success(response.body())

            } else
                return@withContext Result.Error(Exception(response.message()))
        }

    }

    suspend fun getStudentById(id: Int): Result<StudentResponse?> {

        return withContext(Dispatchers.IO) {

            val response = apiService.getStudentById(id)
            if (response.isSuccessful) {
                return@withContext Result.Success(response.body())

            } else
                return@withContext Result.Error(Exception(response.message()))
        }

    }

    suspend fun addStudents(student: Student): Result<StudentResponse?> {
        return withContext(Dispatchers.IO) {
            val response = apiService.addStudent(student)
            if (response.isSuccessful) {
                return@withContext Result.Success(response.body())

            } else
                return@withContext Result.Error(Exception(response.message()))
        }

    }

    suspend fun updateStudents(id: Int, student: Student): Result<StudentResponse?> {
        return withContext(Dispatchers.IO) {
            val response = apiService.updateStudentById(id, student)
            if (response.isSuccessful) {
                return@withContext Result.Success(response.body())

            } else
                return@withContext Result.Error(Exception(response.message()))
        }

    }

    suspend fun deleteStudent(id: Int): Result<Unit?> {
        return withContext(Dispatchers.IO) {
            val response = apiService.deleteStudentById(id)
            if (response.isSuccessful) {
                return@withContext Result.Success(response.body())

            } else
                return@withContext Result.Error(Exception(response.message()))
        }

    }

}