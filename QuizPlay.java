package com.speedquiz.classic;

import java.util.ArrayList;
import java.util.Arrays;
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
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.speedquiz.classic.R;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class QuizPlay extends Activity implements OnClickListener, android.content.DialogInterface.OnClickListener {

	Vibrator vib;
	TextView tvTimer;
	TextView tvWord;
	ImageView check;
	ImageView cross;
	TextView tvCorrect;
	TextView tvIncorrect;
	AlertDialog gameOver;
	int wordCount;
	long setTime;
	ArrayList<String> randomizedList = new ArrayList<String>();
	
	private Thread wordsThread;
	private Thread standbyThread;
	private Handler myHandler = new Handler();
	private long startTime = 0;
	private long timeInMills = 0;
	
	int counter;
	int correct = 0;
	Random generator = new Random();
	ArrayList<String> tempList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz_play);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		//hide the ActionBar
		if(Build.VERSION.SDK_INT >= 11)
			getActionBar().hide();
		
		vib = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
		tvTimer = (TextView) findViewById(R.id.tvTimer);
		tvWord = (TextView) findViewById(R.id.tvWord);
		check = (ImageView) findViewById(R.id.ivCheck);
		cross = (ImageView) findViewById(R.id.ivCross);
		tvCorrect = (TextView) findViewById(R.id.tvCorrect);
		tvIncorrect = (TextView) findViewById(R.id.tvIncorrect);
		
		Intent i = getIntent();
		wordCount = i.getIntExtra("wordcount", 0);
		setTime = i.getIntExtra("seconds", 5) * 1000; //from seconds to milliseconds
		
		check.setOnClickListener(this);
		cross.setOnClickListener(this);
		
		// Look up the AdView as a resource and load a request.
		AdView adView = (AdView) findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		adView.loadAd(adRequest);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		//the long wait: run these as two different threads
		wordsThread = new Thread() {
			public void run() {
				try {
					randomizedList = getWords(wordCount); // the awfully long process
				} catch (Exception e) {
					Toast.makeText(QuizPlay.this, "Error generating words", Toast.LENGTH_SHORT).show();
				}
			}
		};
		standbyThread = new Thread() {
			public void run() {
				try {
					// display Ready... animation (while first thread is still running
					while (wordsThread.isAlive()) {
						tvWord.setText("Ready...");
					}
					// once first thread is finished, start countdown
					tvWord.setText("3");
					Thread.sleep(1000);
					tvWord.setText("2");
					Thread.sleep(1000);
					tvWord.setText("1");
					Thread.sleep(1000);
					
				} catch (Exception e) {
					Toast.makeText(QuizPlay.this, "Error on ready screen", Toast.LENGTH_SHORT).show();
				}
			}
		};
		wordsThread.start();
		standbyThread.start();
		counter = 0; //index that marks which word's on
		while (standbyThread.isAlive()) { } // hold on till this thread finishes
		
		tvWord.setText(randomizedList.get(counter));
		tvCorrect.setText("0");
		tvIncorrect.setText("0");
		
		startTime = SystemClock.uptimeMillis();
		myHandler.postDelayed(updateTimer, 0);
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
			if(counter < randomizedList.size() - 1) {
				counter++;
				correct++;
				tvWord.setText(randomizedList.get(counter));
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
			if(counter < randomizedList.size() - 1) {
				counter++;
				tvWord.setText(randomizedList.get(counter));
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
	public ArrayList<String> getWords(int n)
	{
		tempList = Dictionary.FULLLIST;
		ArrayList<String> result = new ArrayList<String>();
		int size = Dictionary.FULLLIST.size();
		for (int i = 0; i < n; i++)
		{
			int num = generator.nextInt(size);
			result.add(tempList.get(num));
			size--;
			tempList.remove(num);
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
			
			if(timeInMills <= 0) {
				myHandler.removeCallbacks(this); //finish
				gameOver();
			} else {
				myHandler.postDelayed(this, 0); //loop
			}
		}
	};
}
