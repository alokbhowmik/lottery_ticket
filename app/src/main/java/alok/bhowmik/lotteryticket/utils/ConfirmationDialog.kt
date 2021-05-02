package alok.bhowmik.lotteryticket.utils

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface

abstract class ConfirmationDialog {


    fun showConfirmationDialog(context: Context) {
        AlertDialog.Builder(context)
            .setMessage("Are you sure you want to logout ?")
            .setTitle("Logout")
            .setPositiveButton("Ok") { dialogInterface: DialogInterface, i: Int ->
                dialogInterface.dismiss()
                dialogOkDoWork()
            }
            .setNegativeButton("Cancel") { dialogInterface: DialogInterface, i: Int ->
                dialogInterface.dismiss()
            }
            .create()
            .show()
    }

    abstract fun dialogOkDoWork()
}