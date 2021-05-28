package ir.khu.ie.publications.views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ir.khu.ie.publications.R;
import ir.khu.ie.publications.adapters.HomeCardRecyclerAdapter;
import ir.khu.ie.publications.adapters.HomeCategoryRecyclerAdapter;
import ir.khu.ie.publications.models.adapters.HomeCardsModel;
import ir.khu.ie.publications.models.adapters.HomeCategoryModel;

public class HomeFragment extends Fragment {

    private Context context;
    private ArrayList<HomeCategoryModel> categoriesData;
    //private ArrayList<HomeCardsModel> cardsData;

    public HomeFragment() {
    }

    public HomeFragment(Context context, ArrayList<HomeCategoryModel> categoriesData) {
        this.context = context;
        this.categoriesData = categoriesData;
        //this.cardsData = cardsData;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //set arraylist to outer recyclerview
        RecyclerView homeCategoryRecyclerView = view.findViewById(R.id.fragmentHomeRecyclerView);
        HomeCategoryRecyclerAdapter homeCategoryRecyclerAdapter = new HomeCategoryRecyclerAdapter(context, categoriesData);
        homeCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        homeCategoryRecyclerView.setAdapter(homeCategoryRecyclerAdapter);

        /*//set arraylist to inner recyclerview
        RecyclerView homeCardRecyclerView = view.findViewById(R.id.itemHomeCategoryRecyclerInnerRecycler);
        HomeCardRecyclerAdapter homeCardRecyclerAdapter = new HomeCardRecyclerAdapter(context, cardsData);
        homeCardRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        homeCardRecyclerView.setAdapter(homeCardRecyclerAdapter);*/
    }
}