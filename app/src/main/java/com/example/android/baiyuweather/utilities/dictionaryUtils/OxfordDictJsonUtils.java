package com.example.android.baiyuweather.utilities.dictionaryUtils;

import android.content.Context;

import com.example.android.baiyuweather.utilities.dictionaryUtils.DictionaryWordDataHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

/**
 * Created by Baiyubest on 10/4/2017.
 */

public class OxfordDictJsonUtils {

    //fetch data into WeatherDataHolder array
    public static DictionaryWordDataHolder getWordDataFromJson(Context context, String dictionaryJsonStr)
            throws JSONException {


        final String OWM_MESSAGE_CODE = "cod";



        /* String array to hold each day's weather String */


        JSONObject dictJson = new JSONObject(dictionaryJsonStr);

        /* Is there an error? */
        if (dictJson.has(OWM_MESSAGE_CODE)) {
            int errorCode = dictJson.getInt(OWM_MESSAGE_CODE);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    /* Location invalid */
                    return null;
                default:
                    /* Server probably down */
                    return null;
            }
        }
        JSONArray lexicalArray = dictJson.getJSONArray("results").getJSONObject(0).getJSONArray("lexicalEntries");
        DictionaryWordDataHolder parsedWordDatas = new DictionaryWordDataHolder(lexicalArray.length());
        parsedWordDatas.word = dictJson.getJSONArray("results").getJSONObject(0).getString("word");
        //entering lexical
        for(int i = 0; i < lexicalArray.length(); i++){

            String type = lexicalArray.getJSONObject(i).getString("lexicalCategory");
            parsedWordDatas.Lexicals[i].type = type;
            JSONArray senses = lexicalArray.getJSONObject(i).getJSONArray("entries").getJSONObject(0).getJSONArray("senses");
            parsedWordDatas.Lexicals[i].InitDefinitions(senses.length());
            //entering definitions
            for(int j = 0; j < senses.length(); j++){
                if(!senses.getJSONObject(j).has("definitions")){
                    parsedWordDatas.Lexicals[i].definitions[j].wordDefinition = " ";
                    parsedWordDatas.Lexicals[i].definitions[j].InitExample(0);
                    continue;
                }
                String definition = senses.getJSONObject(j).getJSONArray("definitions").get(0).toString();
                parsedWordDatas.Lexicals[i].definitions[j].wordDefinition = definition;
                if(!senses.getJSONObject(j).has("examples")){
                    parsedWordDatas.Lexicals[i].definitions[j].InitExample(0);
                    continue;
                }
                JSONArray examples = senses.getJSONObject(j).getJSONArray("examples");
                parsedWordDatas.Lexicals[i].definitions[j].InitExample(examples.length());
                //entering example
                for(int k = 0; k < examples.length();k++){
                    String example = examples.getJSONObject(k).getString("text");
                    parsedWordDatas.Lexicals[i].definitions[j].examples[k] = example;
                }

            }
        }
        return parsedWordDatas;
    }
}
