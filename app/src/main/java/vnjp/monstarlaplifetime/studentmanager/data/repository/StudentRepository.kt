package vnjp.monstarlaplifetime.studentmanager.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import vnjp.monstarlaplifetime.studentmanager.data.api.ApiService
import vnjp.monstarlaplifetime.studentmanager.data.api.Result
import vnjp.monstarlaplifetime.studentmanager.data.reponse.Student

class StudentRepository(val apiService: ApiService) {

    suspend fun getAllStudents(): Result<List<Student>?> {

        return withContext(Dispatchers.IO) {

            val response = apiService.getAllStudents()
            if (response.isSuccessful) {
                return@withContext Result.Success(response.body())

            } else
                return@withContext Result.Error(Exception(response.message()))
        }

    }

    suspend fun getStudentById(id: Int): Result<Student?> {

        return withContext(Dispatchers.IO) {

            val response = apiService.getStudentById(id)
            if (response.isSuccessful) {
                return@withContext Result.Success(response.body())

            } else
                return@withContext Result.Error(Exception(response.message()))
        }

    }

    suspend fun deleteStudent(id: Int): Result<List<Student>?> {
        return withContext(Dispatchers.IO) {

            val response = apiService.deleteStudentById(id)
            if (response.isSuccessful) {
                return@withContext Result.Success(response.body())

            } else
                return@withContext Result.Error(Exception(response.message()))
        }

    }

}