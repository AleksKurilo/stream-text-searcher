import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class SearchEnginStreamServiceTest {

    private final SearchEnginStreamService searchEnginStreamService = new SearchEnginStreamService(new AggregationResultService());

    @Test
    @DisplayName("Should return correct line offsets for given search input")
    void searchTest(){
        final String search = "Donald";
        final String[] source = FileUtils.readClasspathFileAsList("test-data/big-test.txt").toArray(new String[0]);
        final SearchTask searchTask = SearchTaskImpl.builder()
                .search(search)
                .build();
        final ConcurrentMap<String, Set<DataSearchInfo>> result = new ConcurrentHashMap<>();

        searchEnginStreamService.search(source, List.of(searchTask), result);

        List<Integer> actual = result.get(search).stream()
                .map(DataSearchInfo::getLineOffset)
                .sorted()
                .collect(Collectors.toList());

        assertFalse(actual.isEmpty());
        assertEquals(1, actual.get(0), "wrong lineOffset for given value for the 1st record");
        assertEquals(2, actual.get(1), "wrong lineOffset for given value for the 2st record");
        assertEquals(999, actual.get(2), "wrong lineOffset for given value for the 3st record");
        assertEquals(1000, actual.get(3), "wrong lineOffset for given value for the 4st record");
        assertEquals(1034, actual.get(4), "wrong lineOffset for given value for the 5st record");
    }
}
