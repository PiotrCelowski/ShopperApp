package piotr.celowski.shopperapp.domain.entities

import androidx.room.Embedded
import androidx.room.Relation

data class ShoppingListWithGroceryItems(
    @Embedded val shoppingList: ShoppingList,
    @Relation(
        parentColumn = "shopping_list_id",
        entityColumn = "shopping_list_creator_id"
    )
    val groceries: List<GroceryItem>
)
