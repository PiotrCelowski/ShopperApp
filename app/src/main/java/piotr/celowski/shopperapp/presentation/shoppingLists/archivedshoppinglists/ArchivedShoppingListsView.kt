package piotr.celowski.shopperapp.presentation.shoppingLists.archivedshoppinglists

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import piotr.celowski.shopperapp.R
import piotr.celowski.shopperapp.databinding.FragmentArchivedShoppingListsBinding
import piotr.celowski.shopperapp.domain.usecases.ShoppingListUseCases

class ArchivedShoppingListsView(
    private val inflater: LayoutInflater,
    private val container: ViewGroup?,
    private val context: Context,
    private val shoppingListUseCases: ShoppingListUseCases
) {
    private lateinit var mShoppingListRecycler: RecyclerView
    private lateinit var recyclerView: View

    private var mFragmentArchivedShoppingListsBinding = FragmentArchivedShoppingListsBinding.inflate(
    inflater,
    container,
    false
    )

    fun initFragment() {
        recyclerView = inflater.inflate(R.layout.fragment_active_shopping_lists, container, false)
    }

    fun initArchivedShoppingListRecycler() {
        mShoppingListRecycler = mFragmentArchivedShoppingListsBinding.archivedShoppingListRecyclerView
        mShoppingListRecycler.layoutManager = LinearLayoutManager(context)
        mShoppingListRecycler.adapter = ArchivedShoppingListsAdapter(shoppingListUseCases)
    }

    fun getRootView(): View {
        return mFragmentArchivedShoppingListsBinding.root
    }
}