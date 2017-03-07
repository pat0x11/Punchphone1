package cs65.punchphone;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by CarterJacobsen on 3/6/17.
 */

public class EmployerAdapter extends ArrayAdapter<FrontEndEmployer> {
    private Context context;
    public static ArrayList<FrontEndEmployer> employers;

    public EmployerAdapter(Context context, ArrayList<FrontEndEmployer> resource) {
        super(context, R.layout.support_simple_spinner_dropdown_item,
                resource);
        this.context = context;
        this.employers = resource;
    }
    public int getCount(){
        return employers.size();
    }

    public FrontEndEmployer getItem(int position){
        return employers.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = new TextView(context);
        textView.setText(employers.get(position).getName());
        textView.setTextSize(25);
        return textView;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView textView = new TextView(context);
        textView.setTextColor(Color.BLACK);
        textView.setText(employers.get(position).getName());
        textView.setTextSize(25);

        return textView;
    }
}