//1
package com.ecomarket.app.ui
//2
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import com.ecomarket.app.databinding.ActivityLoginBinding
import com.ecomarket.app.data.ApiClient
import com.ecomarket.app.data.LoginBody
import com.ecomarket.app.util.Prefs
import kotlinx.coroutines.*
//3
class LoginActivity : AppCompatActivity() {
    //4
    private lateinit var b: ActivityLoginBinding
    //5
    private val scope = MainScope()
    //6
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(b.root)
        //7
        val prefs = Prefs(this)
        //8
        fun validate() {
            val e = b.etEmail.text?.toString()?.trim().orEmpty()
            val p = b.etPass.text?.toString()?.trim().orEmpty()
            b.btnLogin.isEnabled = e.contains("@") && p.length >= 4
        }
        //9
        b.etEmail.addTextChangedListener { validate() }
        b.etPass.addTextChangedListener { validate() }
        b.etPass.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE && b.btnLogin.isEnabled) { b.btnLogin.performClick() }
            false
        }
        //10
        b.btnLogin.setOnClickListener {
            b.progress.visibility = android.view.View.VISIBLE
            b.tvError.visibility = android.view.View.GONE
            scope.launch {
                try {
                    val resp = withContext(Dispatchers.IO) { ApiClient.api.login(LoginBody(b.etEmail.text.toString(), b.etPass.text.toString())) }
                    prefs.setToken(resp.token)
                    startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                    finish()
                } catch (ex: Exception) {
                    b.tvError.text = "Error de credenciales o red"
                    b.tvError.visibility = android.view.View.VISIBLE
                } finally {
                    b.progress.visibility = android.view.View.GONE
                }
            }
        }
        //11
        if (prefs.getToken()!=null) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }
    //12
    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}
//13
