package vnjp.monstarlaplifetime.studentmanager.data.api


import retrofit2.Response
import retrofit2.http.*
import vnjp.monstarlaplifetime.studentmanager.data.reponse.Student

interface ApiService {
    @Headers("Accept: application/json")
    @GET("students")
    suspend fun getAllStudents(): Response<List<Student>>

    @Headers("Accept: application/json")
    @DELETE("students/{id}")
    suspend fun deleteStudentById(@Path("id") idStudent: Int): Response<List<Student>>

    @Headers("Accept: application/json")
    @POST("students")
    suspend fun addStudent(@Body student: Student): Response<Student>

    @Headers("Accept: application/json")
    @GET("students/{id}")
    suspend fun getStudentById(@Path("id") idStudent: Int): Response<Student>
}