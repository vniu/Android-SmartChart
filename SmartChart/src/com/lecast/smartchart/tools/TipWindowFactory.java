package com.lecast.smartchart.tools;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class TipWindowFactory
{

    public static Toast createWithImageToast(Context context, String title, int resourceId)
    {
        Toast toast = Toast.makeText(context.getApplicationContext(), title, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastView = (LinearLayout) toast.getView();
        ImageView imageCodeProject = new ImageView(context.getApplicationContext());
        if (resourceId != 0)
            imageCodeProject.setImageResource(resourceId);
        toastView.addView(imageCodeProject, 0);
        return toast;
    }

    public static AlertDialog getDialog(Context context, String title)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener()
        {

            public void onClick(DialogInterface arg0, int arg1)
            {
                return;
            }
        });
        AlertDialog ad = builder.create();
        return ad;
    }
}
