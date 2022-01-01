package ru.soldatov.android.shoppinglist.presentation

import androidx.lifecycle.ViewModel
import ru.soldatov.android.shoppinglist.data.ShopListRepositoryImpl
import ru.soldatov.android.shoppinglist.domain.DeleteShopItemUseCase
import ru.soldatov.android.shoppinglist.domain.EditShopItemUseCase
import ru.soldatov.android.shoppinglist.domain.GetShopListUseCase
import ru.soldatov.android.shoppinglist.domain.ShopItem

class MainViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()

    fun deleteShopItem(shopItem: ShopItem) {
        deleteShopItemUseCase.deleteShopItem(shopItem)
    }

    fun changeEnableState(shopItem: ShopItem) {
        val shopItemCopy = shopItem.copy(enabled = !shopItem.enabled)
        editShopItemUseCase.editShopItem(shopItemCopy)
    }
}