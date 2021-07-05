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
import piotr.celowski.shopperapp.domain.interfaces.ShoppingListWithGroceryItemsDAO
import piotr.celowski.shopperapp.domain.usecases.CommonUseCase
import piotr.celowski.shopperapp.domain.usecases.GroceryItemUseCases

class ShoppingListDetailsAdapter(
        private val shoppingListId: Int,
        private val archivedStatus: Boolean,
        private val shoppingListWithGroceryItemsDAO: ShoppingListWithGroceryItemsDAO,
        private val groceryItemUseCases: GroceryItemUseCases,
        private val shoppingListDetailsController: ShoppingListDetailsController
)
    : RecyclerView.Adapter<ShoppingListDetailsAdapter.ShoppingListsViewHolder>(), CommonUseCase.Listener {

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

    class ShoppingListsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val groceryName: TextView
        val removeButton: ImageView

        init {
            groceryName = view.findViewById(R.id.groceryName)
            removeButton = view.findViewById(R.id.removeButton)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ShoppingListsViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.grocery_item, viewGroup, false)

        return ShoppingListsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShoppingListsViewHolder, position: Int) {
        val singleGroceryName = listOfGroceryItems[position]
        val singleGroceryId = listOfGroceryItemIds[position]

        holder.groceryName.text = singleGroceryName

        if(archivedStatus) {
            holder.removeButton.isVisible = false
        }

        holder.removeButton.setOnClickListener {
            shoppingListDetailsController.removeGroceryItemFromList(singleGroceryId, shoppingListId)
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

    fun addGroceryNamesToList(groceryItems: List<GroceryItem>): MutableList<String> {
        val listOfGroceryNames = mutableListOf<String>()
        for(grocery in groceryItems) {
            listOfGroceryNames.add(grocery.groceryItemName)
        }
        return listOfGroceryNames
    }

    fun addGroceryIdsToList(groceryItems: List<GroceryItem>): MutableList<Int> {
        val listOfGroceryIds = mutableListOf<Int>()
        for(grocery in groceryItems) {
            listOfGroceryIds.add(grocery.groceryItemId)
        }
        return listOfGroceryIds
    }

}