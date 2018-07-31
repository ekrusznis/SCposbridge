package com.ek.posbridge.UI;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ek.posbridge.API.Webservices.GetAllProductsWS;
import com.ek.posbridge.Adapters.ProductListAdapter;
import com.ek.posbridge.Models.Products;
import com.ek.posbridge.R;
import com.ek.posbridge.UI.Dialogs.AddProductToOrderDialogFragment;

import java.util.ArrayList;

public class ProductsFragment extends Fragment {
    private RecyclerView recyclerView;
    private ProductListAdapter adapter;
    private ArrayList<Products> productArrayList;
    private ArrayList<Products> arrayList;
    private int RC_LOGIN = 100;
    TextView totalText;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_fragment_products, container, false);


     }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        productArrayList = new ArrayList<>();
        recyclerView = getView().findViewById(R.id.recycler_view);
        adapter = new ProductListAdapter(productArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        GetAllProductsWS getAllProducts = new GetAllProductsWS(ProductsFragment.this);
        getAllProducts.execute();

        totalText=getView().findViewById(R.id.totalText);


        final FloatingActionButton fab = getView().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddProductToOrderDialogFragment.class);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) getContext(), fab, getString(R.string.transition_dialog));
                startActivityForResult(intent, RC_LOGIN, options.toBundle());


            }
        });

    }

    public ArrayList<Products> getProductArrayList() {
        return productArrayList;
    }

    public ProductListAdapter getAdapter() {
        return adapter;
    }

    public TextView getTotalText() {
        return totalText;
    }

    public void setTotalText() {
        this.totalText = totalText;
    }

    public ArrayList<Products> getArrayList(){
        return arrayList;
    }

}
