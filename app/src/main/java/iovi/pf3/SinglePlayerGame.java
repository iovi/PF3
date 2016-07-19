package iovi.pf3;

import java.util.ArrayList;


public class SinglePlayerGame {
    private ArrayList<String> dict;
    int wordLength;
    Answerer answerAI;
    Questioner questionAI;
    int guessesAI;
    int guessesPlayer;

    public SinglePlayerGame(ArrayList<String> dictionary, int wordLength){
        this.dict=dictionary;
        this.wordLength=wordLength;

    }
    public static String PrettyAnswer(Answer answer)
    {
        return answer.p+"p "+answer.f+"f";
    }

    public void NewGame(){
        guessesAI=0;
        guessesPlayer=0;
        this.answerAI=new Answerer(this.dict,this.wordLength);
        this.answerAI.ChooseWord();
        this.questionAI=new Questioner(this.dict,this.wordLength);
        this.questionAI.StartNewGame();
    }
    public String GetAIGuess(){
        return questionAI.Quest();
    }
    public void AnswerToAI(String word, Answer answer){
        questionAI.AddCondition(new Condition(word,answer));
        guessesAI++;
    }
    public void MakeAIForget(String word){
        questionAI.RemoveWord(word);
    }

    public Answer CheckPlayerGuess(String word){
        Answer answer=answerAI.GetAnswer(word);
        guessesPlayer++;
        return answer;
    }
}
