package piotr.celowski.shopperapp.domain.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import piotr.celowski.shopperapp.domain.entities.GroceryItem

@Dao
interface GroceryItemsDAO {
    @Insert
    fun insert(groceryItem: GroceryItem)

    @Query("DELETE FROM groceries_list WHERE grocery_item_name = :groceryName AND shopping_list_creator_id = :shoppingListId")
    fun removeGroceryFromParticularList(groceryName: String, shoppingListId: Int)
}
