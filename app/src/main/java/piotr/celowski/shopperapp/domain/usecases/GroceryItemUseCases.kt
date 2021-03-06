package piotr.celowski.shopperapp.domain.usecases

import android.util.Log
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import piotr.celowski.shopperapp.domain.entities.GroceryItem
import piotr.celowski.shopperapp.domain.entities.ShoppingListWithGroceryItems
import piotr.celowski.shopperapp.domain.interfaces.GroceryItemsDAO
import piotr.celowski.shopperapp.domain.interfaces.ShoppingListWithGroceryItemsDAO
import javax.inject.Inject

@ActivityScoped
class GroceryItemUseCases @Inject constructor(
    private val groceryItemsDAO: GroceryItemsDAO,
    private val shoppingListWithGroceryItemsDAO: ShoppingListWithGroceryItemsDAO
) : CommonUseCase(shoppingListWithGroceryItemsDAO) {

    fun createGroceryItem(groceryItemName: String, groceryListId: Int): GroceryItem {
        val generatedId = generateId()
        return GroceryItem(generatedId, groceryItemName, groceryListId)
    }

    suspend fun saveGroceryItemToDb(createdGrocery: GroceryItem) {
        try {
            insertGroceryToDb(createdGrocery)
            updateCacheAndNotify()
        } catch (ex: Exception) {
            Log.i("Exception: ", ex.toString())
        }
    }

    private fun generateId(): Int {
        val allGroceries = findAllGroceries(allShoppingListsWithGroceries)

        if (allGroceries?.isEmpty() || allGroceries == null) {
            return 1
        } else {
            return findHighestGroceryIdInList(allGroceries) + 1
        }
    }

    private fun findAllGroceries(shoppingListsWithGroceries: List<ShoppingListWithGroceryItems>): List<GroceryItem> {
        val allGroceries: MutableList<GroceryItem> = mutableListOf()
        for (list in shoppingListsWithGroceries) {
            for (grocery in list.groceries) {
                allGroceries.add(grocery)
            }
        }
        return allGroceries
    }

    suspend fun removeGroceryItemFromListById(groceryItemId: Int, groceryListId: Int) {
        try {
            removeGrocery(groceryItemId, groceryListId)
            updateCacheAndNotify()
        } catch (ex: Exception) {
            Log.i("Exception:", ex.toString())
        }
    }

    private suspend fun removeGrocery(specificGroceryId: Int, shoppingListId: Int) {
        withContext(Dispatchers.IO) {
            groceryItemsDAO.removeGroceryFromParticularList(specificGroceryId, shoppingListId)
        }
    }

    private fun findSpecificGroceryName(
        groceryList: List<GroceryItem>?,
        groceryItemId: Int
    ): String {
        lateinit var groceryItemName: String
        if (groceryList != null) {
            for (grocery in groceryList) {
                if (grocery.groceryItemId == groceryItemId) {
                    groceryItemName = grocery.groceryItemName
                }
            }
        }
        return groceryItemName
    }

    private fun findGroceryIdByNameFromList(
        shoppingListWithGroceryItems: ShoppingListWithGroceryItems?,
        groceryName: String,
        shoppingListId: Int
    ): Int {
        if (shoppingListWithGroceryItems != null) {
            for (grocery in shoppingListWithGroceryItems!!.groceries) {
                if (grocery.groceryItemName == groceryName) {
                    return grocery.groceryItemId
                }
            }
        }
        return 0
    }

    private suspend fun insertGroceryToDb(groceryItem: GroceryItem) {
        withContext(Dispatchers.IO) {
            groceryItemsDAO.insert(groceryItem)
        }
    }

    private fun findHighestGroceryIdInList(groceryList: List<GroceryItem>): Int {
        var highestId = 1
        for (grocery in groceryList) {
            if (grocery.groceryItemId > highestId) {
                highestId = grocery.groceryItemId
            }
        }
        return highestId
    }
}