package vnjp.monstarlaplifetime.studentmanager.ui.screen.liststudent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import vnjp.monstarlaplifetime.studentmanager.R
import vnjp.monstarlaplifetime.studentmanager.data.api.ServiceRetrofit
import vnjp.monstarlaplifetime.studentmanager.data.reponse.StudentResponse
import vnjp.monstarlaplifetime.studentmanager.data.repository.StudentRepository
import vnjp.monstarlaplifetime.studentmanager.util.Common

class ListStudentViewModel : ViewModel() {
    private val apiService = ServiceRetrofit().getService()
    private val repository = StudentRepository(apiService)
    private val _students = MutableLiveData<List<StudentResponse>>()
    val students: LiveData<List<StudentResponse>> = _students
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _isException = MutableLiveData<String>()
    val isException: LiveData<String> = _isException
    private val _deleteStudent = MutableLiveData<Unit>()
    val delStudent: LiveData<Unit> = _deleteStudent
    fun getAllStudent() {
        _isLoading.value = true
        if (!Common.isNetworkAvailable()) {
            Common.showToastError(R.string.noInternet)
            return
        }
        repository.getAllStudents(onDataLoaded = {
            _isLoading.value = false
            _students.value = it
        }, ex = {
            _isLoading.value = false
            _isException.value = it.toString()
        })
    }

    fun deleteStudentById(id: Int) {
        _isLoading.value = true
        if (!Common.isNetworkAvailable()) {
            Common.showToastError(R.string.noInternet)
            return
        }
        repository.deleteStudentId(id, onDataLoaded = {
            _isLoading.value = false
            _deleteStudent.value = it
        }, ex = {
            _isLoading.value = false
            _isException.value = it.toString()
        })
    }

}