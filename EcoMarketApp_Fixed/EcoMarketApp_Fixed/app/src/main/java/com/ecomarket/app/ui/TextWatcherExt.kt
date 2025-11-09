//1
package com.ecomarket.app.ui
//2
import android.text.Editable
import android.text.TextWatcher
//3
fun androidx.appcompat.widget.AppCompatEditText.addTextChangedListener(on: ()->Unit){
    addTextChangedListener(object: TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { on() }
        override fun afterTextChanged(s: Editable?) {}
    })
}
//4
