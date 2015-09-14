package com.jackuhan.hook;

/**
 * Created by hanjiahu on 15/9/14.
 */
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
public class Main implements IXposedHookLoadPackage {

    /**
     * 包加载时候的回调
     */
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        // 将包名不是 com.jackuhan.hook 的应用剔除掉
        if (!lpparam.packageName.equals("com.jackuhan.hook"))
            return;
        XposedBridge.log("Loaded app: " + lpparam.packageName);

        // Hook MainActivity中的loginCorrectInfo(String,String)方法
        findAndHookMethod("com.jackuhan.hook.MainActivity", lpparam.classLoader, "loginCorrectInfo", String.class,
                String.class, new XC_MethodHook() {

                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log("开始劫持了~");
                        XposedBridge.log("参数1 = " + param.args[0]);
                        XposedBridge.log("参数2 = " + param.args[1]);

                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log("劫持结束了~");
                        XposedBridge.log("劫持结束了~参数1 = " + param.args[0]);
                        XposedBridge.log("劫持结束了~参数2 = " + param.args[1]);

                        param.setResult("劫持结果!!!!! ");

                    }
                });
    }


}