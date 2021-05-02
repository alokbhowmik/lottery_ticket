package alok.bhowmik.lotteryticket.lotteryList.viewHolder

import alok.bhowmik.lotteryticket.databinding.ItemLotteryBinding
import alok.bhowmik.lotteryticket.lotteryList.listener.LotteryChooseListener
import alok.bhowmik.lotteryticket.lotteryList.model.Lottery
import alok.bhowmik.lotteryticket.lotteryList.view.LotteryItemView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference

class LotteryViewHolder(private val mBinding: ItemLotteryBinding) :
    RecyclerView.ViewHolder(mBinding.root), LotteryItemView {

    private var mListener: LotteryChooseListener? = null
    private var mPosition = -1
    private var mRef: DatabaseReference? = null

    fun bind(
        mLottery: Lottery,
        position: Int,
        ref: DatabaseReference,
        listener: LotteryChooseListener
    ) {
        mListener = listener
        mPosition = position
        mRef = ref

        mBinding.apply {
            lottery = mLottery
            mView = this@LotteryViewHolder
        }
    }

    override fun onBuyBtnClick() {
        if (mPosition != -1) {
            mRef?.let { ref ->
                mListener?.onBuyLottery(mPosition, ref)
            }
        }
    }
}