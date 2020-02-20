package vnjp.monstarlaplifetime.studentmanager.data.reponse

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class StudentResponse(
    @SerializedName("id")
    @Expose
    var id: Int,
    @SerializedName("name")
    @Expose
    var name: String,
    @SerializedName("age")
    @Expose
    var age: Int,
    @SerializedName("address")
    @Expose
    var address: String,
    @SerializedName("mobile")
    @Expose
    var mobile: String
)

