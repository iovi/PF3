package iovi.pf3;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity
implements PlayerGuessFragment.GuessListener, PlayerAnswerFragment.AnswerListener{

    List<String> answers;
    SinglePlayerGame game;
    int wordlength=4;
    ArrayList<String> dictionary;
    DataBaseHelper dbHelper;
    final static String TAG="PF3";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper=new DataBaseHelper(this);
    }

    @Override
    protected void onStart(){
        try{
            dbHelper.createDataBase();
            dbHelper.openDataBase();
            dictionary=dbHelper.getDictionary(wordlength);
            game=new SinglePlayerGame(dictionary,wordlength);
        } catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        super.onStart();
    }

    @Override
    protected void onStop(){
        super.onStop();
        dbHelper.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_newgame) {
            answers=new ArrayList<>();
            game.NewGame();
            /*FragmentTransaction transaction=getFragmentManager().beginTransaction();
            Fragment topFragment=new PlayerGuessFragment();
            Fragment bottomFragment=new AnswersFragment();
            transaction.add(R.id.topFrame,topFragment);
            transaction.add(R.id.bottomFrame, bottomFragment);
            transaction.commit();*/
            initPlayerGuessFragment();
            initAnswersFragment();
        }
        if (id == R.id.action_settings) {
            //set word length code
            initSettingsDialog();
            game.NewGame();
            initPlayerGuessFragment();
            initAnswersFragment();

        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void guessMade(String guess){
        String check=game.CheckPlayerGuess(guess);
        if (check!=null) {
            AlertDialog.Builder dialog  = new AlertDialog.Builder(this);
            dialog.setMessage(check);
            dialog.setPositiveButton("OK", null);
            dialog.create().show();
        } else {
            AnswersFragment answersFragment = (AnswersFragment) getFragmentManager().findFragmentById(R.id.bottomFrame);
            Answer a=game.AnswerPlayerGuess(guess);
            answersFragment.AddAnswer(guess + " - " + SinglePlayerGame.PrettyAnswer(a));

            initPlayerAnswerFragment();
        }
    }

    @Override
    public void AnswerOK(String guess,Answer answer){
        game.AnswerToAI(guess, answer);
        initPlayerGuessFragment();

    }
    @Override
    public void DeleteGuess(String guess){
        game.MakeAIForget(guess);
        initPlayerAnswerFragment();
    }
    private void initPlayerAnswerFragment(){
        FragmentTransaction transaction=getFragmentManager().beginTransaction();
        Fragment topFragment=new PlayerAnswerFragment();
        Bundle args=new Bundle();
        args.putString("word", game.GetAIGuess());
        args.putInt("wordlength", wordlength);
        topFragment.setArguments(args);
        transaction.replace(R.id.topFrame, topFragment);
        transaction.commit();
    }
    private void initPlayerGuessFragment(){
        FragmentTransaction transaction=getFragmentManager().beginTransaction();
        Fragment topFragment=new PlayerGuessFragment();
        transaction.replace(R.id.topFrame,topFragment);
        transaction.commit();
    }
    private void initAnswersFragment(){
        FragmentTransaction transaction=getFragmentManager().beginTransaction();
        Fragment bottomFragment=new AnswersFragment();
        transaction.replace(R.id.bottomFrame, bottomFragment);
        transaction.commit();
    }

    private void initSettingsDialog() {

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View settingsView = layoutInflater.inflate(R.layout.settings, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(settingsView);

        final EditText editText = (EditText) settingsView.findViewById(R.id.settings_wordlength_edit);

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String wordlengthString=editText.getText().toString();
                wordlength = Integer.parseInt(wordlengthString);
            }
        });
        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
}
