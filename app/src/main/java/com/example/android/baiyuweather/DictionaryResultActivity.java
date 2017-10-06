package com.example.android.baiyuweather;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.android.baiyuweather.utilities.dictionaryUtils.DictionaryWordDataHolder;
import com.example.android.baiyuweather.utilities.NetworkUtils;
import com.example.android.baiyuweather.utilities.dictionaryUtils.OxfordDictJsonUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class DictionaryResultActivity extends AppCompatActivity {
    Context mContext;
    String mWordData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary_result);

        mContext = this;
        Intent intent = getIntent();
        if(intent.hasExtra("query")) {
            new CallbackTask().execute(NetworkUtils.dictionaryEntries(intent.getStringExtra("query")));
        }

        //back button
        ActionBar actionBar = this.getSupportActionBar();

        // Set the action bar back button to look like an up button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //in android calling network requests on the main thread forbidden by default
    //create class to do async job
    private class CallbackTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            //return "nnn";
            //TODO: replace with your own app id and app key
            if(params.length == 0) return null;
            final String app_id = getString(R.string.app_id);
            final String app_key = getString(R.string.dictionary_key);
            try {
                URL url = new URL(params[0]);
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Accept","application/json");
                urlConnection.setRequestProperty("app_id",app_id);
                urlConnection.setRequestProperty("app_key",app_key);

                // read the output from the server
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }

                return stringBuilder.toString();

            }
            catch (Exception e) {
                e.printStackTrace();
                return e.toString();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(result == null) return;
            mWordData = result;
            DictionaryWordDataHolder data = null;
            try {
                data = OxfordDictJsonUtils.getWordDataFromJson(mContext, mWordData);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            if(data != null) {
                showWordsDefinition(data);
            }
            else{
                addUnfound();
            }
        }
        private void addUnfound(){
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View unfound = inflater.inflate(R.layout.word_unfound,null);
            setContentView(unfound);
        }

        private void addDefinition(int index, String str, LinearLayout layout, LayoutInflater inflater){
            index++;
            View custom = inflater.inflate(R.layout.list_item_definition,null);
            TextView tv = (TextView) custom.findViewById(R.id.word_text);
            tv.setText(Integer.toString(index)+" "+str);
            layout.addView(custom);
        }

        private void addExample(int index, int subindex, String str, LinearLayout layout, LayoutInflater inflater){
            index++;
            subindex++;
            View example = inflater.inflate(R.layout.list_item_example,null);
            TextView ex = (TextView) example.findViewById(R.id.example_text);
            ex.setText(Integer.toString(index)+"."+Integer.toString(subindex)+" "+str);
            layout.addView(example);
        }

        private void addWord( String str, LinearLayout layout, LayoutInflater inflater){
            View word = inflater.inflate(R.layout.list_item_word,null);
            TextView wordView = (TextView) word.findViewById(R.id.big_word_text);
            wordView.setText(str);
            layout.addView(word);
        }

        private void addLexical(String str, LinearLayout layout, LayoutInflater inflater){
            View lexi = inflater.inflate(R.layout.list_item_lexical,null);
            TextView lexiText = (TextView) lexi.findViewById(R.id.lexical_type);
            lexiText.setText(str);
            layout.addView(lexi);
        }

        private void showWordsDefinition(DictionaryWordDataHolder data){
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ScrollView parent = (ScrollView) inflater.inflate(R.layout.activity_dictionary_result,
                    null);
            LinearLayout linearLayout = new LinearLayout(DictionaryResultActivity.this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            addWord(data.word, linearLayout,inflater);
            for(int i = 0; i < data.Lexicals.length; i++){
                addLexical(data.Lexicals[i].type, linearLayout,inflater);
                for(int j = 0; j < data.Lexicals[i].definitions.length; j++){
                    addDefinition(j,data.Lexicals[i].definitions[j].wordDefinition,linearLayout,inflater);
                    for(int k = 0; k < data.Lexicals[i].definitions[j].examples.length; k++){
                        addExample(j,k,data.Lexicals[i].definitions[j].examples[k],linearLayout,inflater);
                    }
                }
            }



            parent.addView(linearLayout);
            setContentView(parent);
        }
    }



}
