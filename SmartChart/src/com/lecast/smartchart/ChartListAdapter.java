package com.lecast.smartchart;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ChartListAdapter extends BaseAdapter
{

    private Context context;

    private List<ChartVO> dataProvider;

    private LayoutInflater mInflater;

    public ChartListAdapter(Context context)
    {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void updateDataProvider(List<ChartVO> dataProvider)
    {
        this.dataProvider = dataProvider;

    }

    public int getCount()
    {
        return dataProvider.size();
    }

    public Object getItem(int index)
    {

        return dataProvider.get(index);
    }

    public long getItemId(int index)
    {
        return index;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        CellHolder holder;
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.chart_item, null);
            holder = new CellHolder();
            holder.chartName = (TextView) convertView.findViewById(R.id.chartName);
            convertView.setTag(holder);
        }
        else
        {
            holder = (CellHolder) convertView.getTag();
        }
        ChartVO vo = dataProvider.get(position);
        holder.chartName.setText(vo.getChartName());
        holder.chartName.setTag(vo);
        ItemOnClickListener btnOntouthListener = new ItemOnClickListener(position);
        convertView.setOnClickListener(btnOntouthListener);
        return convertView;
    }

    class ItemOnClickListener implements View.OnClickListener
    {

        int position;

        public ItemOnClickListener(int position)
        {
            this.position = position;
        }

        public void onClick(View v)
        {
            ChartVO chartVO = dataProvider.get(position);
            Intent intent = new Intent(context, ChartActivity.class);
            intent.putExtra("chartType", chartVO.getChartType());
            context.startActivity(intent);
        }
    }

    public class CellHolder
    {

        public TextView chartName;

    }

}
