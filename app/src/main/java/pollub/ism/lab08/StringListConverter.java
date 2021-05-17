package pollub.ism.lab08;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class StringListConverter {

    public static List<String> fromString(String value) {
        String[] strings = value.split(";");
        List<String> list = new ArrayList<>();
        for (String ss : strings) {
            if(!ss.isEmpty()) list.add(ss);
        }
        return list;
    }


    public static String toString(List<String> value) {
        if(value == null) {
            return "";
        }
        StringBuilder output = new StringBuilder();
        for(String s : value) output.append(";").append(s);
        return output.toString();
    }
}
