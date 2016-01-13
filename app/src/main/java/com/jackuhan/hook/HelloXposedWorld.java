package com.jackuhan.hook;

/**
 * Created by hanjiahu on 15/9/15.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class HelloXposedWorld implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
        XposedHelpers.findAndHookMethod(
                "android.content.Intent",
                lpparam.classLoader,
                "getIntExtra",
                String.class,
                int.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param)
                            throws Throwable {
                        Intent intent = (Intent) param.thisObject;
                        final String action = intent.getAction();
                        if (action.equals(Intent.ACTION_BATTERY_CHANGED)) {
                            if (BatteryManager.EXTRA_LEVEL.equals(param.args[0] + "")) {
                                param.setResult(1);
                            } else if ("status".equals(param.args[0] + "")) {
                                XposedBridge.log("status");
                                param.setResult(BatteryManager.BATTERY_STATUS_NOT_CHARGING);
                            }
                        }
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param)
                            throws Throwable {
                    }
                }
        );
    }


//    class BatteryBroadcastReceiver extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//
//            if(intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)){
//                int level = intent.getIntExtra("level", 0); // 电量级别
//                switch (intent.getIntExtra("status", BatteryManager.BATTERY_STATUS_UNKNOWN)) {
//                    case BatteryManager.BATTERY_STATUS_CHARGING:
//                        // 充电状态
//                        break;
//                    case BatteryManager.BATTERY_STATUS_DISCHARGING:
//                        // 放电状态
//                        break;
//                    case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
//                        // 未充电
//                        break;
//                    case BatteryManager.BATTERY_STATUS_FULL:
//                        // 充满电
//                        break;
//                    case BatteryManager.BATTERY_STATUS_UNKNOWN:
//                        // 未知道状态
//                        break;
//                }
//            }
//        }
//    }
}