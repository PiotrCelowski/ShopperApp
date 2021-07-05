package piotr.celowski.shopperapp.presentation.shoppinglistdetails

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import piotr.celowski.shopperapp.domain.usecases.GroceryItemUseCases
import javax.inject.Inject

class ShoppingListDetailsController @Inject constructor(val groceryItemUseCases: GroceryItemUseCases) {

    fun createNewGroceryItemForList(groceryItemName: String, groceryListId: Int) {
        val createdItem = groceryItemUseCases.createGroceryItem(groceryItemName, groceryListId)
        runBlocking {
            launch {
                groceryItemUseCases.saveGroceryItemToDb(createdItem)
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