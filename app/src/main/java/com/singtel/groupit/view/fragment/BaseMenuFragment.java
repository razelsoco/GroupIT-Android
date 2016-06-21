package com.singtel.groupit.view.fragment;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;

import com.singtel.groupit.util.Utils;

public abstract class BaseMenuFragment extends BaseFragment implements OnClickListener {

//	public static final String SIGN_IN = "Sign In";
//	public static final String EDIT = "Edit";


	/**
	 * Convinience method to replace fragment.
	 * Fragments are added to the back stack
	 * @param name -> tag used in the backstack
	 * @param f
	 */

    public void replaceFragment(String name, Fragment f, int containerId) {
        Utils.replaceMenuFragment(getActivity(), name, f, containerId);
    }

	/**
	 * Convinience method to pop back stack.
	 * @param name -> Sent null to pop top of stack. 
	 * Sent a valid tag to pop till one before that tag or until that tag is reached.
	 * @param flag -> Either 0 or FragmentManager.POP_BACK_STACK_INCLUSIVE
	 */
	public void popBackStack(String name, int flag) {
		Utils.popBackStack(getActivity(), name, flag);
	}
	
	public boolean onBackPressed() {
		return false;
	}

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
