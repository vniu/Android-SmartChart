package com.lecast.smartchart.workflow;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.widget.ViewFlipper;

import com.lecast.smartchart.listener.IChartTouchListener;

public class WorkTaskFlowHolder
{

    private List<WorkTaskFlow> workTaskFlows = new ArrayList<WorkTaskFlow>();

    private Context context;

    private ViewFlipper viewFipper;

    private static WorkTaskFlowHolder workTaskHolder;

    public static WorkTaskFlowHolder getInstance(Context context)
    {
        if (workTaskHolder == null)
            workTaskHolder = new WorkTaskFlowHolder(context);
        return workTaskHolder;
    }

    public static void init(Context context)
    {
        if (workTaskHolder == null)
            workTaskHolder = new WorkTaskFlowHolder(context);
    }

    private WorkTaskFlowHolder(Context context)
    {
        this.context = context;
        viewFipper = new ViewFlipper(context);
    }

    private void addWorkTaskFlow(WorkTaskFlow workTaskFlow)
    {
        if (workTaskFlows.contains(workTaskFlow))
            return;
        workTaskFlows.add(workTaskFlow);
    }

    public ViewFlipper getViewFlipper()
    {
        return viewFipper;
    }

    public void convertJsonHandler(String json)
    {
        WorkTaskFlow preWorkTaskFlow = null;
        try
        {
            JSONArray workFlowJsons = new JSONArray(json);
            int size = workFlowJsons.length();
            for (int index = 0; index < size; index++)
            {
                WorkTaskFlow workTaskFlow = new WorkTaskFlow(context, index);
                viewFipper.addView(workTaskFlow.createChartHandler(new TouchHandlerListener(workTaskFlow)));
                addWorkTaskFlow(workTaskFlow);
                if (index != 0)
                {
                    workTaskFlow.setPreTaskFlow(preWorkTaskFlow);
                    preWorkTaskFlow.setNextTaskFlow(workTaskFlow);
                }
                preWorkTaskFlow = workTaskFlow;
            }
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    class TouchHandlerListener implements IChartTouchListener
    {

        private WorkTaskFlow workTaskFlow;

        public TouchHandlerListener(WorkTaskFlow workTaskFlow)
        {
            this.workTaskFlow = workTaskFlow;
        }

        public void execute(Object target)
        {
            WorkTaskExecutor exector = new WorkTaskExecutor(workTaskFlow, viewFipper);
            exector.execute();
        }
    }

    public List<WorkTaskFlow> getWorkTaskFlows()
    {
        return workTaskFlows;
    }
}
