package iovi.pf3;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Iovi on 06.09.2015.
 */
public class PlayerAnswerFragment extends Fragment {

    final static String TAG="PF3";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.playeranswer, null);

        //preparing spinners
        List<String> variants=new ArrayList<>();
        for (int i=0;i<getArguments().getInt("wordlength")+1;i++)
            variants.add(Integer.toString(i));

        ArrayAdapter<String> pSpinnerAdapter=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item,variants);
        pSpinnerAdapter.setDropDownViewResource(R.layout.spinner_item);

        Spinner pSpinner= (Spinner)view.findViewById(R.id.p_spinner);
        pSpinner.setAdapter(pSpinnerAdapter);
        Spinner fSpinner= (Spinner)view.findViewById(R.id.f_spinner);
        fSpinner.setAdapter(pSpinnerAdapter);

        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        TextView textView=(TextView)getActivity().findViewById(R.id.outgoingGuess);
        textView.setText(getArguments().getString("word"));
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (AnswerListener) activity;
        } catch (ClassCastException e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /*@Override
    public void onResume(){
        super.onResume();
        getActivity().setTitle(getArguments().getString("Title"));
    }*/
}
