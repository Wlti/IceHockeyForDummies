package com.example.icehockeyfordummies.async;

import android.app.Application;
import android.os.AsyncTask;

import com.example.icehockeyfordummies.BaseApp;
import com.example.icehockeyfordummies.database.Entity.ClubEntity;
import com.example.icehockeyfordummies.util.OnAsyncEventListener;


public class DeleteClub extends AsyncTask<ClubEntity, Void, Void> {
    private static final String TAG = "DeleteClub";
    private Application app;
    private OnAsyncEventListener callback;
    private Exception exception;

    public DeleteClub(Application app, OnAsyncEventListener callback) {
        this.app = app;
        this.callback = callback;
    }


    @Override
    protected Void doInBackground(ClubEntity... params) {
        try {
            for (ClubEntity club : params)
                ((BaseApp) app).getClubRepository().delete(club);
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
