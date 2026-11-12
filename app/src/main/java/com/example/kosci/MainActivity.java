package com.example.kosci;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.SystemBarStyle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final int LICZBA_KOSCI = 5;

    private final int[] obrazyKosci = {
            R.drawable.kosc1, R.drawable.kosc2, R.drawable.kosc3,
            R.drawable.kosc4, R.drawable.kosc5, R.drawable.kosc6
    };
    private final Random random = new Random();

    private ImageView[] kostki;
    private TextView wynikLosowania;
    private TextView wynikGry;
    private int punktyGry = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this, SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT));
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        kostki = new ImageView[]{
                findViewById(R.id.kostka1),
                findViewById(R.id.kostka2),
                findViewById(R.id.kostka3),
                findViewById(R.id.kostka4),
                findViewById(R.id.kostka5)
        };
        wynikLosowania = findViewById(R.id.wynikLosowania);
        wynikGry = findViewById(R.id.wynikGry);
        Button przyciskRzuc = findViewById(R.id.przyciskRzuc);
        Button przyciskResetuj = findViewById(R.id.przyciskResetuj);

        przyciskRzuc.setOnClickListener(v -> rzucKoscmi());
        przyciskResetuj.setOnClickListener(v -> resetujWynik());
    }

    private void rzucKoscmi() {
        int[] oczka = losujOczka(LICZBA_KOSCI);

        for (int i = 0; i < LICZBA_KOSCI; i++) {
            kostki[i].setImageResource(obrazyKosci[oczka[i] - 1]);
            kostki[i].animate().cancel();
            kostki[i].setRotation(0);
            kostki[i].animate().rotation(360).setDuration(350).start();
        }

        int punkty = policzPunkty(oczka);
        punktyGry += punkty;
        wynikLosowania.setText(getString(R.string.wynik_losowania, punkty));
        wynikGry.setText(getString(R.string.wynik_gry, punktyGry));
    }

    private void resetujWynik() {
        for (ImageView kostka : kostki) {
            kostka.animate().cancel();
            kostka.setRotation(0);
            kostka.setImageResource(R.drawable.question);
        }
        punktyGry = 0;
        wynikLosowania.setText(getString(R.string.wynik_losowania, 0));
        wynikGry.setText(getString(R.string.wynik_gry, 0));
    }

    private int[] losujOczka(int liczbaKosci) {
        int[] oczka = new int[liczbaKosci];
        for (int i = 0; i < liczbaKosci; i++) {
            oczka[i] = random.nextInt(6) + 1;
        }
        return oczka;
    }

    private int policzPunkty(int[] oczka) {
        int[] ileRazy = new int[7];
        for (int wartosc : oczka) {
            ileRazy[wartosc]++;
        }

        int punkty = 0;
        for (int wartosc = 1; wartosc <= 6; wartosc++) {
            if (ileRazy[wartosc] >= 2) {
                punkty += wartosc * ileRazy[wartosc];
            }
        }
        return punkty;
    }
}
