package com.ashinfo.stockrates.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ashinfo.stockrates.Adapters.StockListAdpter;
import com.ashinfo.stockrates.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> StockName, StockSymbol, StockType;
    StockListAdpter stockListAdpter;
    GridView StocksGrid;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    EditText SearchEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        SearchEdit = (EditText)findViewById(R.id.searchEdit);
        StocksGrid = (GridView)findViewById(R.id.stocksGrid);

        StockName = new ArrayList<String>();
        StockSymbol = new ArrayList<String>();
        StockType = new ArrayList<String>();


        SearchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (SearchEdit.getText().toString().isEmpty()){

                } else if (SearchEdit.getText().toString().length() < 2){

                } else {
                    GetStocks(SearchEdit.getText().toString());
                }
            }
        });

        StocksGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent stockDetailsIntent = new Intent(MainActivity.this, StockDetails.class);
                stockDetailsIntent.putExtra("Symbol", StockSymbol.get(i));
                startActivity(stockDetailsIntent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_fav) {
            Intent favIntent = new Intent(MainActivity.this, FavouriteStock.class);
            startActivity(favIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void GetStocks (final String key)
    {
//        progressDialog = new ProgressDialog(MainActivity.this);
//        progressDialog.setMessage("Loading...");
//        progressDialog.setCancelable(false);
//        //progressDialog.setMax(100);
//        progressDialog.show();

        StockName.clear();
        StockSymbol.clear();
        StockType.clear();

        final String url = "https://www.alphavantage.co/query?function=SYMBOL_SEARCH&keywords="+key+"&apikey=6N6WQZAKSKUMWKE0";

        // prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        //progressDialog.cancel();
                        System.out.print(response.toString());
                        try {
                            JSONArray details = response.getJSONArray("bestMatches");
                            for (int i = 0; i < details.length(); i++) {
                                JSONObject jsonObject = details.getJSONObject(i);
                                String stockNameString = jsonObject.getString("2. name");
                                String stockSymbolString = jsonObject.getString("1. symbol");
                                String stockTypeString = jsonObject.getString("3. type");

                                StockName.add(stockNameString);
                                StockSymbol.add(stockSymbolString);
                                StockType.add(stockTypeString);
                            }

                            stockListAdpter = new StockListAdpter(MainActivity.this, StockName, StockSymbol, StockType);
                            StocksGrid.setAdapter(stockListAdpter);

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
