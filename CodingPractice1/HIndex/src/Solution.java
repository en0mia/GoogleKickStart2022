/*
    @author: Simone Nicol <en0mia.dev@gmail.com>
    @created: 06/07/22
    @copyright: Check the repository license.
*/

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Get number of test cases in input
        int testCaseCount = scanner.nextInt();
        // Iterate through test cases
        for (int tc = 1; tc <= testCaseCount; ++tc) {
            // Get number of papers for this test case
            int paperCount = scanner.nextInt();
            // Get number of citations for each paper
            int[] citations = new int[paperCount];
            for (int p = 0; p < paperCount; ++p) {
                citations[p] = scanner.nextInt();
            }
            // Print h-index score after each paper in this test case
            System.out.print("Case #" + tc + ":");
            for (int score : getHIndexScore(citations)) {
                System.out.append(" ").print(score);
            }
            System.out.println();
        }
    }

    public static int[] getHIndexScore(int[] citationsPerPaper) {
        int currentHIndex = 0, counter = 0;
        int[] hIndex = new int[citationsPerPaper.length];
        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.naturalOrder());

        for (int citation : citationsPerPaper) {
            if (citation > currentHIndex) {
                pq.add(citation);
            }

            while (true) {
                // Remove all the element <= currentHIndex
                while (!pq.isEmpty() && pq.peek() <= currentHIndex) {
                    pq.remove();
                }

                if (pq.size() < currentHIndex + 1) {
                    break;
                }

                // Increment the current solution
                currentHIndex ++;
            }

            hIndex[counter ++] = currentHIndex;
        }

        return hIndex;
    }

    public static int[] bruteForce(int[] citationsPerPaper) {
        int oldMax = 1, counter = 0;
        int[] hIndex = new int[citationsPerPaper.length];

        for (int i : citationsPerPaper) {
            if (i <= oldMax) {
                hIndex[counter ++] = oldMax;
                continue;
            }

            boolean found = false;
            // The candidates for the new HIndex are the integers between
            // the current index (counting from 1) and the oldMax.
            // Let's check them until we found the maximum valid candidate.
            for (int j = counter + 1; j >= oldMax && !found; j--) {
                if (checkNewHIndex(citationsPerPaper, counter + 1, j)) {
                    hIndex[counter ++] = j;
                    oldMax = j;
                    found = true;
                }
            }

            // No new valid candidate found.
            if (!found) {
                hIndex[counter ++] = oldMax;
            }
        }

        return hIndex;
    }

    /*
     * Check if the lookFor number is a valid HIndex.
     */
    private static boolean checkNewHIndex(int[] citationsPerPaper, int length, int lookFor) {
        return Arrays.stream(citationsPerPaper)
                .limit(length)
                .filter(i -> i >= lookFor)
                .count() >= lookFor;
    }
}
