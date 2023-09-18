package com.example.kharchapaani.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.Menu;

import com.example.kharchapaani.Utils.Constants;
import com.example.kharchapaani.Utils.Helper;
import com.example.kharchapaani.adapters.TransactionsAdapter;
import com.example.kharchapaani.models.Transaction;
import com.example.kharchapaani.views.fragments.AddTransactionFragment;
import com.example.kharchapaani.R;
import com.example.kharchapaani.databinding.ActivityMainBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    Calendar calendar;
    Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setSupportActionBar(binding.toolBar);
        getSupportActionBar().setTitle("Transactions");

        Constants.setCategories();

        calendar = Calendar.getInstance();
        updateDate();
        binding.nextDateBtn.setOnClickListener(c->{
            calendar.add(Calendar.DATE,1);
            updateDate();
        });
        binding.previousDateBtn.setOnClickListener(c->{
            calendar.add(Calendar.DATE,-1);
            updateDate();
        });

        binding.floatingActionButton.setOnClickListener(c->{
            new AddTransactionFragment().show(getSupportFragmentManager(),null);
        });

        realm.beginTransaction();

        realm.copyToRealmOrUpdate(new Transaction(Constants.INCOME,"Business","Cash","Note here",new Date(),500,new Date().getTime()));
        realm.copyToRealmOrUpdate(new Transaction(Constants.EXPENSE,"Investment","Bank","Note here",new Date(),300,new Date().getTime()));
        realm.copyToRealmOrUpdate(new Transaction(Constants.EXPENSE,"Rent","PhonePe","Note here",new Date(),5000,new Date().getTime()));
        realm.copyToRealmOrUpdate(new Transaction(Constants.INCOME,"Business","Cash","Note here",new Date(),5050,new Date().getTime()));
        realm.copyToRealmOrUpdate(new Transaction(Constants.INCOME,"Loan","Other","Note here",new Date(),5070,new Date().getTime()));

        realm.commitTransaction();

        RealmResults<Transaction> transactions = realm.where(Transaction.class).findAll();

        TransactionsAdapter transactionsAdapter = new TransactionsAdapter(this,transactions);
        binding.transactionsList.setLayoutManager(new LinearLayoutManager(this));
        binding.transactionsList.setAdapter(transactionsAdapter);
    }

    void setupDatabse(){
        Realm.init(this);
        realm = Realm.getDefaultInstance();
    }

    void updateDate(){
        binding.currentDate.setText(Helper.formatDate(calendar.getTime()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}