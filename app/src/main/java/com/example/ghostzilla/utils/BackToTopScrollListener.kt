package com.example.ghostzilla.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ghostzilla.R

abstract class BackToTopScrollListener(val view: View, val requeredContext: Context) : RecyclerView.OnScrollListener() {

    private var animationStarted = false

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        when(newState){
            RecyclerView.SCROLL_STATE_DRAGGING, RecyclerView.SCROLL_STATE_IDLE -> {
                val position =
                    (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition() + 1
                if (position >= 12 && !animationStarted ){
                    animationStarted = true
                    view.apply {
                        visibility = View.VISIBLE
                        val animation = AnimationUtils.loadAnimation(requeredContext, R.anim.fade_in)
                        view.startAnimation(animation)
                    }
                } else if (position <= 12 && animationStarted) {
                    val animation = AnimationUtils.loadAnimation(requeredContext, R.anim.fade_out)
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