package iovi.pf3;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Iovi on 06.09.2015.
 */
public class AnswersFragment extends ListFragment {

    ArrayAdapter<String> adapter;
    List<String> data=new ArrayList<String>();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new ArrayAdapter<>(getActivity(),R.layout.list_item, data);
        setListAdapter(adapter);
    }
    public void AddAnswer(String answer){
        data.add(answer);
        adapter.notifyDataSetChanged();
    }
    public void Clear(){
        adapter.clear();
    }
    public void Toast(){
        Toast.makeText(getActivity(), "sad", Toast.LENGTH_SHORT).show();
    }


}
