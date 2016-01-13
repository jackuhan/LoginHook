package com.jackuhan.hook;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.germainz.hook.R;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by hanjiahu on 15/9/14.
 */
public class MainActivity extends Activity {

    private Button button;
    private android.widget.EditText mUserEditText;
    private android.widget.EditText mPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        this.mPasswordEditText = (EditText) findViewById(R.id.editText2);
        this.mUserEditText = (EditText) findViewById(R.id.editText);
        this.button = (Button) findViewById(R.id.button);

        // 登陆按钮的onClick事件
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 获取用户名
                String username = mUserEditText.getText() + "";
                // 获取密码
                String password = mPasswordEditText.getText() + "";

                Toast.makeText(MainActivity.this, loginCorrectInfo(username, password), Toast.LENGTH_SHORT).show();

            }
        });

    }


    private String loginCorrectInfo(String username, String password) {
        if (username.equals("test") && password.equals("123456")) {
            return "登陆成功！";
        } else {
            return "登陆失败！";
        }
    }



//    /**
//     * xposed install
//     * @return 安装成功返回true，否则false
//     */
//    private boolean install() {
//        // 获取安装的方式，直接写入 or 使用 recovery进行安装
//        final int installMode = getInstallMode();
//
//        // 检查获取Root权限
//        if (!startShell())
//            return false;
//
//        List<String> messages = new LinkedList<String>();
//        boolean showAlert = true;
//        try {
//            messages.add(getString(R.string.sdcard_location, XposedApp.getInstance().getExternalFilesDir(null)));
//            messages.add("");
//
//            messages.add(getString(R.string.file_copying, "Xposed-Disabler-Recovery.zip"));
//
//            // 把Xposed-Disabler-Recovery.zip文件 从asset copy到sdcard中
//            if (AssetUtil.writeAssetToSdcardFile("Xposed-Disabler-Recovery.zip", 00644) == null) {
//                messages.add("");
//                messages.add(getString(R.string.file_extract_failed, "Xposed-Disabler-Recovery.zip"));
//                return false;
//            }
//
//            // 将编译后的app_process二进制文件，从asset文件夹中，copy到/data/data/de.robv.android.xposed.installer/bin/app_process下
//            File appProcessFile = AssetUtil.writeAssetToFile(APP_PROCESS_NAME, new File(XposedApp.BASE_DIR + "bin/app_process"), 00700);
//            if (appProcessFile == null) {
//                showAlert(getString(R.string.file_extract_failed, "app_process"));
//                return false;
//            }
//
//            if (installMode == INSTALL_MODE_NORMAL) {
//                // 普通安装模式
//                // 重新挂载/system为rw模式
//                messages.add(getString(R.string.file_mounting_writable, "/system"));
//                if (mRootUtil.executeWithBusybox("mount -o remount,rw /system", messages) != 0) {
//                    messages.add(getString(R.string.file_mount_writable_failed, "/system"));
//                    messages.add(getString(R.string.file_trying_to_continue));
//                }
//
//                // 查看原有的app_process文件是否已经备份，如果没有备份，现将原有的app_process文件备份一下
//                if (new File("/system/bin/app_process.orig").exists()) {
//                    messages.add(getString(R.string.file_backup_already_exists, "/system/bin/app_process.orig"));
//                } else {
//                    if (mRootUtil.executeWithBusybox("cp -a /system/bin/app_process /system/bin/app_process.orig", messages) != 0) {
//                        messages.add("");
//                        messages.add(getString(R.string.file_backup_failed, "/system/bin/app_process"));
//                        return false;
//                    } else {
//                        messages.add(getString(R.string.file_backup_successful, "/system/bin/app_process.orig"));
//                    }
//
//                    mRootUtil.executeWithBusybox("sync", messages);
//                }
//
//                // 将项目中的自定义app_process文件copy覆盖系统的app_process,修改权限
//                messages.add(getString(R.string.file_copying, "app_process"));
//                if (mRootUtil.executeWithBusybox("cp -a " + appProcessFile.getAbsolutePath() + " /system/bin/app_process", messages) != 0) {
//                    messages.add("");
//                    messages.add(getString(R.string.file_copy_failed, "app_process", "/system/bin"));
//                    return false;
//                }
//                if (mRootUtil.executeWithBusybox("chmod 755 /system/bin/app_process", messages) != 0) {
//                    messages.add("");
//                    messages.add(getString(R.string.file_set_perms_failed, "/system/bin/app_process"));
//                    return false;
//                }
//                if (mRootUtil.executeWithBusybox("chown root:shell /system/bin/app_process", messages) != 0) {
//                    messages.add("");
//                    messages.add(getString(R.string.file_set_owner_failed, "/system/bin/app_process"));
//                    return false;
//                }
//
//            } else if (installMode == INSTALL_MODE_RECOVERY_AUTO) {
//                // 自动进入Recovery
//                if (!prepareAutoFlash(messages, "Xposed-Installer-Recovery.zip"))
//                    return false;
//
//            } else if (installMode == INSTALL_MODE_RECOVERY_MANUAL) {
//                // 手动进入Recovery
//                if (!prepareManualFlash(messages, "Xposed-Installer-Recovery.zip"))
//                    return false;
//            }
//
//            File blocker = new File(XposedApp.BASE_DIR + "conf/disabled");
//            if (blocker.exists()) {
//                messages.add(getString(R.string.file_removing, blocker.getAbsolutePath()));
//                if (mRootUtil.executeWithBusybox("rm " + blocker.getAbsolutePath(), messages) != 0) {
//                    messages.add("");
//                    messages.add(getString(R.string.file_remove_failed, blocker.getAbsolutePath()));
//                    return false;
//                }
//            }
//
//            // copy XposedBridge.jar 到私有目录 XposedBridge.jar是Xposed提供的jar文件，负责在Native层与FrameWork层进行交互
//            messages.add(getString(R.string.file_copying, "XposedBridge.jar"));
//            File jarFile = AssetUtil.writeAssetToFile("XposedBridge.jar", new File(JAR_PATH_NEWVERSION), 00644);
//            if (jarFile == null) {
//                messages.add("");
//                messages.add(getString(R.string.file_extract_failed, "XposedBridge.jar"));
//                return false;
//            }
//
//            mRootUtil.executeWithBusybox("sync", messages);
//
//            showAlert = false;
//            messages.add("");
//
//            if (installMode == INSTALL_MODE_NORMAL) {
//                offerReboot(messages);
//            } else {
//                offerRebootToRecovery(messages, "Xposed-Installer-Recovery.zip", installMode);
//            }
//            return true;
//
//        } finally {
//            // 删除busybox 工具库
//            AssetUtil.removeBusybox();
//
//            if (showAlert)
//                showAlert(TextUtils.join("\n", messages).trim());
//        }
//    }


}
