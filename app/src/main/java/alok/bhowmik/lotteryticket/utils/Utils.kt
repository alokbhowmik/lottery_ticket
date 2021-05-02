package alok.bhowmik.lotteryticket.utils

import android.content.Context
import android.widget.Toast

fun Context.showToast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

const val LOTTERY = "lottery"
const val USER = "user"
const val  USER_EMAIL= "email"
const val  USER_ID= "user_id"
const val  IS_ADMIN= "is_admin"

fun Context.savePreference(key: String, value: String){
    val pref = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this)
    val editor = pref.edit()
    editor.apply {
        putString(key, value)
        commit()
    }
}

fun Context.getFromPrefs( key: String, defaultValue: String): String {

    val pref = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this)
    return try {
        pref.getString(key, defaultValue).toString()
    } catch (e: Exception) {
        e.printStackTrace()
        defaultValue
    }
}

fun Context.saveISAdmin(key: String, value:Boolean){
    val pref = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this)
    val editor = pref.edit()
    editor.apply {
        putBoolean(key, value)
        commit()
    }
}

fun Context.getIsAdmin(key: String, defaultValue: Boolean = false): Boolean {
    val pref = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this)
    return try {
        pref.getBoolean(key, defaultValue)
    } catch (e: Exception) {
        e.printStackTrace()
        defaultValue
    }
}
