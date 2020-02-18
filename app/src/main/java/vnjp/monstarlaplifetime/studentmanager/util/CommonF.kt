package vnjp.monstarlaplifetime.studentmanager.util

import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.os.Build
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import vnjp.monstarlaplifetime.studentmanager.R

object CommonF {
    private var mContext: Context? = null

    fun CommonF(context: Context?) {
        mContext = context
    }

    fun showToastError(msg: Int) {
        showToastShort(msg, null, ToastEnum.ERROR)
    }

    fun showToastError(msg: String?) {
        showToastShort(0, msg, ToastEnum.ERROR)
    }

    @Suppress("DEPRECATION")
    fun isNetworkAvailable(): Boolean {
        try {
            val connectivityManager =
                mContext?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        } catch (ex: Exception) {
            Log.e(
                TAG,
                "isNetworkAvailable: ",
                ex
            )
        }
        return false
    }

    private fun showToastShort(i: Int, msg: String?, error: Any) {
        val toast = Toast(mContext)
        toast.duration = Toast.LENGTH_SHORT
        toast.setGravity(Gravity.TOP, 0, 50)
        val layout: View =
            LayoutInflater.from(
                mContext
            )
                .inflate(R.layout.customtoast_layout, null, false)
        val content = layout.findViewById<TextView>(R.id.toast_content)
        val linearLayout = layout.findViewById<LinearLayout>(R.id.ll_toast_layout)
        val img =
            layout.findViewById<ImageView>(R.id.toast_icon)
        if (msg != null) {
            content.text = msg
        } else {
            content.setText(mContext?.getString(i))
        }
        val drawable: Drawable =
            mContext?.getResources()!!.getDrawable(R.drawable.bg_toast)
        when (error) {
            ToastEnum.ERROR -> {
                drawable.setColorFilter(
                    ContextCompat.getColor(
                        this.mContext!!,
                        R.color.color_error
                    ), PorterDuff.Mode.SRC
                )
                img.setImageResource(R.drawable.ic_error_white)
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            linearLayout.background = drawable
        }
        toast.view = layout
        toast.show()
    }


}