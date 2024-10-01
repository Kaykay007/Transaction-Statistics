package com.korede.transactionstatistics.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AlgorithmSolutions {

    /**
     * Merges overlapping intervals in a given list of intervals.
     *
     * @param intervals List of intervals represented as a 2D array (start, end).
     * @return Merged list of intervals.
     */
    public List<int[]> mergeIntervals(int[][] intervals) {
        List<int[]> merged = new ArrayList<>();

        for (int[] interval : intervals) {
            // If the merged list is empty or the current interval does not overlap with the last merged one
            if (merged.isEmpty() || merged.get(merged.size() - 1)[1] < interval[0]) {
                merged.add(interval);
            } else {
                // There is an overlap, merge the current and previous intervals
                merged.get(merged.size() - 1)[1] = Math.max(merged.get(merged.size() - 1)[1], interval[1]);
            }
        }
        return merged;
    }

    /**
     * Finds the maximum XOR of any subarray within the given array.
     *
     * @param arr The input array.
     * @return The maximum XOR found.
     */
    public int maxSubarrayXOR(int[] arr) {
        int n = arr.length;
        int maxXOR = 0;
        int[] prefixXOR = new int[n + 1];

        // Calculate prefix XOR
        for (int i = 1; i <= n; i++) {
            prefixXOR[i] = prefixXOR[i - 1] ^ arr[i - 1];
        }

        Set<Integer> prefixes = new HashSet<>();

        for (int i = 0; i <= n; i++) {
            for (int p : prefixes) {
                maxXOR = Math.max(maxXOR, prefixXOR[i] ^ p);
            }
            prefixes.add(prefixXOR[i]);
        }

        return maxXOR;
    }
}