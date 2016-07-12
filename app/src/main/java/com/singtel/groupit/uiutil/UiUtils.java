package com.singtel.groupit.uiutil;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.AnimRes;
import android.support.annotation.ColorRes;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.singtel.groupit.R;
import com.singtel.groupit.util.LogUtils;

import java.math.BigDecimal;
import java.util.Random;

/**
 * Created by lanna on 6/7/16.
 *
 */

public class UiUtils {

    /*
        Keyboard
     */
    public static void hideKeyboard(Activity activity, View view) {
        if (view == null || activity == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /*
        Color
     */

    public static final int getColor(Context context, @ColorRes int colorId) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            return ContextCompat.getColor(context, colorId);
        } else {
            return context.getResources().getColor(colorId);
        }
    }

    public static ColorStateList getColorStateList(Context context, @ColorRes int colorId) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            return ContextCompat.getColorStateList(context, colorId);
        } else {
            return context.getResources().getColorStateList(colorId);
        }
    }

    public static int getRandomColor() {
        Random rnd = new Random();
        return rnd.nextInt() & 0x00FFFFFF | 0xFF000000;
    }

    public static int evaluateColor(float color) {
        if (color > 255)
            return 255;
        return (int) color;
    }

    public static int accentColor(int color, float factor) {
        return Color.rgb(evaluateColor(Color.red(color) * factor),
                evaluateColor(Color.green(color) * factor),
                evaluateColor(Color.blue(color) * factor));
    }

    /*
        Size
     */
    public static int getBuiltInSize(Context context, int resId) {
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(resId, tv, true)) {
            return TypedValue.complexToDimensionPixelSize(tv.data,
                    getDisplayMetrics(context));
        }
        return 0;
    }

    public static float dpToPixel(Context context, int dpValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue,
                getDisplayMetrics(context));
    }


    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay()
                .getMetrics(metrics);
        return metrics;
    }


    public static float getConvertedPixels(Context context, int value) {
        return context == null ? 0 : (value * context.getResources().getDisplayMetrics().density);
    }

    /*
        System Views
     */

    public static void setDeviceStatusBarColor(Activity context, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = context.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
    }

    public static void doWhenViewHasSize(final View view, final Runnable runnable) {
        if (view.getWidth() == 0 || view.getHeight() == 0) {
            addOnGlobalLayoutListener(view, runnable);
        } else {
            runnable.run();
        }
    }

    public static void addOnGlobalLayoutListener(final View view, final Runnable runnable) {
        ViewTreeObserver observer = view.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                removeOnGlobalLayoutListener(view, this);
//                Log.i("ui", "onGlobalLayout: ");
                if (runnable != null) {
                    runnable.run();
                }
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void removeOnGlobalLayoutListener(View v, ViewTreeObserver.OnGlobalLayoutListener listener) {
        if (Build.VERSION.SDK_INT < 16) {
            v.getViewTreeObserver().removeGlobalOnLayoutListener(listener);
        } else {
            v.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
        }
    }

    /*
        Progress Dialog
     */
    public static ProgressDialog getProgressDialog(Context context) {
        ProgressDialog pd = new ProgressDialog(context);
        pd.setCancelable(false);
        pd.setMessage(context.getString(R.string.saving));
        return pd;
    }

    public static void dismissDialog(Dialog d) {
        if (d != null && d.isShowing()) {
            d.dismiss();
        }
    }

    /*
        Device / Window Screen
     */

    public static boolean isPortrait(Context context) {
        int orientation = context.getResources().getConfiguration().orientation;
        return orientation != Configuration.ORIENTATION_LANDSCAPE;
    }

    public static boolean isTablet(Context context) {
        return context.getResources().getBoolean(R.bool.is_tablet);
    }

    public static int getScreenOrientation(Activity context) {
        String TAG = "getScreenOrientation";
        int rotation = context.getWindowManager().getDefaultDisplay().getRotation();
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        int orientation;
        // if the device's natural orientation is portrait:
        if ((rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180)
                && height > width
                || (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270)
                && width > height) {
            switch (rotation) {
                case Surface.ROTATION_0:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    break;
                case Surface.ROTATION_90:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    break;
                case Surface.ROTATION_180:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
                    break;
                case Surface.ROTATION_270:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
                    break;
                default:
                    LogUtils.e(TAG, "Unknown screen orientation. Defaulting to portrait.");
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    break;
            }
        }
        // if the device's natural orientation is landscape or if the device
        // is square:
        else {
            switch (rotation) {
                case Surface.ROTATION_0:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    break;
                case Surface.ROTATION_90:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    break;
                case Surface.ROTATION_180:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
                    break;
                case Surface.ROTATION_270:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
                    break;
                default:
                    LogUtils.e(TAG, "Unknown screen orientation. Defaulting to landscape.");
                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    break;
            }
        }

        return orientation;
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public static int getScreenWidthWithoutOrientation() {
        return Math.min(getScreenWidth(), getScreenHeight());
    }

    /*
        Drawable / Image
     */

    /**
     * To handle memory leaks / optimizations
     * <p>
     * A special recursive method to remove drawables that are hogging memory.
     * This can be used when the activity is to be destroyed and somehow GC doesnt
     * do its job properly.
     * EG: In HTC One, GC collects the large bitmap in SplashActivity properly. In S4 however,
     * that does not happen. So this method comes in handy to remove the bitmap and save memory
     * despite the terrible GC behaviour in S4.
     *
     * @param view
     */
    public static void unbindDrawables(View view) {
        if (view.getBackground() != null)
            view.getBackground().setCallback(null);

        if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;
            imageView.setImageBitmap(null);
        } else if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++)
                unbindDrawables(viewGroup.getChildAt(i));

            if (!(view instanceof AdapterView))
                viewGroup.removeAllViews();
        }
    }

    private void scaleImage(ImageView view, int boundBoxInDp) {
        // Get the ImageView and its bitmap
        Drawable drawing = view.getDrawable();
        Bitmap bitmap = ((BitmapDrawable) drawing).getBitmap();

        // Get current dimensions
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        // Determine how much to scale: the dimension requiring less scaling is
        // closer to the its side. This way the image always stays inside your
        // bounding box AND either x/y axis touches it.
        float xScale = ((float) boundBoxInDp) / width;
        float yScale = ((float) boundBoxInDp) / height;
        float scale = (xScale <= yScale) ? xScale : yScale;

        // Create a matrix for the scaling and add the scaling data
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        // Create a new bitmap and convert it to a format understood by the ImageView
        Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        BitmapDrawable result = new BitmapDrawable(scaledBitmap);
        width = scaledBitmap.getWidth();
        height = scaledBitmap.getHeight();

        // Apply the scaled bitmap
        view.setImageDrawable(result);

        // Now change ImageView's dimensions to match the scaled image
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        params.width = width;
        params.height = height;
        view.setLayoutParams(params);
    }

    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_DOWN);
        return bd.floatValue();
    }

    public static SpannableString getImageSpan(String str, Drawable drawable) {
        SpannableString ss = new SpannableString(Html.fromHtml(str) + "   ");
        drawable.setBounds(0, 0, (int) (drawable.getIntrinsicWidth() / 1.2),
                (int) (drawable.getIntrinsicHeight() / 1.2));
        ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM);
        ss.setSpan(span, str.length() + 1, str.length() + 2,
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return ss;
    }

    /*
        Fragment supports
    */
    public static void replaceFragment(FragmentActivity context, String tag,
                                       Fragment f, @IdRes int containerId) {
        replaceFragment(context, tag, f, containerId,
                android.R.anim.fade_in, android.R.anim.fade_out,
                android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public static void replaceFragmentRightIn(FragmentActivity context, String tag,
                                              Fragment f, @IdRes int containerId) {
        replaceFragment(context, tag, f, containerId,
                R.anim.slide_in_right, R.anim.slide_out_left,
                android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    public static void replaceFragment(FragmentActivity context, String tag,
                                       Fragment f, @IdRes int containerId,
                                       @AnimRes int enter, @AnimRes int exit,
                                       @AnimRes int popEnter, @AnimRes int popExit) {
        context.getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(enter, exit, popEnter, popExit)
                .addToBackStack(tag)
                .replace(containerId, f)
                .commit();
    }

    public static void replaceFragmentWithoutHistory(FragmentActivity context,
                                                     Fragment f, @IdRes int containerId) {
        replaceFragmentWithoutHistory(context, f, containerId,
                android.R.anim.fade_in, android.R.anim.fade_out,
                android.R.anim.fade_in, android.R.anim.fade_out);
    }


    public static void replaceFragmentWithoutHistory(FragmentActivity context,
                                                     Fragment f, @IdRes int containerId,
                                                     @AnimRes int enter, @AnimRes int exit,
                                                     @AnimRes int popEnter, @AnimRes int popExit) {
        context.getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(enter, exit, popEnter, popExit)
                .replace(containerId, f)
                .commit();
    }

    public static void replaceFragment(FragmentActivity context,
                                       Fragment f, @IdRes int containerId, boolean addToBackStack) {
        FragmentTransaction ft = context.getSupportFragmentManager().beginTransaction();
        ft.replace(containerId, f);
        if (addToBackStack) {
            ft.addToBackStack(null);
        }
        ft.commit();
        context.getSupportFragmentManager().executePendingTransactions();
    }

    /**
     * @param activity
     * @param name     -> Sent null to pop top of stack.
     *                 Sent a valid tag to pop till that tag is reached.
     * @param flag     -> Either 0 or FragmentManager.POP_BACK_STACK_INCLUSIVE
     */
    public static void popBackStack(FragmentActivity activity, String name, int flag) {
        activity.getSupportFragmentManager().popBackStack(name, flag);
    }

    public static void setStatusBarColor(Context c, int resId){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = ((Activity)c).getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getColor(c, resId));
        }
    }

    /*
        Toast
     */
    public static void makeToastShort(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void makeToastLong(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
