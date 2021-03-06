package com.singtel.groupit.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Surface;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.singtel.groupit.GroupITApplication;
import com.singtel.groupit.R;

import java.math.BigDecimal;
import java.util.Random;

/**
 * Created by lanna on 6/7/16.
 */

public class UiUtils {

    // Custom item offset
    public static class ItemOffsetDecoration extends RecyclerView.ItemDecoration {

        private int itemOffset;
        private int cols;
        private boolean hasOutSidePadding;

        public ItemOffsetDecoration(int itemOffset, int cols, boolean hasOutSidePadding) {
            this.itemOffset = itemOffset;
            this.cols = cols;
            this.hasOutSidePadding = hasOutSidePadding;
        }

        public ItemOffsetDecoration(@NonNull Context context, @DimenRes int itemOffsetId,
                                    int cols, boolean hasOutSidePadding) {
            this(context.getResources().getDimensionPixelSize(itemOffsetId), cols, hasOutSidePadding);
        }

        public ItemOffsetDecoration(int itemOffset, boolean hasOutSidePadding) {
            this(itemOffset, 1, hasOutSidePadding);
        }

        public ItemOffsetDecoration(@NonNull Context context, @DimenRes int itemOffsetId, boolean hasOutSidePadding) {
            this(context, itemOffsetId, 1, hasOutSidePadding);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            int position = parent.getChildAdapterPosition(view);
            int colIndex = position % cols;
            int rowIndex = position / cols;

            // reset data
//            outRect.left = outRect.top = 0;

            // item at left or right side
            if (colIndex < cols - 1 // others except right
                    || (hasOutSidePadding && colIndex == cols - 1) // include right if required
                    ) {
                outRect.right = itemOffset;
            }

            // item at top or bottom side
            if (rowIndex > 0 // others except top
                    || (hasOutSidePadding && rowIndex == 0) // include top if required
                    ) {
                outRect.top = itemOffset;
            }

//            LogUtils.i(this, "getItemOffsets: pos:" + position// + ", outside:" + hasOutSidePadding
//                    + ", row:" + rowIndex + ", col:" + colIndex
//                    + " ->ltrb:(" + outRect.left + "," + outRect.top + "-" + outRect.right + "," + outRect.bottom + ")");
        }
    }

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
        Layout / View / ViewGroup
     */

    static int touchAreaAddition = (int) getConvertedPixels(GroupITApplication.getInstance(), 20);

    public static void enlargeTouchArea(final View parent, final View child) {
        parent.post(new Runnable() {
            @Override
            public void run() {
                int locationChild[] = new int[2];
                child.getLocationOnScreen(locationChild);

                int locationParent[] = new int[2];
                parent.getLocationOnScreen(locationParent);

                int left = locationChild[0] - locationParent[0];
                int top = locationChild[1] - locationParent[1];

                Rect rect = new Rect(left, top, left + child.getWidth(), top + child.getHeight());
                rect.inset(-touchAreaAddition, -touchAreaAddition);

                TouchDelegate delegate = new TouchDelegate(rect, child);
                parent.setTouchDelegate(delegate);
            }
        });
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
}
