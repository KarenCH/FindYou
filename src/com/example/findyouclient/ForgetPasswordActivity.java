package com.example.findyouclient;

import com.example.findyouclient.controller.MethodController;
import com.example.findyouclient.pojo.State;
import com.example.findyouclient.util.EmailUtil;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgetPasswordActivity extends Activity implements OnClickListener{

	private EditText accountText,emailText;
	private Button backBtn,forgetBtn;
	private MethodController controller;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.findyou_forgetpassword);
		accountText=(EditText) findViewById(R.id.forget_account);
		emailText=(EditText) findViewById(R.id.forget_email);
		backBtn=(Button) findViewById(R.id.forget_backBtn);
		forgetBtn=(Button) findViewById(R.id.forget_btn);
		controller=new MethodController();
		backBtn.setOnClickListener(this);
		forgetBtn.setOnClickListener(this);
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.forget_backBtn:
			finish();
			break;
			
		case R.id.forget_btn:
			boolean flage=EmailUtil.emailFormat(emailText.getText().toString());
			if(flage){
				State state=controller.forgetPassword(accountText.getText().toString(), emailText.getText().toString());
				Toast.makeText(ForgetPasswordActivity.this, state.getMessage(), Toast.LENGTH_SHORT).show();
				finish();
			}else{
				Toast.makeText(ForgetPasswordActivity.this, "” œ‰≤ª∫œ∑®", Toast.LENGTH_SHORT).show();
			}
		default:
			break;
		}
		
	}
}
