package alok.bhowmik.lotteryticket.admin.fragment

import alok.bhowmik.lotteryticket.admin.view.AdminAddLotteryView
import alok.bhowmik.lotteryticket.databinding.FragmentAdminAddLotteryBinding
import alok.bhowmik.lotteryticket.lotteryList.model.Lottery
import alok.bhowmik.lotteryticket.utils.AppLoader
import alok.bhowmik.lotteryticket.utils.LOTTERY
import alok.bhowmik.lotteryticket.utils.showToast
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AdminAddLotteryFragment : Fragment(), AdminAddLotteryView {

    private var mBinding: FragmentAdminAddLotteryBinding? = null

    private val lottery = Lottery("", false, "", "0.0", null, null)

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = Firebase.database.reference
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentAdminAddLotteryBinding.inflate(inflater, container, false)
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding?.apply {
            this.mLottery = lottery
            mView = this@AdminAddLotteryFragment
        }
    }

    override fun onAddLotteryBtnClick() {
        if (isValidTicket()) {
            addALottery()
        }
    }

    private fun isValidTicket(): Boolean {
        var isValidTicket = false
        if (lottery.companyName.isNotEmpty()) {
            if (lottery.lotteryNo.length != 4) {
                requireContext().showToast("Ticket no should be 4 digit")
            } else {
                isValidTicket = true
            }
        } else {
            requireContext().showToast("Please provide the ticket name")
        }
        return isValidTicket
    }

    private fun addALottery() {
        AppLoader.createLoader(requireContext())
        Log.d("TAG", "addALottery: $lottery")
        database.child(LOTTERY)
            .push()
            .setValue(lottery)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    requireContext().showToast("Lottery is added successfully")
                } else {
                    requireContext().showToast("Try again !")
                    Log.d("TAG", "addALottery: ${task.exception}")
                }
                AppLoader.hideLoader()
            }
    }
}