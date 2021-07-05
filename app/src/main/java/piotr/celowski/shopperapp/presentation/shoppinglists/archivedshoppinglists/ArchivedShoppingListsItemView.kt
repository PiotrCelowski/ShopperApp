package piotr.celowski.shopperapp.presentation.shoppinglists.archivedshoppinglists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import piotr.celowski.shopperapp.R
import piotr.celowski.shopperapp.presentation.shoppinglists.ShoppingListsController
import piotr.celowski.shopperapp.presentation.shoppinglists.activeshoppinglists.ActiveShoppingListsItemView

class ArchivedShoppingListsItemView(
    private val viewGroup: ViewGroup
) {

    class ArchivedShoppingListsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView
        val date: TextView
        val item: View

        init {
            title = view.findViewById(R.id.listName)
            date = view.findViewById(R.id.date)
            item = view
        }
    }

    fun inflateHolder(): View {
        return LayoutInflater.from(viewGroup.context).inflate(R.layout.archived_shopping_list_item, viewGroup, false)
    }

    fun setOnClickListeners(holder: ArchivedShoppingListsViewHolder, bundle: Bundle?) {
        holder.item.setOnClickListener {
            holder.item.findNavController().navigate(R.id.action_archivedShoppingListsFragment_to_shoppingListDetailsFragment, bundle)
        }
    }
}