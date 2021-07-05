package piotr.celowski.shopperapp.presentation.archivedShoppingLists

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import piotr.celowski.shopperapp.R
import piotr.celowski.shopperapp.databinding.FragmentArchivedShoppingListsBinding
import piotr.celowski.shopperapp.databinding.FragmentShoppingListsBinding
import piotr.celowski.shopperapp.domain.usecases.ShoppingListUseCases
import piotr.celowski.shopperapp.presentation.shoppingLists.ShoppingListsAdapter
import piotr.celowski.shopperapp.presentation.shoppingLists.ShoppingListsController
import javax.inject.Inject

@AndroidEntryPoint
class ArchivedShoppingListsFragment : Fragment() {
    private lateinit var mFragmentArchivedShoppingListsBinding: FragmentArchivedShoppingListsBinding
    private lateinit var mShoppingListRecycler: RecyclerView

    @Inject
    lateinit var shoppingListUseCases: ShoppingListUseCases

    @Inject
    lateinit var shoppingListsController: ShoppingListsController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mFragmentArchivedShoppingListsBinding = FragmentArchivedShoppingListsBinding.inflate(
            layoutInflater,
            container,
            false
        )

        val recyclerFragment = inflater.inflate(R.layout.fragment_shopping_lists, container, false)

        mShoppingListRecycler = mFragmentArchivedShoppingListsBinding.archivedShoppingListRecyclerView
        mShoppingListRecycler.layoutManager = LinearLayoutManager(context)
        mShoppingListRecycler.adapter =
            ArchivedShoppingListsAdapter(shoppingListUseCases)

        shoppingListsController.updateCacheWithDatabase()

        return mFragmentArchivedShoppingListsBinding.root
    }

}