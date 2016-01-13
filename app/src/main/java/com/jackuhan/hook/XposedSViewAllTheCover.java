package com.jackuhan.hook;

/**
 * Created by hanjiahu on 15/9/15.
 */

import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.findClass;
import static de.robv.android.xposed.XposedHelpers.setBooleanField;;

public class XposedSViewAllTheCover implements IXposedHookZygoteInit {

    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {
        Class<?> CoverManagerService = findClass("com.android.server.cover.CoverManagerService", null);

		/* Hook constructor and set value there */
        XposedBridge.hookAllConstructors(CoverManagerService, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                setBooleanField(param.thisObject, "mIsCoverVerified", true);
                setBooleanField(param.thisObject, "sIsDeviceSupportVerityCoverQueried", true);
            }
        });

        findAndHookMethod(CoverManagerService, "isCoverVerfied", XC_MethodReplacement.returnConstant(true));
        findAndHookMethod(CoverManagerService, "isDeviceSupportCoverVerify", XC_MethodReplacement.returnConstant(true));
        findAndHookMethod(CoverManagerService, "updateCoverVerificationLocked", boolean.class, new XC_MethodReplacement() {
            @Override
            protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                setBooleanField(param.thisObject, "mIsCoverVerified", true);
                return null;
            }
        });

        findAndHookMethod(CoverManagerService, "getDefaultTypeOfCover", XC_MethodReplacement.returnConstant(1));

        findAndHookMethod(CoverManagerService, "getCoverState", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Object result = param.getResult();
                XposedHelpers.setIntField(result, "type", 1);
                param.setResult(result);
            }
        });
    }

}
