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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ek.posbridge.Adapters.ProductAdapter;
import com.ek.posbridge.Models.Product;
import com.ek.posbridge.Models.Products;
import com.ek.posbridge.R;
import com.ek.posbridge.API.Webservices.GetProducts;
import com.ek.posbridge.UI.Dialogs.DialogActivity;

import java.util.ArrayList;


public class OrdersFragment extends Fragment {
    private int RC_LOGIN = 100;
    Context context;
    EditText textInput;
    Button goButton;
    String id;
    String upc;
    Double siteprice;
    String sku;
    TextView floatingCostView;

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private ArrayList<Product> productArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_fragment_orders, container, false);
    }

    public TextView getFloatingCostView() {
        return floatingCostView;
    }

    public void setFloatingCostView(TextView floatingCostView) {
        this.floatingCostView = floatingCostView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {


        textInput = getView().findViewById(R.id.textInput);

        goButton = getView().findViewById(R.id.goButton);

        productArrayList = new ArrayList<>();
        recyclerView = getView().findViewById(R.id.recycler_view);
        adapter = new ProductAdapter(productArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = textInput.getText().toString();
                Log.i("TEXT.getText.tostring = ", id);
                GetProducts getProducts = new GetProducts(OrdersFragment.this, floatingCostView);
                getProducts.execute(id);

            }
        });

        floatingCostView = getView().findViewById(R.id.floatingCostView);
        Product product = new Product(sku, upc, siteprice);



        final FloatingActionButton fab = getView().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DialogActivity.class);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) getContext(), fab, getString(R.string.transition_dialog));
                startActivityForResult(intent, RC_LOGIN, options.toBundle());
            }
        });

    }

    public ArrayList<Product> getProductArrayList() {
        return productArrayList;
    }


    public ProductAdapter getAdapter() {
        return adapter;
    }
}
