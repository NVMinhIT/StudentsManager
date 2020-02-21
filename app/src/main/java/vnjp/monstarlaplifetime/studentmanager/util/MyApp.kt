package vnjp.monstarlaplifetime.studentmanager.util

import android.app.Application
import vnjp.monstarlaplifetime.studentmanager.util.Common.CommonF

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        CommonF(this)
    }
}