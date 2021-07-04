package piotr.celowski.shopperapp.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_list_table")
data class ShoppingList(
    @PrimaryKey @ColumnInfo(name = "shopping_list_id") val shoppingListId: Int,
    @ColumnInfo(name = "shopping_list_name") val shoppingListName: String,
    @ColumnInfo(name = "date") val date: String
)