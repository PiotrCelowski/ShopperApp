package piotr.celowski.shopperapp.domain.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import piotr.celowski.shopperapp.domain.entities.ShoppingList

@Dao
interface ShoppingListsDAO {
    @Insert
    suspend fun insert(shoppingList: ShoppingList)

    @Query("SELECT shopping_list_id FROM shopping_list_table ORDER BY date")
    suspend fun getListsIds(): List<Int>
}