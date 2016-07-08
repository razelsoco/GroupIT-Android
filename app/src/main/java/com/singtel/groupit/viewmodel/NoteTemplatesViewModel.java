package com.singtel.groupit.viewmodel;

import android.content.Context;
import android.content.res.Resources;
import android.databinding.ObservableField;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.singtel.groupit.model.DataManager;
import com.singtel.groupit.GroupITApplication;
import com.singtel.groupit.R;
import com.singtel.groupit.model.TestTemplatesResponse;
import com.singtel.groupit.model.domain.Template;
import com.singtel.groupit.view.activity.MessageActivity;
import com.singtel.groupit.view.adapter.ImageTemplateAdapter;
import com.singtel.groupit.view.fragment.NoteTemplatesFragment;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by razelsoco on 27/6/16.
 */

public class NoteTemplatesViewModel implements ViewModel {
    public ObservableField<Drawable> selectedTemplateImage;
    public ObservableField<String> message;
    private BindableFieldTarget bindableFieldTarget;

    Fragment fragment;
    DataManager dataManager;
    ImageTemplateAdapter templatesAdapter;

    Subscription subscription;

    List<Template> templates = new ArrayList<>();
    ItemTemplateViewModel selectedTemplate;

    String selectedImgURL="url";


    public NoteTemplatesViewModel(Fragment fragment) {
        this.fragment = fragment;
        this.dataManager = GroupITApplication.get(fragment.getActivity()).getComponent().dataManager();
        this.selectedTemplateImage = new ObservableField<>();
        this.message = new ObservableField<>("Tap here to enter a custom message");

        this.templatesAdapter = new ImageTemplateAdapter(fragment.getActivity(), new ImageTemplateAdapter.OnViewModelChangeListener() {
            @Override
            public void onItemClicked(View view, int position, ViewModel data) {
                if(selectedTemplate != null)
                    selectedTemplate.selectedState.set(false);

                selectedTemplate = (ItemTemplateViewModel) data;

                selectedImgURL = selectedTemplate.getImageUrl();
                loadSelectedTemplate();

            }
        });
        this.templatesAdapter.setItems(templates);
        bindableFieldTarget = new BindableFieldTarget(selectedTemplateImage, fragment.getActivity().getResources());


        loadContacts();
    }

    private void loadSelectedTemplate(){
        Picasso.with(fragment.getActivity())
                .load(selectedImgURL)
                .placeholder(R.drawable.placeholder)
                .into(bindableFieldTarget);
    }

    /**Binding methods start**/
    public ImageTemplateAdapter getTemplatesAdapter(){
        return this.templatesAdapter;
    }
    public void onCreateMessage(View v){
        fragment.startActivityForResult(MessageActivity.newIntent(fragment.getActivity()), NoteTemplatesFragment.REQUEST_CODE_CREATE_MESSAGE);
    }
    /**Binding methods end**/


    private void loadContacts(){

        subscription = this.dataManager.getTemplates().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(this.dataManager.getScheduler())
                .subscribe(new Subscriber<TestTemplatesResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(TestTemplatesResponse testTemplatesResponse) {
                        if(testTemplatesResponse.templates != null) {
                            templates.addAll(testTemplatesResponse.templates);
                            templatesAdapter.notifyDataSetChanged();
                            templates.get(0).setSelected(true);
                        }
                    }
                });
    }


    public class BindableFieldTarget implements Target {

        private ObservableField<Drawable> observableField;
        private Resources resources;

        public BindableFieldTarget(ObservableField<Drawable> observableField, Resources resources) {
            this.observableField = observableField;
            this.resources = resources;
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            observableField.set(new BitmapDrawable(resources, bitmap));
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            observableField.set(errorDrawable);
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
            observableField.set(placeHolderDrawable);
        }
    }

    @Override
    public void onDestroy() {
        subscription.unsubscribe();
    }
}
