package pollub.ism.lab08;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import pollub.ism.lab08.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ArrayAdapter<CharSequence> adapter;

    private String wybraneWarzywoNazwa = null;
    private Integer wybraneWarzywoIlosc = null;
    private String wybraneWarzywoCzas = null;

    public enum OperacjaMagazynowa {SKLADUJ, WYDAJ};

    private BazaMagazynowa bazaDanych;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter = ArrayAdapter.createFromResource(this, R.array.Asortyment, android.R.layout.simple_dropdown_item_1line);
        binding.spinner.setAdapter(adapter);

        bazaDanych = Room.databaseBuilder(getApplicationContext(), BazaMagazynowa.class, BazaMagazynowa.NAZWA_BAZY)
                .allowMainThreadQueries().build();

        if(bazaDanych.pozycjaMagazynowaDAO().size() == 0){
            String[] asortyment = getResources().getStringArray(R.array.Asortyment);
            for(String nazwa : asortyment){
                PozycjaMagazynowa pozycjaMagazynowa = new PozycjaMagazynowa();
                pozycjaMagazynowa.NAME = nazwa; pozycjaMagazynowa.QUANTITY = 0;pozycjaMagazynowa.LASTCHANGETIME = "";pozycjaMagazynowa.HISTORY = "";
                bazaDanych.pozycjaMagazynowaDAO().insert(pozycjaMagazynowa);
            }
        }
        //listener przycisku skladuj
        binding.przyciskSkladuj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zmienStan(OperacjaMagazynowa.SKLADUJ);

            }
        });
        //listener przycisku wydaj
        binding.przyciskWydaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                zmienStan(OperacjaMagazynowa.WYDAJ);
            }
        });
        //listener spinneru
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                wybraneWarzywoNazwa = adapter.getItem(i).toString(); // <---

                aktualizuj();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //Nie będziemy implementować, ale musi być
            }
        });

    }
    private void aktualizuj(){
        String history = bazaDanych.pozycjaMagazynowaDAO().getHistoryByName(wybraneWarzywoNazwa);
        List<String> formattedHistory = StringListConverter.fromString(history);
        wybraneWarzywoIlosc = bazaDanych.pozycjaMagazynowaDAO().findQuantityByName(wybraneWarzywoNazwa);
        wybraneWarzywoCzas = bazaDanych.pozycjaMagazynowaDAO().getLastChangeTimeByName(wybraneWarzywoNazwa);
        binding.tekstStanMagazynu.setText("Stan magazynu dla " + wybraneWarzywoNazwa + " wynosi: " + wybraneWarzywoIlosc);

        binding.tekstJednostka.setText(wybraneWarzywoCzas);
        binding.logText.getEditableText().clear();
        for(String s : formattedHistory) {
            if(!s.isEmpty())
            binding.logText.append(s + "\n");
        }

    }
    private void zmienStan(OperacjaMagazynowa operacja){
        String newHistory = "";
        Integer zmianaIlosci = null, nowaIlosc = null;
        String history = bazaDanych.pozycjaMagazynowaDAO().getHistoryByName(wybraneWarzywoNazwa);
        List<String> formattedHistory = StringListConverter.fromString(history);
        try {
            zmianaIlosci = Integer.parseInt(binding.edycjaIlosc.getText().toString());
        }catch(NumberFormatException ex){
            return;
        }finally {
            binding.edycjaIlosc.setText("");
        }
        DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("dd.MM.yyyy, hh:mm:ss,");
        String czas = dtf1.format( LocalDateTime.now());
        switch (operacja){
            case SKLADUJ:
                nowaIlosc = wybraneWarzywoIlosc + zmianaIlosci;
                newHistory = czas + " " + wybraneWarzywoIlosc + " -> " + nowaIlosc;

                break;
            case WYDAJ:
                nowaIlosc = wybraneWarzywoIlosc - zmianaIlosci;
                newHistory = czas + " " + wybraneWarzywoIlosc + " -> " + nowaIlosc;

                break;
        }

        formattedHistory.add(newHistory);
        bazaDanych.pozycjaMagazynowaDAO().updateQuantityByName(wybraneWarzywoNazwa,nowaIlosc);
        bazaDanych.pozycjaMagazynowaDAO().updateLastChangeTimeByName(wybraneWarzywoNazwa, czas);
        String t = StringListConverter.toString(formattedHistory);
        bazaDanych.pozycjaMagazynowaDAO().updateHistoryByName(wybraneWarzywoNazwa,t);
        aktualizuj();


    }
}