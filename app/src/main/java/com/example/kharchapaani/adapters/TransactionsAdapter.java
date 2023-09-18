package com.example.kharchapaani.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kharchapaani.R;
import com.example.kharchapaani.Utils.Constants;
import com.example.kharchapaani.Utils.Helper;
import com.example.kharchapaani.databinding.RowTransactionBinding;
import com.example.kharchapaani.models.Category;
import com.example.kharchapaani.models.Transaction;

import java.util.ArrayList;

import io.realm.RealmResults;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.TransactionViewHolder> {
    Context context;
    RealmResults<Transaction> transactions;
    public TransactionsAdapter(Context context, RealmResults<Transaction> transactions){
        this.context = context;
        this.transactions = transactions;;
    }
    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TransactionViewHolder(LayoutInflater.from(context).inflate(R.layout.row_transaction,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);
        holder.binding.transactionAmount.setText(String.valueOf(transaction.getAmount()));
        holder.binding.accountLbl.setText(transaction.getAccount());
        holder.binding.transactionDate.setText(Helper.formatDate(transaction.getDate()));
        holder.binding.transactionCategory.setText(transaction.getCategory());


        Category transactionCategory = Constants.getCategoryDetails(transaction.getCategory());

        holder.binding.categoryIcon.setImageResource(transactionCategory.getCategoryImage());

        if(transaction.getType().equals(Constants.INCOME)){
            holder.binding.transactionAmount.setTextColor(context.getColor(R.color.greenColor));
        }else if(transaction.getType().equals(Constants.EXPENSE)){
            holder.binding.transactionAmount.setTextColor(context.getColor(R.color.redColor));
        }
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder {
        RowTransactionBinding binding;
        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RowTransactionBinding.bind(itemView);
        }
    }
}
