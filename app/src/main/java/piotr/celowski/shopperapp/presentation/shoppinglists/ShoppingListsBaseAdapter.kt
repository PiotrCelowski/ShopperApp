package piotr.celowski.shopperapp.presentation.shoppinglists

import androidx.recyclerview.widget.RecyclerView
import piotr.celowski.shopperapp.domain.entities.ShoppingListWithGroceryItems
import piotr.celowski.shopperapp.domain.usecases.CommonUseCase
import piotr.celowski.shopperapp.domain.usecases.ShoppingListUseCases

open abstract class ShoppingListsBaseAdapter<VIEW_HOLDER : RecyclerView.ViewHolder?>(
        private val shoppingListUseCases: ShoppingListUseCases)
    : RecyclerView.Adapter<VIEW_HOLDER>(), CommonUseCase.Listener {

    var listOfShoppingLists: List<String> = listOf<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var listOfShoppingListsDates: List<String> = listOf<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var listOfShoppingListsIds: List<Int> = listOf<Int>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var listOfShoppingListsArchivedStatus: List<Boolean> = listOf<Boolean>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    init {
        shoppingListUseCases.registerListener(this)
    }

    fun getShoppingListNames(shoppingListsWithGroceries: List<ShoppingListWithGroceryItems>) {
        val listOfNames = mutableListOf<String>()
        val listOfDates = mutableListOf<String>()
        val listOfIds = mutableListOf<Int>()
        val listOfArchivedStatus = mutableListOf<Boolean>()

        for(list in shoppingListsWithGroceries) {
            listOfNames.add(list.shoppingList.shoppingListName)
            listOfDates.add(list.shoppingList.shoppingListDate)
            listOfIds.add(list.shoppingList.shoppingListId)
            listOfArchivedStatus.add(list.shoppingList.shoppingListArchived)
        }
        listOfShoppingLists = listOfNames
        listOfShoppingListsDates = listOfDates
        listOfShoppingListsIds = listOfIds
        listOfShoppingListsArchivedStatus = listOfArchivedStatus
    }
}