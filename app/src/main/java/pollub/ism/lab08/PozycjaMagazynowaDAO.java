package pollub.ism.lab08;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Dao
public interface PozycjaMagazynowaDAO {

    @Insert  //Automatyczna kwerenda wystarczy
    public void insert(PozycjaMagazynowa pozycja);

    @Update //Automatyczna kwerenda wystarczy
    void update(PozycjaMagazynowa pozycja);

    @Query("SELECT QUANTITY FROM Warzywniak WHERE NAME= :wybraneWarzywoNazwa") //Nasza kwerenda
    int findQuantityByName(String wybraneWarzywoNazwa);
    @Query("SELECT LASTCHANGETIME FROM Warzywniak WHERE NAME= :wybraneWarzywoNazwa") //Nasza kwerenda
    String getLastChangeTimeByName(String wybraneWarzywoNazwa);
    @Query("UPDATE Warzywniak SET QUANTITY = :wybraneWarzywoNowaIlosc WHERE NAME= :wybraneWarzywoNazwa")
    void updateQuantityByName(String wybraneWarzywoNazwa, int wybraneWarzywoNowaIlosc);
    @Query("UPDATE Warzywniak SET LASTCHANGETIME = :time WHERE NAME= :wybraneWarzywoNazwa")
    void updateLastChangeTimeByName(String wybraneWarzywoNazwa, String time);
    @Query("UPDATE Warzywniak SET HISTORY = :newHistory WHERE NAME= :wybraneWarzywoNazwa")
    void updateHistoryByName(String wybraneWarzywoNazwa, String newHistory);

    @Query("SELECT HISTORY FROM Warzywniak WHERE NAME= :wybraneWarzywoNazwa")
    String getHistoryByName(String wybraneWarzywoNazwa);

    @Query("SELECT COUNT(*) FROM Warzywniak") //Ile jest rekordów w tabeli
    int size();
}