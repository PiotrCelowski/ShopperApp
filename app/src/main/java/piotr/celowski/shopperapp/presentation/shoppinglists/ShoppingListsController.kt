package piotr.celowski.shopperapp.presentation.shoppinglists

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import piotr.celowski.shopperapp.domain.usecases.ShoppingListUseCases
import javax.inject.Inject

class ShoppingListsController @Inject constructor(val shoppingListUseCases: ShoppingListUseCases) {

    fun createNewShoppingList(shoppingListName: String) {
        val createdList = shoppingListUseCases.createShoppingList(shoppingListName)
        runBlocking {
            launch {
                shoppingListUseCases.saveListToDb(createdList)
            }
        }
    }

    fun updateCacheWithDatabase() {
        runBlocking {
            launch {
                shoppingListUseCases.updateCacheAndNotify()
            }
        }
    }

    fun archiveShoppingList(shoppingListId: Int) {
        runBlocking {
            launch {
                shoppingListUseCases.archiveShoppingList(shoppingListId)
            }
        }
    }
}