import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

public interface SearchEngineService {

    void search(String[] source, List<SearchTask> searchTasks, ConcurrentMap<String, Set<DataSearchInfo>> result);
}
