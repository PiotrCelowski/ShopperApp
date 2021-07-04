package piotr.celowski.shopperapp.presentation.shoppingLists

import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import piotr.celowski.shopperapp.R
import piotr.celowski.shopperapp.domain.entities.ShoppingListWithGroceryItems
import piotr.celowski.shopperapp.domain.interfaces.ShoppingListWithGroceryItemsDAO
import piotr.celowski.shopperapp.domain.usecases.CommonUseCase
import piotr.celowski.shopperapp.domain.usecases.ShoppingListUseCases

class ShoppingListsAdapter(
        private val shoppingListWithGroceryItemsDAO: ShoppingListWithGroceryItemsDAO,
        private val shoppingListUseCases: ShoppingListUseCases)
    : RecyclerView.Adapter<ShoppingListsAdapter.ShoppingListsViewHolder>(), CommonUseCase.Listener {
    private var listOfShoppingLists: List<String> = listOf<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    private var listOfShoppingListsDates: List<String> = listOf<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    init {
        shoppingListUseCases.registerListener(this)
    }

    class ShoppingListsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView
        val date: TextView

        init {
            title = view.findViewById(R.id.listName)
            date = view.findViewById(R.id.date)
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

        holder.title.text = singleShoppingList
        holder.date.text = singleShoppingDate

        holder.title.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        Log.i("Update count", "update")
        return listOfShoppingLists.size
    }

    override fun onCacheUpdated(shoppingListsWithGroceries: List<ShoppingListWithGroceryItems>) {
        listOfShoppingLists = getShoppingListNames(shoppingListsWithGroceries)
        listOfShoppingListsDates = getShoppingListDates(shoppingListsWithGroceries)
    }

    fun getShoppingListNames(shoppingListsWithGroceries: List<ShoppingListWithGroceryItems>): MutableList<String> {
        val listOfNames = mutableListOf<String>()
        for(list in shoppingListsWithGroceries) {
            listOfNames.add(list.shoppingList.shoppingListName)
        }
        return listOfNames
    }

    fun getShoppingListDates(shoppingListsWithGroceries: List<ShoppingListWithGroceryItems>): MutableList<String> {
        val listOfDates = mutableListOf<String>()
        for(list in shoppingListsWithGroceries) {
            listOfDates.add(list.shoppingList.date)
        }
        return listOfDates
    }
}