package com.pradip.profile.home.ui

import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import com.pradip.core.ui.BaseFragment
import com.pradip.data.user.local.entities.ProfileL
import com.pradip.profile.R
import com.pradip.profile.home.di.VideoListModule
import com.pradip.profile.home.viewmodel.FetchVideosViewModel
import com.yuyakaido.android.cardstackview.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : BaseFragment(R.layout.fragment_home), CardStackListener {

    override val fragmentTag = TAG
    lateinit var manager: CardStackLayoutManager
    lateinit var adapter: CardStackAdapter
    private val profiles = mutableListOf<ProfileL>()

    private val viewModel: FetchVideosViewModel by sharedViewModel()

    override fun loadDependencies() = VideoListModule.load()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        manager = CardStackLayoutManager(context, this)
        adapter = CardStackAdapter(profiles)
        initialize()
        viewModel.getProfiles()


        viewModel.uiModel.observe(viewLifecycleOwner, Observer {
            it.profiles?.let { product ->
                setData(product)
            }

            like_button.setOnClickListener {
                swipeCardAtDirection(Direction.Right)
            }
            skip_button.setOnClickListener {
                swipeCardAtDirection(Direction.Left)
            }
            next_button.setOnClickListener {
                swipeCardAtDirection(Direction.Top)
            }

            it.error?.let {
                if (it is Exception) {
                    Toast.makeText(context, "Failed To find Profiles!", Toast.LENGTH_LONG).show()
                }
            }
        })


    }

    private fun swipeCardAtDirection(direction: Direction) {
        val setting = SwipeAnimationSetting.Builder()
            .setDirection(direction)
            .setDuration(Duration.Normal.duration)
            .setInterpolator(AccelerateInterpolator())
            .build()
        manager.setSwipeAnimationSetting(setting)
        card_stack_view.swipe()
    }

    private fun setData(profileList: List<ProfileL>) {
        profiles.clear()
        profiles.addAll(profileList)
        adapter?.notifyDataSetChanged()
    }

    private fun initialize() {
        manager.setStackFrom(StackFrom.None)
        manager.setVisibleCount(3)
        manager.setTranslationInterval(8.0f)
        manager.setScaleInterval(0.95f)
        manager.setSwipeThreshold(0.3f)
        manager.setMaxDegree(20.0f)
        manager.setDirections(Direction.HORIZONTAL)
        manager.setCanScrollHorizontal(true)
        manager.setCanScrollVertical(true)
        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
        manager.setOverlayInterpolator(LinearInterpolator())
        card_stack_view.layoutManager = manager
        card_stack_view.adapter = adapter
        card_stack_view.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }
    }

    private fun showError(ex: Throwable) {

    }

    companion object {
        const val TAG = "HomeFragment"
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {

    }

    override fun onCardSwiped(direction: Direction?) {
        val profile = profiles[manager.topPosition - 1].copy()
        when (direction) {
            Direction.Right -> {
                viewModel.updateStatus(profile.apply { hotOrNot = 1 })
            }
            Direction.Left -> {
                viewModel.updateStatus(profile.apply { hotOrNot = 2 })
            }

        }
    }

    override fun onCardRewound() {

    }

    override fun onCardCanceled() {

    }

    override fun onCardAppeared(view: View?, position: Int) {
        setState(position)
    }

    private fun setState(position: Int) {
        when (profiles[position].hotOrNot) {
            1 -> {
                tv_state.visibility = View.VISIBLE
                tv_state.text = "You have already liked the profile! you can skip to next profile!"
                like_button.visibility = View.GONE
                skip_button.visibility = View.GONE
                manager.setCanScrollHorizontal(false)
            }
            2 -> {
                tv_state.visibility = View.VISIBLE
                tv_state.text = "You have already dis-liked the profile! you can skip to next profile!"
                like_button.visibility = View.GONE
                skip_button.visibility = View.GONE
                manager.setCanScrollHorizontal(false)

            }
            else -> {
                tv_state.visibility = View.GONE
                like_button.visibility = View.VISIBLE
                skip_button.visibility = View.VISIBLE
                manager.setCanScrollHorizontal(true)
            }
        }
    }

    override fun onCardDisappeared(view: View?, position: Int) {
        if (manager.topPosition == profiles.size-1) {
            no_data.visibility = View.VISIBLE
            like_button.visibility = View.GONE
            skip_button.visibility = View.GONE
            tv_state.visibility = View.GONE
            next_button.visibility = View.GONE
        }
    }


}
