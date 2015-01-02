package com.speedquiz.classic;

import java.util.ArrayList;
import java.util.Random;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Vibrator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.speedquiz.classic.R;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class QuizPlay extends Activity implements OnClickListener, android.content.DialogInterface.OnClickListener {

	Vibrator vib;
	TextView tvTimer;
	float timerTextSize;
	TextView tvWord;
	TextView tvWordCounter;
	ImageView check;
	ImageView cross;
	TextView tvCorrect;
	TextView tvIncorrect;
	AlertDialog gameOver;
	int wordCount;
	long setTime;
	String[] randomizedList;
	
	private Handler myHandler = new Handler();
	private long startTime = 0;
	private long timeInMills = 0;
	
	int counter;
	int correct = 0;
	Random generator = new Random();
	Dictionary dict;
	ArrayList<String> temp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz_play);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		//hide the ActionBar
		if(Build.VERSION.SDK_INT >= 11)
			getActionBar().hide();

		dict = (Dictionary) this.getApplicationContext();
		vib = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
		tvTimer = (TextView) findViewById(R.id.tvTimer);
		timerTextSize = tvTimer.getTextSize();
		tvWord = (TextView) findViewById(R.id.tvWord);
		tvWordCounter = (TextView) findViewById(R.id.tvWordCounter);
		check = (ImageView) findViewById(R.id.ivCheck);
		cross = (ImageView) findViewById(R.id.ivCross);
		tvCorrect = (TextView) findViewById(R.id.tvCorrect);
		tvIncorrect = (TextView) findViewById(R.id.tvIncorrect);
		check.setOnClickListener(this);
		cross.setOnClickListener(this);
		
		Intent i = getIntent();
		wordCount = i.getIntExtra("wordcount", 0);
		randomizedList = new String[wordCount];
		setTime = i.getIntExtra("seconds", 5) * 1000 + 1000; //from seconds to milliseconds
		
		
		// Look up the AdView as a resource and load a request.
		AdView adView = (AdView) findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		adView.loadAd(adRequest);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		randomizedList = getWords(wordCount);
		counter = 0; //index that marks which word's on
		tvWord.setText(randomizedList[counter]);
		tvCorrect.setText("0");
		tvIncorrect.setText("0");
		
		startTime = SystemClock.uptimeMillis();
		myHandler.postDelayed(updateTimer, 0);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		myHandler.removeCallbacks(updateTimer);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		myHandler.removeCallbacks(updateTimer);
	}
	
	public void gameOver() {
		myHandler.removeCallbacks(updateTimer);
		check.setClickable(false);
		cross.setClickable(false);
		gameOver = new AlertDialog.Builder(this)
		.setTitle("게임끝")
		.setMessage("\n" + "맞춘단어: " + correct + "개" + "\n" + "넘긴단어: " + (counter - correct) + "개" + "\n" + "남은단어: " + (wordCount - counter))
		.setPositiveButton("쩝", this)
		.create();
		gameOver.show();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ivCheck:
			 // Vibrate for 100 milliseconds
			 vib.vibrate(100);
			//if more words left
			if(counter < randomizedList.length - 1) {
				counter++;
				correct++;
				tvWord.setText(randomizedList[counter]);
				tvCorrect.setText("" + correct);
			} else {
				counter++;
				correct++;
				tvCorrect.setText("" + correct);
				gameOver();
			}
			break;

		case R.id.ivCross:
			// Vibrate for 500 milliseconds
			 vib.vibrate(500);
			//if more words left
			if(counter < randomizedList.length - 1) {
				counter++;
				tvWord.setText(randomizedList[counter]);
				tvIncorrect.setText("" + (counter - correct));
			} else {
				counter++;
				tvIncorrect.setText("" + (counter - correct));
				gameOver();
			}
			break;
			
		default:
			break;
		}
		
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		finish();
	}

	/**
	 * Gets n random words from Dictionary
	 * @param n number of words
	 * @return List of random words to use for quiz
	 */
	public String[] getWords(int n)
	{
		dict.loadDefault();
		dict.loadCustom();
		temp = dict.getEntireList();
		String[] result = new String[n];
		int size = temp.size();
		int num;
		for (int i = 0; i < n; i++)
		{
			num = generator.nextInt(size);
			result[i] = temp.get(num);
			size--;
			temp.remove(num);
		}
		return result;
	}
	
	private Runnable updateTimer = new Runnable() {
		
		@Override
		public void run() {
			timeInMills = setTime - (SystemClock.uptimeMillis() - startTime);
			
			int seconds = (int) (timeInMills/1000);
			int minutes = seconds/60;
			seconds = seconds % 60;
			int milliseconds = (int)(timeInMills % 1000);
			tvTimer.setText("" + minutes + ":" + String.format("%02d", seconds) + ":" + String.format("%03d", milliseconds));
			
			if(timeInMills <= 10000) {
				tvTimer.setTextColor(Color.RED);
				tvTimer.setTextSize(timerTextSize + 15);
			}
			if(timeInMills <= 0) {
				myHandler.removeCallbacks(this); //finish
				gameOver();
			} else {
				myHandler.postDelayed(this, 0); //loop
			}
			tvWordCounter.setText("단어: " + (counter + 1) + "/" + wordCount);
		}
	};
}
