package lagecong.com.mvp

interface MainActivityContract {
    interface View {
        fun showToast(succes:Boolean)
        fun setPresenter(presenter:Presenter)
    }

    interface Presenter {
        fun validate(user:String,password:String)
    }

}