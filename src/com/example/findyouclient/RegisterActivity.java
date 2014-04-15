package com.example.findyouclient;

import com.example.findyouclient.controller.MethodController;
import com.example.findyouclient.pojo.State;
import com.example.findyouclient.util.EmailUtil;
import com.example.findyouclient.util.ImageUtil;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	
	private EditText accountText, passwordText,rewordText,emailText;
	private Button registerBtn,backBtn;
	private MethodController controller;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.findyou_register);
		accountText=(EditText) findViewById(R.id.register_accountText);
		passwordText=(EditText) findViewById(R.id.register_passwordText);
		rewordText=(EditText) findViewById(R.id.register_rewordText);
		emailText=(EditText) findViewById(R.id.register_emailText);
		registerBtn=(Button) findViewById(R.id.register_registerBtn);
		backBtn=(Button) findViewById(R.id.backBtn);
		controller=new MethodController();
		
		registerBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if("".equals(accountText.getText().toString())){
					Toast.makeText(RegisterActivity.this, "账号不能为空", Toast.LENGTH_SHORT).show();
				}else if("".equals(passwordText.getText().toString())){
					Toast.makeText(RegisterActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
				}else if(!passwordText.getText().toString().equals(rewordText.getText().toString())){
					Toast.makeText(RegisterActivity.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
				}else if("".equals(emailText.getText().toString())){
					Toast.makeText(RegisterActivity.this, "邮箱不能为空", Toast.LENGTH_SHORT).show();
				}else if(!EmailUtil.emailFormat(emailText.getText().toString())){
					Toast.makeText(RegisterActivity.this, "邮箱不合法", Toast.LENGTH_SHORT).show();
				}else{
					State state=controller.register(accountText.getText().toString(), passwordText.getText().toString(), emailText.getText().toString());
					if(state.getStateCode()==0){
						Toast.makeText(RegisterActivity.this, "成功注册！请登录！", Toast.LENGTH_SHORT).show();
						finish();
					}else{
						Toast.makeText(RegisterActivity.this, state.getMessage(), Toast.LENGTH_SHORT).show();
					}
					
					
				}
			}
		});
		
		backBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		
	}

}
