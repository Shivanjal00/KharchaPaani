package com.example.kharchapaani.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.kharchapaani.Utils.Constants;
import com.example.kharchapaani.models.Transaction;

import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainViewModel extends AndroidViewModel {

    public MutableLiveData<RealmResults<Transaction>> transactions = new MutableLiveData<>();
    public MutableLiveData<Double> totalIncome = new MutableLiveData<>();
    public MutableLiveData<Double> totalExpense = new MutableLiveData<>();
    public MutableLiveData<Double> totalAmount = new MutableLiveData<>();
    Realm realm;
    public MainViewModel(@NonNull Application application) {
        super(application);
        Realm.init(application);
        setupDatabse();
    }

    public void getTransaction(Calendar calendar){
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);

        RealmResults<Transaction> newTransactions = realm.where(Transaction.class)
                .greaterThanOrEqualTo("date",calendar.getTime())
                .lessThan("date",new Date(calendar.getTime().getTime()+24*60*60*1000))
                .findAll();

        double income =  realm.where(Transaction.class)
                .greaterThanOrEqualTo("date",calendar.getTime())
                .lessThan("date",new Date(calendar.getTime().getTime()+24*60*60*1000))
                .equalTo("type", Constants.INCOME)
                .sum("amount").doubleValue();

        double expense =  realm.where(Transaction.class)
                .greaterThanOrEqualTo("date",calendar.getTime())
                .lessThan("date",new Date(calendar.getTime().getTime()+24*60*60*1000))
                .equalTo("type", Constants.EXPENSE)
                .sum("amount").doubleValue();

        double total =  realm.where(Transaction.class)
                .greaterThanOrEqualTo("date",calendar.getTime())
                .lessThan("date",new Date(calendar.getTime().getTime()+24*60*60*1000))
                .sum("amount").doubleValue();

        totalIncome.setValue(income);
        totalExpense.setValue(expense);
        totalAmount.setValue(total);

//        RealmResults<Transaction> newTransactions = realm.where(Transaction.class)
//                .equalTo("date",calendar.getTime()).findAll();

        transactions.setValue(newTransactions);
    }

    public  void addTransaction(Transaction transaction){
        realm.beginTransaction();

        realm.copyToRealmOrUpdate(transaction);

        realm.commitTransaction();

    }

//    public  void addTransactions(){
//        realm.beginTransaction();
//
//        realm.copyToRealmOrUpdate(new Transaction(Constants.INCOME,"Business","Cash","Note here",new Date(),500,new Date().getTime()));
//        realm.copyToRealmOrUpdate(new Transaction(Constants.EXPENSE,"Investment","Bank","Note here",new Date(),300,new Date().getTime()));
//        realm.copyToRealmOrUpdate(new Transaction(Constants.EXPENSE,"Rent","PhonePe","Note here",new Date(),5000,new Date().getTime()));
//        realm.copyToRealmOrUpdate(new Transaction(Constants.INCOME,"Business","Cash","Note here",new Date(),5050,new Date().getTime()));
//        realm.copyToRealmOrUpdate(new Transaction(Constants.INCOME,"Loan","Other","Note here",new Date(),5070,new Date().getTime()));
//
//        realm.commitTransaction();
//    }
    void setupDatabse(){
        realm = Realm.getDefaultInstance();
    }
}
