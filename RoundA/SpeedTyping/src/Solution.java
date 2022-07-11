/*
    @author: Simone Nicol <en0mia.dev@gmail.com>
    @created: 11/07/22
    @copyright: Check the repository license.
*/

import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        String produced, input;
        int tests;
        Scanner in = new Scanner(System.in);
        tests = in.nextInt();

        for (int i = 0; i < tests; i++) {
            input = in.next();
            produced = in.next();
            int result = solve(input, produced);
            System.out.printf("Case #%d: %s%n", i + 1, result >= 0 ? Integer.toString(result) : "IMPOSSIBLE");
        }
    }

    public static int solve(String input, String produced) {
        int inputIndex = 0;
        int result = 0;

        if (produced.length() < input.length()) {
            return -1;
        }
        
        if (input.equals(produced)) {
            return 0;
        }

        for (int i = 0; i < produced.length(); i++) {
            // We reached the end, we have to remove length() - i chars to match.
            if (inputIndex == input.length()) {
                result += produced.length() - i;
                break;
            }
            if (produced.charAt(i) == input.charAt(inputIndex)) {
                inputIndex ++;
                continue;
            }

            result ++;
        }

        // We didn't reach the end
        if (inputIndex != input.length()) {
            return -1;
        }

        return result;
    }
}
