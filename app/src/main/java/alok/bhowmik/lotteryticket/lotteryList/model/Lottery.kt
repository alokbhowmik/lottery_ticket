package alok.bhowmik.lotteryticket.lotteryList.model

data class Lottery(
    var companyName: String ="",
    var hasBuy: Boolean = false,
    var lotteryNo: String ="",
    var amount: String ="",
    var userId: String? = null,
    var userEmail: String? = null
)