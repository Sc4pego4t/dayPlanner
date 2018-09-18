package ru.scapegoats.dayplanner.utils;

import android.app.IntentService;
import android.content.Intent;
import androidx.annotation.Nullable;

public class BackgroundService extends IntentService {
    public BackgroundService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
