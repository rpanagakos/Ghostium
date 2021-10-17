package com.example.ghostzilla.ui.tabs.trends

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.models.pricing.Crypto
import com.example.ghostzilla.ui.tabs.TabsBinding.loadImageFromUrl
import kotlinx.android.synthetic.main.holder_trends_item.view.*
import java.text.DecimalFormat

class TrendsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun present(data: LocalModel, onClickElement: (selected: LocalModel) -> Unit) {
        when(data){
            is Crypto -> {
                val dec = DecimalFormat("##.###")
                itemView.contractImg.loadImageFromUrl(data.logoUrl)
                itemView.contractName.text = data.contractName
                itemView.contractTickerSumbol.text = data.contractTickerSymbol
                (data.quoteRate.toString() + "$").also { itemView.contractRate.text = it }
            }
        }
    }
}