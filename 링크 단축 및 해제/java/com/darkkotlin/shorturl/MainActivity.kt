package com.darkkotlin.shorturl

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import org.json.JSONObject
import org.jsoup.Jsoup

class MainActivity : Activity() {

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menu.add(0, 0, 0, "오픈 소스 라이선스").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        startActivity(Intent(this, LicenseActivity::class.java))
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = LinearLayout(this)
        layout.orientation = 1

        val txt1 = TextView(this)
        txt1.text = "URL : "
        txt1.textSize = 18f
        layout.addView(txt1)
        val txt2 = EditText(this)
        txt2.hint = "URL을 입력하세요..."
        layout.addView(txt2)

        val txt3 = EditText(this)

        val sho = Button(this)
        sho.text = "단축"
        sho.setOnClickListener {
            val input = txt2.text.toString()
            if (input.isBlank()) {
                toast("입력한 내용이 없어요")
            } else {
                toast("링크 단축을 시작합니다...")
                Thread({ compress(input, txt3) }).start()
            }
        }
        layout.addView(sho)
        val lon = Button(this)
        lon.text = "단축 해제"
        lon.setOnClickListener {
            val input = txt2.text.toString()
            if (input.isBlank()) {
                toast("입력한 내용이 없어요")
            } else {
                toast("링크 단축 해제를 시작합니다...")
                Thread({ decompress(input, txt3) }).start()
            }
        }
        layout.addView(lon)

        txt3.hint = "결과가 출력되는 곳"
        layout.addView(txt3)

        val maker = TextView(this)
        maker.text = "\n© 2021 Dark Tornado, All rights reserved.\n"
        maker.textSize = 13f
        maker.gravity = Gravity.CENTER
        layout.addView(maker)

        val pad = dip2px(16)
        layout.setPadding(pad, pad, pad, pad)

        val scroll = ScrollView(this)
        scroll.addView(layout)
        setContentView(scroll)
    }

    fun compress(url: String, txt: EditText) {
        val data = Jsoup.connect("https://han.gl/shorten")
                .data("url", url)
                .post().text()
        val error = JSONObject(data)["error"] as Int
        val result: String;
        if (error == 0) result = JSONObject(data)["short"] as String
        else result = JSONObject(data)["msg"] as String
        runOnUiThread {
            txt.setText(result)
            toast("링크 단축 완료")
        }
    }

    fun decompress(url: String, txt: EditText) {
        val data = Jsoup.connect("https://checkshorturl.com/expand.php")
                .header("content-type", "application/x-www-form-urlencoded")
                .header("referer", "https://checkshorturl.com/")
                .data("u", url)
                .ignoreContentType(true)
                .post();
        val result = data.select("td").select("a").first().text()
        runOnUiThread {
            txt.setText(result)
            toast("링크 단축 해제 완료")
        }
    }

    fun toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_LONG).show()

    fun dip2px(dips: Int) = Math.ceil((dips * resources.displayMetrics.density).toDouble()).toInt()

}
