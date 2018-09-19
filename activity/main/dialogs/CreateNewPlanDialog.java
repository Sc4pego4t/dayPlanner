package ru.scapegoats.dayplanner.activity.main.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.Window;
import android.widget.TimePicker;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import ru.scapegoats.dayplanner.R;
import ru.scapegoats.dayplanner.activity.main.CardContent;
import ru.scapegoats.dayplanner.activity.main.MainView;
import ru.scapegoats.dayplanner.activity.main.adapter.MyRecyclerViewAdapter;
import ru.scapegoats.dayplanner.modules.AbstractActivity;

public class CreateNewPlanDialog extends Dialog {

    private AbstractActivity context;

    public CreateNewPlanDialog(AbstractActivity context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_dialog);

        final TimePicker timePicker = findViewById(R.id.timePicker);

        //if device use 24h format then we change our timepicker
        if(DateFormat.is24HourFormat(context)){
            timePicker.setIs24HourView(true);
        }

        //handle click on DISMISS button
        findViewById(R.id.butDissmis).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        //handle click on ADD button
        findViewById(R.id.butAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextInputEditText editText = findViewById(R.id.textField);
                TextInputLayout textInputLayout = findViewById(R.id.textFieldLayout);

                String text = editText.getText().toString();
                //if textfield is empty
                if (text.length() > 0) {
                    //hide dialog and add new note to array
                    MyRecyclerViewAdapter adapter = (((MainView) context.getView()).adapter);
                    adapter.addItem(timePicker,text);
                    dismiss();
                } else {
                    //show error message
                    String errorText = context.getResources().getText(R.string.inputError).toString();
                    textInputLayout.setError(errorText);
                }
            }
        });
    }



}
