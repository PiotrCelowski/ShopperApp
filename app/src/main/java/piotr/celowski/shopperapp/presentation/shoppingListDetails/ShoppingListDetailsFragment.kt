package piotr.celowski.shopperapp.presentation.shoppingListDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import piotr.celowski.shopperapp.R
import piotr.celowski.shopperapp.databinding.FragmentShoppingListDetailsBinding
import piotr.celowski.shopperapp.domain.interfaces.ShoppingListWithGroceryItemsDAO
import piotr.celowski.shopperapp.domain.usecases.GroceryItemUseCases
import piotr.celowski.shopperapp.domain.usecases.ShoppingListUseCases
import piotr.celowski.shopperapp.presentation.shoppingLists.ShoppingListsController
import javax.inject.Inject

@AndroidEntryPoint
class ShoppingListDetailsFragment: Fragment() {
    private lateinit var mFragmentShoppingListDetailsBinding: FragmentShoppingListDetailsBinding
    private lateinit var mShoppingListDetailsRecycler: RecyclerView
    private var shoppingListId: Int = 0
    private var shoppingListArchivedStatus: Boolean = false

    @Inject
    lateinit var shoppingListsWithGroceryItemsDAO: ShoppingListWithGroceryItemsDAO

    @Inject
    lateinit var grocerItemUseCases: GroceryItemUseCases

    @Inject
    lateinit var shoppingListDetailsController: ShoppingListDetailsController

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mFragmentShoppingListDetailsBinding = FragmentShoppingListDetailsBinding.inflate(
                layoutInflater,
                container,
                false
        )

        shoppingListId = arguments?.getInt("shoppingListId")!!
        shoppingListArchivedStatus = arguments?.getBoolean("shoppingListArchived")!!

        val recyclerFragment = inflater.inflate(R.layout.fragment_shopping_list_details, container, false)

        mShoppingListDetailsRecycler = mFragmentShoppingListDetailsBinding.shoppingListDetailsRecyclerView
        mShoppingListDetailsRecycler.layoutManager = LinearLayoutManager(context)
        mShoppingListDetailsRecycler.adapter = ShoppingListDetailsAdapter(shoppingListId, shoppingListArchivedStatus, shoppingListsWithGroceryItemsDAO, grocerItemUseCases, shoppingListDetailsController)

        if(shoppingListArchivedStatus) {
            mFragmentShoppingListDetailsBinding.addGroceryItemFloatingButton.isVisible = false
        } else {
            mFragmentShoppingListDetailsBinding.addGroceryItemFloatingButton.setOnClickListener {
                shoppingListDetailsController.createNewGroceryItemForList(
                    "GroceryItem",
                    shoppingListId
                )
            }
        }

        shoppingListDetailsController.updateCacheWithDatabase()

        return mFragmentShoppingListDetailsBinding.root
    }
}