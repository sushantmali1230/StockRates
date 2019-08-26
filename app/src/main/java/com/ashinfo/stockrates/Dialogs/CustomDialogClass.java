package com.ashinfo.stockrates.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;

import com.ashinfo.stockrates.R;


public class CustomDialogClass extends Dialog {

    public Activity c;
    public Dialog d;

    public CustomDialogClass(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_to_fav_dialog);
    }
}
