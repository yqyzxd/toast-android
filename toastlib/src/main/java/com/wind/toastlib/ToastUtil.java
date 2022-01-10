package com.wind.toastlib;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.StyleRes;
import androidx.fragment.app.Fragment;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created By wind
 * on 2020-01-03
 */
public class ToastUtil {
    public static void showToast(Activity activity, int resId) {
        showToast(activity, activity.getResources().getString(resId), false);
    }

    public static void showToast(Activity activity, int resId, boolean warning) {
        showToast(activity, activity.getResources().getString(resId), warning);
    }

    public static void showToast(Fragment fragment, int resId) {
        showToast(fragment, fragment.getResources().getString(resId), false,false);
    }

    public static void showToast(Fragment fragment, String msg) {
        showToast(fragment, msg, false,false);
    }




    public static void showToast(Activity activity, String msg) {
        showToast(activity, msg, false);
    }
    public static void showToast(Activity activity, String msg, boolean warning){
        showToast(activity, msg, warning,false);
    }
    public static void showToast(Activity activity, String msg, boolean warning,boolean cross) {
        if (activity == null || activity.isFinishing()) {
            return;
        }
        //判断是否有显示悬浮窗的权限
        //android5.0以下无法获取是否打开系统通知权限
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            showFakeToast(activity
                            .getWindow()
                            .getDecorView()
                            .findViewById(android.R.id.content)
                    , msg);

        } else {
            boolean isNotificationEnabled = NotificationUtil.isNotificationEnabled(activity);
            if (isNotificationEnabled && cross) {
                // Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();

                showSystemToast(activity, msg, warning);
            } else {
                showFakeToast(activity
                                .getWindow()
                                .getDecorView()
                                .findViewById(android.R.id.content)
                        , msg);
            }
        }
    }

    public static void showToast(Fragment fragment, String msg, boolean warning,boolean cross) {

        if (fragment != null && fragment.getView() != null) {
            Activity activity = fragment.getActivity();
            if (activity == null || activity.isFinishing()) {
                return;
            }
            if (fragment.getView().findViewById(R.id.toast_container) != null) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    showFakeToast(fragment.getView().findViewById(R.id.toast_container)
                            , msg);
                } else {
                    boolean isNotificationEnabled = NotificationUtil.isNotificationEnabled(fragment.getActivity());
                    if (isNotificationEnabled && cross) {

                        showSystemToast(activity, msg, warning);
                    } else {
                        showFakeToast(fragment
                                        .getView()
                                        .findViewById(R.id.toast_container)
                                , msg);
                    }
                }
            } else {
                showToast(activity, msg, warning,cross);
            }

        }

    }

    private static void showFakeToast(View view, String msg) {
        TransientFrame
                .make(view,
                        msg, TransientFrame.LENGTH_SHORT)
                .show();
    }


    private static void showSystemToast(Context context, String msg, boolean warning) {
        View toastView = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.layout_toast, null);
        ViewGroup ll_toast_container = toastView.findViewById(R.id.ll_toast_container);
        TextView tv = toastView.findViewById(R.id.tv);
        if (tv == null) {
            return;
        }
        tv.setText(msg);
        tv.setActivated(warning);
        if (warning) {
            tv.setTextColor(Color.WHITE);
        }
        int screenWidth = 1080;
        int screenHeight = 1920;
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity == null || activity.isFinishing()) {
                return;
            }
            WindowManager windowManager = activity.getWindowManager();
            Display display = windowManager.getDefaultDisplay();

            screenWidth = display.getWidth();
            screenHeight = display.getHeight();
        }


      //  ll_toast_container.getLayoutParams().width = screenWidth;
        //ll_toast_container.getLayoutParams().height = screenHeight;


        Toast toast = new Toast(context.getApplicationContext());

        toast.setGravity(Gravity.TOP, 0, 0);
        // tv.setAlpha(0.9f);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(toastView);
        toastView.setAlpha(0);
        toastView.animate()
                .alpha(1f)
                .translationY(dip2px(context,10))
                .setStartDelay(50)
                .start();
        //反射TN 获取 mParams，设置windowAnimations
        setWidnowAnimations(toast,0);
        toast.show();
    }

    private static void setWidnowAnimations(Toast toast, @StyleRes int style) {
        try {
            Object mTN = null;
            if (Build.VERSION.SDK_INT >= 28) {//android P
                //TODO 待验证元反射
                Method metaGetDeclaredFieldMethod =
                        Class.class.getDeclaredMethod("getDeclaredField", String.class); // 公开API，无问题
                if (metaGetDeclaredFieldMethod != null) {
                    metaGetDeclaredFieldMethod.setAccessible(true);
                    Field mTNFiled = (Field) metaGetDeclaredFieldMethod.invoke(toast.getClass(), "mTN");
                    mTNFiled.setAccessible(true);
                    mTN = mTNFiled.get(toast);
                }
            } else {
                Field field = toast.getClass().getDeclaredField("mTN");
                field.setAccessible(true);
                mTN = field.get(toast);
            }

            if (mTN != null) {
                Field field;
                if (Build.VERSION.SDK_INT >= 28){
                    field= (Field) getDeclaredMethod().invoke(mTN.getClass(),"mParams");
                }else {
                    field = mTN.getClass().getField("mParams");
                }

                field.setAccessible(true);
                Object mParams = field.get(mTN);
                if (mParams != null
                        && mParams instanceof WindowManager.LayoutParams) {
                    WindowManager.LayoutParams params = (WindowManager.LayoutParams) mParams;
                    params.windowAnimations = style;
                    System.out.println("params.windowAnimations success");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static Method getDeclaredMethod() {
        Method metaGetDeclaredFieldMethod =null;
        try {
            metaGetDeclaredFieldMethod = Class.class.getDeclaredMethod("getDeclaredField", String.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return metaGetDeclaredFieldMethod;
    }

    public static int dip2px(Context context, float dipValue) {
        if (context==null){
            return (int) dipValue;
        }
        final float density = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * density + 0.5f);
    }
}
