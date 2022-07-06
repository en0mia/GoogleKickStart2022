/*
    @author: Simone Nicol <en0mia.dev@gmail.com>
    @created: 06/07/22
    @copyright: Check the repository license.
*/

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Solution {
    public static void main (String[] args) {
        Scanner in = new Scanner(System.in);

        int T = in.nextInt();

        for (int t = 1; t <= T; ++t) {
            String kingdom = in.next();
            System.out.println(
                    "Case #" + t + ": " + kingdom + " is ruled by " + getRuler(kingdom) + ".");
        }
    }

    private static String getRuler(String kingdom) {
        Set<Character> vowels = new HashSet<>(Arrays.asList('a', 'e', 'i', 'o', 'u'));
        Character last = kingdom.toLowerCase().charAt(kingdom.length() - 1);

        if (last.equals('y')) {
            return "nobody";
        }

        if (vowels.contains(last)) {
            return "Alice";
        }

        return "Bob";
    }
}
