package org.java.benchmark;

public class Adder {
    public static long add(int iterations) {
        long sum = 0L;
        for (int i = 0; i < iterations; i++) {
            for (int j = 0; j < iterations; j++) {
                int s = i + j;
                sum += s;
            }
        }
        return sum;
    }
}
