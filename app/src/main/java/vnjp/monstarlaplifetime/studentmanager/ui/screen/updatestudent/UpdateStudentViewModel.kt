package vnjp.monstarlaplifetime.studentmanager.ui.screen.updatestudent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import vnjp.monstarlaplifetime.studentmanager.R
import vnjp.monstarlaplifetime.studentmanager.data.api.ServiceRetrofit
import vnjp.monstarlaplifetime.studentmanager.data.reponse.Student
import vnjp.monstarlaplifetime.studentmanager.data.reponse.StudentResponse
import vnjp.monstarlaplifetime.studentmanager.data.repository.StudentRepository
import vnjp.monstarlaplifetime.studentmanager.util.Common

class UpdateStudentViewModel : ViewModel() {
    private val apiService = ServiceRetrofit().getService()
    private val repository = StudentRepository(apiService)
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private var _student = MutableLiveData<StudentResponse>()
    val student: LiveData<StudentResponse> = _student
    private val _isException = MutableLiveData<String>()
    val isException: LiveData<String> = _isException
    private var _updateStudent = MutableLiveData<StudentResponse>()
    val updateStudent: LiveData<StudentResponse> = _updateStudent

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