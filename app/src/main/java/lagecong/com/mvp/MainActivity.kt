package lagecong.com.mvp

import android.opengl.Visibility
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
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

    override fun showLoading(show: Boolean) {
       if (show){
           progresBar.visibility = View.VISIBLE
       }else {
           progresBar.visibility = View.GONE
       }
    }
    override fun showToast() {
       Toast.makeText(this@MainActivity,"Username / Password Tidak Boleh Kosong",
           Toast.LENGTH_LONG).show()
    }
    override fun showData(data: String) {
        textHasil.text = data
    }
    override fun setPresenter(presenter: MainActivityContract.Presenter) {
        mPresenter = presenter
    }
}
