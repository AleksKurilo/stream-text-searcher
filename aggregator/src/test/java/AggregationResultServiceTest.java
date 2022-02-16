import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AggregationResultServiceTest {

    private AggregationResultService unit = new AggregationResultService();

    @Test
    void mergeTest(){
        Set<DataSearchInfo> data1 = new HashSet<>();
        data1.add(DataSearchInfo.builder().lineOffset(0).charOffset(1).build());
        Set<DataSearchInfo> data2 = new HashSet<>();
        data2.add(DataSearchInfo.builder().lineOffset(1).charOffset(2).build());

        Map<String, Set<DataSearchInfo>> source = Map.of("1", data1);
        ConcurrentHashMap<String, Set<DataSearchInfo>> target = new ConcurrentHashMap<>();
        target.put("1", data2);

         unit.merge(source, target);

        assertEquals(1, target.size(), "wrong size of the result map");
        assertEquals(2, target.get("1").size(), "wrong size of the data search info list for the first map");
    }
}
