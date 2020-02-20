package vnjp.monstarlaplifetime.studentmanager.data.reponse

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Student(
    @SerializedName("address")
    @Expose
    var address: String,
    @SerializedName("age")
    @Expose
    var age: Int,
    @SerializedName("id")
    @Expose
    var id: Int,
    @SerializedName("mobile")
    @Expose
    var mobile: String,
    @SerializedName("name")
    @Expose
    var name: String
)

