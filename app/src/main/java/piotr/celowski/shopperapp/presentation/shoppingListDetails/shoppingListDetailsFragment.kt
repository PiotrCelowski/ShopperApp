package piotr.celowski.shopperapp.presentation.shoppingListDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import piotr.celowski.shopperapp.databinding.FragmentShoppingListDetailsBinding

class shoppingListDetailsFragment: Fragment() {
    private lateinit var mFragmentShoppingListDetails: FragmentShoppingListDetailsBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mFragmentShoppingListDetails = FragmentShoppingListDetailsBinding.inflate(
                layoutInflater,
                container,
                false
        )

        return mFragmentShoppingListDetails.root
    }
}