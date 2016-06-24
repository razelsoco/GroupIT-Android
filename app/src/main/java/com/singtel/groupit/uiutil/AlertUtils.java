package com.singtel.groupit.uiutil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.StringRes;

import com.singtel.groupit.R;
import com.singtel.groupit.util.NetworkUtils;


public class AlertUtils {

    public static void showAlert(Context context,
                                 String title, String message,
                                 String btnOk, DialogInterface.OnClickListener btnOkClick,
                                 String btnCancel, DialogInterface.OnClickListener btnCancelClick) {
        //if(isShowingDialog) return;
        if (((Activity) context).isFinishing()) return;

        if (title == null && message == null) {
            showInternetAlert(context, btnOkClick);
            return;
        }

        final AlertDialog ad = new AlertDialog.Builder(context).create();
        ad.setCancelable(false);

        if (btnOk == null) { btnOk = context.getString(R.string.ok); }
        if (title != null) { ad.setTitle(title); }
        if (message != null) { ad.setMessage(message); }

        if (btnOkClick == null) {
            btnOkClick = new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ad.dismiss();

                }
            };
        }
        ad.setButton(AlertDialog.BUTTON_POSITIVE, btnOk, btnOkClick);

        if (btnCancel != null) {
            if (btnCancelClick == null) {
                btnCancelClick = new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ad.dismiss();
                    }
                };
            }
            ad.setButton(AlertDialog.BUTTON_NEGATIVE, btnCancel, btnCancelClick);
        }

        ad.show();
    }

    public static void showAlert(Context context,
                                 String title, String message,
                                 @StringRes int btnOk, DialogInterface.OnClickListener btnOkClick,
                                 @StringRes int btnCancel, DialogInterface.OnClickListener btnCancelClick) {
        showAlert(context,
                title, message,
                context.getString(btnOk), btnOkClick,
                context.getString(btnCancel), btnCancelClick);
    }

    public static void showAlert(Context context,
                                 @StringRes int title, @StringRes int message,
                                 String btnOk, DialogInterface.OnClickListener btnOkClick,
                                 String btnCancel, DialogInterface.OnClickListener btnCancelClick) {
        showAlert(context,
                context.getString(title), context.getString(message),
                btnOk, btnOkClick,
                btnCancel, btnCancelClick);
    }

    public static void showAlert(Context context,
                                 @StringRes int title, @StringRes int message,
                                 @StringRes int btnOk, DialogInterface.OnClickListener btnOkClick,
                                 @StringRes int btnCancel, DialogInterface.OnClickListener btnCancelClick) {
        showAlert(context, context.getString(title), context.getString(message),
                context.getString(btnOk), btnOkClick,
                context.getString(btnCancel), btnCancelClick);
    }

    public static void showInternetAlert(Context context, DialogInterface.OnClickListener btnCancelClick) {
        if (NetworkUtils.isOnline(context)) {
            showAlert(context,
                    R.string.generic_error_msg_title, R.string.generic_error_msg,
                    null, btnCancelClick, null, null);
        } else {
            showAlert(context,
                    R.string.internet_connection_problem_title, R.string.internet_connection_problem_message,
                    null, btnCancelClick, null, null);
        }
    }
}
