package piotr.celowski.shopperapp.application.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import piotr.celowski.shopperapp.domain.entities.GroceryItem
import piotr.celowski.shopperapp.domain.entities.ShoppingList
import piotr.celowski.shopperapp.domain.interfaces.GroceryItemsDAO
import piotr.celowski.shopperapp.domain.interfaces.ShoppingListWithGroceryItemsDAO
import piotr.celowski.shopperapp.domain.interfaces.ShoppingListsDAO

@Database(version = 1, entities = [GroceryItem::class, ShoppingList::class] )
abstract class ShoppingListsDatabase: RoomDatabase() {
    abstract fun shoppingListsDao(): ShoppingListsDAO
    abstract fun shoppingListWithGroceryItemsDao(): ShoppingListWithGroceryItemsDAO
    abstract fun groceryItemsDao(): GroceryItemsDAO

    companion object {

        @Volatile
        private var INSTANCE: ShoppingListsDatabase? = null

        fun getInstance(context: Context): ShoppingListsDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ShoppingListsDatabase::class.java,
                        "shopping_lists_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}