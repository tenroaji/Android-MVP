package lagecong.com.mvp.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ModelDiagnostic {
    @SerializedName("code")
    @Expose
    var code: Int? = null
    @SerializedName("status")
    @Expose
    var status: String? = null
}
