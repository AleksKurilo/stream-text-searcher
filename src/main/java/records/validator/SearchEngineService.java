package records.validator;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SearchEngineService {

    Map<String, Set<DataSearchInfo>> match(int partition, List<String> search, String text);
}
