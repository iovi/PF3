package iovi.pf3;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Aspenson on 18.01.2016.
 */
public class Questioner {
    private ArrayList<String> dictionary;
    private int wordLength;
    public ArrayList<Condition> conditions;
    public Questioner(ArrayList<String> dictionary, int length){
        this.dictionary=dictionary;
        this.wordLength=length;
        this.conditions=new ArrayList<>();
    }
    
    public void StartNewGame(){
        this.conditions.clear();
    }
        
    public boolean AddCondition(Condition condition){
        return conditions.add(condition);
    }
    public boolean RemoveWord(String word){
        return dictionary.remove(word);
    }
    public String Quest(){
        Random random=new Random();
        int r=random.nextInt(this.dictionary.size());
        int loop=0;

        for(int i = r;;i++)
        {
            if (i==r) loop++;
            if (loop>1) return "";
            if (i==dictionary.size()) i=0;
            String currentWord=dictionary.get(i);
            //have no same letters

            if (currentWord.length()==this.wordLength && this.ReleaseConditions(currentWord))
            {

                return currentWord;
            }
        }
    }
    public boolean ReleaseConditions(String suggest){
        Answerer answerer=new Answerer(this.dictionary,this.wordLength);
        answerer.setRiddleWord(suggest);
        for (int i=0;i<conditions.size();i++){
            Condition c=conditions.get(i);
            if (!c.answer.equalTo(answerer.GetAnswer(c.word))) return false;
        }
        return  true;
    }
    public boolean HasSameLetters(String s){
        for(int j=0;j<s.length();j++)
            if (s.indexOf(s.charAt(j),j+1)!=-1)
                return true;
        return false;
    }


}
