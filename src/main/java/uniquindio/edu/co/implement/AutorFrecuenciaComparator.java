package uniquindio.edu.co.implement;

import java.util.Comparator;
import java.util.Map;

public class AutorFrecuenciaComparator implements Comparator<Map.Entry<String, Integer>> {
    @Override
    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
        int res = o1.getValue().compareTo(o2.getValue());
        if (res == 0){
            return o1.getKey().compareTo(o2.getKey());
        }
        return res;
    }

}
