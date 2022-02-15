package records.validator;

import java.util.Map;
import java.util.Set;

public interface OutputService {

    void print(Map<String, Set<DataSearchInfo>> map);
}
