package com.rdp.ghostium.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.text.Html
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// Class to download Images which extends [Html.ImageGetter]
class ImageGetter(
    private val context: Context,
    private val res: Resources,
    private val htmlTextView: TextView
) : Html.ImageGetter {

    // Function needs to overridden when extending [Html.ImageGetter] ,
    // which will download the image
    override fun getDrawable(url: String): Drawable {
        val holder = BitmapDrawablePlaceHolder(res, null)

        // Coroutine Scope to download image in Background
        CoroutineScope(Dispatchers.IO).launch {
            Glide.with(context)
                .asBitmap()
                .load(url)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        val drawable = BitmapDrawable(res, resource)

                        val width = getScreenWidth()
                        val aspectRatio: Float =
                            (drawable.intrinsicWidth.toFloat()) / (drawable.intrinsicHeight.toFloat())
                        val height = width / aspectRatio
                        drawable.setBounds(0, 0, width, height.toInt())
                        holder.setDrawable(drawable)
                        holder.setBounds(0, 0, width, height.toInt())
                        CoroutineScope(Dispatchers.Main).launch {
                            htmlTextView.text = htmlTextView.text
                        }
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {}
                })
        }
        return holder
    }

    // Actually Putting images
    internal class BitmapDrawablePlaceHolder(res: Resources, bitmap: Bitmap?) :
        BitmapDrawable(res, bitmap) {
        private var drawable: Drawable? = null

        override fun draw(canvas: Canvas) {
            drawable?.run { draw(canvas) }
        }

        fun setDrawable(drawable: Drawable) {
            this.drawable = drawable
        }
    }

    // Function to get screenWidth used above
    fun getScreenWidth() =
        Resources.getSystem().displayMetrics.widthPixels
}
