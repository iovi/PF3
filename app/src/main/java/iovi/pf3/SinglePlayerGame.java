package iovi.pf3;

import java.util.ArrayList;


public class SinglePlayerGame {
    private ArrayList<String> dict;
    int wordLength;
    Answerer answerAI;
    Questioner questionAI;

    public SinglePlayerGame(ArrayList<String> dictionary, int wordLength){
        this.dict=dictionary;
        this.wordLength=wordLength;
        this.answerAI=new Answerer(this.dict,this.wordLength);
        this.questionAI=new Questioner(this.dict,this.wordLength);
    }
    public static String PrettyAnswer(Answer answer)
    {
        return answer.p+"p "+answer.f+"f";
    }
    public boolean play() throws Exception {

        String guess, AIguess, playerAnswerText;
        boolean needOneMoreQuest=true;
        this.questionAI.StartNewGame();
        this.answerAI.ChooseWord();
        do {
            guess = IOPiF.input("Your guess: ");
            Answer a = this.answerAI.GetAnswer(guess);
            if (a.p==-1)
                IOPiF.output("illegal guess!\n");
            IOPiF.output(PrettyAnswer(answerAI.GetAnswer(guess)));
            if (a.equalTo(new Answer(4, 0))) {
                return true;
            }

            for (Condition c : questionAI.conditions){
                System.out.print(c.word + " - "+c.answer.p+"p "+c.answer.f+"f\n");
            }
            needOneMoreQuest=true;
            while (needOneMoreQuest){
                AIguess = questionAI.Quest();
                IOPiF.output(AIguess);
                playerAnswerText = IOPiF.input("Your answer (pf): ");
                if (playerAnswerText.compareTo("no")!=0){
                    needOneMoreQuest=false;
                    Answer playerAnswer = new Answer(Integer.parseInt(playerAnswerText.substring(0, 1)),
                        Integer.parseInt(playerAnswerText.substring(1, 2)));
                    if (playerAnswer.equalTo(new Answer(4,0)))
                        return false;

                    questionAI.AddCondition(new Condition(AIguess, playerAnswer));

                }
                else {
                    questionAI.RemoveWord(AIguess);
                }
            }
        } while (true);
    }
}
