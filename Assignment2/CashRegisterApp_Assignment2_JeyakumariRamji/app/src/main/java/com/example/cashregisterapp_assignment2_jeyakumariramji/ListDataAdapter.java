package com.example.cashregisterapp_assignment2_jeyakumariramji;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;
import java.util.Locale;

public class ListDataAdapter extends BaseAdapter {
    private List<Product> productList;
    private Context context;

    public ListDataAdapter(Context context, List<Product> productList) {
        this.productList = productList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Product getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        }
        Product product = productList.get(position);

        // Bind the product data to the views
        TextView typeTextView = convertView.findViewById(R.id.productType);
        TextView priceTV = convertView.findViewById(R.id.productPrice);
        TextView quantityTextView = convertView.findViewById(R.id.productQuantity);

        typeTextView.setText(product.getType());
        priceTV.setText(String.format(Locale.getDefault(), "$%.2f", product.getPrice()));
        quantityTextView.setText(String.valueOf(product.getQuantity()));

        return convertView;
    }
}
