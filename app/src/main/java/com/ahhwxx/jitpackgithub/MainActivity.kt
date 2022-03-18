package com.ahhwxx.jitpackgithub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ahhwxx.lib.MyLog

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MyLog.i(this.javaClass.simpleName)
    }
}