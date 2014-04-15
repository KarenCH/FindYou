package com.example.findyouclient;

import com.example.findyouclient.controller.MethodController;
import com.example.findyouclient.pojo.State;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private Button loginBtn;
	private Button registerBtn;
	private EditText accountText;
	private EditText passwordText;
	private TextView forgetPassword;
	private MethodController controller;
	public static String key;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.findyou_login);
		loginBtn=(Button) findViewById(R.id.login_loginBtn);
		registerBtn=(Button) findViewById(R.id.login_registerBtn);
		accountText=(EditText) findViewById(R.id.login_accountText);
		passwordText=(EditText) findViewById(R.id.login_passwordText);
		forgetPassword=(TextView) findViewById(R.id.login_forgetPassword);
		
		controller=new MethodController();
		
		
		loginBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				State state=controller.login(accountText.getText().toString(), passwordText.getText().toString());
				
				if(state.getStateCode()==0){
					key=state.getMessage();
					Toast.makeText(LoginActivity.this, "µÇÂ¼³É¹¦", Toast.LENGTH_SHORT).show();
					Intent intent=new Intent(LoginActivity.this, HomeActivity.class);
					startActivity(intent);
					finish();
				}else{
					Toast.makeText(LoginActivity.this, state.getMessage(), Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		
		registerBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(LoginActivity.this, RegisterActivity.class);
				startActivity(intent);
			}
		});
		
		forgetPassword.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(LoginActivity.this, ForgetPasswordActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}
}
