package ru.scapegoats.dayplanner.activity.main;

import android.content.Context;
import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ru.scapegoats.dayplanner.R;
import ru.scapegoats.dayplanner.activity.main.adapter.MyRecyclerViewAdapter;
import ru.scapegoats.dayplanner.activity.main.dialogs.CreateNewPlanDialog;
import ru.scapegoats.dayplanner.modules.Viewable;

public class MainView implements Viewable<String>,RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof MyRecyclerViewAdapter.ViewHolder) {
            // remove the item from recycler view
            adapter.removeItem(viewHolder.getAdapterPosition());
        }
    }

    RecyclerView recyclerView;
    RecyclerView.LayoutManager manager;
    public MyRecyclerViewAdapter adapter;
    MainActivity activity;
    public CoordinatorLayout coordinatorLayout;

    MainView(final MainActivity activity, View rootView){
        this.activity=activity;
        Context context=rootView.getContext();
        coordinatorLayout=rootView.findViewById(R.id.coordinator);
        recyclerView=rootView.findViewById(R.id.recycler);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        rootView.findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateNewPlanDialog dialog=new CreateNewPlanDialog(activity);
                dialog.show();
            }
        });
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

        manager=new LinearLayoutManager(context);

//        rootView.findViewById()
    }
    @Override
    public void onAttach(String data) {

    }
}
