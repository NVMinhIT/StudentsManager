package vnjp.monstarlaplifetime.studentmanager.ui.screen.liststudent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import vnjp.monstarlaplifetime.studentmanager.R
import vnjp.monstarlaplifetime.studentmanager.data.api.Result
import vnjp.monstarlaplifetime.studentmanager.data.api.ServiceRetrofit
import vnjp.monstarlaplifetime.studentmanager.data.reponse.Student
import vnjp.monstarlaplifetime.studentmanager.data.repository.StudentRepository
import vnjp.monstarlaplifetime.studentmanager.util.CommonF

class ListStudentViewModel : ViewModel() {
    private val apiService = ServiceRetrofit().getService()
    private val repository = StudentRepository(apiService)
    private val _students = MutableLiveData<List<Student>>()
    val students: LiveData<List<Student>> = _students
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _isException = MutableLiveData<String>()
    val isException: LiveData<String> = _isException


    private val _deleteStudent = MutableLiveData<List<Student>>()
    val delStudent: LiveData<List<Student>> = _deleteStudent
    fun getAllStudent() = viewModelScope.launch {
        _isLoading.value = true
        if (!CommonF.isNetworkAvailable()) {
            CommonF.showToastError(R.string.noInternet)
            return@launch
        }
        val result = repository.getAllStudents()
        if (result is Result.Success) {
            _isLoading.value = false
            _students.value = result.data
        } else {
            _isLoading.value = false
            _isException.value = result.toString()
        }
    }

    fun deleteStudent(id: Int) = viewModelScope.launch {
        _isLoading.value = true
        if (!CommonF.isNetworkAvailable()) {
            CommonF.showToastError(R.string.noInternet)
            return@launch
        }
        val result = repository.deleteStudent(id)
        if (result is Result.Success) {
            _isLoading.value = false
            _deleteStudent.value = result.data
        } else {
            _isLoading.value = false
            _isException.value = result.toString()
        }
    }


}