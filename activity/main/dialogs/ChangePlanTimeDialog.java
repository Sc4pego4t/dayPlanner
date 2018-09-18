package ru.scapegoats.dayplanner.activity.main.dialogs;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import ru.scapegoats.dayplanner.activity.main.MainActivity;

public class ChangePlanTimeDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Calendar myCalender = Calendar.getInstance();
        int hour = myCalender.get(Calendar.HOUR_OF_DAY);
        int minute = myCalender.get(Calendar.MINUTE);


        MainActivity activity=(MainActivity) getActivity();
        return new TimePickerDialog(activity,  (TimePickerDialog.OnTimeSetListener) activity.getView().adapter, hour, minute, true);


    }
}
