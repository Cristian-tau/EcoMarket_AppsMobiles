//1
package com.ecomarket.app.ui
//2
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.ecomarket.app.data.ApiClient
import com.ecomarket.app.databinding.ActivityPerfilBinding
import com.ecomarket.app.util.Prefs
import kotlinx.coroutines.*
//3
class PerfilActivity: AppCompatActivity(){
    //4
    private lateinit var b: ActivityPerfilBinding
    //5
    private val scope = MainScope()
    //6
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(b.root)
        //7
        val prefs = Prefs(this)
        val t = prefs.getToken()
        if (t!=null){
            scope.launch {
                try {
                    val me = withContext(Dispatchers.IO){ ApiClient.api.me("Bearer $t") }
                    prefs.setPerfil(me.name, me.email, prefs.getImg())
                    b.tvNombre.text = me.name
                    b.tvEmail.text = me.email
                } catch (e: Exception){
                    b.tvNombre.text = "Sin conexión"
                }
            }
        }
        //8
        val img = Prefs(this).getImg()
        if (img!=null){ b.imgPerfil.load(Uri.parse(img)) }
    }
    //9
    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}
//10
