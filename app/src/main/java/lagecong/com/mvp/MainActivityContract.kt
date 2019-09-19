package lagecong.com.mvp

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