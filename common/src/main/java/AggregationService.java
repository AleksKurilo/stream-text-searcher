import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

public interface AggregationService {

    void merge(Map<String, Set<DataSearchInfo>> source, ConcurrentMap<String, Set<DataSearchInfo>> target);
}
