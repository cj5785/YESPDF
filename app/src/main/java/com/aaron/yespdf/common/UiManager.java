package com.aaron.yespdf.common;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.IntRange;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.Toolbar;

import com.aaron.base.util.StatusBarUtils;
import com.aaron.yespdf.R;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ToastUtils;

/**
 * @author Aaron aaronzzxup@gmail.com
 */
public final class UiManager {

    public static void setNavigationBarColor(Activity activity, int color) {
        activity.getWindow().setNavigationBarColor(color);
    }

    public static void setStatusBar(Activity activity, Toolbar toolbar) {
        toolbar.setPadding(0, ConvertUtils.dp2px(25), 0, 0);
        int version = Build.VERSION.SDK_INT;
        if (version >= Build.VERSION_CODES.M) {
            StatusBarUtils.setTransparent(activity, true);
        } else {
            StatusBarUtils.setTranslucent(activity, 60);
        }
    }

    public static void setTransparentStatusBar(Activity activity) {
        StatusBarUtils.setTransparent(activity);
    }

    public static void setTranslucentStatusBar(Activity activity) {
        StatusBarUtils.setTranslucent(activity, 100);
    }

    public static void setTransparentNavigationBar(Window window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.setNavigationBarColor(Color.TRANSPARENT);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    public static void setTransparentStatusBar(Window window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setNavigationBarColor(Color.TRANSPARENT);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    public static void setTranslucentStatusBar(Activity activity, @IntRange(from = 0, to = 255) int alpha) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().setStatusBarColor(Color.argb(alpha, 0, 0, 0));
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    public static void showShort(CharSequence text) {
        ToastUtils.setGravity(Gravity.BOTTOM, 0, 250);
        View toastView = ToastUtils.showCustomShort(R.layout.app_toast);
        TextView tv    = toastView.findViewById(R.id.app_tv);
        tv.setText(text);
    }

    public static void showShort(@StringRes int res) {
        ToastUtils.setGravity(Gravity.BOTTOM, 0, 250);
        View toastView = ToastUtils.showCustomShort(R.layout.app_toast);
        TextView tv = toastView.findViewById(R.id.app_tv);
        tv.setText(res);
    }

    public static void showCenterShort(@StringRes int res) {
        ToastUtils.setGravity(Gravity.CENTER, 0, 0);
        View toastView = ToastUtils.showCustomShort(R.layout.app_toast);
        TextView tv = toastView.findViewById(R.id.app_tv);
        tv.setText(res);
    }

    private UiManager() {}
}
