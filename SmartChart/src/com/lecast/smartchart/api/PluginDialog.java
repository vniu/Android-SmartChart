package com.lecast.smartchart.api;

import android.R;
import android.app.Dialog;
import android.content.Context;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

public class PluginDialog extends Dialog
{

    protected Context context;

    public PluginDialog(Context context)
    {
        super(context, R.style.Theme_Black_NoTitleBar);
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 1.0f;
        lp.dimAmount = 0.0f;
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setWindowAnimations(R.style.Animation_Translucent);
        this.context = context;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            this.dismiss();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
