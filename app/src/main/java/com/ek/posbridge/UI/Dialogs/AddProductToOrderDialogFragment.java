package com.ek.posbridge.UI.Dialogs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.ek.posbridge.Adapters.ProductListAdapter;
import com.ek.posbridge.Models.Products;
import com.ek.posbridge.R;
import com.ek.posbridge.Square.SquarePOSChargeActivity;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.TRUE;

public class AddProductToOrderDialogFragment extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ProductListAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    Button save;
    Button close;
    private List<Products> secondList = new ArrayList<Products>();
    TextView totalText;
    String totalStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_productresponse);

        totalText = findViewById(R.id.totalText);


        ProductListAdapter adapter = new ProductListAdapter(totalStr);
        adapter.getTotalstr();
//        Log.i("ADDPRODUCTDIALOGFRAG TOTAL = ", totalStr);
        totalText.setText(totalStr);



        mRecyclerView = findViewById(R.id.recyclerView);
        adapter = new ProductListAdapter((ArrayList<Products>) secondList);
        mLayoutManager = new LinearLayoutManager(AddProductToOrderDialogFragment.this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.hasFixedSize();
        mRecyclerView.getHeight();
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        close = findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //pass in the total price value to square

                Intent intent = new Intent(AddProductToOrderDialogFragment.this, SquarePOSChargeActivity.class );
                startActivity(intent);
            }
        });


    }

}
