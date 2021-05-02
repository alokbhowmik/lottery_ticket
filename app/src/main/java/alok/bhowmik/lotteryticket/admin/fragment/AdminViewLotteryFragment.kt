package alok.bhowmik.lotteryticket.admin.fragment

import alok.bhowmik.lotteryticket.databinding.FragmentAdminViewLotteryBinding
import alok.bhowmik.lotteryticket.lotteryList.adapter.LotteryListAdapter
import alok.bhowmik.lotteryticket.lotteryList.listener.LotteryChooseListener
import alok.bhowmik.lotteryticket.lotteryList.model.Lottery
import alok.bhowmik.lotteryticket.utils.LOTTERY
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AdminViewLotteryFragment : Fragment(), LotteryChooseListener {

    private var mBinding: FragmentAdminViewLotteryBinding? = null
    private lateinit var database: DatabaseReference
    private var mAdapter: LotteryListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = Firebase.database.reference
        val query = database.child(LOTTERY).orderByChild("hasBuy").equalTo(true)
        val option = FirebaseRecyclerOptions.Builder<Lottery>()
            .setQuery(query, Lottery::class.java)
            .setLifecycleOwner(this)
            .build()

        mAdapter = LotteryListAdapter(requireContext(), this, option)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentAdminViewLotteryBinding.inflate(inflater, container, false)
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding?.apply {
            viewLotteryRV.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = mAdapter
            }
        }
    }
    override fun onBuyLottery(position: Int, ref: DatabaseReference) {

    }
}