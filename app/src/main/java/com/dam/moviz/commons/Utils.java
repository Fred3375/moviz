package com.dam.moviz.commons;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class Utils {

    public static void showSnackBar(View baseView,String msg){
        Snackbar.make(baseView, msg, Snackbar.LENGTH_LONG).show();
    }
}
