package records.validator;


import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class Main {

    private static final SearchServiceImpl searchDonald = SearchServiceImpl.builder()
            .search("Donald")
            .build();

    private static final SearchServiceImpl searchDavid = SearchServiceImpl.builder()
            .search("David")
            .build();

    private static final List<SearchServiceImpl> searchServiceList = List.of(searchDonald, searchDavid);
    private static final OutputService OUTPUT_SERVICE = new OutputConsoleService();
    private static final ConcurrentMap<String, Set<DataSearchInfo>> result = new ConcurrentHashMap();

    public static void main(String[] args) {
        log.info("Start script");
        String[] source = FileUtils.readClasspathFileAsList("source/big-1.txt").toArray(new String[0]);
        final AtomicInteger processedRecords = new AtomicInteger(0);
        final AtomicBoolean flag = new AtomicBoolean(true);

        Flowable.just(getPartition(processedRecords.get(), 1000, source))
                .flatMapCompletable(firstPage -> Flowable.range(0, 1000_000)
                                .takeWhile(counter -> flag.get())
                                .flatMap(counter -> Flowable.just(getPartition(processedRecords.get(), 1000, source))
                                                .doOnNext(records -> Flowable.fromIterable(searchServiceList)
                                                        .subscribeOn(Schedulers.io())
                                                        .doOnNext(searcher -> result.putAll(searcher.search(records)))
                                                        .blockingSubscribe()
                                                )
                                                .doOnNext(records -> {
                                                    processedRecords.getAndUpdate(pr -> pr + 1000);
                                                    flag.set(!(processedRecords.get() > source.length));
                                                })
                                )
                                .ignoreElements()
                )
                .blockingGet();

        OUTPUT_SERVICE.print(result);
        log.info("Finished script");
    }

    private static String[] getPartition(int startOf, int limit, String[] source) {
        String[] partition = new String[limit];

        if (startOf + limit > source.length) {
            limit = source.length - startOf;
        }

        for (int i = 0; i < limit; i++) {
            partition[i] = source[startOf + i];
        }


        return partition;
    }


}
