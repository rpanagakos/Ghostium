package com.rdp.ghostium.abstraction.listeners

import com.rdp.ghostium.abstraction.LocalModel

interface GeneralClickListener {
    fun onClick(data: LocalModel, position : Int)

}