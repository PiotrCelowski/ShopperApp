package piotr.celowski.shopperapp.presentation.archivedShoppingLists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import piotr.celowski.shopperapp.R
import piotr.celowski.shopperapp.domain.entities.ShoppingListWithGroceryItems
import piotr.celowski.shopperapp.domain.usecases.CommonUseCase
import piotr.celowski.shopperapp.domain.usecases.ShoppingListUseCases

class ArchivedShoppingListsAdapter(
    private val shoppingListUseCases: ShoppingListUseCases
) : RecyclerView.Adapter<ArchivedShoppingListsAdapter.ArchivedShoppingListsViewHolder>(), CommonUseCase.Listener {
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
    private var listOfShoppingListsArchivedStatus: List<Boolean> = listOf<Boolean>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    init {
        shoppingListUseCases.registerListener(this)
    }

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

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArchivedShoppingListsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.archived_shopping_list_item, parent, false)

        return ArchivedShoppingListsViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ArchivedShoppingListsViewHolder,
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

        holder.item.setOnClickListener {
            holder.item.findNavController().navigate(R.id.action_archivedShoppingListsFragment_to_shoppingListDetailsFragment, bundle)
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
        getShoppingListNames(archivedShoppingListsWithGroceries)
    }

    private fun getShoppingListNames(shoppingListsWithGroceries: List<ShoppingListWithGroceryItems>) {
        val listOfNames = mutableListOf<String>()
        val listOfDates = mutableListOf<String>()
        val listOfIds = mutableListOf<Int>()
        val listOfArchivedStatus = mutableListOf<Boolean>()

        for(list in shoppingListsWithGroceries) {
            listOfNames.add(list.shoppingList.shoppingListName)
            listOfDates.add(list.shoppingList.shoppingListDate)
            listOfIds.add(list.shoppingList.shoppingListId)
            listOfArchivedStatus.add(list.shoppingList.shoppingListArchived)
        }
        listOfShoppingLists = listOfNames
        listOfShoppingListsDates = listOfDates
        listOfShoppingListsIds = listOfIds
        listOfShoppingListsArchivedStatus = listOfArchivedStatus
    }
}