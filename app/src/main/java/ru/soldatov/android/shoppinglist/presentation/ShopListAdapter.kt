package ru.soldatov.android.shoppinglist.presentation

import android.util.Log
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import ru.soldatov.android.shoppinglist.R
import ru.soldatov.android.shoppinglist.databinding.ItemShopDisabledBinding
import ru.soldatov.android.shoppinglist.databinding.ItemShopEnabledBinding
import ru.soldatov.android.shoppinglist.domain.ShopItem
import java.lang.RuntimeException

class ShopListAdapter : ListAdapter<ShopItem, ShopListViewHolder>(ShopItemDiffCallback()) {

    var shopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var shopItemClickListener: ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopListViewHolder {

        Log.d("ShopListAdapter", "onCreateViewHolder")

        val layout = when(viewType) {
            ENABLED -> R.layout.item_shop_enabled
            DISABLED -> R.layout.item_shop_disabled
            else -> throw RuntimeException("Not create layout")
        }

        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layout,
            parent,
            false
        )
        return ShopListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShopListViewHolder, position: Int) {
        val shopItem = getItem(position)
        val binding = holder.binding

        binding.root.setOnLongClickListener {
            shopItemLongClickListener?.invoke(shopItem)
            true
        }
        binding.root.setOnClickListener {
            shopItemClickListener?.invoke(shopItem)
        }

        when(binding) {
            is ItemShopEnabledBinding -> {
                binding.tvName.text = shopItem.name
                binding.tvCount.text = shopItem.count.toString()
            }
            is ItemShopDisabledBinding -> {
                binding.tvName.text = shopItem.name
                binding.tvCount.text = shopItem.count.toString()
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        val enabled = getItem(position).enabled
        return if (enabled)
            ENABLED
        else
            DISABLED
    }

    companion object {
        const val ENABLED = 0
        const val DISABLED = 1
        const val MAX_POOL_RV = 30
    }

}