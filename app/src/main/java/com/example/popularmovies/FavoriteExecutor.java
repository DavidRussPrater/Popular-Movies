package com.example.popularmovies;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

public class FavoriteExecutor implements Executor {
    @Override
    public void execute(@NonNull Runnable runnable) {
        new Thread(runnable).start();
    }
}
