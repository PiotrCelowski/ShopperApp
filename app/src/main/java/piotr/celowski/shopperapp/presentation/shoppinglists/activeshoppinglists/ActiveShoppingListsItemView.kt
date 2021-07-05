package piotr.celowski.shopperapp.presentation.shoppinglists.activeshoppinglists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import piotr.celowski.shopperapp.R
import piotr.celowski.shopperapp.presentation.shoppinglists.ShoppingListsController

class ActiveShoppingListsItemView(
    private val viewGroup: ViewGroup) {

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

    fun inflateHolder(): View {
        return LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.shopping_list_item, viewGroup, false)
    }

    fun setOnClickListeners(holder: ShoppingListsViewHolder, shoppingListsController: ShoppingListsController, singleShoppingListId: Int, bundle: Bundle?) {
        holder.item.setOnClickListener {
            holder.item.findNavController().navigate(R.id.action_shoppingListsFragment_to_shoppingListDetailsFragment, bundle)
        }

        holder.archiveButton.setOnClickListener {
            shoppingListsController.archiveShoppingList(singleShoppingListId)
        }
    }
}