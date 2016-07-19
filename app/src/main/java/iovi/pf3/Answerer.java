package iovi.pf3;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Aspenson on 10.01.2016.
 */
public class Answerer
{
    private String riddleWord;
    private ArrayList<String> dictionary;
    private int wordLength;

    public Answerer(ArrayList<String> dictionary, int wordLength)
    {
        riddleWord="";
        this.dictionary = dictionary;
        this.wordLength=wordLength;
    }
    public boolean ChooseWord()
    {
        Random random=new Random();
        int r=random.nextInt(this.dictionary.size()), loop=0;

        for(int i = r;;i++)
        {
            if (i==r) loop++;
            if (loop>1) return false;
            if (i==dictionary.size()) i=0;
            if (dictionary.get(i).length()==this.wordLength)
            {
                riddleWord=dictionary.get(i);
                return true;
            }
        }
    }
    public void setRiddleWord(String word){
        this.riddleWord=word;
    }
    public Answer GetAnswer(String guess){
        if (riddleWord.length()!=guess.length()
                || riddleWord.length()==0)
            return new Answer(-1,-1);
        if (dictionary.indexOf(guess)==-1)
            return new Answer(-1,0);
        int foundP=0, foundF=0;
        for (int i=0;i<guess.length();i++){
            for (int j=0;j<riddleWord.length();j++){
                if (guess.charAt(i)==riddleWord.charAt(j))
                {
                    if (i==j) foundP++;
                    else foundF++;
                    break;
                }
            }
        }
        return new Answer(foundP,foundF);
    }
    public String getRiddleWord(){
        return this.riddleWord;
    }
}
