package piotr.celowski.shopperapp.presentation.shoppinglists.activeshoppinglists

import android.os.Bundle
import android.view.ViewGroup
import piotr.celowski.shopperapp.domain.entities.ShoppingListWithGroceryItems
import piotr.celowski.shopperapp.domain.usecases.ShoppingListUseCases
import piotr.celowski.shopperapp.presentation.shoppinglists.ShoppingListsBaseAdapter
import piotr.celowski.shopperapp.presentation.shoppinglists.ShoppingListsController

class ActiveShoppingListsAdapter(
        private val shoppingListUseCases: ShoppingListUseCases,
        private val shoppingListsController: ShoppingListsController
) : ShoppingListsBaseAdapter<ActiveShoppingListsItemView.ShoppingListsViewHolder>(shoppingListUseCases) {

    private lateinit var activeShoppingListsItemView: ActiveShoppingListsItemView

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ActiveShoppingListsItemView.ShoppingListsViewHolder {
        activeShoppingListsItemView = ActiveShoppingListsItemView(viewGroup)
        val view = activeShoppingListsItemView.inflateHolder()
        return ActiveShoppingListsItemView.ShoppingListsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ActiveShoppingListsItemView.ShoppingListsViewHolder, position: Int) {
        val singleShoppingList = listOfShoppingLists[position]
        val singleShoppingDate = listOfShoppingListsDates[position]
        val singleShoppingListId = listOfShoppingListsIds[position]
        val singleShoppingListArchived = listOfShoppingListsArchivedStatus[position]

        val bundle: Bundle? = Bundle()
        bundle!!.putInt("shoppingListId", singleShoppingListId)
        bundle!!.putBoolean("shoppingListArchived", singleShoppingListArchived)

        holder.title.text = singleShoppingList
        holder.date.text = singleShoppingDate

        activeShoppingListsItemView.setOnClickListeners(holder, shoppingListsController, singleShoppingListId, bundle)
    }

    override fun getItemCount(): Int {
        return listOfShoppingLists.size
    }

    override fun onCacheUpdated(
        activeShoppingListsWithGroceries: List<ShoppingListWithGroceryItems>,
        archivedShoppingListsWithGroceries: List<ShoppingListWithGroceryItems>,
        allShoppingListsWithGroceries: List<ShoppingListWithGroceryItems>
    ) {
        getShoppingListNames(activeShoppingListsWithGroceries)
    }

}