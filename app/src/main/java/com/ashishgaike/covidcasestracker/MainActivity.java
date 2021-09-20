package com.ashishgaike.covidcasestracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.ashishgaike.covidcasestracker.api.Apiutilities;
import com.ashishgaike.covidcasestracker.api.CountryData;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ashishgaike.covidcasestracker.api.Apiutilities.getApiInerface;
import static java.lang.Integer.parseInt;

public class MainActivity extends AppCompatActivity {

    private TextView Conform, TodayConform, Active, Recoverd, TodayReecoverd, Death, TodayDeath, Test;
    private List<CountryData> list;
    private PieChart pieChart;
    String country = "India";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);

        list = new ArrayList<>();
        if(getIntent().getStringExtra("country") != null){
            country = getIntent().getStringExtra("country");
        }

        init();
        TextView cname = findViewById(R.id.sname);

        cname.setText(country);

        cname.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this,stateactivity.class)));
        getApiInerface().getCountryData().enqueue(new Callback<List<CountryData>>() {
            @Override
            public void onResponse(Call<List<CountryData>>call, Response<List<CountryData>>response) {
                list.addAll(response.body());

                for(int i = 0; i<list.size();i++){
                    if(list.get(i).getCountry().equals(country)){
                        int conform = parseInt(list.get(i).getCases());
                        int active = parseInt(list.get(i).getActive());
                        int recovered = parseInt(list.get(i).getRecovered());
                        int death = parseInt(list.get(i).getDeaths());

                        Active.setText(NumberFormat.getInstance().format(active));
                        Conform.setText(NumberFormat.getInstance().format(conform));
                        Recoverd.setText(NumberFormat.getInstance().format(recovered));
                        Death.setText(NumberFormat.getInstance().format(death));

                        TodayDeath.setText(NumberFormat.getInstance().format(parseInt(list.get(i).getTodayDeaths())));
                        TodayConform.setText(NumberFormat.getInstance().format(parseInt(list.get(i).getTodayCases())));
                        TodayReecoverd.setText(NumberFormat.getInstance().format(parseInt(list.get(i).getTodayRecovered())));
                        Test.setText(NumberFormat.getInstance().format(parseInt(list.get(i).getTests())));

                        pieChart.addPieSlice(new PieModel("conform", conform, getResources().getColor(R.color.Gold)));
                        pieChart.addPieSlice(new PieModel("active", active, getResources().getColor(R.color.Aqua)));
                        pieChart.addPieSlice(new PieModel("recovered", recovered, getResources().getColor(R.color.green)));
                        pieChart.addPieSlice(new PieModel("death", death, getResources().getColor(R.color.Red)));

                        pieChart.startAnimation();


                    }
                }
            }

            @Override
            public void onFailure(Call<List<CountryData>>call, Throwable t) {
                Toast.makeText(MainActivity.this,"Error:"+t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void init(){

        Conform = findViewById(R.id.Confirm);
        TodayConform = findViewById(R.id.TodayConform);
        Active = findViewById(R.id.Active);
        Recoverd = findViewById(R.id.Recovered);
        TodayReecoverd = findViewById(R.id.TodayRecovered);
        Death = findViewById(R.id.Death);
        TodayDeath = findViewById(R.id.TodayDeath);
        Test =findViewById(R.id.Tests);
        pieChart = findViewById(R.id.pieChart);
    }
}