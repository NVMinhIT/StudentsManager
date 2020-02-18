package vnjp.monstarlaplifetime.studentmanager.ui.screen.detailstudent

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

class DetailStudentViewModel : ViewModel() {
    private val apiService = ServiceRetrofit().getService()
    private val repository = StudentRepository(apiService)
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private var _student = MutableLiveData<Student>()
    val student: LiveData<Student> = _student


    fun getStudentById(id: Int) = viewModelScope.launch {
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
}