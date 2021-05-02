package alok.bhowmik.lotteryticket.utils

import alok.bhowmik.lotteryticket.R
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable


class AppLoader {

    companion object {
        private var dialog: Dialog? = null

        fun createLoader(context: Context) {
            dialog = Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
            dialog?.apply {
                setContentView(R.layout.loader)
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }?.show()
        }

        fun hideLoader() {
            dialog?.let {
                if (it.isShowing) {
                    it.dismiss()
                }
            }
        }
    }
}