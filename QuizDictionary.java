package com.speedquiz.classic;

import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class QuizDictionary extends Activity implements android.content.DialogInterface.OnClickListener {

	private ListView lvWordList;
	private EditText etNewWordInput;
	private Button bAddWord;
	private ArrayAdapter<String> adapter;
	private AlertDialog editWordDialog;
	private EditText etEditWordInput;
	Dictionary dict;
	List<String> LIST;
	
	int selectedPos;
	String selectedWord;
	ArrayList<String> temp;
	ArrayList<String> defaultList;
	ArrayList<String> customList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz_dictionary);
		if(Build.VERSION.SDK_INT >= 11)
			getActionBar().hide();

		dict = (Dictionary) this.getApplicationContext();
		lvWordList = (ListView) findViewById(R.id.lvDict);
		etNewWordInput = (EditText) findViewById(R.id.etNewWordInput);
		bAddWord = (Button) findViewById(R.id.bAddWord);
		
		lvWordList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				selectedPos = position;
				selectedWord = LIST.get(position);
						
				etEditWordInput = new EditText(QuizDictionary.this);
				etEditWordInput.setText(selectedWord);
				etEditWordInput.setHint("단어입력");
				
				editWordDialog = new AlertDialog.Builder(QuizDictionary.this)
						.setTitle("편집")
						.setView(etEditWordInput)
						.setPositiveButton("저장", QuizDictionary.this)
						.setNeutralButton("삭제", QuizDictionary.this)
						.setNegativeButton("취소", QuizDictionary.this)
						.create();
				editWordDialog.show();
			}
		});
		
		bAddWord.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String word = etNewWordInput.getText().toString().trim();
				if (!word.equals("")) {
					dict.loadCustom();
					temp = dict.getCustomList();
					temp.add(word);
					dict.saveToCustomList(temp);
					refresh();
					int pos = LIST.indexOf(word);
					lvWordList.smoothScrollToPosition(pos); //set list to view the new added word
					//make list rect at pos glow a light blue
				}
			}
		});
	}
	
	@Override
	protected void onResume() {
		refresh();
		super.onResume();
	}
	
	public void refresh() {
		dict.loadDefault();
		dict.loadCustom();
		LIST = dict.getEntireList();
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, LIST);
		lvWordList.setAdapter(adapter);
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		if (which == AlertDialog.BUTTON_POSITIVE) { //저장(edited): deletes the word at pos, add the new word to custom list & save
			String editedWord = etEditWordInput.getText().toString().trim();
			if(!selectedWord.equals(editedWord)) { //if there are changes made to the word
				if (selectedPos < dict.getDefaultList().size()) { //in default list
					dict.loadDefault();
					temp = dict.getDefaultList();
					temp.remove(selectedWord);
					temp.add(editedWord);
					dict.saveToDefaultList(temp);
				} else { //in custom list
					dict.loadCustom();
					temp = dict.getCustomList();
					temp.remove(selectedWord);
					temp.add(editedWord);
					dict.saveToCustomList(temp);
				}
			}
		}
		else if (which == AlertDialog.BUTTON_NEUTRAL) { //삭제 deletes word
			if (selectedPos < dict.getDefaultList().size()) { //in default list
				dict.loadDefault();
				temp = dict.getDefaultList();
				temp.remove(selectedWord);
				dict.saveToDefaultList(temp);
			} else { //in custom list
				dict.loadCustom();
				temp = dict.getCustomList();
				temp.remove(selectedWord);
				dict.saveToCustomList(temp);
			}
		}
		refresh();
	}
}
