package lagecong.com.mvp

import android.content.Context
import android.os.Handler
import android.text.TextUtils

class MainActivityPresenter(context: Context, private val view:MainActivityContract.View):MainActivityContract.Presenter{

    init{
        view.setPresenter(this)
    }

    override fun validate(user: String, password: String) {
        if(user != "" || password != "") {
            view.showLoading(true)
            Handler().postDelayed({
                view.showLoading(false)
                val data = "user = $user dan password = $password"
                view.showData(data)
            }, 3000)
        }else{
            view.showToast()
        }

    }

}