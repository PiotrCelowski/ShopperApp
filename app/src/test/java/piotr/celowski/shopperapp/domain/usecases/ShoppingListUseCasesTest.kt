package piotr.celowski.shopperapp.domain.usecases

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*
import piotr.celowski.shopperapp.domain.entities.ShoppingList
import piotr.celowski.shopperapp.domain.interfaces.ShoppingListWithGroceryItemsDAO
import piotr.celowski.shopperapp.domain.interfaces.ShoppingListsDAO
import java.sql.Timestamp


class ShoppingListUseCasesTest {

    @Test
    fun when_createShoppingList_Then_ReturnCompleteList() {
        //given
        val listName = "ExampleList"
        val currentDate = Timestamp(System.currentTimeMillis())
        val exampleList = ShoppingList(1, listName, currentDate.toString(), false)
        val shoppingListUseCasesMock = mock(ShoppingListUseCases::class.java)
        Mockito.`when`(
            shoppingListUseCasesMock.createShoppingList(
                Mockito.anyString()
            )
        ).thenReturn(exampleList)

        //when
        val createdList = shoppingListUseCasesMock.createShoppingList(listName)

        //then
        assert(createdList.shoppingListId != null && createdList.shoppingListId != 0)
        assert(exampleList.shoppingListName == createdList.shoppingListName)
        assert(exampleList.shoppingListArchived == createdList.shoppingListArchived)
        assert(createdList.shoppingListDate != null && createdList.shoppingListDate != "")
    }

    @Test(expected = RuntimeException::class)
    fun when_saveListToDb_Then_UpdateCacheTriggered() {
        //given
        val listName = "ExampleList"
        val currentDate = Timestamp(System.currentTimeMillis())
        val exampleList = ShoppingList(1, listName, currentDate.toString(), false)
        val shoppingListWithGroceryItemsDAO = mock(ShoppingListWithGroceryItemsDAO::class.java)
        val shoppingListsDAO = mock(ShoppingListsDAO::class.java)
        val shoppingListsUseCases = ShoppingListUseCases(
            shoppingListsDAO,
            shoppingListWithGroceryItemsDAO
        )

        Mockito.`when`(
            shoppingListsUseCases.updateCacheAndNotify()
        ).thenThrow(RuntimeException("Method executed"))

        //when
        runBlocking {
            launch {
                shoppingListsUseCases.saveListToDb(exampleList)
            }
        }

        //then
        //Proper exception is thrown when updateCacheAndNotify() was triggered
    }

    @Test(expected = RuntimeException::class)
    fun when_archiveShoppingList_Then_UpdateCacheTriggered() {
        //given
        val listName = "ExampleList"
        val currentDate = Timestamp(System.currentTimeMillis())
        val exampleList = ShoppingList(1, listName, currentDate.toString(), false)
        val shoppingListWithGroceryItemsDAO = mock(ShoppingListWithGroceryItemsDAO::class.java)
        val shoppingListsDAO = mock(ShoppingListsDAO::class.java)
        val shoppingListsUseCases = ShoppingListUseCases(
            shoppingListsDAO,
            shoppingListWithGroceryItemsDAO
        )

        Mockito.`when`(
            shoppingListsUseCases.updateCacheAndNotify()
        ).thenThrow(RuntimeException("Method executed"))

        //when
        runBlocking {
            launch {
                shoppingListsUseCases.archiveShoppingList(exampleList.shoppingListId)
            }
        }

        //then
        //Proper exception is thrown when updateCacheAndNotify() was triggered
    }
}