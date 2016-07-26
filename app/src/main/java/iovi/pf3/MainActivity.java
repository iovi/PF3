package iovi.pf3;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity
implements PlayerGuessFragment.GuessListener{

    List<String> answers;
    SinglePlayerGame game;
    int wordlength=4;
    ArrayList<String> dictionary;
    DataBaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper=new DataBaseHelper(this);
        try{
            dbHelper.createDataBase();
        } catch (IOException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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

            dbHelper.close();
            try{
                dbHelper.openDataBase();
            } catch (SQLException e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            answers=new ArrayList<>();
            game=new SinglePlayerGame(dictionary,wordlength);
            FragmentTransaction transaction=getFragmentManager().beginTransaction();
            Fragment topFragment=new PlayerGuessFragment();
            Fragment bottomFragment=new AnswersFragment();
            transaction.add(R.id.topFrame,topFragment);
            transaction.add(R.id.bottomFrame, bottomFragment);
            transaction.commit();
        }
        if (id == R.id.action_settings) {
            //AnswersFragment answersFragment = (AnswersFragment)getFragmentManager().findFragmentById(R.id.bottomFrame);
            //answersFragment.AddAnswer("Vasya");
            try{
                dictionary=dbHelper.getDictionary(4);
            } catch (SQLException e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(this, dictionary.size(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void guessMade(String guess){
        AnswersFragment answersFragment = (AnswersFragment)getFragmentManager().findFragmentById(R.id.bottomFrame);
        answersFragment.AddAnswer(guess);
    }
}
