@file:Suppress("DEPRECATION")

package vnjp.monstarlaplifetime.studentmanager.util

import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.os.Build
import android.text.TextUtils
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

object Common {
    private var mContext: Context? = null

    fun CommonF(context: Context?) {
        mContext = context
    }

    fun showToastSuccess(msg: Int) {
        showToastShort(msg, null, ToastEnum.SUCCESS)
    }

    fun showToastSuccess(msg: String?) {
        showToastShort(0, msg, ToastEnum.SUCCESS)
    }

    fun showToastWarning(msg: Int) {
        showToastShort(msg, null, ToastEnum.WARN)
    }

    fun showToastWarning(msg: String?) {
        showToastShort(0, msg, ToastEnum.WARN)
    }

    fun showToastError(msg: Int) {
        showToastShort(msg, null, ToastEnum.ERROR)
    }

    fun showToastError(msg: String?) {
        showToastShort(0, msg, ToastEnum.ERROR)
    }

    fun showToastInfo(msg: Int) {
        showToastShort(msg, null, ToastEnum.INFO)
    }

    fun showToastInfo(msg: String?) {
        showToastShort(0, msg, ToastEnum.INFO)
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

    fun isNullOrEmpty(s: String?): Boolean {
        return if (s == null) {
            true
        } else TextUtils.isEmpty(s)
    }

    private fun showToastShort(i: Int, msg: String?, error: Any) {
        val toast = Toast(mContext)
        toast.duration = Toast.LENGTH_SHORT
        toast.setGravity(Gravity.TOP, 0, 50)
        val layout: View =
            LayoutInflater.from(
                mContext
            )
                .inflate(R.layout.custom_layout_toast, null, false)
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
            ToastEnum.WARN -> {
                drawable.setColorFilter(
                    ContextCompat.getColor(
                        this.mContext!!,
                        R.color.color_warn
                    ), PorterDuff.Mode.SRC
                )
                img.setImageResource(R.drawable.ic_warning_white)
            }
            ToastEnum.INFO -> {
                drawable.setColorFilter(
                    ContextCompat.getColor(
                        this.mContext!!,
                        R.color.color_info
                    ), PorterDuff.Mode.SRC
                )
                img.setImageResource(R.drawable.ic_info_white)
            }
            ToastEnum.SUCCESS -> {
                drawable.setColorFilter(
                    ContextCompat.getColor(
                        this.mContext!!,
                        R.color.color_success
                    ), PorterDuff.Mode.SRC
                )
                img.setImageResource(R.drawable.ic_success_white)
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            linearLayout.background = drawable
        }
        toast.view = layout
        toast.show()
    }


}