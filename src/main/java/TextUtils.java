import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TextUtils {

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
}
