package iovi.pf3;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Iovi on 06.09.2015.
 */
public class PlayerGuessFragment extends Fragment {
    /*@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }*/
    GuessListener mCallback;

    public interface GuessListener {
        void guessMade(String guess);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (GuessListener) activity;
        } catch (ClassCastException e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.player_guess, null);
        Button button=(Button)view.findViewById(R.id.guess_button);
        final EditText edit=(EditText)view.findViewById(R.id.guess_edit);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mCallback.guessMade(edit.getText().toString());
            }
        });
        return view;
    }



    /*@Override
    public void onResume(){
        super.onResume();
        getActivity().setTitle(getArguments().getString("Title"));
    }*/
}
