package com.singtel.groupit.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.BeforeCloseListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnClosedListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnOpenListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnOpenedListener;
import com.singtel.groupit.R;
import com.singtel.groupit.util.UiUtils;
import com.singtel.groupit.util.Utils;
import com.singtel.groupit.view.fragment.BaseMenuFragment;

public abstract class SlidingActivity extends BaseActivity {

    private static final String EXTRA_MENU_SHOWING = "opened";

    private View pbContainer;
    private Fragment mFrag; // The current fragment shown in the menu (the thing that pops out from sliding menu)
    private SlidingMenu mDrawer;
    private boolean loadingProfile = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpSlidingMenu(savedInstanceState);

        if (savedInstanceState != null) {
            showLoadingProfile(savedInstanceState.getBoolean("loadingProfile", false));
        }

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Fragment frag = getSupportFragmentManager().findFragmentById(R.id.menu_frame);
                if (frag instanceof BaseMenuFragment) {
                    ((BaseMenuFragment) frag).refreshData();
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(EXTRA_MENU_SHOWING, mDrawer.isMenuShowing());
        savedInstanceState.putBoolean("loadingProfile", loadingProfile);
        super.onSaveInstanceState(savedInstanceState);
    }


    /**
     * Triggers the showing of a progress bar view over the sliding menu.
     * Controls the loading overlay on the dashboard
     *
     * @param showProgress
     */
    public void showLoadingProfile(boolean showProgress) {
        if (pbContainer == null) {
            loadingProfile = false;
            //Log.d(" testing"," testing pb null");
            return;
        }
        //Log.d(" testing"," testing pb not null "+showProgress);
        if (showProgress) {
            loadingProfile = true;
            pbContainer.setVisibility(View.VISIBLE);
        } else {
            loadingProfile = false;
            pbContainer.setVisibility(View.GONE);
        }
    }

    private void setUpSlidingMenu(Bundle savedInstanceState) {
        mDrawer = new SlidingMenu(this);
        mDrawer.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
        mDrawer.setMenu(R.layout.menu_frame);
        mDrawer.setMode(SlidingMenu.LEFT);
        mDrawer.setTouchModeBehind(SlidingMenu.TOUCHMODE_MARGIN);
        mDrawer.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
//		mDrawer.setFadeDegree(0.35f);
        mDrawer.setShadowWidthRes(R.dimen.shadow_width);
        if (UiUtils.isTablet(this)) {
            mDrawer.setShadowDrawable(R.drawable.menu_shadow);
        }
        mDrawer.setBehindScrollScale(0f);
        mDrawer.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        mDrawer.setAboveFadeDegree(0.85f);

        if ((savedInstanceState != null
                && savedInstanceState.getBoolean(EXTRA_MENU_SHOWING, false))) {
            mDrawer.showMenu();
        }

        mDrawer.setOnClosedListener(new OnClosedListener() {

            @Override
            public void onClosed() {
                //Propagates the close event to the current visible menu fragment
                Fragment frag = getSupportFragmentManager().findFragmentById(R.id.menu_frame);
                if (frag instanceof BaseMenuFragment) {
                    ((BaseMenuFragment) frag).onMenuClosing();
                }

                //Pops all menu related back stack
                while (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    //Uncomment the 2 lines below in the event that in the future, the content part of the screen also uses the back stack.
                    //The 2 lines below, when u figure out a solution to the statement in the <> below,
                    //will result in the popping of all menu fragments when the menu is closed.
//						&& 
//						getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount()-1).getId() == <figure out a way to differentiate menu fragments with content fragments>){
//					LogUtils.d("pop back stack "+getSupportFragmentManager().getBackStackEntryCount());
                    getSupportFragmentManager().popBackStackImmediate();
                }
                onDrawerClosed();
            }
        });
        mDrawer.setOnOpenedListener(new OnOpenedListener() {

            @Override
            public void onOpened() {
                onDrawerOpened();
            }
        });
        mDrawer.setOnOpenListener(new OnOpenListener() {
            @Override
            public void onOpen() {
                onDrawerOpen();
            }
        });
        mDrawer.setBeforeCloseListener(new BeforeCloseListener() {
            @Override
            public boolean canClose() {
                return canCloseDrawer();
            }
        });

        pbContainer = this.findViewById(R.id.pb_container);

        mFrag = getSupportFragmentManager().findFragmentById(R.id.menu_frame);
        if (mFrag == null) {
            mFrag = onCreateMenuPanel();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.menu_frame, mFrag)
                    .commit();
        }
    }

    protected void onDrawerOpen() {
    }

    protected void onDrawerOpened() {
    }

    protected boolean canCloseDrawer() {
        return true;
    }

    protected abstract void onDrawerClosed();

    /**
     * For child fragments to override and decide what fragment
     * that INITIALLY populates the sliding menu pop up.
     * Instantiate and return fragment of choice here.
     *
     * @return
     */
    protected abstract Fragment onCreateMenuPanel();

    /**
     * Manages the process of changing fragments in the sliding menu popup
     *
     * @param addToBackStack
     * @param fragment
     */
    protected void showMenuFragment(Fragment fragment, boolean addToBackStack) {
        mFrag = fragment;
        Utils.replaceFragment(this, mFrag, R.id.menu_frame, addToBackStack);
        showMenu();
    }

    protected void showMenuFragment(String name, Fragment fragment) {
        mFrag = fragment;
        Utils.replaceFragment(this, name, mFrag, R.id.menu_frame);
        showMenu();
    }

    public void showMenu() {
        if (mDrawer != null) {
            mDrawer.showMenu();
        }
    }

    public void showContent() {
        if (mDrawer != null) {
            mDrawer.showContent();
        }
    }

    public void toggle() {
        if (mDrawer != null) {
            mDrawer.toggle();
        }
    }

    public boolean isMenuShowing() {
        return mDrawer.isMenuShowing();
    }

}
