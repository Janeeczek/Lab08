package pollub.ism.lab08;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "InfoPozycjaMagazynowa")
public class InfoPozycjaMagazynowa {
    @PrimaryKey(autoGenerate = true)
    public int _id;
    public String LOG;
    public int POZYCJAMAGAZYNOWA_ID;

    public InfoPozycjaMagazynowa(String LOG, int POZYCJAMAGAZYNOWA_ID) {
        this.LOG = LOG;
        this.POZYCJAMAGAZYNOWA_ID = POZYCJAMAGAZYNOWA_ID;
    }
}