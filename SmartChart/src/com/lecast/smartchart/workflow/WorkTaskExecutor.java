package com.lecast.smartchart.workflow;

import org.json.JSONArray;
import org.json.JSONException;

import android.os.AsyncTask;
import android.widget.ViewFlipper;

public class WorkTaskExecutor
{

    private WorkTaskFlow workTaskFlow;

    private ViewFlipper viewFlipper;

    public WorkTaskExecutor(WorkTaskFlow workTaskFlow, ViewFlipper viewFlipper)
    {
        this.workTaskFlow = workTaskFlow;
        this.viewFlipper = viewFlipper;
    }

    public void execute(Object... params)
    {
        new LoadDataHandler().execute(params);
    }

    class LoadDataHandler extends AsyncTask<Object, Void, String>
    {

        protected String doInBackground(Object... params)
        {
            return null;
        }

        protected void onPostExecute(String result)
        {
            try
            {
                JSONArray jsonArray = new JSONArray(result);
                if (jsonArray != null)
                {
                    workTaskFlow.notifyChanged(jsonArray);
                    workTaskFlow.repaint();
                    viewFlipper.setDisplayedChild(workTaskFlow.getTaskIndex());
                }
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }
}
