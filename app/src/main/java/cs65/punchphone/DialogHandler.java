package cs65.punchphone;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

/**
 * Created by CarterJacobsen on 2/26/17.
 */

public class DialogHandler extends DialogFragment {

    private static String DAY_KEY = "day_key"; //Key to get the day of the
    // week being adjusted
    private static String ID_KEY = "id_key";

    public static DialogHandler newInstance(int view, String day){
        DialogHandler fragment = new DialogHandler();
        Bundle args = new Bundle();
        args.putString(DAY_KEY, day);
        args.putInt(ID_KEY, view);
        fragment.setArguments(args);
        return fragment;
    }

    public Dialog onCreateDialog(Bundle savedState){
        final String day = getArguments().getString(DAY_KEY);
        final int id = getArguments().getInt(ID_KEY);

        final Activity parent = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(parent);

        builder.setTitle(day);
        final EditText entry = new EditText(parent);
        entry.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(entry);
        builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String current = entry.getText().toString();
                if(current.equals(""))current= "0";
                ((ScheduleActivity) getActivity()).printHours(id, Double
                        .parseDouble(current));
            }
        });
        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface
                .OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }
}
