package com.singtel.groupit.viewmodel;

import android.databinding.BindingAdapter;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.view.View;
import android.widget.ImageView;

import com.singtel.groupit.R;
import com.singtel.groupit.model.domain.Contact;
import com.singtel.groupit.model.domain.Template;
import com.squareup.picasso.Picasso;

/**
 * Created by razelsoco on 28/6/16.
 */
public class ItemTemplateViewModel implements ViewModel{
    public ObservableBoolean selectedState;
    public Template template;

    public ItemTemplateViewModel(Template template) {
        this.selectedState = new ObservableBoolean(template.isSelected());
        this.template = template;
    }

    public void onImageClicked(View v){
        this.selectedState.set(!this.selectedState.get());
    }

    public String getImageUrl() {
        // The URL will usually come from a model (i.e Profile)
        return this.template.getUrl();
    }

    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Picasso.with(view.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.placeholder)
                .into(view);
    }

    public void setSelectedState(boolean isSelected){
        this.selectedState.set(isSelected);
    }

    @Override
    public void onDestroy() {

    }
}
