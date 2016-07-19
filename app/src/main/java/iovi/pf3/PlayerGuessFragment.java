package iovi.pf3;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Iovi on 06.09.2015.
 */
public class PlayerGuessFragment extends Fragment {
    /*@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.playerguess, null);
    }

    /*@Override
    public void onResume(){
        super.onResume();
        getActivity().setTitle(getArguments().getString("Title"));
    }*/
}
