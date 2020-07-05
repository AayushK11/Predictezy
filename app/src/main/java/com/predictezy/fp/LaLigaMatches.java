package com.predictezy.fp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
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
import java.lang.reflect.Array;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.predictezy.fp.LaLigaMatches */
public class LaLigaMatches extends AppCompatActivity {
    String AwayInputTeam;
    Spinner AwayTeam;
    String HomeInputTeam;
    Spinner HomeTeam;
    TextView awayTeamChances;
    TextView awayTeamOutput;
    TextView awayTeamScore;
    TextView backToHomePage;
    Button buttonPredict;
    Toast calculate;
    RelativeLayout card;
    Toast connect;
    int count = 0;

    /* renamed from: db */
    ArrayList<HashMap<String, String>> db = null;
    TextView drawChances;
    Toast error;
    TextView heading;
    TextView homeTeamChances;
    TextView homeTeamOutput;
    TextView homeTeamScore;
    AdView mAdView;
    RelativeLayout predictions;
    TextView progress;
    ProgressBar progressBar;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.la_liga_matches);
        this.error = Toast.makeText(this, "Poor Network Connection. Could not connect", Toast.LENGTH_SHORT);
        this.connect = Toast.makeText(this, "Connected to the Server", Toast.LENGTH_SHORT);
        this.calculate = Toast.makeText(this, "Calculating Predictions. Please Wait", Toast.LENGTH_SHORT);
        this.HomeTeam = (Spinner) findViewById(R.id.homeTeamSpinner);
        this.AwayTeam = (Spinner) findViewById(R.id.awayTeamSpinner);
        this.buttonPredict = (Button) findViewById(R.id.buttonStartPredictions);
        this.homeTeamOutput = (TextView) findViewById(R.id.homeTeamOutput);
        this.awayTeamOutput = (TextView) findViewById(R.id.awayTeamOutput);
        this.predictions = (RelativeLayout) findViewById(R.id.output);
        this.backToHomePage = (TextView) findViewById(R.id.backButton);
        this.homeTeamScore = (TextView) findViewById(R.id.homeTeamOutputScore);
        this.awayTeamScore = (TextView) findViewById(R.id.awayTeamOutputScore);
        this.homeTeamChances = (TextView) findViewById(R.id.homeTeamChances);
        this.drawChances = (TextView) findViewById(R.id.drawChances);
        this.awayTeamChances = (TextView) findViewById(R.id.awayTeamChances);
        this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
        this.card = (RelativeLayout) findViewById(R.id.cards);
        this.heading = (TextView) findViewById(R.id.heading);
        this.progress = (TextView) findViewById(R.id.pleasewait);
        ArrayAdapter createFromResource = ArrayAdapter.createFromResource(this, R.array.LLTeams, R.layout.spinner_text);
        createFromResource.setDropDownViewResource(R.layout.spinner_dropdown);
        this.HomeTeam.setAdapter(createFromResource);
        this.AwayTeam.setAdapter(createFromResource);
        this.HomeTeam.setPrompt("Select a Home Team");
        this.AwayTeam.setPrompt("Select an Away Team");
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-2924639732611391/6773233939");
        MobileAds.initialize((Context) this, (OnInitializationCompleteListener) new OnInitializationCompleteListener() {
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        this.mAdView = (AdView) findViewById(R.id.ads);
        this.mAdView.loadAd(new Builder().build());
        new Thread() {
            public void run() {
                String str = "null";
                LaLigaMatches LaLigaMatches = LaLigaMatches.this;
                LaLigaMatches.db = LaLigaMatches.getItems();
                while (String.valueOf(LaLigaMatches.db).equals("null") && LaLigaMatches.count<10) {

                    if (String.valueOf(LaLigaMatches.this.db).equals(str) && LaLigaMatches.this.count < 10) {
                        try {
                            sleep(1000);
                            LaLigaMatches.this.count++;
                            System.out.println(LaLigaMatches.this.count);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (String.valueOf(LaLigaMatches.this.db).equals(str)) {
                    LaLigaMatches.this.runOnUiThread(new Runnable() {
                        public void run() {
                            LaLigaMatches.this.error.show();
                            LaLigaMatches.this.startActivity(new Intent(LaLigaMatches.this, HomePage.class));
                        }
                    });
                } else {
                    LaLigaMatches.this.runOnUiThread(new Runnable() {
                        public void run() {
                            LaLigaMatches.this.Visibility();
                        }
                    });
                }
            }
        }.start();
        this.buttonPredict.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                LaLigaMatches LaLigaMatches = LaLigaMatches.this;
                LaLigaMatches.HomeInputTeam = LaLigaMatches.HomeTeam.getSelectedItem().toString();
                LaLigaMatches LaLigaMatches2 = LaLigaMatches.this;
                LaLigaMatches2.AwayInputTeam = LaLigaMatches2.AwayTeam.getSelectedItem().toString();
                String str = "";
                LaLigaMatches.this.homeTeamScore.setText(str);
                LaLigaMatches.this.awayTeamScore.setText(str);
                String str2 = "Invalid Teams Chosen";
                if (LaLigaMatches.this.HomeInputTeam.equals(LaLigaMatches.this.AwayInputTeam)) {
                    LaLigaMatches.this.predictions.setVisibility(View.INVISIBLE);
                    Toast.makeText(LaLigaMatches.this, str2, Toast.LENGTH_SHORT).show();
                    return;
                }
                String str3 = "Select A Team";
                if (LaLigaMatches.this.HomeInputTeam.equals(str3) || LaLigaMatches.this.AwayInputTeam.equals(str3)) {
                    Toast.makeText(LaLigaMatches.this, str2, Toast.LENGTH_SHORT).show();
                    LaLigaMatches.this.predictions.setVisibility(View.INVISIBLE);
                    return;
                }
                else {
                    LaLigaMatches.this.predictions.setVisibility(View.VISIBLE);
                    LaLigaMatches LaLigaMatches3 = LaLigaMatches.this;
                    LaLigaMatches3.startPredictions(LaLigaMatches3.HomeInputTeam, LaLigaMatches.this.AwayInputTeam);
                }
            }
        });
        this.backToHomePage.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                LaLigaMatches.this.startActivity(new Intent(LaLigaMatches.this, HomePage.class));
            }
        });
        this.heading.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                LaLigaMatches.this.startActivity(new Intent(LaLigaMatches.this, HomePage.class));
            }
        });
    }

    public void Visibility() {
        this.progressBar.setVisibility(View.GONE);
        this.progress.setVisibility(View.INVISIBLE);
        this.card.setVisibility(View.VISIBLE);
        this.heading.setVisibility(View.VISIBLE);
        this.predictions.setVisibility(View.INVISIBLE);
        this.connect.show();
    }

    /* access modifiers changed from: private */
    public ArrayList<HashMap<String, String>> getItems() {
        StringRequest stringRequest = new StringRequest(0, "https://script.google.com/macros/s/AKfycbyTOaPkjc3NJ2JRE6onn72x7vqvV_mAL9Fzh3aNm42O4OADFf8/exec?action=getItems", new Listener<String>() {
            public void onResponse(String str) {
                LaLigaMatches LaLigaMatches = LaLigaMatches.this;
                LaLigaMatches.db = LaLigaMatches.parseItems(str);
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
        String str2 = "awayDefence";
        String str3 = "awayAttack";
        String str4 = "homeDefence";
        String str5 = "homeAttack";
        String str6 = "team";
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

    /* access modifiers changed from: private */
    public void startPredictions(String str, String str2) {
        HashMap hashMap = (HashMap) this.db.get(0);
        String[] strArr = new String[2];
        String[] strArr2 = new String[2];
        String[] strArr3 = new String[2];
        Iterator it = this.db.iterator();
        while (it.hasNext()) {
            HashMap hashMap2 = (HashMap) it.next();
            String str3 = "team";
            String str4 = "homeDefence";
            String str5 = "homeAttack";
            if (Objects.equals(hashMap2.get(str3), "All Avg")) {
                strArr[0] = (String) hashMap2.get(str5);
                strArr[1] = (String) hashMap2.get(str4);
            } else if (Objects.equals(hashMap2.get(str3), str)) {
                strArr2[0] = (String) hashMap2.get(str5);
                strArr2[1] = (String) hashMap2.get(str4);
            } else if (Objects.equals(hashMap2.get(str3), str2)) {
                strArr3[0] = (String) hashMap2.get("awayAttack");
                strArr3[1] = (String) hashMap2.get("awayDefence");
            }
        }
        calculateScore(strArr, strArr2, strArr3);
    }

    private void calculateScore(String[] strArr, String[] strArr2, String[] strArr3) {
        int i = 1;
        double parseDouble = Double.parseDouble(strArr2[0]) * Double.parseDouble(strArr3[1]) * Double.parseDouble(strArr[0]);
        double parseDouble2 = Double.parseDouble(strArr2[1]) * Double.parseDouble(strArr3[0]) * Double.parseDouble(strArr[1]);
        int i2 = 6;
        double[] dArr = new double[6];
        double[] dArr2 = new double[6];
        double[][] dArr3 = (double[][]) Array.newInstance(double.class, new int[]{6, 6});
        int i3 = 0;
        while (true) {
            double d = 1.0d;
            if (i3 >= i2) {
                break;
            }
            int i4 = i;
            while (i4 <= i3) {
                d *= (double) i4;
                i4++;
                parseDouble2 = parseDouble2;
            }
            double d2 = parseDouble2;
            double[] dArr4 = dArr;
            double d3 = (double) i3;
            dArr4[i3] = (Math.exp(-parseDouble) * Math.pow(parseDouble, d3)) / d;
            double d4 = d2;
            double d5 = parseDouble;
            dArr2[i3] = (Math.exp(-d4) * Math.pow(d4, d3)) / d;
            i3++;
            dArr = dArr4;
            parseDouble = d5;
            i2 = 6;
            parseDouble2 = d4;
            i = 1;
        }
        double[] dArr5 = dArr;
        int i5 = 0;
        double d6 = 0.0d;
        double d7 = 0.0d;
        double d8 = 0.0d;
        while (true) {
            if (i5 >= 6) {
                break;
            }
            int i6 = 0;
            for (int i7 = 6; i6 < i7; i7 = 6) {
                dArr3[i5][i6] = dArr5[i5] * dArr2[i6];
                if (d6 < dArr3[i5][i6]) {
                    double d9 = (double) i6;
                    double d10 = (double) i5;
                    d6 = dArr3[i5][i6];
                    d8 = d9;
                    d7 = d10;
                }
                i6++;
            }
            i5++;
        }
        double d11 = 0.0d;
        double d12 = 0.0d;
        double d13 = 0.0d;
        for (int i8 = 0; i8 < 6; i8++) {
            for (int i9 = 0; i9 < 6; i9++) {
                if (i8 == i9) {
                    d13 += dArr3[i8][i9];
                } else if (i8 > i9) {
                    d11 += dArr3[i8][i9];
                } else if (i9 > i8) {
                    d12 += dArr3[i8][i9];
                }
            }
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        decimalFormat.setRoundingMode(RoundingMode.UP);
        double d14 = d11 * 100.0d;
        double d15 = d12 * 100.0d;
        double d16 = d13 * 100.0d;
        String valueOf = String.valueOf((int) d7);
        String valueOf2 = String.valueOf((int) d8);
        StringBuilder sb = new StringBuilder();
        sb.append(decimalFormat.format(d14));
        String str = "%";
        sb.append(str);
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(decimalFormat.format(d16));
        sb3.append(str);
        String sb4 = sb3.toString();
        StringBuilder sb5 = new StringBuilder();
        sb5.append(decimalFormat.format(d15));
        sb5.append(str);
        String sb6 = sb5.toString();
        String[] stringArray = getResources().getStringArray(R.array.LLTeams);
        String[] stringArray2 = getResources().getStringArray(R.array.LLTeamsShortHand);
        for (int i10 = 1; i10 < stringArray.length; i10++) {
            if (this.HomeInputTeam.equals(stringArray[i10])) {
                this.HomeInputTeam = stringArray2[i10 - 1];
                System.out.println(this.HomeInputTeam);
            }
            if (this.AwayInputTeam.equals(stringArray[i10])) {
                this.AwayInputTeam = stringArray2[i10 - 1];
                System.out.println(this.AwayInputTeam);
            }
        }
        this.homeTeamScore.setText(valueOf);
        this.awayTeamScore.setText(valueOf2);
        this.homeTeamOutput.setText(this.HomeInputTeam);
        this.awayTeamOutput.setText(this.AwayInputTeam);
        this.homeTeamChances.setText(sb2);
        this.drawChances.setText(sb4);
        this.awayTeamChances.setText(sb6);
    }
}
