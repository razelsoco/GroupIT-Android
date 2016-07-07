package com.singtel.groupit.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.singtel.groupit.model.DataManager;
import com.singtel.groupit.GroupITApplication;
import com.singtel.groupit.model.TestContactsResponse;
import com.singtel.groupit.model.domain.Contact;
import com.singtel.groupit.view.activity.SelectableContactsActivity;
import com.singtel.groupit.view.adapter.BaseRecyclerAdapter;
import com.singtel.groupit.view.adapter.FilteredContactsAdapter;
import com.singtel.groupit.view.adapter.RecepientsAdapter;
import com.singtel.groupit.view.fragment.RecepientsFragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by razelsoco on 27/6/16.
 */

public class RecepientsViewModel implements ViewModel {
    public ObservableInt recepientsListVisibility;
    public ObservableInt autocompleteListVisibility;
    public ObservableField<String> searchString;

    DataManager dataManager;
    Context context;
    RecepientsAdapter recepientsAdapter;
    FilteredContactsAdapter filteredContactsAdapter;

    Subscription subscription;
    Fragment fragment;

    ArrayList<Contact> allContacts = new ArrayList<>();
    List<Contact> addedContacts = new ArrayList<>();
    List<Contact> filteredContacts = new ArrayList<>();

    public RecepientsViewModel(Fragment fragment) {
        this.fragment = fragment;
        this.context = fragment.getContext();
        this.dataManager = GroupITApplication.get(context).getComponent().dataManager();

        this.recepientsListVisibility = new ObservableInt(View.VISIBLE);
        this.autocompleteListVisibility = new ObservableInt(View.GONE);
        this.searchString = new ObservableField<String>("");

        this.recepientsAdapter = new RecepientsAdapter(context, new BaseRecyclerAdapter.OnItemClickListener<Contact>() {
            @Override
            public void onItemClicked(View view, int position, Contact data) {
                deleteRecepient(data);
            }
        });
        this.recepientsAdapter.setItems(addedContacts);

        this.filteredContactsAdapter = new FilteredContactsAdapter(context, new BaseRecyclerAdapter.OnItemClickListener<Contact>() {
            @Override
            public void onItemClicked(View view, int position, Contact data) {
                addRecepient(data);
            }
        });
        this.filteredContactsAdapter.setItems(filteredContacts);

        loadContacts();
    }

    /**Binding methods start**/
    public RecepientsAdapter getRecepientsAdapter(){
        return this.recepientsAdapter;
    }

    public FilteredContactsAdapter getFilteredContactsAdapter() {
        return filteredContactsAdapter;
    }

    public TextWatcher getContactNameEditTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                onSearchContact(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
    }
    /**Binding methods end**/

    private void deleteRecepient(Contact data){
        addedContacts.remove(data);
        recepientsAdapter.notifyDataSetChanged();
    }

    private void addRecepient(Contact data){
        if(!addedContacts.contains(data))
            addedContacts.add(data);

        searchString.set("");
    }

    private void loadContacts(){
        subscription = this.dataManager.getContacts().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(this.dataManager.getScheduler())
                .subscribe(new Subscriber<TestContactsResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(TestContactsResponse testContactsResponse) {
                        if(testContactsResponse.contacts != null)
                            allContacts = testContactsResponse.contacts;
                    }
                });
    }

    public void onSearchContact(String text){
        searchString.set(text);
        recepientsListVisibility.set(TextUtils.isEmpty(text) ? View.VISIBLE : View.GONE);
        autocompleteListVisibility.set(TextUtils.isEmpty(text)  ? View.GONE : View.VISIBLE);

        if(!TextUtils.isEmpty(text) ){
            //this.allContacts;
            this.filteredContacts.clear();
            this.filteredContacts.addAll(allContacts);
            filterUsers(this.filteredContacts);
            filteredContactsAdapter.notifyDataSetChanged();
        }

    }

    public void onOpenAllContacts(View view){
        fragment.startActivityForResult(SelectableContactsActivity.newIntent(context, allContacts), RecepientsFragment.REQUEST_CODE_SELECT_USERS);
    }

    @Override
    public void onDestroy() {
        subscription.unsubscribe();
    }

    public void filterUsers(List<Contact> contactArrayList) {
        Iterator<Contact> contactIterator = contactArrayList.iterator();
        while (contactIterator.hasNext()) {
            Contact c = contactIterator.next();
            if (!c.getName().toLowerCase().contains(searchString.get())) {
                contactIterator.remove();
            }
        }
    }

    public void filterSelectedUsers(ArrayList<Contact> contactArrayList){
        this.allContacts.clear();
        this.allContacts.addAll(contactArrayList);

        this.addedContacts.clear();
        this.addedContacts.addAll(allContacts);

        Iterator<Contact> contactIterator = this.addedContacts.iterator();
        while (contactIterator.hasNext()) {
            Contact c = contactIterator.next();
            if (!c.isSelected()) {
                contactIterator.remove();
            }
        }

        recepientsAdapter.notifyDataSetChanged();

    }
}
