import constant.ConfigurationDefaults;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import lombok.RequiredArgsConstructor;
import utils.TextUtils;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
public class SearchEnginStreamService implements SearchEngineService {

    private final AggregationService aggregationService;

    @Override
    public void search(String[] source, List<SearchTask> searchTasks, ConcurrentMap<String, Set<DataSearchInfo>> result) {
        final AtomicInteger processedLines = new AtomicInteger(0);
        final AtomicBoolean flag = new AtomicBoolean(true);

        Flowable.just(TextUtils.getPartition(processedLines.get(), ConfigurationDefaults.LINE_LIMIT, source))
                .flatMapCompletable(firstPage -> Flowable.range(0, ConfigurationDefaults.MAX_TOTAL_LINE)
                        .takeWhile(counter -> flag.get())
                        .flatMap(counter -> Flowable.just(TextUtils.getPartition(processedLines.get(), ConfigurationDefaults.LINE_LIMIT, source))
                                .doOnNext(records -> Flowable.fromIterable(searchTasks)
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
    }
}
