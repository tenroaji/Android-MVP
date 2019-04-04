package lagecong.com.mvp

import android.content.Context
import android.text.TextUtils

class MainActivityPresenter(context: Context, private val view:MainActivityContract.View):MainActivityContract.Presenter{

    init{
        view.setPresenter(this)
    }

    override fun validate(user: String, password: String) {
        if (TextUtils.equals(user,"docotel") && TextUtils.equals(password,"grup")){
            view.showToast(true)
        }else{
            view.showToast(false)
        }
    }

}