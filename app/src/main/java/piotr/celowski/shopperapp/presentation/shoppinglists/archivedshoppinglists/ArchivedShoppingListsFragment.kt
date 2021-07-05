package piotr.celowski.shopperapp.presentation.shoppinglists.archivedshoppinglists

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import piotr.celowski.shopperapp.domain.usecases.ShoppingListUseCases
import piotr.celowski.shopperapp.presentation.shoppinglists.ShoppingListsController
import javax.inject.Inject

@AndroidEntryPoint
class ArchivedShoppingListsFragment : Fragment() {
    private lateinit var archivedShoppingListsView: ArchivedShoppingListsView

    @Inject lateinit var shoppingListUseCases: ShoppingListUseCases
    @Inject lateinit var shoppingListsController: ShoppingListsController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        archivedShoppingListsView = ArchivedShoppingListsView(inflater, container, requireContext(), shoppingListUseCases)
        archivedShoppingListsView.initFragment()
        archivedShoppingListsView.initArchivedShoppingListRecycler()
        shoppingListsController.updateCacheWithDatabase()
        return archivedShoppingListsView.getRootView()
    }

}