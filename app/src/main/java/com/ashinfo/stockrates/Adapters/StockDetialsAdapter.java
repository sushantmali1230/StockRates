package com.ashinfo.stockrates.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ashinfo.stockrates.R;

import java.util.ArrayList;

public class StockDetialsAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> Symbol, Open, High, Low, Price, Volume, LastTradingDay, PriceCLose, Change, ChangePercent;

    public StockDetialsAdapter (Context context1, ArrayList<String> symbol, ArrayList<String> open, ArrayList<String> high,
                                ArrayList<String> low, ArrayList<String> price, ArrayList<String> volume, ArrayList<String> lastTradingDay,
                                ArrayList<String> previousChange, ArrayList<String> change, ArrayList<String> changePercent){
        this.context = context1;
        this.Symbol = symbol;
        this.Open = open;
        this.High = high;
        this.Low = low;
        this.Price = price;
        this.Volume = volume;
        this.LastTradingDay = lastTradingDay;
        this.PriceCLose = previousChange;
        this.Change = change;
        this.ChangePercent = changePercent;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {
            gridView = new View(context);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.single_grid_item_stock_details, null);
            //holder = new RecyclerView.ViewHolder();
        } else {
            gridView = (View) convertView;
        }

        TextView StockSymbolText = (TextView)gridView.findViewById(R.id.stockSymbolText);
        TextView StockOpenText = (TextView)gridView.findViewById(R.id.stockOpenText);
        TextView StockHighTect = (TextView)gridView.findViewById(R.id.stockHighText);
        TextView StockLowText = (TextView)gridView.findViewById(R.id.stockLowText);
        TextView StockPriceText = (TextView)gridView.findViewById(R.id.stockPriceText);
        TextView StockVolumeText = (TextView)gridView.findViewById(R.id.stockVolumeText);
        TextView StockLastTradingText = (TextView)gridView.findViewById(R.id.stockTradingDayText);
        TextView StockPriceCloseText = (TextView)gridView.findViewById(R.id.stockPreviousCloseText);
        TextView StockChangeText = (TextView)gridView.findViewById(R.id.stockChangeText);
        TextView StockChangePercentText = (TextView)gridView.findViewById(R.id.stockChangePercentText);

        StockSymbolText.setText("Symbol : " + Symbol.get(position));
        StockOpenText.setText("Open : " + Open.get(position));
        StockHighTect.setText("High : " + High.get(position));
        StockLowText.setText("Low : "+ Low.get(position));
        StockPriceText.setText("Price :" + Price.get(position));
        StockVolumeText.setText("Volume : " + Volume.get(position));
        StockLastTradingText.setText("Last Trading Day : " + LastTradingDay.get(position));
        StockPriceCloseText.setText("Price Close : " + PriceCLose.get(position));
        StockChangeText.setText("Change : " + Change.get(position));
        StockChangePercentText.setText("Change Percent : "+ ChangePercent.get(position));


        return gridView;
    }

    @Override
    public int getCount() {
        return Symbol.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
