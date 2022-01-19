package com.github.yqyzxd.toast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wind.toastlib.ToastUtil

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ToastUtil.showToast(this,"哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈")
    }
}