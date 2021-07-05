package piotr.celowski.shopperapp.presentation.shoppinglists.archivedshoppinglists

import android.os.Bundle
import android.view.ViewGroup
import piotr.celowski.shopperapp.domain.entities.ShoppingListWithGroceryItems
import piotr.celowski.shopperapp.domain.usecases.ShoppingListUseCases
import piotr.celowski.shopperapp.presentation.shoppinglists.ShoppingListsBaseAdapter

class ArchivedShoppingListsAdapter(
    private val shoppingListUseCases: ShoppingListUseCases
) : ShoppingListsBaseAdapter<ArchivedShoppingListsItemView.ArchivedShoppingListsViewHolder>(shoppingListUseCases) {

    private lateinit var archivedShoppingListsItemView: ArchivedShoppingListsItemView

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArchivedShoppingListsItemView.ArchivedShoppingListsViewHolder {
        archivedShoppingListsItemView = ArchivedShoppingListsItemView(parent)
        val view = archivedShoppingListsItemView.inflateHolder()
        return ArchivedShoppingListsItemView.ArchivedShoppingListsViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ArchivedShoppingListsItemView.ArchivedShoppingListsViewHolder,
        position: Int
    ) {
        val singleShoppingList = listOfShoppingLists[position]
        val singleShoppingDate = listOfShoppingListsDates[position]
        val singleShoppingListId = listOfShoppingListsIds[position]
        val singleShoppingListArchived = listOfShoppingListsArchivedStatus[position]

        holder.title.text = singleShoppingList
        holder.date.text = singleShoppingDate

        val bundle: Bundle? = Bundle()
        bundle!!.putInt("shoppingListId", singleShoppingListId)
        bundle!!.putBoolean("shoppingListArchived", singleShoppingListArchived)

        archivedShoppingListsItemView.setOnClickListeners(holder, bundle)
    }

    override fun getItemCount(): Int {
        return listOfShoppingLists.size
    }

    override fun onCacheUpdated(
        activeShoppingListsWithGroceries: List<ShoppingListWithGroceryItems>,
        archivedShoppingListsWithGroceries: List<ShoppingListWithGroceryItems>,
        allShoppingListsWithGroceries: List<ShoppingListWithGroceryItems>
    ) {
        getShoppingListNames(archivedShoppingListsWithGroceries)
    }
}