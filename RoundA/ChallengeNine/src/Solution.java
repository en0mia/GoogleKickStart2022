/*
    @author: Simone Nicol <en0mia.dev@gmail.com>
    @created: 11/07/22
    @copyright: Check the repository license.
*/

import java.util.*;

public class Solution {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int tests = in.nextInt();

        for (int i = 0; i < tests; i++) {
            String number = in.next();
            System.out.printf("Case #%d: %s%n", i + 1, solve(number));
        }
    }

    public static String solve(String number) {
        int digitToAdd = findDigitToAdd(number);
        int position = -1;

        if (digitToAdd == 0) {
            return String.format("%c0%s", number.charAt(0), number.substring(1));
        }

        for (int i = 0; i < number.length() && position == -1; i++) {
            if ((number.charAt(i) - '0') > digitToAdd) {
                position = i;
            }
        }

        if (position == -1) {
            return number + digitToAdd;
        }

        if (position == 0) {
            return digitToAdd + number;
        }

        return String.format("%s%d%s", number.substring(0, position), digitToAdd, number.substring(position));
    }

    public static int findDigitToAdd(String number) {
        int sum = Arrays.stream(number.split(""))
                .mapToInt(Integer::parseInt)
                .sum();

        if (sum % 9 == 0) {
            return 0;
        }

        if (sum <= 9) {
            return 9 - sum;
        }

        return 9 - (sum % 9);
    }
}
