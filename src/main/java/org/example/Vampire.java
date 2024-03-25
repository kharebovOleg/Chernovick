package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * todo разобраться как работает этот метод
 */
public class Vampire {
    public static void findVampire() {
        for (int x = 10; x < 100; x++) {
            String sx = String.valueOf(x);
            for (int y = x; y < 100; y++) {
                int v = x * y;
                String sy = String.valueOf(y);
                String sv = String.valueOf(v);
                if (sortVampire(sx + sy).equals(sortVampire(sv))) {
                    System.out.printf("%d * %d = %d%n", x, y, v);
                }
            }
        }
    }

    private static List<Character> sortVampire(String v) {
        List<Character> vc = new ArrayList<Character>();
        for (int j = 0; j < v.length(); j++) {
            vc.add(v.charAt(j));
        }
        Collections.sort(vc);
        return vc;
    }
}
