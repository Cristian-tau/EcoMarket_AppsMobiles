//1
package com.ecomarket.app.util
//2
import android.content.Context
//3
class Prefs(context: Context) {
    //4
    private val sp = context.getSharedPreferences("eco_prefs", Context.MODE_PRIVATE)
    //5
    fun setToken(t: String?) { sp.edit().putString("token", t).apply() }
    //6
    fun getToken(): String? = sp.getString("token", null)
    //7
    fun setPerfil(nombre: String?, email: String?, uri: String?) {
        sp.edit().putString("nombre", nombre).putString("email", email).putString("img", uri).apply()
    }
    //8
    fun getNombre(): String? = sp.getString("nombre", null)
    //9
    fun getEmail(): String? = sp.getString("email", null)
    //10
    fun getImg(): String? = sp.getString("img", null)
    //11
    fun clear() { sp.edit().clear().apply() }
}
//12
