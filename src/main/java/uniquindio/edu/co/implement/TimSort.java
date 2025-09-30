package uniquindio.edu.co.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
@Service
@RequiredArgsConstructor
public class TimSortExample {
    public static void timSort(int[] arr) {
        // TimSort está implementado dentro de Arrays.sort en Java
        Arrays.sort(arr); 
    }
}
