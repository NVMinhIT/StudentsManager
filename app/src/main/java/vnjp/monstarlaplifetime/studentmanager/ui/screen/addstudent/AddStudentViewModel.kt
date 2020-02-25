package vnjp.monstarlaplifetime.studentmanager.ui.screen.addstudent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import vnjp.monstarlaplifetime.studentmanager.R
import vnjp.monstarlaplifetime.studentmanager.data.api.ServiceRetrofit
import vnjp.monstarlaplifetime.studentmanager.data.response.Student
import vnjp.monstarlaplifetime.studentmanager.data.repository.StudentRepository
import vnjp.monstarlaplifetime.studentmanager.util.Common

class AddStudentViewModel : ViewModel() {
    private var apiService = ServiceRetrofit().getService()
    private var studentRepository = StudentRepository(apiService)
    private val _students = MutableLiveData<Student>()
    val students: LiveData<Student> = _students
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _isException = MutableLiveData<String>()
    val isException: LiveData<String> = _isException

    fun addStudents(student: Student) {
        _isLoading.value = true
        if (!Common.isNetworkAvailable()) {
            Common.showToastError(R.string.noInternet)
            return
        }
        studentRepository.addStudents(student, onDataLoaded = {
            _isLoading.value = false
            _students.value = it
        }, ex = {
            _isLoading.value = false
            _isException.value = it.toString()
        })
    }


}