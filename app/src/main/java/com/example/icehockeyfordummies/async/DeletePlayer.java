package com.example.icehockeyfordummies.async;

import android.app.Application;
import android.os.AsyncTask;

import com.example.icehockeyfordummies.BaseApp;
import com.example.icehockeyfordummies.database.Entity.PlayerEntity;
import com.example.icehockeyfordummies.util.OnAsyncEventListener;


public class DeletePlayer extends AsyncTask<PlayerEntity, Void, Void> {
    private static final String TAG = "DeletePlayer";
    private Application app;
    private OnAsyncEventListener callback;
    private Exception exception;

    public DeletePlayer(Application app, OnAsyncEventListener callback) {
        this.app = app;
        this.callback = callback;
    }


    @Override
    protected Void doInBackground(PlayerEntity... params) {
        try {
            for (PlayerEntity player : params)
                ((BaseApp) app).getPlayerRepository().delete(player);
        } catch (Exception e) {
            exception = e;
        }

        return null;
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        if (callback != null) {
            if (exception == null) {
                callback.onSuccess();
            } else {
                callback.onFailure(exception);
            }
        }
    }
}
