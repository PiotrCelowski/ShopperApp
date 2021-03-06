package piotr.celowski.shopperapp.presentation.shoppinglistdetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import piotr.celowski.shopperapp.R
import piotr.celowski.shopperapp.domain.entities.GroceryItem
import piotr.celowski.shopperapp.domain.entities.ShoppingListWithGroceryItems
import piotr.celowski.shopperapp.domain.usecases.CommonUseCase
import piotr.celowski.shopperapp.domain.usecases.GroceryItemUseCases

class ShoppingListDetailsAdapter(
        private val shoppingListId: Int,
        private val archivedStatus: Boolean,
        private val groceryItemUseCases: GroceryItemUseCases,
        private val shoppingListDetailsController: ShoppingListDetailsController
) : RecyclerView.Adapter<ShoppingListDetailsItemView.ShoppingListsViewHolder>(), CommonUseCase.Listener {
    private lateinit var shoppingListDetailsItemView: ShoppingListDetailsItemView

    private var listOfGroceryItems: List<String> = listOf<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var listOfGroceryItemIds: List<Int> = listOf<Int>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    init {
        groceryItemUseCases.registerListener(this)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ShoppingListDetailsItemView.ShoppingListsViewHolder {
        shoppingListDetailsItemView = ShoppingListDetailsItemView(viewGroup)
        val view = shoppingListDetailsItemView.inflateHolder()

        return ShoppingListDetailsItemView.ShoppingListsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShoppingListDetailsItemView.ShoppingListsViewHolder, position: Int) {
        val singleGroceryName = listOfGroceryItems[position]
        val singleGroceryId = listOfGroceryItemIds[position]

        holder.groceryName.text = singleGroceryName

        if (archivedStatus) {
            shoppingListDetailsItemView.removeButton(holder)
        } else {
            shoppingListDetailsItemView.setOnClickListeners(
                holder,
                shoppingListDetailsController,
                singleGroceryId,
                shoppingListId
            )
        }
    }

    override fun getItemCount(): Int {
        return listOfGroceryItems.size
    }

    override fun onCacheUpdated(
        activeShoppingListsWithGroceries: List<ShoppingListWithGroceryItems>,
        archivedShoppingListsWithGroceries: List<ShoppingListWithGroceryItems>,
        allShoppingListsWithGroceries: List<ShoppingListWithGroceryItems>
    ) {
        getGroceryNamesForSpecificList(allShoppingListsWithGroceries, shoppingListId)
    }

    private fun getGroceryNamesForSpecificList(shoppingListsWithGroceries: List<ShoppingListWithGroceryItems>, shoppingListId: Int) {
        lateinit var listOfGroceryItemsNames: List<String>
        lateinit var listOfGroceryItemsIdstemp: List<Int>

        for(list in shoppingListsWithGroceries) {
            if(list.shoppingList.shoppingListId == shoppingListId) {
                listOfGroceryItemsNames = addGroceryNamesToList(list.groceries)
                listOfGroceryItemsIdstemp = addGroceryIdsToList(list.groceries)
            }
        }
        listOfGroceryItems = listOfGroceryItemsNames
        listOfGroceryItemIds = listOfGroceryItemsIdstemp
    }

    private fun addGroceryNamesToList(groceryItems: List<GroceryItem>): MutableList<String> {
        val listOfGroceryNames = mutableListOf<String>()
        for(grocery in groceryItems) {
            listOfGroceryNames.add(grocery.groceryItemName)
        }
        return listOfGroceryNames
    }

    private fun addGroceryIdsToList(groceryItems: List<GroceryItem>): MutableList<Int> {
        val listOfGroceryIds = mutableListOf<Int>()
        for(grocery in groceryItems) {
            listOfGroceryIds.add(grocery.groceryItemId)
        }
        return listOfGroceryIds
    }

}