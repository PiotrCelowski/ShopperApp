package piotr.celowski.shopperapp.application.dependencyinjection.databasemodule

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import piotr.celowski.shopperapp.domain.interfaces.ShoppingListWithGroceryItemsDAO
import piotr.celowski.shopperapp.domain.interfaces.ShoppingListsDAO
import piotr.celowski.shopperapp.application.databases.ShoppingListsDatabase
import piotr.celowski.shopperapp.domain.interfaces.GroceryItemsDAO
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideShoppingListsDatabase(@ApplicationContext context: Context): ShoppingListsDatabase {
        return ShoppingListsDatabase.getInstance(context)
    }

    @Provides
    fun provideShoppingListsDao(shoppingListsDatabase: ShoppingListsDatabase): ShoppingListsDAO {
        return shoppingListsDatabase.shoppingListsDao()
    }

    @Provides
    fun provideShoppingListsWithGroceryItemsDao(shoppingListsDatabase: ShoppingListsDatabase): ShoppingListWithGroceryItemsDAO {
        return shoppingListsDatabase.shoppingListWithGroceryItemsDao()
    }

    @Provides
    fun providesGroceryItemsDao(shoppingListsDatabase: ShoppingListsDatabase): GroceryItemsDAO {
        return shoppingListsDatabase.groceryItemsDao()
    }
}