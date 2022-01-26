package com.example.ghostzilla.abstraction.listeners

import com.example.ghostzilla.abstraction.LocalModel

interface GeneralClickListener {
    fun onClick(data: LocalModel, position : Int)

}