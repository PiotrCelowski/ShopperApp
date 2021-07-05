package piotr.celowski.shopperapp.presentation.shoppingLists.activeshoppinglists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import piotr.celowski.shopperapp.domain.usecases.ShoppingListUseCases
import piotr.celowski.shopperapp.presentation.shoppingLists.ShoppingListsController
import javax.inject.Inject

@AndroidEntryPoint
class ActiveShoppingListsFragment : Fragment() {
    @Inject lateinit var shoppingListsController: ShoppingListsController
    @Inject lateinit var shoppingListUseCases: ShoppingListUseCases
    private lateinit var activeShoppingListsView: ActiveShoppingListsView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activeShoppingListsView = ActiveShoppingListsView(inflater, container, requireContext(), shoppingListUseCases, shoppingListsController)
        activeShoppingListsView.initFragment()
        activeShoppingListsView.initActiveShoppingListRecycler()
        activeShoppingListsView.setupOnClickListeners()
        shoppingListsController.updateCacheWithDatabase()
        return activeShoppingListsView.getRootView()
    }
}