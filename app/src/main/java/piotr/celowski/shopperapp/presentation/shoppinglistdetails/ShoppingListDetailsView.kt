package piotr.celowski.shopperapp.presentation.shoppinglistdetails

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import piotr.celowski.shopperapp.R
import piotr.celowski.shopperapp.databinding.FragmentShoppingListDetailsBinding
import piotr.celowski.shopperapp.domain.usecases.GroceryItemUseCases

class ShoppingListDetailsView(
    private val layoutInflater: LayoutInflater,
    private val container: ViewGroup?,
    private val shoppingListId: Int,
    private val shoppingListArchivedStatus: Boolean,
    private val context: Context,
    private val groceryItemUseCases: GroceryItemUseCases,
    private val shoppingListDetailsController: ShoppingListDetailsController
) {
    private lateinit var mShoppingListDetailsRecycler: RecyclerView
    private lateinit var recyclerFragment: View

    private var mFragmentShoppingListDetailsBinding = FragmentShoppingListDetailsBinding.inflate(
    layoutInflater,
    container,
    false
    )

    fun initFragment() {
        recyclerFragment = layoutInflater.inflate(R.layout.fragment_shopping_list_details, container, false)
    }

    fun initRecyclerView() {
        mShoppingListDetailsRecycler = mFragmentShoppingListDetailsBinding.shoppingListDetailsRecyclerView
        mShoppingListDetailsRecycler.layoutManager = LinearLayoutManager(context)
        mShoppingListDetailsRecycler.adapter = ShoppingListDetailsAdapter(shoppingListId, shoppingListArchivedStatus, groceryItemUseCases, shoppingListDetailsController)

    }

    fun setupOnClickListeners() {
        if(shoppingListArchivedStatus) {
            mFragmentShoppingListDetailsBinding.addGroceryItemFloatingButton.isVisible = false
        } else {
            configureAddGroceryItemButton()
        }
    }

    private fun configureAddGroceryItemButton() {
        mFragmentShoppingListDetailsBinding.addGroceryItemFloatingButton.setOnClickListener {
            shoppingListDetailsController.createNewGroceryItemForList(
                "GroceryItem",
                shoppingListId
            )
        }
    }

    fun getRootView(): View {
        return mFragmentShoppingListDetailsBinding.root
    }
}