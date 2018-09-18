package ru.scapegoats.dayplanner.activity.main.adapter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;
import ru.scapegoats.dayplanner.R;
import ru.scapegoats.dayplanner.activity.main.CardContent;
import ru.scapegoats.dayplanner.activity.main.MainActivity;
import ru.scapegoats.dayplanner.activity.main.dialogs.ChangePlanTimeDialog;
import ru.scapegoats.dayplanner.activity.main.dialogs.CreateNewPlanDialog;
import ru.scapegoats.dayplanner.activity.main.reciever.TimeNotificationReciever;
import ru.scapegoats.dayplanner.modules.AbstractActivity;
import ru.scapegoats.dayplanner.utils.MySharedPreference;


public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements TimePickerDialog.OnTimeSetListener{

    //data array
    private List<CardContent> list;
    private AbstractActivity activity;


    public MyRecyclerViewAdapter(List<CardContent> list, AbstractActivity activity) {
        this.list = list;
        this.activity=activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);
        return new ViewHolder(v);
    }




    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView  foregroundCard;
        RelativeLayout backgroundCard;
        TextView textView;
        Button button;

        public ViewHolder(@NonNull View view) {
            super(view);
            backgroundCard =view.findViewById(R.id.background);
            foregroundCard =view.findViewById(R.id.foreground);
            textView=view.findViewById(R.id.card_textview);
            button = view.findViewById(R.id.card_button);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerViewAdapter.ViewHolder holder, final int position) {
        CardContent cardContent = list.get(position);

        holder.textView.setText(cardContent.getText());
        holder.button.setText(cardContent.getTime());

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialog=new ChangePlanTimeDialog();
                dialog.show(activity.getSupportFragmentManager(),"time picker");
                selectedCardId=position;
            }
        });

        Log.e("EEEEEr", cardContent.toString());
    }
    //method for changing plan time
    //value for decting from which card ID is
    private int selectedCardId;
    //after changetimeialog
    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        CardContent card=new CardContent(getTime(timePicker),list.get(selectedCardId).getText());
        changeItem(card,selectedCardId);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void removeItem(int position){
        MySharedPreference.deleteElementFromList(position,activity);

        deleteAlarm(position);

        list.remove(position);
        notifyItemRemoved(position);
    }

    public void addItem(TimePicker picker,String text){

        //if this time is not reserved
        if(checkIsTimeNotReserved(getTime(picker))){
            //create alarm with notification and add new plan to list

            CardContent card = new CardContent(getTime(picker), text);
            MySharedPreference.addElementToList(card, activity);
            list = MySharedPreference.getTodoList(activity);
            notifyDataSetChanged();

            int id = getCardId(getTime(picker));

            setAlarm(picker, text, id);
        }
    }

    public void changeItem(CardContent card, int position){
        if(checkIsTimeNotReserved(card.getTime())) {
            MySharedPreference.changeElementFromList(card, position, activity);
            list = MySharedPreference.getTodoList(activity);
            notifyDataSetChanged();
        }

    }


    private void setAlarm(TimePicker picker, String content, int alarmId) {
        AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(activity, TimeNotificationReciever.class);

        intent.putExtra(TimeNotificationReciever.NOTIFICATION_CONTENT, content);
        Log.e("ID",alarmId+"");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(activity, alarmId,
                intent,0 );

// На случай, если мы ранее запускали активити, а потом поменяли время,
// откажемся от уведомления
        alarmManager.cancel(pendingIntent);

        Calendar currentTime=Calendar.getInstance();
        Calendar planTime=Calendar.getInstance();

        planTime.set(Calendar.HOUR, picker.getCurrentHour());
        planTime.set(Calendar.MINUTE, picker.getCurrentMinute());

        if (currentTime.getTimeInMillis()>planTime.getTimeInMillis()){
            planTime.add(Calendar.DATE,1);
            Log.e("AB","time");
        }
        Log.e("A","alarm setted");

// Устанавливаем разовое напоминание
        alarmManager.set(AlarmManager.RTC_WAKEUP, planTime.getTimeInMillis(), pendingIntent);
    }

    private void deleteAlarm(int alarmId){
        AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(activity, TimeNotificationReciever.class);

        Log.e("ID",alarmId+"");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(activity, alarmId,
                intent,0 );

// На случай, если мы ранее запускали активити, а потом поменяли время,
// откажемся от уведомления
        alarmManager.cancel(pendingIntent);
    }


    private String getTime(TimePicker picker) {

        return addZeroIfOnlyOneDigit(picker.getCurrentHour())
                + " : "
                + addZeroIfOnlyOneDigit(picker.getCurrentMinute()) ;
    }
    private int getCardId(String time){
        int id=0;
        for (CardContent card :list){
            if(card.getTime().equals(time)) {
                Log.e("gg","eq");
                break;
            }
            id++;
        }
        return id;
    }

    private String addZeroIfOnlyOneDigit(int num){
        //if number contain only one digit then we add 0
        return num > 9
                ? num+""
                : "0" + num;
    }

    private boolean checkIsTimeNotReserved(String time){
        for (CardContent card :list){
            if(card.getTime().equals(time)) {
                CoordinatorLayout coordinatorLayout=((MainActivity)activity).getView().coordinatorLayout;
                String msg=activity.getResources().getString(R.string.msgTimeResved);
                Snackbar.make(coordinatorLayout,msg,Snackbar.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }

}
