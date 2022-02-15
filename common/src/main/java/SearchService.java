import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SearchService {

    Map<String, Set<DataSearchInfo>> search(String[] lines) ;
}
