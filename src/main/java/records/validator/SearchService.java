package records.validator;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SearchService {

    Map<String, Set<DataSearchInfo>> match(int partitionNumber, List<String> dataSearchList, String source);

}
