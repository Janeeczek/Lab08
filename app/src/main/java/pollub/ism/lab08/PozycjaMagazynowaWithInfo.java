package pollub.ism.lab08;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class PozycjaMagazynowaWithInfo {
    @Embedded
    public PozycjaMagazynowa pozycjaMagazynowa;

    @Relation(parentColumn = "_id", entityColumn = "POZYCJAMAGAZYNOWA_ID", entity = InfoPozycjaMagazynowa.class)
    public List<PozycjaMagazynowaWithInfo> infos;
}
