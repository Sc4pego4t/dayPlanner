package ru.scapegoats.dayplanner.activity.main.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_dialog);

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
                TimePicker timePicker = findViewById(R.id.timePicker);
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
