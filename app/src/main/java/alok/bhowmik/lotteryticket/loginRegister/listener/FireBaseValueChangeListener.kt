package alok.bhowmik.lotteryticket.loginRegister.listener

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

abstract class FireBaseValueChangeListener : ValueEventListener {
    override fun onDataChange(snapshot: DataSnapshot) {
        doWork()
    }

    override fun onCancelled(error: DatabaseError) {
        failure(error)
    }

    abstract fun doWork()
    abstract fun failure(error: DatabaseError)
}