package piotr.celowski.shopperapp.presentation.shoppingLists.activeshoppinglists

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import piotr.celowski.shopperapp.R
import piotr.celowski.shopperapp.databinding.FragmentActiveShoppingListsBinding
import piotr.celowski.shopperapp.domain.usecases.ShoppingListUseCases
import piotr.celowski.shopperapp.presentation.shoppingLists.ShoppingListsController

class ActiveShoppingListsView(
    private val layoutInflater: LayoutInflater,
    private val container: ViewGroup?,
    private val context: Context,
    private val shoppingListUseCases: ShoppingListUseCases,
    private val shoppingListsController: ShoppingListsController
) {
    private var mFragmentShoppingListsBinding: FragmentActiveShoppingListsBinding =
        FragmentActiveShoppingListsBinding.inflate(
            layoutInflater,
            container,
            false
        )

    private lateinit var recyclerFragment: View
    private lateinit var mShoppingListRecycler: RecyclerView

    fun initFragment() {
        recyclerFragment = layoutInflater.inflate(R.layout.fragment_active_shopping_lists, container, false)
    }

    fun initActiveShoppingListRecycler() {
        mShoppingListRecycler = mFragmentShoppingListsBinding.shoppingListRecyclerView
        mShoppingListRecycler.layoutManager = LinearLayoutManager(context)
        mShoppingListRecycler.adapter = ActiveShoppingListsAdapter(shoppingListUseCases, shoppingListsController)
    }

    fun setupOnClickListeners() {
        mFragmentShoppingListsBinding.addListFloatingButton.setOnClickListener {
            shoppingListsController.createNewShoppingList("Shopping List")
        }
    }

    fun getRootView(): View {
        return mFragmentShoppingListsBinding.root
    }



}