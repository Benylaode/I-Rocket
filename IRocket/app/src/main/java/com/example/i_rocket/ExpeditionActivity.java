package com.example.i_rocket;

import android.annotation.SuppressLint;
import android.content.Context;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import android.widget.Button;
import android.widget.ImageView;

import android.widget.TextView;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.example.i_rocket.fragment.OfflineFragment;
import com.squareup.picasso.Picasso;
import java.util.List;


public class ExpeditionActivity extends AppCompatActivity {

    private Expedition expedition;
    private List<Expedition> expeditions;
    private Boolean tersimpan =false;
    private final Context context = this ;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expedition);
        
        Intent intent = getIntent();
        expedition = intent.getParcelableExtra("EXP");
        String tag_frg = intent.getStringExtra("fragment");
        String home = "HOME";
        String search = "SEARCH";
        String offline = "OFFLINE";
        if(home.equals(tag_frg)){
            expeditions = Tampil.results;
        }else if(search.equals(tag_frg)){
            expeditions = Tampil.results_src;
        } else if (offline.equals(tag_frg)) {
            expeditions = DataSource.expeditions_off;
        }

        // Initialize Views
        ImageView photoIss = findViewById(R.id.photo_iss);
        TextView masthead = findViewById(R.id.masthead);
        TextView headline = findViewById(R.id.headline);
        TextView captionIss = findViewById(R.id.caption_iss);
        TextView byline = findViewById(R.id.byline);
        TextView lead = findViewById(R.id.lead);
        TextView bodyText = findViewById(R.id.body_text);
        ImageView patchMisi = findViewById(R.id.patch_misi);
        TextView captionPatch = findViewById(R.id.caption_patch);
        TextView pullQuote = findViewById(R.id.pull_quote);
        Button saveButton = findViewById(R.id.save_button);
        Button google = findViewById(R.id.google);


        if(expedition.getEnd() == null ){
            expedition.setEnd("dalam waktu yang blm diketahui");
        }
        if(expedition == null){
            Log.d("TAG expeditionactivity", "onCreate: salah");
        }
        for (Expedition ex : DataSource.expeditions_off){
            if (ex.getId() == expedition.getId()){
                saveButton.setText("Hapus Koran");
                tersimpan = true;
            }
        }
        for (Expedition ex : expeditions){
            if (ex.getId() == expedition.getId()){
                expedition = ex;
            }
        }
        if(expedition.getEnd() == null ){
            expedition.setEnd("dalam waktu yang blm diketahui");
        }
        masthead.setText(expedition.getName());
        if(expedition.getMissionPatches() != null){
            if(!expedition.getMissionPatches().isEmpty()){
                Picasso.get().load(expedition.getMissionPatches().get(0).getImageUrl()).into(patchMisi);
                captionPatch.setText("Patch misi "+ expedition.getMissionPatches().get(0).getName()+" oleh "+ expedition.getMissionPatches().get(0).getAgency().getName());
            }else{
                patchMisi.setMinimumHeight(150);
            }
        }else{
            patchMisi.setMinimumHeight(150);
        }

        StringBuilder daftar_spw = new StringBuilder();
        int no = 1;
        if(expedition.getSpacewalks() != null){
            for (Spacewalk spacewalk : expedition.getSpacewalks() ){
                String spw = spacewalk.getName();
                daftar_spw.append("\n").append(no).append(" ").append(spw);
                no++;
            }
        }else {
            daftar_spw = new StringBuilder("tidak ada data");
        }
        if(expedition.getSpacestation() != null){
            Picasso.get().load(expedition.getSpacestation().getImageUrl()).into(photoIss);
            Log.d("GAMBAR", "onCreate: " + expedition.getSpacestation().getImageUrl());
            headline.setText("Ekspedisi " + expedition.getName() + " ke Stasiun "+expedition.getSpacestation().getName()+": Misi dan Aktivitas Terbaru");
            lead.setText("Ekspedisi " + expedition.getName() + " ke Stasiun Luar Angkasa "+expedition.getSpacestation().getName()+" telah dimulai pada " + expedition.getStart() + " dan akan berlangsung hingga " + expedition.getEnd() + ". Dalam misi ini, berbagai kegiatan penting termasuk spacewalks telah dilakukan untuk mendukung operasional SpaceStation.");
            captionIss.setText("Gambar Stasiun Luar " + expedition.getSpacestation().getName());
            byline.setText("Author: " + expedition.getSpacestation().getName());
            bodyText.setText("Misi "+expedition.getName()+ "\n" +
                    " merupakan salah satu misi penting yang berlangsung di "+ expedition.getSpacestation().getName()+". Selama misi ini, berbagai kegiatan ilmiah dan teknis telah dilakukan untuk memastikan kelangsungan operasional "+expedition.getSpacestation().getName()+".\n" +
                    "\nDetail Misi dan Durasi\nMisi ini dimulai pada ("+expedition.getStart().substring(0,10)+") dan dijadwalkan berakhir pada  ("+expedition.getEnd().substring(0,10)+"). Selama misi ini, para astronot telah melakukan berbagai eksperimen ilmiah dan perbaikan teknis di "+expedition.getSpacestation().getName()+".\n" +
                    "\nSpacewalks :" +
                    daftar_spw);

            pullQuote.setText("Ekspedisi telah membawa banyak inovasi dan perbaikan teknis penting bagi operasional stasiun.");

        }
        if(!tersimpan){
            Log.d("Tersimpan", "onCreate: ");
            saveButton.setOnClickListener(v -> {
                DataSource.addExpedition(this, expedition.getId(), expedition.getName(), expedition.getUrl(), expedition.getStart(), expedition.getEnd(), expedition.getSpacestation().getName(), expedition.getSpacestation().getImageUrl());
                DataSource.getData(this);
                saveButton.setText("Hapus Koran");
                tersimpan = true;
            });
        }else {
            saveButton.setOnClickListener(v -> {
                Log.d("button", "onCreate: tidak tersave");
                DataSource.removeExpedition(this, expedition.getId());
                DataSource.getData(this);
                saveButton.setText("Simpan Koran");
                tersimpan = false;
            });
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Konfirmasi");
        builder.setMessage("Apakah Anda yakin menghapus");
        builder.setPositiveButton("Ya", (dialog, which) -> {
            DataSource.removeExpedition(context, expedition.getId());
            DataSource.getData(context);
            saveButton.setText("Simpan Koran");
            OfflineFragment fragment = new OfflineFragment();
            fragment.refresh();
            tersimpan = false;
        });
        builder.setNegativeButton("Tidak", (dialog, which) -> dialog.cancel());
        saveButton.setOnClickListener(v -> {
            if (!tersimpan) {
                Log.d("Tersimpan", "onCreate: ");
                DataSource.addExpedition(this, expedition.getId(), expedition.getName(), expedition.getUrl(), expedition.getStart(), expedition.getEnd(), expedition.getSpacestation().getName(), expedition.getSpacestation().getImageUrl());
                DataSource.getData(this);
                saveButton.setText("Hapus Koran");
                tersimpan = true;
            } else {
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
        google.setOnClickListener(v -> {
            Intent intent1 = new Intent(Intent.ACTION_VIEW);
            intent1.setData(Uri.parse("https://www.google.com/search?q=" + expedition.getName()));
            startActivity(intent1);
        });

    }
}
