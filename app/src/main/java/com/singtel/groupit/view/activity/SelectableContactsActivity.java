package com.singtel.groupit.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.singtel.groupit.R;
import com.singtel.groupit.model.domain.Contact;
import com.singtel.groupit.uiutil.DividerItemDecoration;
import com.singtel.groupit.uiutil.UiUtils;
import com.singtel.groupit.view.adapter.AllContactsAdapter;
import com.singtel.groupit.view.fragment.RecepientsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by razelsoco on 27/6/16.
 */

public class SelectableContactsActivity extends BaseActivity {

    private static final String EXTRA_ALL_CONTACTS="all_contacts";

    AllContactsAdapter allContactsAdapter;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_selectable_contacts;
    }

    public static Intent newIntent(Context context, ArrayList<Contact> contacts){
        Intent intent = new Intent(context, SelectableContactsActivity.class);
        intent.putParcelableArrayListExtra(EXTRA_ALL_CONTACTS, contacts);
        return intent;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        allContactsAdapter = new AllContactsAdapter(this);
        ArrayList<Contact> contacts = getIntent().getExtras().getParcelableArrayList(EXTRA_ALL_CONTACTS);
        allContactsAdapter.setItems(contacts);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.contacts_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, R.drawable.divider_silver));
        recyclerView.setAdapter(allContactsAdapter);
    }
}
