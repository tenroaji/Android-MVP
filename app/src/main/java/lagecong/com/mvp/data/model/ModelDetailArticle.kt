package lagecong.com.mvp.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ModelDetailArticle(
    @SerializedName("response")
    @Expose
    var response: ModelDetailResponse?,
    @SerializedName("diagnostic")
    @Expose
    var diagnostic: ModelDiagnostic
)
