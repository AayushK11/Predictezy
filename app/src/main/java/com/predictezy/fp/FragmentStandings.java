package com.predictezy.fp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.fragment.app.Fragment;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

/* renamed from: com.predictezy.fp.FragmentStandings */
public class FragmentStandings extends Fragment {
    AdView mAdView;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_standings, viewGroup, false);
        RelativeLayout relativeLayout = (RelativeLayout) inflate.findViewById(R.id.PremierLeague);
        RelativeLayout relativeLayout2 = (RelativeLayout) inflate.findViewById(R.id.LaLiga);
        RelativeLayout relativeLayout3 = (RelativeLayout) inflate.findViewById(R.id.Bundesliga);
        RelativeLayout relativeLayout4 = (RelativeLayout) inflate.findViewById(R.id.SerieA);
        RelativeLayout relativeLayout5 = (RelativeLayout) inflate.findViewById(R.id.Ligue1);
        AdView adView = new AdView(getContext());
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-2924639732611391/8469458984");
        MobileAds.initialize(getContext(), (OnInitializationCompleteListener) new OnInitializationCompleteListener() {
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        this.mAdView = (AdView) inflate.findViewById(R.id.ads);
        this.mAdView.loadAd(new Builder().build());
        relativeLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                FragmentStandings.this.startActivity(new Intent(FragmentStandings.this.getContext(), PremierLeagueStandings.class));
            }
        });
        relativeLayout2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                FragmentStandings.this.startActivity(new Intent(FragmentStandings.this.getContext(), LaLigaStandings.class));
            }
        });
        relativeLayout3.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                FragmentStandings.this.startActivity(new Intent(FragmentStandings.this.getContext(), BundesligaStandings.class));
            }
        });
        relativeLayout4.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                FragmentStandings.this.startActivity(new Intent(FragmentStandings.this.getContext(), SerieAStandings.class));
            }
        });
        relativeLayout5.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                FragmentStandings.this.startActivity(new Intent(FragmentStandings.this.getContext(), Ligue1Standings.class));
            }
        });
        return inflate;
    }
}
