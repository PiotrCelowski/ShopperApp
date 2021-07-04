package piotr.celowski.shopperapp.presentation.shoppingListDetails

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import piotr.celowski.shopperapp.domain.usecases.GroceryItemUseCases
import javax.inject.Inject

class ShoppingListDetailsController @Inject constructor(val groceryItemUseCases: GroceryItemUseCases) {

    fun createNewGroceryItemForList(groceryItemName: String, groceryListId: Int) {
        runBlocking {
            launch {
                groceryItemUseCases.createGroceryItemAndAddToList(groceryItemName, groceryListId)
            }
        }
    }

    fun updateCacheWithDatabase() {
        runBlocking {
            launch {
                groceryItemUseCases.updateCacheAndNotify()
            }
        }
    }

    fun removeGroceryItemFromList(groceryItemId: Int, shoppingListId: Int) {
        runBlocking {
            launch {
                groceryItemUseCases.removeGroceryItemFromListById(groceryItemId, shoppingListId)
            }
        }
    }
}