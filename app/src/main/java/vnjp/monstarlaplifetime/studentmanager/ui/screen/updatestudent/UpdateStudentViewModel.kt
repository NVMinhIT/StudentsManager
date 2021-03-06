package vnjp.monstarlaplifetime.studentmanager.ui.screen.updatestudent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import vnjp.monstarlaplifetime.studentmanager.R
import vnjp.monstarlaplifetime.studentmanager.data.api.ServiceRetrofit
import vnjp.monstarlaplifetime.studentmanager.data.response.Student
import vnjp.monstarlaplifetime.studentmanager.data.repository.StudentRepository
import vnjp.monstarlaplifetime.studentmanager.util.Common

class UpdateStudentViewModel : ViewModel() {
    private val apiService = ServiceRetrofit().getService()
    private val repository = StudentRepository(apiService)
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private var _student = MutableLiveData<Student>()
    val student: LiveData<Student> = _student
    private val _isException = MutableLiveData<String>()
    val isException: LiveData<String> = _isException
    private var _updateStudent = MutableLiveData<Student>()
    val updateStudent: LiveData<Student> = _updateStudent

    fun getStudentId(id: Int) {
        _isLoading.value = true
        if (!Common.isNetworkAvailable()) {
            Common.showToastError(R.string.noInternet)
            return
        }
        repository.getStudentById(id, onDataLoaded = {
            _isLoading.value = false
            _student.value = it
        }, ex = {
            _isLoading.value = false
            _isException.value = it.toString()
        })
    }

    fun updateStudent(id: Int, student: Student) {
        _isLoading.value = true
        if (!Common.isNetworkAvailable()) {
            Common.showToastError(R.string.noInternet)
            return
        }
        repository.updateStudents(id, student, onDataLoaded = {
            _isLoading.value = false
            _updateStudent.value = it
        }, ex = {
            _isLoading.value = false
            _isException.value = it.toString()
        })
    }
}