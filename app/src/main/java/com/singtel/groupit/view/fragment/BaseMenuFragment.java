package com.singtel.groupit.view.fragment;

import android.support.v4.app.Fragment;
import com.singtel.groupit.R;
import com.singtel.groupit.util.Utils;

public abstract class BaseMenuFragment extends BaseFragment{

	/**
	 * Convinience method to replace fragment.
	 * Fragments are added to the back stack
	 * @param name -> tag used in the backstack
	 * @param f
	 */

    public void replaceMenuFragment(String name, Fragment f) {
        Utils.replaceMenuFragment(getActivity(), name, f, R.id.menu_frame);
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

	public void onMenuClosing(){

	}

	public void onBackPressed(){
		getFragmentManager().popBackStack();
	}
}
