import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class Main {

    private static final SearchService searchDonald = SearchServiceImpl.builder()
            .search("Donald")
            .build();

    private static final SearchServiceImpl searchDavid = SearchServiceImpl.builder()
            .search("David")
            .build();

    private static final List<SearchService> searchServiceList = List.of(searchDonald, searchDavid);
    private static final AggregationService aggregationService = new AggregationResultService();
    private static final OutputService outputService = new OutputConsoleService();
    private static final ConcurrentMap<String, Set<DataSearchInfo>> result = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        log.info("Start application");

        final String[] source = FileUtils.readClasspathFileAsList("source/big-1.txt").toArray(new String[0]);
        final AtomicInteger processedLines = new AtomicInteger(0);
        final AtomicBoolean flag = new AtomicBoolean(true);

        Flowable.just(TextUtils.getPartition(processedLines.get(), ConfigurationDefaults.LINE_LIMIT, source))
                .flatMapCompletable(firstPage -> Flowable.range(0, 1000_000_000)
                        .takeWhile(counter -> flag.get())
                        .flatMap(counter -> Flowable.just(TextUtils.getPartition(processedLines.get(), ConfigurationDefaults.LINE_LIMIT, source))
                                .doOnNext(records -> Flowable.fromIterable(searchServiceList)
                                        .subscribeOn(Schedulers.io())
                                        .doOnNext(searcher -> aggregationService.merge(searcher.search(records), result))
                                        .blockingSubscribe()
                                )
                                .doOnNext(records -> {
                                    processedLines.getAndUpdate(pr -> pr + ConfigurationDefaults.LINE_LIMIT);
                                    flag.set(!(processedLines.get() > source.length));
                                })
                        )
                        .ignoreElements()
                )
                .blockingGet();

        outputService.print(result);
        log.info("Finished application");
    }
}
