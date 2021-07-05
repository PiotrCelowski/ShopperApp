package piotr.celowski.shopperapp.presentation.shoppingLists.activeshoppinglists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import piotr.celowski.shopperapp.R
import piotr.celowski.shopperapp.domain.entities.ShoppingListWithGroceryItems
import piotr.celowski.shopperapp.domain.usecases.ShoppingListUseCases
import piotr.celowski.shopperapp.presentation.shoppingLists.ShoppingListsAdapter
import piotr.celowski.shopperapp.presentation.shoppingLists.ShoppingListsController

class ActiveShoppingListsAdapter(
        private val shoppingListUseCases: ShoppingListUseCases,
        private val shoppingListsController: ShoppingListsController
)
    : ShoppingListsAdapter<ActiveShoppingListsAdapter.ShoppingListsViewHolder>(shoppingListUseCases) {

    class ShoppingListsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView
        val date: TextView
        val item: View
        val archiveButton: ImageView

        init {
            title = view.findViewById(R.id.listName)
            date = view.findViewById(R.id.date)
            item = view
            archiveButton = view.findViewById(R.id.archiveButton)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ShoppingListsViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.shopping_list_item, viewGroup, false)

        return ShoppingListsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShoppingListsViewHolder, position: Int) {
        val singleShoppingList = listOfShoppingLists[position]
        val singleShoppingDate = listOfShoppingListsDates[position]
        val singleShoppingListId = listOfShoppingListsIds[position]
        val singleShoppingListArchived = listOfShoppingListsArchivedStatus[position]

        val bundle: Bundle? = Bundle()
        bundle!!.putInt("shoppingListId", singleShoppingListId)
        bundle!!.putBoolean("shoppingListArchived", singleShoppingListArchived)

        holder.title.text = singleShoppingList
        holder.date.text = singleShoppingDate


        holder.item.setOnClickListener {
            holder.item.findNavController().navigate(R.id.action_shoppingListsFragment_to_shoppingListDetailsFragment, bundle)
        }

        holder.archiveButton.setOnClickListener {
            shoppingListsController.archiveShoppingList(singleShoppingListId)
        }
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