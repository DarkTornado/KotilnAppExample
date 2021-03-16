package com.darkkotlin.shorturl

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.widget.ScrollView
import android.widget.LinearLayout
import android.text.Html
import android.widget.TextView
import android.widget.Toast
import java.io.BufferedReader
import java.io.InputStreamReader

class LicenseActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar!!.title = "오픈 소스 라이선스"

        val layout = LinearLayout(this)
        layout.orientation = 1

        loadLicenseInfo(layout, "jsoup", "jsoup", "MIT License", "Jonathan Hedley", true);
        loadLicenseInfo(layout, "초간단 han.gl 링크단축", "short", "MIT License", "Lano", false)
        loadLicenseInfo(layout, "단축되기전 링크 가져오기", "long", "MIT License", "조유리즈", false)


        val pad = dip2px(16)
        layout.setPadding(pad, pad, pad, pad)
        val scroll = ScrollView(this)
        scroll.addView(layout)
        setContentView(scroll)
    }


    private fun loadLicenseInfo(layout: LinearLayout, name: String, fileName: String, license: String, dev: String, tf: Boolean) {
        val pad = dip2px(10)
        val title = TextView(this)
        if (tf) title.text = Html.fromHtml("<b>$name<b>")
        else title.text = Html.fromHtml("<br><b>$name<b>")
        title.textSize = 24f
        title.setTextColor(Color.BLACK)
        title.setPadding(pad, 0, pad, dip2px(1))
        layout.addView(title)
        val subtitle = TextView(this)
        subtitle.text = "  by $dev, $license"
        subtitle.textSize = 20f
        subtitle.setTextColor(Color.BLACK)
        subtitle.setPadding(pad, 0, pad, pad)
        layout.addView(subtitle)

        val value = loadLicense(fileName)
        val txt = TextView(this)
        txt.text = value
        txt.textSize = 17f
        txt.setTextColor(Color.BLACK)
        txt.setPadding(pad, pad, pad, pad)
        txt.setBackgroundColor(Color.argb(50, 0, 0, 0))
        layout.addView(txt)
    }

    private fun loadLicense(name: String): String {
        try {
            val isr = InputStreamReader(assets.open(name + ".txt"))
            val br = BufferedReader(isr)
            val str = StringBuilder(br.readLine())
            var line = br.readLine()
            while (line != null) {
                str.append("\n").append(line)
                line = br.readLine()
            }
            isr.close()
            br.close()
            return str.toString()
        } catch (e: Exception) {
            toast(e.toString())
            return "라이선스 정보 불러오기 실패"
        }

    }

    fun toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_LONG).show()

    fun dip2px(dips: Int) = Math.ceil((dips * resources.displayMetrics.density).toDouble()).toInt()

}
