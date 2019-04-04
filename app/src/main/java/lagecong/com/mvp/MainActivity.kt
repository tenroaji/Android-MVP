package lagecong.com.mvp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),MainActivityContract.View {
var mPresenter : MainActivityContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MainActivityPresenter(this,this)
        mLogin.setOnClickListener {
            mPresenter?.validate(editUser.text.toString(),editPassword.text.toString())
        }
    }

    override fun showToast(succes: Boolean) {
        val b = "a"
        val a = b?:"lapar"
        if (succes){
            Toast.makeText(this,a,Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(this,a,Toast.LENGTH_LONG).show()
        }
    }

    override fun setPresenter(presenter: MainActivityContract.Presenter) {
        mPresenter = presenter
    }
}
