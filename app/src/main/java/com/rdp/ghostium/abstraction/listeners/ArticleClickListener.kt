package com.rdp.ghostium.abstraction.listeners

import com.rdp.ghostium.abstraction.LocalModel

interface ArticleClickListener {

    fun articleOnClick(data: LocalModel)
}