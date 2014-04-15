package com.example.findyouclient;

import java.util.Calendar;

import com.example.findyouclient.controller.MethodController;
import com.example.findyouclient.pojo.UserInfo;
import com.example.findyouclient.util.ImageUtil;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class UserInfoActivity extends Activity {

	private static final int RESULT_ICON_IMAGE=0x1222;
	
	private ImageButton userIconBtn;
	private EditText nameText,descriptionText,addressText,professionText;
	private TextView dateText;
	private RadioGroup group;
	private RadioButton manBtn,womanBtn;
	private Button submit,cancel;
	private MethodController controller;
	private UserInfo info=new UserInfo();
	private Bitmap combitmap;
	private DatePickerDialog datePickerDialog;
	
	
	private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			dateText.setText(year + "-" + (monthOfYear + 1) + "-"
					+ dayOfMonth);
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.findyou_userinfo);
		userIconBtn =(ImageButton) findViewById(R.id.userinfo_usericon);
		nameText=(EditText) findViewById(R.id.userinfo_username);
		descriptionText=(EditText) findViewById(R.id.userinfo_description);
		addressText=(EditText) findViewById(R.id.userinfo_address);
		professionText=(EditText) findViewById(R.id.userinfo_profession);
		dateText=(TextView) findViewById(R.id.userinfo_date);
		group=(RadioGroup) findViewById(R.id.userinfo_radiogroup);
		manBtn=(RadioButton) findViewById(R.id.userinfo_man);
		womanBtn=(RadioButton) findViewById(R.id.userinfo_woman);
		submit=(Button) findViewById(R.id.userinfo_submit);
		cancel=(Button) findViewById(R.id.userinfo_cancel);
		
		info=controller.getUserInfo(LoginActivity.key);
		userIconBtn.setImageBitmap(ImageUtil.strToBitmap(info.getIcon()));
		
		nameText.setText(info.getName());
		descriptionText.setText(info.getIntroduction());
		addressText.setText(info.getAddress());
		professionText.setText(info.getProfession());
		dateText.setText(info.getBirthday());
		
		
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		datePickerDialog = new DatePickerDialog(
				UserInfoActivity.this, listener, year, month, day);
		
		
		if(info.isSex()){
			manBtn.setChecked(true);
		}else{
			womanBtn.setChecked(true);
		}
		
		
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				if(arg1==manBtn.getId()){
					info.setSex(true);
				}else{
					info.setSex(false);
				}
			}
		});
		
		userIconBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent imageIntent=new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(imageIntent, RESULT_ICON_IMAGE);
			}
		});
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				info.setIcon(ImageUtil.bitmapToStr(combitmap));
				info.setName(nameText.getText().toString());
				info.setIntroduction(descriptionText.getText().toString());
				info.setAddress(addressText.getText().toString());
				info.setProfession(professionText.getText().toString());
				info.setBirthday(dateText.getText().toString());

				controller.setUserInfo(LoginActivity.key, info); 
			}
		});
		
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent backInt=new Intent(UserInfoActivity.this,HomeActivity.class);
				startActivity(backInt);
			}
		});
		
		dateText.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				datePickerDialog.show();
			}
		});
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == RESULT_ICON_IMAGE && resultCode == RESULT_OK && null != data) {
	        Uri selectedImage = data.getData();
	        String[] filePathColumn = { MediaStore.Images.Media.DATA };

	        Cursor cursor = getContentResolver().query(selectedImage,
	                filePathColumn, null, null, null);
	        cursor.moveToFirst();

	        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	        String picturePath = cursor.getString(columnIndex);
	        String pictureType=picturePath.split("\\.")[picturePath.split("\\.").length-1];
	        cursor.close();
			Bitmap newbitmap=BitmapFactory.decodeFile(picturePath);
			
			Bitmap turnbitmap=  ImageUtil.toRoundBitmap(ImageUtil.extractMiniThumb(newbitmap, userIconBtn.getWidth(), userIconBtn.getHeight()));
			
			combitmap=ImageUtil.compressImage(newbitmap,pictureType);
			userIconBtn.setImageBitmap(turnbitmap);
					
			
		}
	}
}
