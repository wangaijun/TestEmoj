package com.waj.testemoj

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.Spanned
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

class MainActivity : Activity() {
    var str:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        et1.addTextChangedListener(NotEmojiWatcher(et1,this))
//        val filter = object : InputFilter{
//            override fun filter(
//                source: CharSequence?,
//                start: Int,
//                end: Int,
//                dest: Spanned?,
//                dstart: Int,
//                dend: Int
//            ): CharSequence {
//                if (StringUtil.filterCharToNormal(et1.text.toString()).equals("")){
//                    Toast.makeText(this@MainActivity,"不支持的字符",Toast.LENGTH_SHORT).show()
//                    return ""
//                }
//                return source?:""
//            }
//        }
//        et1.filters = arrayOf(filter)

        et1.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                str = s.toString()
                Log.e("waj123",str)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        btn.setOnClickListener {
            et2.text = et1.text
            val body = "{\"attachments\":[],\"delayReasons\":[],\"description\":\"$str\",\"id\":\"\",\"occurredDate\":1540809384132,\"pm\":\"\",\"solution\":\"\",\"temperature\":\"3℃~15℃\",\"weather\":\"晴\",\"windPower\":\"西北风微风\"}"
            val token = "02f13e59-cc57-4ead-9a11-f722ac105007"
            val url = "http://bim5d-pro-test.glodon.com/api/v1/projects/15633293392477074065/weeklyPlan/weeklyTask/id/1577491176339908/weeklyTrackItem/save"
            val request = Request.Builder()
                .url(url)
                .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"),body))
                .header("Content-Type","application/json; charset=utf-8")
                .addHeader("Authorization","Bearer $token")
                .build()
            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    tvResult.post { tvResult.append(e.localizedMessage) }
                }

                override fun onResponse(call: Call, response: Response) {
                    val str = response.body()?.string()
                    tvResult.post { tvResult.append(str)}
                }
            })
        }
    }
}
