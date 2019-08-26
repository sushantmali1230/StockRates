package com.ashinfo.stockrates.Activities;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ashinfo.stockrates.Adapters.StockDetialsAdapter;
import com.ashinfo.stockrates.Helper.DatabaseHelper;
import com.ashinfo.stockrates.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FavouriteStock extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    ArrayList<String> StockList;
    ArrayList<String> Symbol, Open, High, Low, Price, Volume, LastTradingDay, PriceCLose, Change, ChangePercent;
    RequestQueue requestQueue;
    StockDetialsAdapter stockDetialsAdapter;
    GridView StockDetialsGrid;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_stock);

        getSupportActionBar().setTitle("Favourites");

        databaseHelper = new DatabaseHelper(FavouriteStock.this);
        StockList = new ArrayList<String>();

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        StockDetialsGrid = (GridView)findViewById(R.id.stockDetailsGrid);

        Symbol = new ArrayList<String>();
        Open = new ArrayList<String>();
        High = new ArrayList<String>();
        Low = new ArrayList<String>();
        Price = new ArrayList<String>();
        Volume = new ArrayList<String>();
        LastTradingDay = new ArrayList<String>();
        PriceCLose = new ArrayList<String>();
        Change = new ArrayList<String>();
        ChangePercent = new ArrayList<String>();

        try {
            Cursor data = databaseHelper.getData();
            while (data.moveToNext()) {
                StockList.add(data.getString(1));
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        for (int i = 0; i < StockList.size(); i++){
            progressBar.setVisibility(View.VISIBLE);
            GetStockDetails(StockList.get(i));
        }
    }

    public void GetStockDetails (final String SymbolGot)
    {
//        progressDialog = new ProgressDialog(FavouriteStock.this);
//        progressDialog.setMessage("Loading...");
//        progressDialog.setCancelable(false);
//        //progressDialog.setMax(100);
//        progressDialog.show();

        //Toast.makeText(FavouriteStock.this, SymbolGot, Toast.LENGTH_SHORT).show();

        final String url = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol="+SymbolGot+"&apikey=6N6WQZAKSKUMWKE0";

        // prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {

                        //Toast.makeText(FavouriteStock.this, response.toString(), Toast.LENGTH_SHORT).show();

                        //progressDialog.cancel();
                        System.out.print(response.toString());
                        try {
                            JSONObject details = response.getJSONObject("Global Quote");
                            String symbolString = details.getString("01. symbol");
                            String openString = details.getString("02. open");
                            String highString = details.getString("03. high");
                            String lowString = details.getString("04. low");
                            String priceString = details.getString("05. price");
                            String volumeString = details.getString("06. volume");
                            String lastTradingString = details.getString("07. latest trading day");
                            String previousCloseString = details.getString("08. previous close");
                            String changeString = details.getString("09. change");
                            String changePercentString = details.getString("10. change percent");

                            Symbol.add(symbolString);
                            Open.add(openString);
                            High.add(highString);
                            Low.add(lowString);
                            Price.add(priceString);
                            Volume.add(volumeString);
                            LastTradingDay.add(lastTradingString);
                            PriceCLose.add(previousCloseString);
                            Change.add(changeString);
                            ChangePercent.add(changePercentString);

                            stockDetialsAdapter = new StockDetialsAdapter(FavouriteStock.this, Symbol, Open, High, Low, Price, Volume, LastTradingDay, PriceCLose, Change, ChangePercent);
                            stockDetialsAdapter.notifyDataSetChanged();
                            StockDetialsGrid.setAdapter(stockDetialsAdapter);
                            progressBar.setVisibility(View.GONE);

                        } catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.print("Error.Response");
                    }
                }
        );

        requestQueue.add(getRequest);
    }
}
