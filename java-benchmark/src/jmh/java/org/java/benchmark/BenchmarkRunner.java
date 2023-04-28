package org.java.benchmark;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

@State(Scope.Benchmark)
public class BenchmarkRunner {

    @Benchmark
    public void measure(Blackhole blackhole) {
        blackhole.consume(Adder.add(1000));
    }
}