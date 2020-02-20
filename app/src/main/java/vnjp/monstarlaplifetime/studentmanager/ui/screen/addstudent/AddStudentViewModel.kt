package vnjp.monstarlaplifetime.studentmanager.ui.screen.addstudent

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

class AddStudentViewModel : ViewModel() {
    private var apiService = ServiceRetrofit().getService()
    private var studentRepository = StudentRepository(apiService)
    private val _students = MutableLiveData<StudentResponse>()
    val students: LiveData<StudentResponse> = _students
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _isException = MutableLiveData<String>()
    val isException: LiveData<String> = _isException

    fun addStudents(student: Student) = viewModelScope.launch {
        _isLoading.value = true
        if (!CommonF.isNetworkAvailable()) {
            CommonF.showToastError(R.string.noInternet)
            return@launch
        }
        val result = studentRepository.addStudents(student)
        if (result is Result.Success) {
            _isLoading.value = false
            _students.value = result.data
        } else {
            _isLoading.value = false
            _isException.value = result.toString()
        }

    }
}