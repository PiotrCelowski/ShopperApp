package piotr.celowski.shopperapp.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "groceries_list")
data class GroceryItem(
    @PrimaryKey @ColumnInfo(name = "grocery_item_id") val groceryItemId: Int,
    @ColumnInfo(name = "grocery_item_name") val groceryItemName: String,
    @ColumnInfo(name = "shopping_list_creator_id") val shoppingListCreatorId: Int
)