package com.speedquiz.classic;

import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import com.speedquiz.classic.R;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class QuizMenu extends Activity implements OnClickListener, DialogInterface.OnClickListener {

	RelativeLayout layout;
	private Button bPlay;
	private Button bDictionary;
	private Button bQuit;
	private AlertDialog playDialog;
	private NumberPicker picker;
	private String[] npNums = new String[60];
	private EditText etInput;
	Intent i;
	Dictionary dict;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz_menu);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		//hide the ActionBar
		if(Build.VERSION.SDK_INT >= 11)
			getActionBar().hide();
		//array multiples of 5 for number picker
		for (int i = 1; i <= 60; i++) {
			npNums[i-1] = Integer.toString(i * 5);
		}
		dict = (Dictionary) this.getApplicationContext();
		layout = (RelativeLayout) findViewById(R.id.reLayout);
		bPlay = (Button) findViewById(R.id.bPlay);
		bDictionary = (Button) findViewById(R.id.bDictionary);
		bQuit = (Button) findViewById(R.id.bQuit);
		bPlay.setOnClickListener(this);
		bDictionary.setOnClickListener(this);
		bQuit.setOnClickListener(this);
		layout.addView(new QuizMenuText(this));
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bPlay:
			View v1;
			if(Build.VERSION.SDK_INT >= 11) {
				picker = new NumberPicker(this);
				picker.setMaxValue(50);
				picker.setMinValue(3);
				picker.setValue(15);
				v1 = picker;
			} else {
				etInput = new EditText(this);
				etInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
				//check value of input, must be less than 21
				v1 = etInput;
			}
			
			playDialog = new AlertDialog.Builder(this)
			.setTitle("게임 설정")
			.setView(v1)
			.setMessage("단어 몇개?")
			.setPositiveButton("다음", this)
			.setNegativeButton("취소", this)
			.create();
			playDialog.show();
			break;
			
		case R.id.bDictionary:
			Intent id = new Intent(this, QuizDictionary.class);
			startActivity(id);
			break;
			
		case R.id.bQuit:
			finish();
			break;
		
		default:
			break;
		}
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		switch (which) {
		case AlertDialog.BUTTON_POSITIVE:
			i = new Intent(this, QuizPlay.class);
			
			View v2;
			if(Build.VERSION.SDK_INT >= 11) {
				i.putExtra("wordcount", picker.getValue());
				picker = new NumberPicker(this);
				picker.setMaxValue(npNums.length - 1);
				picker.setMinValue(0);
				picker.setDisplayedValues(npNums);
				picker.setValue(10);
				picker.setScrollBarStyle(NumberPicker.SCROLLBARS_OUTSIDE_OVERLAY);
				v2 = picker;
				
				playDialog = new AlertDialog.Builder(this)
				.setTitle("게임 설정")
				.setView(v2)
				.setMessage("몇초?")
				.setNeutralButton( "시작", this)
				.setNegativeButton("취소", this)
				.create();
				playDialog.show();
			} else {
				dict.loadDefault();
				dict.loadCustom();
				int fullSize = dict.getEntireList().size();
				
				//check input type
				String text = etInput.getText().toString();
				boolean textIsInt; int count;
				try {
					Integer.parseInt(text);
					textIsInt = true;
					count = Integer.valueOf(etInput.getText().toString());
				} catch (NumberFormatException e) {
					textIsInt = false;
					count = -1;
				}
				
				if(!textIsInt) {
					Toast.makeText(this, "1 ~ " + fullSize + " 사이 정수를 입력하세요", Toast.LENGTH_SHORT).show();
				}
				else if(count <= fullSize && count > 0) {
					i.putExtra("wordcount", Integer.valueOf(etInput.getText().toString()));
					etInput = new EditText(this);
					etInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
					v2 = etInput;
					
					playDialog = new AlertDialog.Builder(this)
					.setTitle("게임 설정")
					.setView(v2)
					.setMessage("몇초?")
					.setNeutralButton( "시작", this)
					.setNegativeButton("취소", this)
					.create();
					playDialog.show();
				}
				else { Toast.makeText(this, "1 ~ " + fullSize + " 사이 정수를 입력하세요", Toast.LENGTH_SHORT).show(); }
			}
			break;
			
		case AlertDialog.BUTTON_NEUTRAL:
			if(Build.VERSION.SDK_INT >= 11) {
				i.putExtra("seconds", Integer.parseInt(npNums[picker.getValue()]));
				startActivity(i);
			} else {
				//check input type
				String text = etInput.getText().toString();
				boolean textIsInt; int count;
				try {
					Integer.parseInt(text);
					textIsInt = true;
					count = Integer.valueOf(etInput.getText().toString());
				} catch (NumberFormatException e) {
					textIsInt = false;
					count = -1;
				}
				
				if(!textIsInt) {
					Toast.makeText(this, "1 ~ 180 사이의 정수를 입력하세요", Toast.LENGTH_SHORT).show();
				}
				else if(count >= 1 && count <=180) {
					i.putExtra("seconds", Integer.valueOf(etInput.getText().toString()));
					startActivity(i);
				}
				else { Toast.makeText(this, "1 ~ 180 사이의 정수를 입력하세요", Toast.LENGTH_SHORT).show(); }
			}
			break;
		
		case AlertDialog.BUTTON_NEGATIVE:
			playDialog.dismiss();
			break;
			
		default:
			break;
		}
	}
}
