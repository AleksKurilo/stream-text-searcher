package records.validator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class AggregatorServiceImpl implements AggregatorService {

    @Override
    public Map<String, Set<DataSearchInfo>> merge(Map<String, Set<DataSearchInfo>>... maps) {
        Map<String, Set<DataSearchInfo>> result = new HashMap<>();
        for (Map<String, Set<DataSearchInfo>> r : maps) {
            r.forEach((key, value) -> {
                result.computeIfPresent(key, (key1, value1) -> add(value1, value));
                result.putIfAbsent(key, value);
            });
        }

        return result;
    }

    private Set<DataSearchInfo> add(Set<DataSearchInfo> target, Set<DataSearchInfo> value) {
        target.addAll(value);
        return target;
    }

}
