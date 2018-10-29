package com.waj.testemoj

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        et1.addTextChangedListener(NotEmojiWatcher(et1,this))

        et1.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                et2.text = et1.text

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        btn.setOnClickListener {
            et2.text = et1.text
        }
    }
}
