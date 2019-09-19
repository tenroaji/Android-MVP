package lagecong.com.mvp
/**
 * Created by Andi Tenroaji Ahmad on 9/20/2019.
 */
interface MainActivityContract {
    interface View {
        fun showLoading(show:Boolean)
        fun showData(data:String)
        fun showToast()
        fun setPresenter(presenter:Presenter)
    }

    interface Presenter {
        fun validate(user:String,password:String)
    }

}