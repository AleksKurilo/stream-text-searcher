package records.validator;

import java.util.Map;
import java.util.Set;

public interface AggregatorService {

    Map<String, Set<DataSearchInfo>> merge(Map<String, Set<DataSearchInfo>>... maps);

}
