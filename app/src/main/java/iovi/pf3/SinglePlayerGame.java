package iovi.pf3;

import java.util.ArrayList;


public class SinglePlayerGame {
    private ArrayList<String> dict;
    int wordLength;
    Answerer answerAI;
    Questioner questionAI;
    private int guessesAI;
    private int guessesPlayer;
    private boolean playerWordFound;
    private boolean AIWordFound;

    final static String TAG="PF3";

    public SinglePlayerGame(ArrayList<String> dictionary){
        this.dict=dictionary;
    }
    public static String PrettyAnswer(Answer answer)
    {
        return answer.p+"p "+answer.f+"f";
    }

    public void NewGame(int wordlength){
        this.wordLength=wordlength;
        guessesAI=0;
        guessesPlayer=0;
        playerWordFound=false;
        AIWordFound=false;
        this.answerAI=new Answerer(this.dict,this.wordLength);
        this.answerAI.ChooseWord();
        this.questionAI=new Questioner(this.dict,this.wordLength);
        this.questionAI.StartNewGame();
    }
    public String GetAIGuess(){
        return questionAI.Quest();
    }
    public void AnswerToAI(String word, Answer answer){
        if (answer.equalTo(new Answer(wordLength,0))) {
            playerWordFound = true;
        }
        else
            questionAI.AddCondition(new Condition(word,answer));
        guessesAI++;
    }
    public void MakeAIForget(String word){
        questionAI.RemoveWord(word);
    }

    public Answer AnswerPlayerGuess(String word){
        Answer answer=answerAI.GetAnswer(word);
        guessesPlayer++;
        if (answer.equalTo(new Answer(wordLength,0))) {
            AIWordFound = true;
        }
        return answer;
    }

    public enum GuessError{NoError, NoSuchWord,WrongWordLength}

    public GuessError CheckPlayerGuess(String word){
        if (word.length()!=wordLength)
            return GuessError.WrongWordLength;
        if (dict.indexOf(word)==-1)
            return GuessError.NoSuchWord;
        return GuessError.NoError;
    }

    public enum GameStatus{PlayerIsWinner,AIIsWinner,Draw,GameIsGoingOn}

    public GameStatus GetGameStatus(){
        if (AIWordFound==false && playerWordFound==false) return GameStatus.GameIsGoingOn;
        else {  //some word is found
            if (AIWordFound==false){
                if (guessesAI>guessesPlayer)
                    return GameStatus.GameIsGoingOn;
                else
                    return GameStatus.AIIsWinner;
            }
            if (playerWordFound==false) {
                if (guessesPlayer>guessesAI)
                    return GameStatus.GameIsGoingOn;
                else
                    return GameStatus.PlayerIsWinner;
            }
        }
        return GameStatus.Draw;


    }

}
