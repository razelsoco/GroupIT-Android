package com.singtel.groupit.view.fragment;

public abstract class BaseMenuFragment extends BaseFragment{

    /**
     * Called when the sliding menu is closing.
     * Called by the @SlidingActivity class.
     */
    public void onMenuClosing() {

    }
	
	public void onBackPressed(){
		getFragmentManager().popBackStack();
	}
}
