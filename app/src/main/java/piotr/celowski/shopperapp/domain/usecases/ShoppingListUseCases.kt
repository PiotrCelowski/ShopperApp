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

    suspend fun createShoppingListAndSaveToDb(shoppingListName: String) {
        //generateId
        val newId = findHighestShoppingListId(allShoppingListsWithGroceries) + 1
        //generate date
        val currentDate = Timestamp(System.currentTimeMillis())

        val generatedShoppingList = ShoppingList(newId, shoppingListName, currentDate.toString(), false)

        try {
            writeToDb(generatedShoppingList)
            updateCacheAndNotify()
        } catch(ex: Exception) {
            Log.i("Exception: ", ex.toString())
        }
    }

    suspend fun removeShoppingList(shoppingListName: String) {
        try {
            removeFromDb(shoppingListName)
            updateCacheAndNotify()
        } catch (ex: Exception) {
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

    suspend fun removeGroceryItemsForParticularList(shoppingListId: Int) {
        withContext(Dispatchers.IO) {
            shoppingListWithGroceryItemsDAO.removeGroceriesForParticularList(shoppingListId)
        }
    }

    suspend fun removeGroceryItemsForParticularList(shoppingListName: String): Boolean {
        val shoppingListId = findShoppingListIdByName(activeShoppingListsWithGroceries, shoppingListName)
        if (shoppingListId != null) {
            withContext(Dispatchers.IO) {
                shoppingListWithGroceryItemsDAO.removeGroceriesForParticularList(shoppingListId)
            }
            return true
        } else {
            return false
        }
    }

    private suspend fun writeToDb(shoppingList: ShoppingList) {
        withContext(Dispatchers.IO) {
            shoppingListDAO.insert(shoppingList)
        }
    }

    private suspend fun removeFromDb(shoppingListName: String) {
        val shoppingListId = findShoppingListIdByName(activeShoppingListsWithGroceries, shoppingListName)
        withContext(Dispatchers.IO) {
            shoppingListWithGroceryItemsDAO.removeShoppingList(shoppingListId!!)
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

    private fun findShoppingListNameById(shoppingListsWithGroceryItems: List<ShoppingListWithGroceryItems>, providedShoppingListId: Int): String? {
        for(list in shoppingListsWithGroceryItems) {
            if(list.shoppingList.shoppingListId == providedShoppingListId) {
                return list.shoppingList.shoppingListName
            }
        }
        return null
    }
}