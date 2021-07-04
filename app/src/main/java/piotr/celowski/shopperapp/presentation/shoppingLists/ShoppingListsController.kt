package piotr.celowski.shopperapp.presentation.shoppingLists

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import piotr.celowski.shopperapp.domain.usecases.ShoppingListUseCases
import javax.inject.Inject

class ShoppingListsController @Inject constructor(val shoppingListUseCases: ShoppingListUseCases) {

    fun createNewShoppingList(shoppingListName: String) {
        runBlocking {
            launch {
                shoppingListUseCases.createShoppingListAndSaveToDb(shoppingListName)
            }
        }
    }
}