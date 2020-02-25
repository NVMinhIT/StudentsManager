package vnjp.monstarlaplifetime.studentmanager.data.api

import retrofit2.Call
import retrofit2.http.*
import vnjp.monstarlaplifetime.studentmanager.data.response.Student

interface ApiService {
    @Headers("Accept: application/json")
    @GET("students")
    fun getAllStudents(): Call<List<Student>>

    @Headers("Accept: application/json")
    @DELETE("students/{id}")
    fun deleteStudentById(@Path("id") idStudent: Int): Call<Unit>

    @Headers("Accept: application/json")
    @POST("students")
    fun addStudent(@Body student: Student): Call<Student>

    @Headers("Accept: application/json")
    @GET("students/{id}")
    fun getStudentById(@Path("id") idStudent: Int): Call<Student>

    @Headers("Accept: application/json")
    @PUT("students/{id}")
    fun updateStudentById(@Path("id") idStudent: Int, @Body student: Student): Call<Student>
}