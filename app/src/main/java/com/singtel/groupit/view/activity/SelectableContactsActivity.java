package com.singtel.groupit.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.singtel.groupit.R;
import com.singtel.groupit.model.domain.User;
import com.singtel.groupit.uiutil.DividerItemDecoration;
import com.singtel.groupit.view.adapter.SelectUsersAdapter;

import java.util.ArrayList;

/**
 * Created by razelsoco on 27/6/16.
 */

public class SelectableContactsActivity extends BaseActivity {

    public static final String EXTRA_ALL_USERS ="all_users";

    SelectUsersAdapter selectUsersAdapter;
    ArrayList<User> allUsers;
    @Override
    protected int getLayoutRes() {
        return R.layout.activity_select_users;
    }

    public static Intent newIntent(Context context, ArrayList<User> contacts){
        Intent intent = new Intent(context, SelectableContactsActivity.class);
        intent.putParcelableArrayListExtra(EXTRA_ALL_USERS, contacts);
        return intent;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectUsersAdapter = new SelectUsersAdapter();
        allUsers = getIntent().getExtras().getParcelableArrayList(EXTRA_ALL_USERS);
        selectUsersAdapter.setItems(allUsers);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.contacts_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, R.drawable.divider_silver));
        recyclerView.setAdapter(selectUsersAdapter);

        findViewById(R.id.bt_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.bt_done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK, getIntent().putExtra(EXTRA_ALL_USERS, allUsers));
                finish();
            }
        });
    }

}
