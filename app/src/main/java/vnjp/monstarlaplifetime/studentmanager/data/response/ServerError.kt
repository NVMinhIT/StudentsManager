package vnjp.monstarlaplifetime.studentmanager.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ServerError(
    @SerializedName("timestamp")
    @Expose
    var timestamp: String? = null,
    @SerializedName("status")
    @Expose
    var status: Int? = null,
    @SerializedName("error")
    @Expose
    var error: String? = null,
    @SerializedName("message")
    @Expose
    var message: String? = null,
    @SerializedName("path")
    @Expose
    var path: String? = null
)


