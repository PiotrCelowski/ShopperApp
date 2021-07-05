package piotr.celowski.shopperapp.presentation.shoppinglistdetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import piotr.celowski.shopperapp.R

class ShoppingListDetailsItemView(
    private val viewGroup: ViewGroup) {

    class ShoppingListsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val groceryName: TextView
        val removeButton: ImageView

        init {
            groceryName = view.findViewById(R.id.groceryName)
            removeButton = view.findViewById(R.id.removeButton)
        }
    }

    fun inflateHolder(): View {
        return LayoutInflater.from(viewGroup.context).inflate(R.layout.grocery_item, viewGroup, false)
    }

    fun removeButton(holder: ShoppingListsViewHolder) {
        holder.removeButton.isVisible = false
    }

    fun setOnClickListeners(holder: ShoppingListsViewHolder, shoppingListDetailsController: ShoppingListDetailsController, singleGroceryId: Int, shoppingListId: Int) {
        holder.removeButton.setOnClickListener {
            shoppingListDetailsController.removeGroceryItemFromList(singleGroceryId, shoppingListId)
        }
    }
}