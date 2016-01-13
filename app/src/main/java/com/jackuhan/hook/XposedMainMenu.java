package com.jackuhan.hook;

/**
 * Created by hanjiahu on 15/9/15.
 */
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class XposedMainMenu implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(final LoadPackageParam packageParam) throws Throwable {

        findAndHookMethod("android.view.ViewConfiguration",
                packageParam.classLoader, "hasPermanentMenuKey",
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param)
                            throws Throwable {
                        param.setResult(Boolean.valueOf(false));
                    }
                });
    }

}

