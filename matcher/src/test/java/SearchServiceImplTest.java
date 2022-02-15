import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class SearchServiceImplTest {

    private SearchServiceImpl unit = new SearchServiceImpl("Donald");

    @Test
    void matchTest() {
        String[] text = List.of(
                "1: 1111",
                "2: Donald Richard 55 $$ 666",
                "3: MacDonald Donald Mat Donald"
        ).toArray(new String[0]);

        Map<String, Set<DataSearchInfo>> actual = unit.search(text);

        assertNotNull(actual.get("Donald"), "should contains result");
        assertEquals(3, actual.get("Donald").size(), "should contains 3 result");
        assertEquals(2, actual.get("Donald").stream().findFirst().get().lineOffset, "wrong lineOffset for given value");
        assertEquals(1, actual.get("Donald").stream().findFirst().get().charOffset, "wrong charOffset for given value");
    }
}
