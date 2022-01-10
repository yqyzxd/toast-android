package com.wind.toastlib;

import android.content.Context;

import androidx.core.app.NotificationManagerCompat;

/**
 * Created by wind on 2018/3/28.
 */

public class NotificationUtil {
    private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
    private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";
    /**
     * 用来判断是否开启通知权限
     * */
    public static boolean isNotificationEnabled(Context context){
        if (context==null){
            return false;
        }
        boolean isOpened = false;
        try {
            isOpened = NotificationManagerCompat.from(context).areNotificationsEnabled();
        } catch (Exception e) {
            e.printStackTrace();
            isOpened = false;
        }
        return isOpened;
        /*if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT){
            AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            ApplicationInfo appInfo = context.getApplicationInfo();

            String pkg = context.getApplicationContext().getPackageName();

            int uid = appInfo.uid;

            Class appOpsClass = null;

            try {
                appOpsClass = Class.forName(AppOpsManager.class.getName());
                Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE, String.class);
                Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);
                int value = (int)opPostNotificationValue.get(Integer.class);
                boolean enabled= ((int)checkOpNoThrowMethod.invoke(mAppOps,value, uid, pkg) == AppOpsManager.MODE_ALLOWED);

                System.out.println("isNotificationEnabled:"+enabled);
                return enabled;
            } catch (Exception e) {
                System.out.println("isNotificationEnabled:"+e.getMessage());
                e.printStackTrace();
                return false;
            }
        }else{
            return false;
        }*/
    }
}
