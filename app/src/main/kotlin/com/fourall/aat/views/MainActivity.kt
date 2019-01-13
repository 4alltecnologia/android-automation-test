package com.fourall.aat.views

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import com.fourall.aat.R
import com.fourall.aat.extensions.closeKeyboard
import com.fourall.aat.extensions.isKeyboardOpened
import com.fourall.aat.models.User
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var nameIsOk = false
    private var ageIsOk = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // toolbar title
        title = getString(R.string.app_name)

        textInputEditTextName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                nameIsOk = p0?.isNotEmpty() ?: false
                enableButtonNext(ageIsOk && nameIsOk)
                enableButtonClean(nameIsOk || ageIsOk)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        textInputEditTextAge.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                ageIsOk = p0?.isNotEmpty() ?: false
                enableButtonNext(nameIsOk && ageIsOk)
                enableButtonClean(nameIsOk || ageIsOk)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        btnOk.setOnClickListener {
            if (isKeyboardOpened()) closeKeyboard(currentFocus)

            val age = textInputEditTextAge.text.toString()
            val name = textInputEditTextName.text.toString()

            val person = User(age, name)

            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("person", person)
            startActivity(intent)
        }

        btnClean.setOnClickListener {
            textInputEditTextName.text.clear()
            textInputEditTextAge.text.clear()
        }
    }

    override fun onResume() {
        super.onResume()
        enableButtonNext(nameIsOk && ageIsOk)
        enableButtonClean(nameIsOk || ageIsOk)
    }

    private fun enableButtonNext(enableButton: Boolean) {
        btnOk.isEnabled = enableButton
    }

    private fun enableButtonClean(enableButton: Boolean) {
        btnClean.isEnabled = enableButton
    }

}
