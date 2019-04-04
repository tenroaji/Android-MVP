package lagecong.com.mvp.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ModelPagination {
    @SerializedName("total")
    @Expose
    var total: Int? = null
    @SerializedName("per_page")
    @Expose
    var perPage: Int? = null
    @SerializedName("current_page")
    @Expose
    var currentPage: Int? = null
    @SerializedName("last_page")
    @Expose
    var lastPage: Int? = null
    @SerializedName("from")
    @Expose
    var from: Int? = null
    @SerializedName("to")
    @Expose
    var to: Int? = null
}
