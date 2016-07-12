package com.singtel.groupit.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;

import com.singtel.groupit.model.DataManager;
import com.singtel.groupit.GroupITApplication;
import com.singtel.groupit.model.TestContactsResponse;
import com.singtel.groupit.model.domain.NewNoteSession;
import com.singtel.groupit.model.domain.Suborganization;
import com.singtel.groupit.model.domain.User;
import com.singtel.groupit.view.activity.SelectableContactsActivity;
import com.singtel.groupit.view.adapter.BaseRecyclerAdapter;
import com.singtel.groupit.view.adapter.FilteredContactsAdapter;
import com.singtel.groupit.view.adapter.SelectedUsersAdapter;
import com.singtel.groupit.view.fragment.RecipientsFragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by razelsoco on 27/6/16.
 */

public class RecipientsViewModel implements ViewModel {
    public ObservableInt selectedUsersListVisibility;
    public ObservableInt searchedUsersListVisibility;
    public ObservableField<String> searchString;

    DataManager dataManager;
    Context context;
    SelectedUsersAdapter selectedUsersAdapter;
    FilteredContactsAdapter searchedUsersAdapter;
    NewNoteSession newNoteSession;

    Subscription subscription;
    Fragment fragment;

    ArrayList<User> allUsers = new ArrayList<>();
    List<User> selectedUsers = new ArrayList<>();
    List<User> searchedUsers = new ArrayList<>();

    public RecipientsViewModel(Fragment fragment) {
        this.fragment = fragment;
        this.context = fragment.getContext();
        this.dataManager = GroupITApplication.get(context).getComponent().dataManager();
        this.newNoteSession = NewNoteSession.getInstance();

        this.selectedUsersListVisibility = new ObservableInt(View.VISIBLE);
        this.searchedUsersListVisibility = new ObservableInt(View.GONE);
        this.searchString = new ObservableField<String>("");

        this.selectedUsersAdapter = new SelectedUsersAdapter(context, new BaseRecyclerAdapter.OnItemClickListener<User>() {
            @Override
            public void onItemClicked(View view, int position, User data) {
                deleteRecepient(data);
            }
        });

        this.searchedUsersAdapter = new FilteredContactsAdapter(context, new BaseRecyclerAdapter.OnItemClickListener<User>() {
            @Override
            public void onItemClicked(View view, int position, User data) {
                addRecepient(data);
            }
        });

        this.selectedUsersAdapter.setItems(selectedUsers);
        this.searchedUsersAdapter.setItems(searchedUsers);

        loadContacts();
    }

    /**Binding methods start**/
    public SelectedUsersAdapter getSelectedUsersAdapter(){
        return selectedUsersAdapter;
    }

    public FilteredContactsAdapter getSearchedUsersAdapter() {
        return searchedUsersAdapter;
    }

    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        onSearchContact(charSequence.toString());
    }

    /**Binding methods end**/

    private void deleteRecepient(User data){
        selectedUsers.remove(data);
        selectedUsersAdapter.notifyDataSetChanged();
        newNoteSession.deleteRecipient(data);
    }

    private void addRecepient(User data){
        if(!selectedUsers.contains(data))
            selectedUsers.add(data);

        searchString.set("");

        newNoteSession.addRecipient(data);
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
                        if(testContactsResponse.users != null) {
                            allUsers = testContactsResponse.users;
                            filterGroupITUsers(allUsers);
                        }
                    }
                });
    }

    public void onSearchContact(String text){
        searchString.set(text);
        selectedUsersListVisibility.set(TextUtils.isEmpty(text) ? View.VISIBLE : View.GONE);
        searchedUsersListVisibility.set(TextUtils.isEmpty(text)  ? View.GONE : View.VISIBLE);

        if(!TextUtils.isEmpty(text) ){
            //this.allUsers;
            this.searchedUsers.clear();
            this.searchedUsers.addAll(allUsers);
            filterSearchedUsers(this.searchedUsers);
            searchedUsersAdapter.notifyDataSetChanged();
        }

    }

    public void onOpenAllContacts(View view){
        fragment.startActivityForResult(SelectableContactsActivity.newIntent(context, allUsers), RecipientsFragment.REQUEST_CODE_SELECT_USERS);
    }

    @Override
    public void onDestroy() {
        subscription.unsubscribe();
    }

    /**
     * When user types on the text field to search for a user,
     * this method will filter the user list according to the search string
     * */
    public void filterSearchedUsers(List<User> userArrayList) {
        Iterator<User> userIterator = userArrayList.iterator();
        while (userIterator.hasNext()) {
            User c = userIterator.next();
            if (!c.getName().toLowerCase().contains(searchString.get())) {
                userIterator.remove();
            }
        }
    }

    /**
     * This method is called after {@link SelectableContactsActivity} returns result.
     * User list will be filtered according to the selected state of each user
     * */
    public void filterSelectedUsers(List<User> userArrayList){
        //update all user list, passing thru parcealable does not give the exact instance of the object
        //so we need to update the all users list
        this.allUsers.clear();
        this.allUsers.addAll(userArrayList);

        this.selectedUsers.clear();
        this.selectedUsers.addAll(allUsers);

        Iterator<User> userIterator = this.selectedUsers.iterator();
        while (userIterator.hasNext()) {
            User c = userIterator.next();
            if (!c.isSelected()) {
                userIterator.remove();
            }
        }

        selectedUsersAdapter.notifyDataSetChanged();
        newNoteSession.setRecipients(selectedUsers);

    }

    /**
     * Note sending feature is only applicable for users in the GroupIT department
     * This method filters the users returned by server since it returns all type of user
     * */
    public void filterGroupITUsers(List<User> userArrayList){
        Iterator<User> userIterator = userArrayList.iterator();
        while (userIterator.hasNext()) {
            User user = userIterator.next();
            if (user.getSuborganizations() == null) {
                userIterator.remove();
            }else{
                boolean isGroupIT=false;

                for(Suborganization so : user.getSuborganizations()){
                    if(so.getName().equalsIgnoreCase("GroupIT"))
                        isGroupIT = true;
                }

                if(!isGroupIT)
                    userIterator.remove();
            }
        }
    }
}
