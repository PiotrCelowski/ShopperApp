package piotr.celowski.shopperapp.domain.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import piotr.celowski.shopperapp.domain.common.BaseObservable
import piotr.celowski.shopperapp.domain.entities.ShoppingListWithGroceryItems
import piotr.celowski.shopperapp.domain.interfaces.ShoppingListWithGroceryItemsDAO

open class CommonUseCase(private val shoppingListWithGroceryItemsDAO: ShoppingListWithGroceryItemsDAO): BaseObservable<CommonUseCase.Listener>() {
    lateinit var activeShoppingListsWithGroceries: List<ShoppingListWithGroceryItems>
    lateinit var archivedShoppingListsWithGroceries: List<ShoppingListWithGroceryItems>
    lateinit var allShoppingListsWithGroceries: List<ShoppingListWithGroceryItems>

    interface Listener {
        fun onCacheUpdated(activeShoppingListsWithGroceries: List<ShoppingListWithGroceryItems>,
                           archivedShoppingListsWithGroceries: List<ShoppingListWithGroceryItems>,
                           allShoppingListsWithGroceries: List<ShoppingListWithGroceryItems>)
    }

    init {
        updateCacheAndNotify()
    }

    fun updateCacheAndNotify() {
        activeShoppingListsWithGroceries = runBlocking {
            fetchActiveShoppingListsWithGroceries()
        }
        archivedShoppingListsWithGroceries = runBlocking {
            fetchArchivedShoppingListsWithGroceries()
        }
        allShoppingListsWithGroceries = runBlocking {
            fetchAllShoppingListsWithGroceries()
        }
        notifyListeners(activeShoppingListsWithGroceries, archivedShoppingListsWithGroceries, allShoppingListsWithGroceries)
    }

    private suspend fun fetchActiveShoppingListsWithGroceries(): List<ShoppingListWithGroceryItems> {
        return withContext(Dispatchers.IO) {
            shoppingListWithGroceryItemsDAO.getShoppingListsWithGroceries(false)
        }
    }

    private suspend fun fetchArchivedShoppingListsWithGroceries(): List<ShoppingListWithGroceryItems> {
        return withContext(Dispatchers.IO) {
            shoppingListWithGroceryItemsDAO.getShoppingListsWithGroceries(true)
        }
    }

    private suspend fun fetchAllShoppingListsWithGroceries(): List<ShoppingListWithGroceryItems> {
        return withContext(Dispatchers.IO) {
            shoppingListWithGroceryItemsDAO.getAllShoppingListsWithGroceries()
        }
    }

    private fun notifyListeners(activeShoppingListsWithGroceries: List<ShoppingListWithGroceryItems>,
                                archivedShoppingListsWithGroceries: List<ShoppingListWithGroceryItems>,
                                allShoppingListsWithGroceries: List<ShoppingListWithGroceryItems>) {
        for(listener in getListeners()) {
            listener.onCacheUpdated(activeShoppingListsWithGroceries, archivedShoppingListsWithGroceries, allShoppingListsWithGroceries)
        }
    }

}