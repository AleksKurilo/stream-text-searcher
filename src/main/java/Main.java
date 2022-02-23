import lombok.extern.slf4j.Slf4j;
import utils.FileUtils;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
public class Main {

    private static final String NAMES = "James,John,Robert,Michael,William,David,Richard,Charles,Joseph,Thomas,Christopher,Daniel,Paul,Mark,Donald," +
            "George,Kenneth,Steven,Edward,Brian,Ronald,Anthony,Kevin,Jason,Matthew,Gary,Timothy,Jose,Larry,Jeffrey,Frank,Scott,Eric," +
            "Stephen,Andrew,Raymond,Gregory,Joshua,Jerry,Dennis,Walter,Patrick,Peter,Harold,Douglas,Henry,Carl,Arthur,Ryan,Roger";

    private static final AggregationService aggregationService = new AggregationResultService();
    private static final OutputService outputService = new OutputConsoleService();
    private static final SearchEnginStreamService searchEnginStreamService = new SearchEnginStreamService(aggregationService);
    private static final ConcurrentMap<String, Set<DataSearchInfo>> result = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        log.info("Start application");

        final String[] source = FileUtils.readClasspathFileAsList("source/big.txt").toArray(new String[0]);
        List<SearchTask> searchTasks = SearchUtils.buildSearchTaskList(NAMES);
        searchEnginStreamService.search(source, searchTasks, result);
        outputService.print(result);

        log.info("Finished application");
    }
}