package com.predictezy.fp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper.Callback;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.predictezy.fp.SerieAStandings */
public class SerieAStandings extends AppCompatActivity {
    Button backButton;
    Toast calculate;
    Toast connect;
    int count = 0;

    /* renamed from: db */
    ArrayList<HashMap<String, String>> db = null;
    Toast error;
    TextView heading;
    RelativeLayout input;
    AdView mAdView;
    TextView progress;
    ProgressBar progressBar;
    TableLayout tableLayout;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.serie_a_standings);
        this.error = Toast.makeText(this, "Poor Network Connection. Could not connect", Toast.LENGTH_SHORT);
        this.connect = Toast.makeText(this, "Connected to the Server", Toast.LENGTH_SHORT);
        this.calculate = Toast.makeText(this, "Calculating Predictions. Please Wait", Toast.LENGTH_SHORT);
        this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
        this.progress = (TextView) findViewById(R.id.pleasewait);
        this.heading = (TextView) findViewById(R.id.heading);
        this.input = (RelativeLayout) findViewById(R.id.input);
        this.tableLayout = (TableLayout) findViewById(R.id.tablelayout);
        this.backButton = (Button) findViewById(R.id.backButton);
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-2924639732611391/7511600535");
        MobileAds.initialize((Context) this, (OnInitializationCompleteListener) new OnInitializationCompleteListener() {
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        this.mAdView = (AdView) findViewById(R.id.ads);
        this.mAdView.loadAd(new Builder().build());
        new Thread() {
            public void run() {
                String str = "null";
                SerieAStandings SerieAStandings = SerieAStandings.this;
                SerieAStandings.db = SerieAStandings.getItems();
                while (String.valueOf(SerieAStandings.db).equals("null") && SerieAStandings.count<10) {
                    if (String.valueOf(SerieAStandings.this.db).equals(str) && SerieAStandings.this.count < 10) {
                        try {
                            sleep(1000);
                            SerieAStandings.this.count++;
                            System.out.println(SerieAStandings.this.count);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (String.valueOf(SerieAStandings.this.db).equals(str)) {
                    SerieAStandings.this.runOnUiThread(new Runnable() {
                        public void run() {
                            SerieAStandings.this.error.show();
                            SerieAStandings.this.startActivity(new Intent(SerieAStandings.this, HomePage.class));
                        }
                    });
                } else {
                    SerieAStandings.this.runOnUiThread(new Runnable() {
                        public void run() {
                            SerieAStandings.this.Visibility();
                        }
                    });
                }
            }
        }.start();
        this.backButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SerieAStandings.this.startActivity(new Intent(SerieAStandings.this, HomePage.class));
            }
        });
        this.heading.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SerieAStandings.this.startActivity(new Intent(SerieAStandings.this, HomePage.class));
            }
        });
    }

    public void Visibility() {
        this.progressBar.setVisibility(View.GONE);
        this.progress.setVisibility(View.INVISIBLE);
        this.input.setVisibility(View.VISIBLE);
        this.tableLayout.setVisibility(View.VISIBLE);
        this.heading.setVisibility(View.VISIBLE);
        startcalc();
    }

    /* access modifiers changed from: private */
    public ArrayList<HashMap<String, String>> getItems() {
        StringRequest stringRequest = new StringRequest(0, "https://script.google.com/macros/s/AKfycbxng-2_Dy0Ry_4bArlsO2LB9has7PgttOzr97RNM6t8ONUlT-k/exec?action=getItems", new Listener<String>() {
            public void onResponse(String str) {
                SerieAStandings SerieAStandings = SerieAStandings.this;
                SerieAStandings.db = SerieAStandings.parseItems(str);
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000, 0, 1.0f));
        Volley.newRequestQueue(this).add(stringRequest);
        return this.db;
    }

    /* access modifiers changed from: private */
    public ArrayList<HashMap<String, String>> parseItems(String str) {
        String str2 = "seasonID";
        String str3 = "awayScore";
        String str4 = "homeScore";
        String str5 = "awayTeam";
        String str6 = "homeTeam";
        this.db = new ArrayList<>();
        try {
            JSONArray jSONArray = new JSONObject(str).getJSONArray(Param.ITEMS);
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                String string = jSONObject.getString(str6);
                String string2 = jSONObject.getString(str5);
                String string3 = jSONObject.getString(str4);
                String string4 = jSONObject.getString(str3);
                String string5 = jSONObject.getString(str2);
                HashMap hashMap = new HashMap();
                hashMap.put(str6, string);
                hashMap.put(str5, string2);
                hashMap.put(str4, string3);
                hashMap.put(str3, string4);
                hashMap.put(str2, string5);
                this.db.add(hashMap);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this.db;
    }

    private void startcalc() {
        int i;
        String[] stringArray = getResources().getStringArray(R.array.SATeams);
        int[] iArr = new int[38];
        int[] iArr2 = new int[38];
        int i2 = 20;
        double[] dArr = new double[20];
        int i3 = 1;
        while (true) {
            i = 0;
            if (i3 >= stringArray.length) {
                break;
            }
            Iterator it = this.db.iterator();
            int i4 = 0;
            int i5 = 0;
            while (it.hasNext()) {
                HashMap hashMap = (HashMap) it.next();
                String str = "awayScore";
                String str2 = "homeScore";
                if (Objects.equals(hashMap.get("homeTeam"), stringArray[i3])) {
                    if (Integer.parseInt((String) Objects.requireNonNull(hashMap.get(str2))) > Integer.parseInt((String) Objects.requireNonNull(hashMap.get(str)))) {
                        i5 += 3;
                        iArr[i4] = i5;
                        i4++;
                        iArr2[i4 - 1] = i4;
                    }
                    if (Integer.parseInt((String) Objects.requireNonNull(hashMap.get(str2))) < Integer.parseInt((String) Objects.requireNonNull(hashMap.get(str)))) {
                        iArr[i4] = i5;
                        i4++;
                        iArr2[i4 - 1] = i4;
                    }
                    if (Integer.parseInt((String) Objects.requireNonNull(hashMap.get(str2))) == Integer.parseInt((String) Objects.requireNonNull(hashMap.get(str)))) {
                        i5++;
                        iArr[i4] = i5;
                        i4++;
                        iArr2[i4 - 1] = i4;
                    }
                }
                if (Objects.equals(hashMap.get("awayTeam"), stringArray[i3])) {
                    if (Integer.parseInt((String) Objects.requireNonNull(hashMap.get(str2))) < Integer.parseInt((String) Objects.requireNonNull(hashMap.get(str)))) {
                        i5 += 3;
                        iArr[i4] = i5;
                        i4++;
                        iArr2[i4 - 1] = i4;
                    }
                    if (Integer.parseInt((String) Objects.requireNonNull(hashMap.get(str2))) > Integer.parseInt((String) Objects.requireNonNull(hashMap.get(str)))) {
                        iArr[i4] = i5;
                        i4++;
                        iArr2[i4 - 1] = i4;
                    }
                    if (Integer.parseInt((String) Objects.requireNonNull(hashMap.get(str2))) == Integer.parseInt((String) Objects.requireNonNull(hashMap.get(str)))) {
                        i5++;
                        iArr[i4] = i5;
                        i4++;
                        iArr2[i4 - 1] = i4;
                    }
                }
            }
            double d = 0.0d;
            double d2 = 0.0d;
            double d3 = 0.0d;
            double d4 = 0.0d;
            while (i < i4) {
                d += (double) (iArr2[i] * iArr[i]);
                d4 += (double) (iArr2[i] * iArr2[i]);
                d2 += (double) iArr2[i];
                d3 += (double) iArr[i];
                i++;
                dArr = dArr;
            }
            double[] dArr2 = dArr;
            double d5 = (double) i4;
            double pow = ((d * d5) - (d2 * d3)) / ((d4 * d5) - Math.pow(d2, 2.0d));
            dArr2[i3 - 1] = (pow * 38.0d) + ((d3 - (d2 * pow)) / d5);
            i3++;
            dArr = dArr2;
            i2 = 20;
        }
        double[] dArr3 = dArr;
        int i6 = 1;
        for (int i7 = i2; i6 < i7; i7 = 20) {
            for (int i8 = i6; i8 > 0; i8--) {
                int i9 = i8 - 1;
                if (dArr3[i8] > dArr3[i9]) {
                    double d6 = dArr3[i8];
                    dArr3[i8] = dArr3[i9];
                    dArr3[i9] = d6;
                    int i10 = i8 + 1;
                    String str3 = stringArray[i10];
                    stringArray[i10] = stringArray[i8];
                    stringArray[i8] = str3;
                }
            }
            i6++;
        }
        TableRow tableRow = new TableRow(this);
        tableRow.setLayoutParams(new LayoutParams(-1, -2, 4.0f));
        TextView textView = new TextView(this);
        textView.setText(R.string.position);
        textView.setBackgroundResource(R.drawable.cell_heading_border);
        textView.setTextColor(ContextCompat.getColor(this, R.color.white));
        textView.setGravity(17);
        textView.setTextSize(15.0f);
        textView.setPadding(5, 5, 5, 5);
        LayoutParams layoutParams = new LayoutParams(0, -2, 1.0f);
        textView.setLayoutParams(layoutParams);
        TextView textView2 = new TextView(this);
        textView2.setText(R.string.team);
        textView2.setBackgroundResource(R.drawable.cell_heading_border);
        textView2.setTextColor(ContextCompat.getColor(this, R.color.white));
        textView2.setPadding(5, 5, 5, 5);
        textView2.setGravity(17);
        textView2.setTextSize(15.0f);
        LayoutParams layoutParams2 = new LayoutParams(0, -2, 2.0f);
        textView2.setLayoutParams(layoutParams2);
        TextView textView3 = new TextView(this);
        textView3.setText(R.string.points);
        textView3.setBackgroundResource(R.drawable.cell_heading_border);
        textView3.setTextColor(ContextCompat.getColor(this, R.color.white));
        textView3.setGravity(17);
        textView3.setTextSize(15.0f);
        textView3.setPadding(5, 5, 5, 5);
        int i11 = -2;
        LayoutParams layoutParams3 = new LayoutParams(0, -2, 1.0f);
        textView3.setLayoutParams(layoutParams3);
        tableRow.addView(textView);
        tableRow.addView(textView2);
        tableRow.addView(textView3);
        int i12 = -1;
        this.tableLayout.addView(tableRow, new TableLayout.LayoutParams(-1, -2));
        int i13 = 20;
        while (i < i13) {
            TableRow tableRow2 = new TableRow(this);
            tableRow2.setId(i + 100);
            tableRow2.setLayoutParams(new LayoutParams(i12, i11, 4.0f));
            TextView textView4 = new TextView(this);
            textView4.setId(i + Callback.DEFAULT_DRAG_ANIMATION_DURATION);
            int i14 = i + 1;
            textView4.setText(Integer.toString(i14));
            textView4.setBackgroundResource(R.drawable.cell_border);
            textView4.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            textView4.setSingleLine(true);
            textView4.setGravity(17);
            textView4.setPadding(7, 7, 7, 7);
            textView4.setLayoutParams(layoutParams);
            TextView textView5 = new TextView(this);
            textView5.setId(i + 300);
            textView5.setText(stringArray[i14]);
            textView5.setBackgroundResource(R.drawable.cell_border);
            textView5.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            textView5.setPadding(7, 7, 7, 7);
            textView5.setSingleLine(true);
            textView5.setLayoutParams(layoutParams2);
            TextView textView6 = new TextView(this);
            textView6.setId(i + 400);
            textView6.setText(Integer.toString((int) dArr3[i]));
            textView6.setBackgroundResource(R.drawable.cell_border);
            textView6.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            textView6.setGravity(17);
            textView6.setSingleLine(true);
            textView6.setPadding(7, 7, 7, 7);
            textView6.setLayoutParams(layoutParams3);
            tableRow2.addView(textView4);
            tableRow2.addView(textView5);
            tableRow2.addView(textView6);
            this.tableLayout.addView(tableRow2, new TableLayout.LayoutParams(-1, -2));
            i12 = -1;
            i13 = 20;
            i = i14;
            i11 = -2;
        }
    }
}
