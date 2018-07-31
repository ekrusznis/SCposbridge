package com.ek.posbridge.Adapters;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import android.widget.LinearLayout;
import android.widget.TextView;
import com.ek.posbridge.Models.Products;
import com.ek.posbridge.R;
import com.ek.posbridge.Square.SquarePOSChargeActivity;
import com.ek.posbridge.UI.ProductsFragment;


import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/*
* THIS IS FOR THE PRODUCTS FRAGMENT LIST VIEW
* */

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {
    public List<Products> productsList;
    private boolean isChecked;
    Double price;
    String sku;
    String upc;
    public List <Products> secondList;
    public ArrayList<Products> productArrayList;
    private ArrayList<Products> arrayList;
    double total = 0.00;
    String totalstr;
    ProductsFragment fragment;

    public boolean getChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public ProductListAdapter(ArrayList<Products> productsList, ProductsFragment fragment) {
        this.fragment = fragment;
        this.productsList = productsList;
        setHasStableIds(true);
    }

    public ProductListAdapter(ArrayList<Products> productArrayList){
        this.productArrayList = productArrayList;
        setHasStableIds(true);
    }



    public ProductListAdapter(String totalstr){
        this.totalstr = totalstr;
   }
    public String getTotalstr() {
        return totalstr;
    }



    @Override
    public ProductListAdapter.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.single_sku_row, parent, false);
        return new ProductListAdapter.ProductViewHolder(view);
    }

        class ProductViewHolder extends RecyclerView.ViewHolder {
            TextView txtSKU, txtUPC, txtPrice;
            ImageView greencheck;
            public Boolean firstClick = true;


            ProductViewHolder(final View itemView) {
                super(itemView);
                txtSKU =  itemView.findViewById(R.id.sku);
                txtUPC =  itemView.findViewById(R.id.upc);
                txtPrice =  itemView.findViewById(R.id.price);
                greencheck = itemView.findViewById(R.id.greencheck);

                price = Double.parseDouble(txtPrice.getText().toString());
                sku = txtSKU.getText().toString();
                upc = txtUPC.getText().toString();

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (firstClick) {
                            greencheck.setVisibility(View.VISIBLE);
                            firstClick = false;
                            isChecked = true;
                            Log.i("FirsClickChecked = ", "true");

                            Products products = new Products(sku, upc, price);
                            List <Products> secondList = new ArrayList<>();
                            products.setSku(sku);
                            products.setUpc(upc);
                            products.setPrice(price);
                            secondList.add(products);

                            total = total + products.getPrice();
                            totalstr = String.valueOf(total);

                            fragment.getTotalText().setText(totalstr);

                        }
                        else {
                            greencheck.setVisibility(View.INVISIBLE);
                            greencheck.setTag(70);
                            isChecked = false;
                            firstClick = true;
                            Log.i("isChecked = ", "false");

                            Products products = new Products(sku, upc, price);
                            total = total - products.getPrice();
                            totalstr = String.valueOf(total);

                            Log.i("TOTAL = ", totalstr);
//                            totalText.setText(totalstr);

                        }


                    }

                });

            }

        }

    @Override
    public void onBindViewHolder(final ProductListAdapter.ProductViewHolder holder, int position) {
        holder.txtSKU.setText(productsList.get(position).getSku());
        holder.txtUPC.setText(productsList.get(position).getUpc());
        holder.txtPrice.setText(String.valueOf(productsList.get(position).getPrice()));
    }

    @Override
    public int getItemCount() {
        try {
            if (productsList.size() < 0) {
                System.out.println("ProductListAdapter - productList.size = 0");
            } else {
                return productsList.size();

            }
        }catch (Exception e){
            System.out.println("Exception in ProductListAdapter - producstList.size");
        }

           return 0;
    }




    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public ArrayList<Products> getArrayList(){
        return arrayList;
    }
}
