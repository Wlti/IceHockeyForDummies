package com.example.icehockeyfordummies.async;

import android.app.Application;
import android.os.AsyncTask;

import com.example.icehockeyfordummies.BaseApp;
import com.example.icehockeyfordummies.database.Entity.LeagueEntity;
import com.example.icehockeyfordummies.util.OnAsyncEventListener;


public class CreateLeague extends AsyncTask<LeagueEntity, Void, Void> {
    private static final String TAG = "CreateLeague";
    private Application app;
    private OnAsyncEventListener callback;
    private Exception exception;

    public CreateLeague(Application app, OnAsyncEventListener callback) {
        this.app = app;
        this.callback = callback;
    }


    @Override
    protected Void doInBackground(LeagueEntity... params) {
        try {
            for (LeagueEntity league : params)
                ((BaseApp) app).getLeagueRepository().insert(league);
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
