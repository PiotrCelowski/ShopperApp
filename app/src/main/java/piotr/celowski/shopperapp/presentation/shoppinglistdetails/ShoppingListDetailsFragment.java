package piotr.celowski.shopperapp.presentation.shoppinglistdetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import piotr.celowski.shopperapp.domain.usecases.GroceryItemUseCases;

@AndroidEntryPoint
public class ShoppingListDetailsFragment extends Fragment {
    private int shoppingListId = 0;
    private Boolean shoppingListArchivedStatus = false;
    private ShoppingListDetailsView shoppingListDetailsView;

    @Inject GroceryItemUseCases groceryItemUseCases;
    @Inject ShoppingListDetailsController shoppingListDetailsController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        shoppingListId = getArguments().getInt("shoppingListId");
        shoppingListArchivedStatus = getArguments().getBoolean("shoppingListArchived");

        shoppingListDetailsView = new ShoppingListDetailsView(
                inflater, container, shoppingListId, shoppingListArchivedStatus,
                getContext(), groceryItemUseCases, shoppingListDetailsController);
        shoppingListDetailsView.initFragment();
        shoppingListDetailsView.initRecyclerView();
        shoppingListDetailsView.setupOnClickListeners();

        shoppingListDetailsController.updateCacheWithDatabase();

        return shoppingListDetailsView.getRootView();
    }
}
