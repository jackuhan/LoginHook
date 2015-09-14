登录劫持案例
============================
代码参考《Android Hook神器：XPosed入门与登陆劫持演示》。
http://www.csdn.net/article/2015-08-14/2825462


安装本地服务XposedInstaller

需要安装XposedInstall.apk本地服务应用，我们能够在其官网的framework栏目中找到，下载并安装。地址为： http://repo.xposed.info/module/de.robv.android.xposed.installer。

安装好后进入XposedInstaller应用程序，会出现需要激活框架的界面，如下图所示。这里我们点击“安装/更新”就能完成框架的激活了。部分设备如果不支持直接写入的话，可以选择“安装方式”，修改为在Recovery模式下自动安装即可。

 
因为安装时会存在需要Root权限，安装后会启动Xposed的app_process，所以安装过程中会存在设备多次重新启动。

TIPS：由于国内的部分ROM对Xposed不兼容，如果安装Xposed不成功的话，强制使用Recovery写入可能会造成设备反复重启而无法正常启动。
下载使用API库

其API库XposedBridgeApi-.jar（version是XposedAPI的版本号，如我们这里是XposedBridgeApi-54.jar）文件，我们能够在Xposed的官方支持xda论坛找到，其地址为： http://forum.xda-developers.com/xposed/xposed-api-changelog-developer-news-t2714067。

下载完毕后我们需要将Xposed Library复制到lib目录（注意是lib目录，不是Android提供的libs目录），然后将这个jar包添加到Build PATH中。

 
如果直接将jar包放置到了libs目录下，很可能会产生错误： 
“IllegalAccessError: Class ref in pre-verified class resolved to unexpected  implementation” 
估计Xposed作者在其框架内部也引用了BridgeApi，这样操作避免重复引用。


当然，我们使用Xposed进行Hook也分为如下几个步骤：

1. 在AndroidManifest.xml文件中配置插件名称与Api版本号

[xml] view plaincopy
<application  
        android:allowBackup="true"  
        android:icon="@drawable/ic_launcher"  
        android:label="@string/app_name"  
        android:theme="@style/AppTheme" >  
  
        <meta-data  
            android:name="xposedmodule"  
            android:value="true" />  
        
<!-- 模块描述 -->
  
        <meta-data  
            android:name="xposeddescription"  
            android:value="一个登陆劫持的样例" />  
        
<!-- 最低版本号 -->
  
        <meta-data  
            android:name="xposedminversion"  
            android:value="30" />  
</application>  
2. 新建一个入口类并继承并实现IXposedHookLoadPackage接口

如下操作，我们新建了一个com.example.loginhook.Main的类，并实现IXposedHookLoadPackage接口中的handleLoadPackage方法，将非com.example.login包名的应用过滤掉，即我们只操作包名为com.example.login的应用。如下所示：

[java] view plaincopy
public class Main implements IXposedHookLoadPackage {  
  
    /** 
     * 包加载时候的回调 
     */  
    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {  
        // 将包名不是 com.example.login 的应用剔除掉  
        if (!lpparam.packageName.equals("com.example.login"))  
            return;  
        XposedBridge.log("Loaded app: " + lpparam.packageName);  
    }  
}  
3. 声明主入口路径

需要在assets文件夹中新建一个xposed_init的文件，并在其中声明主入口类。如这里我们的主入口类为com.example.loginhook.Main。

 
4. 使用findAndHookMethod方法Hook劫持登陆信息

这是最重要的一步，我们之前所分析的都需要到这一步进行操作。如我们之前所分析的登陆程序，我们需要劫持，就是需要Hook其com.example.login.MainActivity中的isCorrectInfo方法。我们使用Xposed提供的findAndHookMethod直接进行MethodHook操作（与Cydia很类似）。在其Hook回调中使用XposedBridge.log方法，将登陆的账号密码信息打印至Xposed的日志中。
