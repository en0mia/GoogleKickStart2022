/*
    @author: Simone Nicol <en0mia.dev@gmail.com>
    @created: 08/07/22
    @copyright: Check the repository license.
*/

import java.util.*;

public class Solution {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int testCases = in.nextInt();
        for (int testCase = 1; testCase <= testCases; testCase++) {
            int numberOfFriends = in.nextInt();
            int numberOfForibbenTeas = in.nextInt();
            int optionsOffered = in.nextInt();
            int[][] friendsOrders = new int[numberOfFriends][optionsOffered];
            for (int friend = 0; friend < numberOfFriends; friend++) {
                String currentFriendOrder = in.next().trim();
                for (int option = 0; option < optionsOffered; option++) {
                    friendsOrders[friend][option] =
                            Character.getNumericValue(currentFriendOrder.charAt(option));
                }
            }
            int[][] forbiddenOrders = new int[numberOfForibbenTeas][optionsOffered];
            for (int forbiddenIndex = 0; forbiddenIndex < numberOfForibbenTeas; forbiddenIndex++) {
                String currentForbiddenTeaOrder = in.next().trim();
                for (int option = 0; option < optionsOffered; option++) {
                    forbiddenOrders[forbiddenIndex][option] =
                            Character.getNumericValue(currentForbiddenTeaOrder.charAt(option));
                }
            }
            int ans = findSmallestNumberOfComplaints(friendsOrders, forbiddenOrders);
            System.out.println("Case #" + testCase + ": " + ans);
        }
    }

    static int findSmallestNumberOfComplaints(int[][] friendsOrders, int[][] forbiddenOrders) {
        int smallestNumberOfComplaints = 0;
        final int p = friendsOrders[0].length;
        final int n = friendsOrders.length;

        Set<String> forbidden = new HashSet<>();
        Set<String> visited = new HashSet<>();
        PriorityQueue<Preference> pq = new PriorityQueue<>();

        // Array containing the number of 1s for each column.
        int[] counts = new int[p];

        for (int[] rows : friendsOrders) {
            for (int c = 0; c < rows.length; c++) {
                counts[c] += rows[c];
            }
        }

        // Prepare the forbidden set
        StringBuilder sb;
        for (int[] rows : forbiddenOrders) {
            sb = new StringBuilder();
            Arrays.stream(rows)
                    .forEach(sb::append);
            forbidden.add(sb.toString());
        }

        int minSum = 0;
        sb = new StringBuilder();

        for (int i = 0; i < counts.length; i++) {
            int value = counts[i];
            // More 1s
            if (value > (n - value)) {
                sb.append(1);
                minSum += n - value;
            } else {
                sb.append(0);
                minSum += value;
            }
        }

        Preference current = new Preference(sb.toString(), minSum);
        Preference next;

        pq.add(current);
        while (!pq.isEmpty()) {
            current = pq.poll();
            visited.add(current.string);

            if (!forbidden.contains(current.string)) {
                return current.weight;
            }

            // For each bit, change it and push the result to the queue if we didn't visit it in the past.
            for (int i = 0; i < current.string.length(); i++) {
                if (current.string.charAt(i) == '1') {
                    next = new Preference(
                            String.format("%s%d%s",
                                    current.string.substring(0, i),
                                    0,
                                    current.string.substring(i+1)),
                            current.weight + counts[i] - (n - counts[i]));
                } else {
                    next = new Preference(
                            String.format("%s%d%s",
                                    current.string.substring(0, i),
                                    1,
                                    current.string.substring(i+1)),
                            current.weight + (n - counts[i]) - counts[i]);
                }
                if (!visited.contains(next.string)) {
                    pq.add(next);
                }
            }
        }
        return smallestNumberOfComplaints;
    }

    static class Preference implements Comparable<Preference>{
        String string;
        int weight;

        public Preference(String string, int weight) {
            this.string = string;
            this.weight = weight;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Preference that = (Preference) o;

            return string.equals(that.string);
        }

        @Override
        public int hashCode() {
            return string.hashCode();
        }

        @Override
        public int compareTo(Preference o) {
            return this.weight - o.weight;
        }
    }
}
