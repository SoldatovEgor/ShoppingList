package ru.soldatov.android.shoppinglist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import ru.soldatov.android.shoppinglist.data.ShopListRepositoryImpl
import ru.soldatov.android.shoppinglist.domain.DeleteShopItemUseCase
import ru.soldatov.android.shoppinglist.domain.EditShopItemUseCase
import ru.soldatov.android.shoppinglist.domain.GetShopListUseCase
import ru.soldatov.android.shoppinglist.domain.ShopItem

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ShopListRepositoryImpl(application)

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()

    fun deleteShopItem(shopItem: ShopItem) {
        viewModelScope.launch {
            deleteShopItemUseCase.deleteShopItem(shopItem)
        }
    }

    fun changeEnableState(shopItem: ShopItem) {
        viewModelScope.launch {
            val shopItemCopy = shopItem.copy(enabled = !shopItem.enabled)
            editShopItemUseCase.editShopItem(shopItemCopy)
        }
    }
}