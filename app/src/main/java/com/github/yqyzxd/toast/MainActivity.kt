package com.github.yqyzxd.toast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wind.toastlib.ToastUtil

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ToastUtil.showToast(
            this,
            "健身房生活方式开发商康师傅还是开发客户是客服回复是客服核实客户富士康富士康还是符合事实付款后是否符合是否还会发回复时候发货后恢复"
        )

    }
}