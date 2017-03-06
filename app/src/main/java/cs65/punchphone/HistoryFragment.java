package cs65.punchphone;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import cs65.punchphone.data.PunchEntry;
import cs65.punchphone.data.PunchEntryDbHelper;

public class HistoryFragment extends Fragment {

    List <PunchEntry> values;
    private PunchEntryDbHelper punchEntryDbHelper;
    private ListView history;
    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_history, container, false);
        punchEntryDbHelper = new PunchEntryDbHelper(getActivity());
        values = punchEntryDbHelper.fetchEntries();
        history = (ListView) view.findViewById(R.id.list_entries);
        history.setAdapter(new PunchListAdapter(getActivity().getApplicationContext(), values));

        return view;
    }

}
