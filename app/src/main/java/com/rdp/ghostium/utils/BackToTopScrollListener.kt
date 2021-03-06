package com.rdp.ghostium.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rdp.ghostium.R

abstract class BackToTopScrollListener(val view: View, private val requiredContext: Context) :
    RecyclerView.OnScrollListener() {

    private var animationStarted = false

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        when (newState) {
            RecyclerView.SCROLL_STATE_DRAGGING, RecyclerView.SCROLL_STATE_IDLE -> {
                val position =
                    (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition() + 1
                if (position >= 12 && !animationStarted) {
                    animationStarted = true
                    view.apply {
                        visibility = View.VISIBLE
                        val animation =
                            AnimationUtils.loadAnimation(requiredContext, R.anim.fade_in)
                        view.startAnimation(animation)
                    }
                } else if (position <= 12 && animationStarted) {
                    val animation = AnimationUtils.loadAnimation(requiredContext, R.anim.fade_out)
                    view.startAnimation(animation)
                    Handler(Looper.getMainLooper()).postDelayed({
                        view.visibility = View.GONE
                    }, 200)
                    animationStarted = false
                }
            }
        }
    }
}