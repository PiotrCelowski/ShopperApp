package piotr.celowski.shopperapp.domain.usecases

import android.util.Log
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import piotr.celowski.shopperapp.domain.entities.ShoppingList
import piotr.celowski.shopperapp.domain.entities.ShoppingListWithGroceryItems
import piotr.celowski.shopperapp.domain.interfaces.ShoppingListWithGroceryItemsDAO
import piotr.celowski.shopperapp.domain.interfaces.ShoppingListsDAO
import java.lang.Exception
import java.sql.Timestamp
import javax.inject.Inject

@ActivityScoped
class ShoppingListUseCases @Inject constructor(
    private val shoppingListDAO: ShoppingListsDAO,
    private val shoppingListWithGroceryItemsDAO: ShoppingListWithGroceryItemsDAO)
    : CommonUseCase(shoppingListWithGroceryItemsDAO) {

    fun createShoppingList(shoppingListName: String): ShoppingList {
        val generatedId = findHighestShoppingListId(allShoppingListsWithGroceries) + 1
        val currentDate = Timestamp(System.currentTimeMillis())
        return ShoppingList(generatedId, shoppingListName, currentDate.toString(), false)
    }

    suspend fun saveListToDb(generatedShoppingList: ShoppingList) {
        try {
            writeToDb(generatedShoppingList)
            updateCacheAndNotify()
        } catch(ex: Exception) {
            Log.i("Exception: ", ex.toString())
        }
    }

    suspend fun archiveShoppingList(shoppingListId: Int) {
        try {
            changeArchiveStatusInDb(shoppingListId)
            updateCacheAndNotify()
        } catch (ex: Exception) {
            Log.i("Exception: ", ex.toString())
        }
    }

    private suspend fun changeArchiveStatusInDb(shoppingListId: Int) {
        withContext(Dispatchers.IO) {
            shoppingListDAO.archiveShoppingList(true, shoppingListId)
        }
    }

    private suspend fun writeToDb(shoppingList: ShoppingList) {
        withContext(Dispatchers.IO) {
            shoppingListDAO.insert(shoppingList)
        }
    }

    private fun findHighestShoppingListId(shoppingListsWithGroceryItems: List<ShoppingListWithGroceryItems>): Int {
        var highestId = 0
        for(list in shoppingListsWithGroceryItems) {
            if(list.shoppingList.shoppingListId > highestId) {
                highestId = list.shoppingList.shoppingListId
            }
        }
        return highestId
    }

    private fun findShoppingListIdByName(shoppingListsWithGroceryItems: List<ShoppingListWithGroceryItems>, providedShoppingListName: String): Int? {
        for(list in shoppingListsWithGroceryItems) {
            if(list.shoppingList.shoppingListName == providedShoppingListName) {
                return list.shoppingList.shoppingListId
            }
        }
        return null
    }
}