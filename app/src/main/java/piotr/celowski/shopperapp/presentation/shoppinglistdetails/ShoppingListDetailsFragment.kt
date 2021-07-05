package piotr.celowski.shopperapp.presentation.shoppinglistdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import piotr.celowski.shopperapp.domain.usecases.GroceryItemUseCases
import javax.inject.Inject

@AndroidEntryPoint
class ShoppingListDetailsFragment: Fragment() {
    private var shoppingListId: Int = 0
    private var shoppingListArchivedStatus: Boolean = false
    private lateinit var shoppingListDetailsView: ShoppingListDetailsView

    @Inject lateinit var groceryItemUseCases: GroceryItemUseCases
    @Inject lateinit var shoppingListDetailsController: ShoppingListDetailsController

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        shoppingListId = arguments?.getInt("shoppingListId")!!
        shoppingListArchivedStatus = arguments?.getBoolean("shoppingListArchived")!!

        shoppingListDetailsView = ShoppingListDetailsView(inflater, container, shoppingListId, shoppingListArchivedStatus, requireContext(), groceryItemUseCases, shoppingListDetailsController)
        shoppingListDetailsView.initFragment()
        shoppingListDetailsView.initRecyclerView()
        shoppingListDetailsView.setupOnClickListeners()

        shoppingListDetailsController.updateCacheWithDatabase()

        return shoppingListDetailsView.getRootView()
    }
}