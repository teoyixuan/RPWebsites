package sg.edu.rp.c346.rpwebsites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    Spinner spnCat, spnSub;
    Button btn;
    TextView tvCat, tvSub;
    ArrayList<String> al;
    ArrayAdapter<String> aa;
    WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spnCat = findViewById(R.id.spinner1);
        spnSub = findViewById(R.id.spinner2);
        btn = findViewById(R.id.buttonGo);
        wv = findViewById(R.id.webView);
        tvCat = findViewById(R.id.textViewCategory);
        tvSub = findViewById(R.id.textViewSub);

        wv.setWebViewClient(new WebViewClient());

        al = new ArrayList<>();
        aa = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, al);

        WebSettings ws = wv.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setDisplayZoomControls(true);
        spnCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        al.clear();
                        String[] strCat = getResources().getStringArray(R.array.RP_sub);
                        al.addAll(Arrays.asList(strCat));
                        spnSub.setAdapter(aa);
                        spnSub.setSelection(0);
                        break;
                    case 1:
                        al.clear();
                        String[] strCat1 = getResources().getStringArray(R.array.SOI_sub);
                        al.addAll(Arrays.asList(strCat1));
                        spnSub.setAdapter(aa);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String[][] sites = {
                        {
                            "https://www.grab.com/sg/",
                                "https://www.rp.edu.sg/student-life",
                        },
                        {
                                "https://www.rp.edu.sg/soi/full-time-diplomas/details/r47",
                                "https://www.rp.edu.sg/soi/full-time-diplomas/details/r12",
                        }
                };
                tvCat.setVisibility(View.GONE);
                tvSub.setVisibility(View.GONE);
                spnSub.setVisibility(View.GONE);
                spnCat.setVisibility(View.GONE);
                btn.setVisibility(View.GONE);
                String url = sites[spnCat.getSelectedItemPosition()][spnSub.getSelectedItemPosition()];
                wv.loadUrl(url);

//                int posCat = spnCat.getSelectedItemPosition();
//                int posSub = spnSub.getSelectedItemPosition();
//                if(posCat == 0){
//                    if(posSub == 0){
//                        wv.loadUrl("https://www.rp.edu.sg/");
//                    }
//                    else{
//                        wv.loadUrl("https://www.rp.edu.sg/student-life");
//                    }
//                }
//                else{
//                    if(posSub == 0){
//                        wv.loadUrl("https://www.rp.edu.sg/soi/full-time-diplomas/details/r47");
//                    }
//                    else{
//                        wv.loadUrl("https://www.rp.edu.sg/soi/full-time-diplomas/details/r12");
//                    }
//                }
            }
        });

    }
    @Override
    public void onBackPressed(){
        tvCat.setVisibility(View.VISIBLE);
        tvSub.setVisibility(View.VISIBLE);
        spnSub.setVisibility(View.VISIBLE);
        spnCat.setVisibility(View.VISIBLE);
        btn.setVisibility(View.VISIBLE);
        wv.setVisibility(View.GONE);
    }

    @Override
    public void onPause() {
        super.onPause();

        int spn1 = spnCat.getSelectedItemPosition();
        int spn2 = spnSub.getSelectedItemPosition();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEdit = prefs.edit();

        prefEdit.putInt("spn1", spn1);
        prefEdit.putInt("spn2", spn2);

        prefEdit.commit();

    }

    public void onResume() {
        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        int msg = prefs.getInt("spn1", 0);
        int msg2 = prefs.getInt("spn2", 0);
        spnCat.setSelection(msg);
        spnSub.setSelection(msg2);
    }
}
