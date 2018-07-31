package com.ek.posbridge.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ek.posbridge.Models.Products;
import com.ek.posbridge.R;

import java.util.ArrayList;

public class ProductListDialogAdapter extends RecyclerView.Adapter<ProductListDialogAdapter.ProductViewHolder> {
    private ArrayList<Products> productsList;
    String price;
    String sku;
    String upc;
    Context context;
    private Intent intent;
    public static ArrayList<String> list;

    public ProductListDialogAdapter(ArrayList<Products> productsList) {

        this.productsList = productsList;
        setHasStableIds(true);
    }
    @Override
    public ProductListDialogAdapter.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.single, parent, false);
        return new ProductListDialogAdapter.ProductViewHolder(view);
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView txtSKU, txtUPC, txtPrice;


        ProductViewHolder(final View itemView) {
            super(itemView);
            txtSKU =  itemView.findViewById(R.id.sku);
            txtUPC =  itemView.findViewById(R.id.upc);
            txtPrice =  itemView.findViewById(R.id.price);

            price = txtPrice.toString();
            sku = txtSKU.toString();
            upc = txtUPC.toString();


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }

            });

        }

    }

    @Override
    public void onBindViewHolder(final ProductListDialogAdapter.ProductViewHolder holder, int position) {
        holder.txtSKU.setText(productsList.get(position).getSku());
        holder.txtUPC.setText(productsList.get(position).getUpc());
        holder.txtPrice.setText(String.valueOf(productsList.get(position).getPrice()));


    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


}