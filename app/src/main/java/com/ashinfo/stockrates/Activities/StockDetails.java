package com.ashinfo.stockrates.Activities;

import android.app.ProgressDialog;
import android.graphics.Path;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ashinfo.stockrates.Adapters.StockDetialsAdapter;
import com.ashinfo.stockrates.Adapters.StockListAdpter;
import com.ashinfo.stockrates.Dialogs.CustomDialogClass;
import com.ashinfo.stockrates.Helper.DatabaseHelper;
import com.ashinfo.stockrates.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StockDetails extends AppCompatActivity {

    ArrayList<String> Symbol, Open, High, Low, Price, Volume, LastTradingDay, PriceCLose, Change, ChangePercent;
    StockDetialsAdapter stockDetialsAdapter;
    GridView StockDetialsGrid;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    Button AddToFav;
    DatabaseHelper databaseHelper;
    CustomDialogClass customDialogClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_details);
        getSupportActionBar().setTitle("Stock Details");

        customDialogClass = new CustomDialogClass(StockDetails.this);
        databaseHelper = new DatabaseHelper(StockDetails.this);
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        AddToFav = (Button)findViewById(R.id.favBtn);
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

        GetStockDetails(getIntent().getStringExtra("Symbol"));

        AddToFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialogClass.show();
                final EditText MinPriceEdit = (EditText)customDialogClass.findViewById(R.id.minPriceEdit);
                final EditText MaxPriceEdit = (EditText)customDialogClass.findViewById(R.id.maxPriceEdit);
                Button SaveBtn = (Button)customDialogClass.findViewById(R.id.saveBtn);

                SaveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (MinPriceEdit.getText().toString().isEmpty()){
                            Toast.makeText(StockDetails.this, "Please Enter Minimum Price You Want.", Toast.LENGTH_SHORT).show();
                        } else if (MaxPriceEdit.getText().toString().isEmpty()){
                            Toast.makeText(StockDetails.this, "Please Enter Maximum Price You Want.", Toast.LENGTH_SHORT).show();
                        } else {
                            databaseHelper.addData(getIntent().getStringExtra("Symbol"), MinPriceEdit.getText().toString(), MaxPriceEdit.getText().toString());
                            customDialogClass.cancel();
                        }
                    }
                });
            }
        });

    }

    public void GetStockDetails (final String SymbolGot)
    {
        progressDialog = new ProgressDialog(StockDetails.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        //progressDialog.setMax(100);
        progressDialog.show();

        final String url = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol="+SymbolGot+"&apikey=6N6WQZAKSKUMWKE0";

        // prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {

                        //Toast.makeText(StockDetails.this, response.toString(), Toast.LENGTH_SHORT).show();

                        progressDialog.cancel();
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

                            stockDetialsAdapter = new StockDetialsAdapter(StockDetails.this, Symbol, Open, High, Low, Price, Volume, LastTradingDay, PriceCLose, Change, ChangePercent);
                            StockDetialsGrid.setAdapter(stockDetialsAdapter);

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
