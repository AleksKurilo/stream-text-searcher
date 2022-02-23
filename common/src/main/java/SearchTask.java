import java.util.Map;
import java.util.Set;

public interface SearchTask {

    Map<String, Set<DataSearchInfo>> search(String[] lines) ;
}
