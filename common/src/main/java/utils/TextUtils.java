package utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TextUtils {

    /**
     * Current method will return subarray.
     */
    public static String[] getPartition(int startOf, int limit, String[] source) {
        String[] partition = new String[limit];

        if (startOf + limit > source.length) {
            limit = source.length - startOf;
        }

        if (limit >= 0) {
            System.arraycopy(source, startOf, partition, 0, limit);
        }

        return partition;
    }

    /**
     * Method adds to each line from the {@link InputStream} its ordinal number.
     * Example output: <code>[1:Some string, 2:Some next string]</code>
     */
    public static List<String> buildNumberedLinesArray(InputStream resourceAsStream) {
        final AtomicInteger counter = new AtomicInteger();
        return new BufferedReader(new InputStreamReader(resourceAsStream))
                .lines()
                .map(line -> counter.incrementAndGet() + ":" + line)
                .collect(Collectors.toList());
    }

    /**
     * The method removes from a line its ordinal number.
     */
    public static String buildUnnumberedLine(String line){
        return Arrays.stream(line.split(":")).skip(1).collect(Collectors.joining());
    }
}
