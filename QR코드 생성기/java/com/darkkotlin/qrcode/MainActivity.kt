package com.darkkotlin.qrcode

import android.app.Activity
import android.os.Bundle
import android.webkit.WebView
import android.widget.*

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = LinearLayout(this)
        layout.orientation = 1

        val txt1 = TextView(this)
        txt1.text = "내용 : "
        txt1.textSize = 18f
        layout.addView(txt1)
        val txt2 = EditText(this)
        txt2.hint = "QR 코드에 담을 내용 입력..."
        layout.addView(txt2)

        val web = WebView(this)
        val btn = Button(this)
        btn.text = "생성"
        btn.setOnClickListener {
            val input = txt2.text.toString()
            if (input.isBlank()) Toast.makeText(this, "입력된 내용이 없어요.", Toast.LENGTH_LONG).show()
            else web.loadData("<img width=100% src='https://chart.apis.google.com/chart?cht=qr&chs=320x320&chl=$input'>",
                    "text/html; charset=UTF-8", null);
        }
        layout.addView(btn);
        layout.addView(web);

        val pad = dip2px(16)
        layout.setPadding(pad, pad, pad, pad)
        val scroll = ScrollView(this)
        scroll.addView(layout)
        setContentView(scroll)
    }


    fun dip2px(dips: Int): Int {
        return Math.ceil((dips * resources.displayMetrics.density).toDouble()).toInt()
    }
}
