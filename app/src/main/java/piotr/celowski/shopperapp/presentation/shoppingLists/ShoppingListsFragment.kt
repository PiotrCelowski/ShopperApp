package piotr.celowski.shopperapp.presentation.shoppingLists

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import piotr.celowski.shopperapp.R
import piotr.celowski.shopperapp.domain.interfaces.ShoppingListsDAO
import piotr.celowski.shopperapp.application.databases.ShoppingListsDatabase
import piotr.celowski.shopperapp.databinding.FragmentShoppingListsBinding
import piotr.celowski.shopperapp.domain.interfaces.GroceryItemsDAO
import piotr.celowski.shopperapp.domain.interfaces.ShoppingListWithGroceryItemsDAO
import piotr.celowski.shopperapp.domain.usecases.GroceryItemUseCases
import piotr.celowski.shopperapp.domain.usecases.ShoppingListUseCases
import javax.inject.Inject

@AndroidEntryPoint
class ShoppingListsFragment : Fragment() {
    private lateinit var mFragmentShoppingListsBinding: FragmentShoppingListsBinding
    private lateinit var mShoppingListRecycler: RecyclerView

    @Inject
    lateinit var shoppingListsDao: ShoppingListsDAO
    @Inject
    lateinit var shoppingListsWithGroceryItemsDAO: ShoppingListWithGroceryItemsDAO
    @Inject
    lateinit var shoppingListDb: ShoppingListsDatabase
    @Inject
    lateinit var groceryItemsDAO: GroceryItemsDAO
    @Inject
    lateinit var shoppingListsController: ShoppingListsController
    @Inject
    lateinit var shoppingListUseCases: ShoppingListUseCases


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mFragmentShoppingListsBinding = FragmentShoppingListsBinding.inflate(
            layoutInflater,
            container,
            false
        )

        val recyclerFragment = inflater.inflate(R.layout.fragment_shopping_lists, container, false)

        bb()

        mShoppingListRecycler = mFragmentShoppingListsBinding.shoppingListRecyclerView
        mShoppingListRecycler.layoutManager = LinearLayoutManager(context)
        mShoppingListRecycler.adapter = ShoppingListsAdapter(shoppingListsWithGroceryItemsDAO, shoppingListUseCases)

        mFragmentShoppingListsBinding.floatingActionButton.setOnClickListener {
            shoppingListsController.createNewShoppingList("Shopping List")
        }

        return mFragmentShoppingListsBinding.root
        }

    fun aa() = runBlocking {
        launch {
            val shoppingListUseCases = ShoppingListUseCases(shoppingListsDao, shoppingListsWithGroceryItemsDAO)
            shoppingListUseCases.createShoppingListAndSaveToDb("SybiX")
            shoppingListUseCases.createShoppingListAndSaveToDb("Pietrek")

            val groceryItemUseCases = GroceryItemUseCases(groceryItemsDAO, shoppingListsWithGroceryItemsDAO)
            groceryItemUseCases.createGroceryItemAndAddToList("Ziemniaki", 2)
            groceryItemUseCases.createGroceryItemAndAddToList("Marchewka", 1)
            groceryItemUseCases.createGroceryItemAndAddToList("Czeresnie", 2)
            groceryItemUseCases.createGroceryItemAndAddToList("Chleb", 1)
            groceryItemUseCases.createGroceryItemAndAddToList("Papier toaletowy", 2)
            groceryItemUseCases.createGroceryItemAndAddToList("Salami", 1)
            groceryItemUseCases.createGroceryItemAndAddToList("Gnoje", 1)

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("onDestroy", "fragment onDestroy executed")
        bb()
    }

    fun bb() = runBlocking {
        launch {
            shoppingListsWithGroceryItemsDAO.cleanGroceriesListTable()
            shoppingListsWithGroceryItemsDAO.cleanShoppingListTable()
        }
    }

}