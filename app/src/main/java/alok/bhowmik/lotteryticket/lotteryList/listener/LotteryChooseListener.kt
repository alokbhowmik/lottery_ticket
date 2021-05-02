package alok.bhowmik.lotteryticket.lotteryList.listener

import com.google.firebase.database.DatabaseReference

interface LotteryChooseListener {
    fun onBuyLottery(position: Int, ref: DatabaseReference)
}