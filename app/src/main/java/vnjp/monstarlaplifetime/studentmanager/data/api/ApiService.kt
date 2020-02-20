package vnjp.monstarlaplifetime.studentmanager.data.api

import retrofit2.Response
import retrofit2.http.*
import vnjp.monstarlaplifetime.studentmanager.data.reponse.Student
import vnjp.monstarlaplifetime.studentmanager.data.reponse.StudentResponse

interface ApiService {
    @Headers("Accept: application/json")
    @GET("students")
    suspend fun getAllStudents(): Response<List<StudentResponse>>

    @Headers("Accept: application/json")
    @DELETE("students/{id}")
    suspend fun deleteStudentById(@Path("id") idStudent: Int) : Response<Unit>
//    suspend fun deleteStudentById(@Path("id") idStudent: Int): Response<okhttp3.Headers>
    @Headers("Accept: application/json")
    @POST("students")
    suspend fun addStudent(@Body student: Student): Response<StudentResponse>

    @Headers("Accept: application/json")
    @GET("students/{id}")
    suspend fun getStudentById(@Path("id") idStudent: Int): Response<StudentResponse>

    @Headers("Accept: application/json")
    @PUT("students/{id}")
    suspend fun updateStudentById(@Path("id") idStudent: Int, @Body student: Student): Response<StudentResponse>
}