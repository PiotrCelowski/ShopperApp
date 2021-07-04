package piotr.celowski.shopperapp.domain.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import piotr.celowski.shopperapp.domain.common.BaseObservable
import piotr.celowski.shopperapp.domain.entities.ShoppingListWithGroceryItems
import piotr.celowski.shopperapp.domain.interfaces.ShoppingListWithGroceryItemsDAO

open class CommonUseCase(private val shoppingListWithGroceryItemsDAO: ShoppingListWithGroceryItemsDAO): BaseObservable<CommonUseCase.Listener>() {
    lateinit var shoppingListsWithGroceries: List<ShoppingListWithGroceryItems>

    interface Listener {
        fun onCacheUpdated(shoppingListsWithGroceries: List<ShoppingListWithGroceryItems>)
    }

    init {
        updateCacheAndNotify()
    }

    fun updateCacheAndNotify() {
        shoppingListsWithGroceries = runBlocking {
            fetchShoppingListsWithGroceries()
        }
        notifyListeners(shoppingListsWithGroceries)
    }

    private suspend fun fetchShoppingListsWithGroceries(): List<ShoppingListWithGroceryItems> {
        return withContext(Dispatchers.IO) {
            shoppingListWithGroceryItemsDAO.getShoppingListsWithGroceries()
        }
    }

    private fun notifyListeners(shoppingListsWithGroceries: List<ShoppingListWithGroceryItems>) {
        for(listener in getListeners()) {
            listener.onCacheUpdated(shoppingListsWithGroceries)
        }
    }

}