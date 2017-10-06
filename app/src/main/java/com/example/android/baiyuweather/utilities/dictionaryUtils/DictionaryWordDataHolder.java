package com.example.android.baiyuweather.utilities.dictionaryUtils;

/**
 * Created by Baiyubest on 10/4/2017.
 */

public class DictionaryWordDataHolder{
    public class Definition{
        public String wordDefinition = new String();
        public String[] examples;
        public void InitExample(int lenght){
            examples = new String[lenght];
            for(int i = 0; i < lenght; i++){
                examples[i] = new String();
            }
        }
    }
    public class LexicalProperty{
        public String type = new String();
        public Definition[] definitions;
        public void InitDefinitions(int length){
            definitions = new Definition[length];
            for(int i = 0; i < length; i++){
                definitions[i] = new Definition();
            }
        }
    }
    public String word = new String();
    public LexicalProperty[] Lexicals;
    DictionaryWordDataHolder(int length){
        Lexicals = new LexicalProperty[length];
        for(int i = 0; i < length; i++){
            Lexicals[i] = new LexicalProperty();
        }
    }

}
