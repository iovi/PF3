package iovi.pf3;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Iovi on 06.09.2015.
 */
public class PlayerAnswerFragment extends Fragment {

    final static String TAG="PF3";

    /*@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.playeranswer, null);
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        TextView textView=(TextView)getActivity().findViewById(R.id.outgoingGuess);
        textView.setText(getArguments().getString("word"));
    }



    /*@Override
    public void onResume(){
        super.onResume();
        getActivity().setTitle(getArguments().getString("Title"));
    }*/
}
