//1
package com.ecomarket.app.ui
//2
import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.ecomarket.app.databinding.ActivityHomeBinding
import com.ecomarket.app.util.Prefs
//3
class HomeActivity: AppCompatActivity(){
    //4
    private lateinit var b: ActivityHomeBinding
    //5
    private val pick = registerForActivityResult(ActivityResultContracts.GetContent()){ uri: Uri? ->
        if (uri!=null){ Prefs(this).setPerfil(null,null,uri.toString()) }
    }
    //6
    private val camera = registerForActivityResult(ActivityResultContracts.TakePicturePreview()){ bmp ->
        if (bmp!=null){
            val uri = android.provider.MediaStore.Images.Media.insertImage(contentResolver,bmp,"perfil","")
            Prefs(this).setPerfil(null,null,Uri.parse(uri).toString())
        }
    }
    //7
    private val reqPerm = registerForActivityResult(ActivityResultContracts.RequestPermission()){}
    //8
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(b.root)
        //9
        b.btnPerfil.setOnClickListener { startActivity(Intent(this, PerfilActivity::class.java)) }
        //10
        b.btnGallery.setOnClickListener {
            val perm = if (Build.VERSION.SDK_INT>=33) Manifest.permission.READ_MEDIA_IMAGES else Manifest.permission.READ_EXTERNAL_STORAGE
            reqPerm.launch(perm)
            pick.launch("image/*")
        }
        //11
        b.btnCamera.setOnClickListener {
            reqPerm.launch(Manifest.permission.CAMERA)
            camera.launch(null)
        }
        //12
        b.btnLogout.setOnClickListener {
            Prefs(this).clear()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
//13
