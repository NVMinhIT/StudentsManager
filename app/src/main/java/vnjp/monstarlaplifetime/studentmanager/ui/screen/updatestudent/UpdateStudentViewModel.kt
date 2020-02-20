package vnjp.monstarlaplifetime.studentmanager.ui.screen.updatestudent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import vnjp.monstarlaplifetime.studentmanager.R
import vnjp.monstarlaplifetime.studentmanager.data.api.Result
import vnjp.monstarlaplifetime.studentmanager.data.api.ServiceRetrofit
import vnjp.monstarlaplifetime.studentmanager.data.reponse.Student
import vnjp.monstarlaplifetime.studentmanager.data.reponse.StudentResponse
import vnjp.monstarlaplifetime.studentmanager.data.repository.StudentRepository
import vnjp.monstarlaplifetime.studentmanager.util.CommonF

class UpdateStudentViewModel : ViewModel() {
    private val apiService = ServiceRetrofit().getService()
    private val repository = StudentRepository(apiService)
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private var _student = MutableLiveData<StudentResponse>()
    val student: LiveData<StudentResponse> = _student
    private var _updateStudent = MutableLiveData<StudentResponse>()
    val updateStudent: LiveData<StudentResponse> = _updateStudent
    fun getStudentId(id: Int) = viewModelScope.launch {
        _isLoading.value = true
        if (!CommonF.isNetworkAvailable()) {
            CommonF.showToastError(R.string.noInternet)
            return@launch
        }
        val result = repository.getStudentById(id)
        if (result is Result.Success) {
            _isLoading.value = false
            _student.value = result.data
        } else {
            _isLoading.value = false
            // _isException.value = result.toString()
        }
    }

    fun updateStudent(idUpdate: Int, student: Student) = viewModelScope.launch {
        _isLoading.value = true
        if (!CommonF.isNetworkAvailable()) {
            CommonF.showToastError(R.string.noInternet)
            return@launch
        }
        val result = repository.updateStudents(idUpdate, student)
        if (result is Result.Success) {
            _isLoading.value = false
            _updateStudent.value = result.data
        } else {
            _isLoading.value = false
            // _isException.value = result.toString()
        }
    }
}