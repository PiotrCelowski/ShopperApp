package piotr.celowski.shopperapp.domain.interfaces

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import piotr.celowski.shopperapp.domain.entities.ShoppingListWithGroceryItems

@Dao
interface ShoppingListWithGroceryItemsDAO {

    @Transaction
    @Query("SELECT * FROM shopping_list_table WHERE shopping_list_archived = :shoppingListArchived ORDER BY shopping_list_date")
    suspend fun getShoppingListsWithGroceries(shoppingListArchived: Boolean): List<ShoppingListWithGroceryItems>

    @Transaction
    @Query("SELECT * FROM shopping_list_table ORDER BY shopping_list_date")
    suspend fun getAllShoppingListsWithGroceries(): List<ShoppingListWithGroceryItems>


    @Transaction
    @Query("DELETE FROM shopping_list_table WHERE shopping_list_id = :shoppingListId")
    suspend fun removeShoppingList(shoppingListId: Int)

    @Transaction
    @Query("SELECT * FROM shopping_list_table WHERE shopping_list_id = :shoppingListId")
    suspend fun fetchParticularListWithGroceries(shoppingListId: Int): ShoppingListWithGroceryItems

    @Transaction
    @Query("DELETE FROM shopping_list_table")
    suspend fun cleanShoppingListTable()

    @Transaction
    @Query("DELETE FROM groceries_list")
    suspend fun cleanGroceriesListTable()

    @Transaction
    @Query("DELETE FROM groceries_list WHERE shopping_list_creator_id = :shoppingListId ")
    suspend fun removeGroceriesForParticularList(shoppingListId: Int)
}