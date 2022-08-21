package com.tui.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tui.app.utils.InternetConnectivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity(){

    @Inject
    lateinit var internetConnectivity:InternetConnectivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onStart() {
        super.onStart()
        internetConnectivity.registerCallback(this)
    }

    override fun onStop() {
        super.onStop()
        internetConnectivity.unregisterCallback(this)
    }

}