package de.akquinet.fp.functionalloops;

import java.util.function.Function;
import java.util.stream.DoubleStream;

public class IntegrationJavaStreams {

    static double integrate(double start, double end, int precision, Function<Double, Double> f) {
        double step = (end - start) / (double) precision;
        return DoubleStream
                .iterate(start,
                        x -> x < end,
                        x -> x + step
                )
                .map(x -> f.apply(x) * step)
                .sum();
    }
}
