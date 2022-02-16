import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

public class AggregationResultService implements AggregationService {

    @Override
    public void merge(Map<String, Set<DataSearchInfo>> source, ConcurrentMap<String, Set<DataSearchInfo>> target) {
        source.forEach((key, value) -> {
            target.computeIfPresent(key, (key1, value1) -> merge(value1, value));
            target.putIfAbsent(key, value);
        });
    }

    private Set<DataSearchInfo> merge(Set<DataSearchInfo> target, Set<DataSearchInfo> value) {
        target.addAll(value);
        return target;
    }
}
