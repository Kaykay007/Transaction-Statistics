package com.korede.transactionstatistics;

import com.korede.transactionstatistics.model.AlgorithmSolutions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class TransactionStatisticsApplication {

    public static void main(String[] args) {
        AlgorithmSolutions algo = new AlgorithmSolutions();
        SpringApplication.run(TransactionStatisticsApplication.class, args);
        int[][] intervals = {{1, 3}, {2, 6}, {8, 10}, {15, 18}};
        List<int[]> mergedIntervals = algo.mergeIntervals(intervals);
        System.out.println("Merged Intervals:");
        for (int[] interval : mergedIntervals) {
            System.out.println("[" + interval[0] + ", " + interval[1] + "]");
        }

        // Test maxSubarrayXOR
        int[] arr = {1, 2, 3, 4};
        int maxXor = algo.maxSubarrayXOR(arr);
        System.out.println("Maximum XOR of subarray: " + maxXor);
    }


}
