package com.example.iqinvest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private List<Item> itemList;
    private ItemClickListener itemClickListener;

    public ItemAdapter(List<Item> itemList, ItemClickListener itemClickListener) {
        this.itemList = itemList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        final Item item = itemList.get(position);

        holder.priceTextView.setText("Price: " + item.getPrice() + "₽");
        holder.clickIncomeTextView.setText("Click Income: " + item.getClickIncome());
        holder.incomePerSecondTextView.setText("Income per Second: " + item.getIncomePerSecond() + "₽");

        holder.buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView priceTextView;
        TextView clickIncomeTextView;
        TextView incomePerSecondTextView;
        Button buyButton;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            clickIncomeTextView = itemView.findViewById(R.id.clickIncomeTextView);
            incomePerSecondTextView = itemView.findViewById(R.id.incomePerSecondTextView);
            buyButton = itemView.findViewById(R.id.buyButton);
        }
    }

    public interface ItemClickListener {
        void onItemClick(Item item);
    }
}
