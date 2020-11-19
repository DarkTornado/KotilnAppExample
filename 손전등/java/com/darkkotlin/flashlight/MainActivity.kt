package com.darkkotlin.flashlight

import android.app.Activity
import android.content.Context
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ToggleButton

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = LinearLayout(this)
        layout.orientation = 1

        val btn = ToggleButton(this)
        btn.textOn = "플래시 켜짐"
        btn.textOff = "플래시 꺼짐"
        btn.isChecked = false
        btn.setOnCheckedChangeListener { view, isChecked ->
            val cm = getSystemService(Context.CAMERA_SERVICE) as CameraManager
            cm.setTorchMode(cm.cameraIdList[0], isChecked)
        }
        layout.addView(btn)

        setContentView(layout)
    }
}
