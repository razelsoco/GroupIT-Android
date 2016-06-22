package com.singtel.groupit.view.fragment;

import android.view.View;
import android.view.View.OnClickListener;

public abstract class BaseMenuFragment extends BaseFragment implements OnClickListener {

    /**
     * Called when the sliding menu is closing.
     * Called by the @SlidingActivity class.
     */
    public void onMenuClosing() {

    }
	
	@Override
	public void onClick(View v) {

	}

    public void refreshData() {
    }
}
