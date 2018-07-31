package com.ek.posbridge.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ek.posbridge.Models.Product;
import com.ek.posbridge.R;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>  {

    private ArrayList<Product> productList;

    public ProductAdapter(ArrayList<Product> productList) {
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.single_row, parent, false);
        return new ProductViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        holder.txtSKU.setText(productList.get(position).getSku());
        holder.txtUPC.setText(productList.get(position).getUpc());
        holder.txtPrice.setText(String.valueOf(productList.get(position).getPrice()));


    }
    @Override
    public int getItemCount() {
        return productList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView txtSKU, txtUPC, txtPrice;

        ProductViewHolder(View itemView) {
            super(itemView);
            txtSKU =  itemView.findViewById(R.id.sku);
            txtUPC =  itemView.findViewById(R.id.upc);
            txtPrice =  itemView.findViewById(R.id.price);
        }
    }
}
