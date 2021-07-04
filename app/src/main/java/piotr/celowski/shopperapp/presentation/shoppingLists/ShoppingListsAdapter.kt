package piotr.celowski.shopperapp.presentation.shoppingLists

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
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
    private var listOfShoppingListsIds: List<Int> = listOf<Int>()
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

        val bundle: Bundle? = Bundle()
        bundle!!.putInt("shoppingListId", singleShoppingListId)

        holder.title.text = singleShoppingList
        holder.date.text = singleShoppingDate


        holder.item.setOnClickListener {
            holder.item.findNavController().navigate(R.id.action_shoppingListsFragment_to_shoppingListDetailsFragment, bundle)
        }

        holder.archiveButton.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return listOfShoppingLists.size
    }

    override fun onCacheUpdated(shoppingListsWithGroceries: List<ShoppingListWithGroceryItems>) {
        getShoppingListNames(shoppingListsWithGroceries)
    }

    fun getShoppingListNames(shoppingListsWithGroceries: List<ShoppingListWithGroceryItems>) {
        val listOfNames = mutableListOf<String>()
        val listOfDates = mutableListOf<String>()
        val listOfIds = mutableListOf<Int>()

        for(list in shoppingListsWithGroceries) {
            listOfNames.add(list.shoppingList.shoppingListName)
            listOfDates.add(list.shoppingList.date)
            listOfIds.add(list.shoppingList.shoppingListId)
        }
        listOfShoppingLists = listOfNames
        listOfShoppingListsDates = listOfDates
        listOfShoppingListsIds = listOfIds
    }

}