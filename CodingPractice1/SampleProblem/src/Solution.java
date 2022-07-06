/*
    @author: Simone Nicol <en0mia.dev@gmail.com>
    @created: 06/07/22
    @copyright: Check the repository license.
*/

import java.util.ArrayList;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        int tests, bags, guys;
        Scanner input = new Scanner(System.in);

        tests = input.nextInt();
        ArrayList<Integer> bagsList = new ArrayList<>();

        for (int i = 0; i < tests; i++) {
            bags = input.nextInt();
            guys = input.nextInt();

            for (int j = 0; j < bags; j++) {
                bagsList.add(input.nextInt());
            }

            System.out.printf("Case #%d: %d%n", i+1, solve(bagsList, guys));
            bagsList.clear();
        }
    }

    private static int solve(ArrayList<Integer> bags, int guys) {
        return bags.stream().mapToInt(n -> n).sum() % guys;
    }
}
