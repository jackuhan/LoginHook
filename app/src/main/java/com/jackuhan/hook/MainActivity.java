package com.jackuhan.hook;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.germainz.hook.R;

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


}
