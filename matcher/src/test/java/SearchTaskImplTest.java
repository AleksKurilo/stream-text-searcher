import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class SearchTaskImplTest {

    private final SearchTaskImpl unit = new SearchTaskImpl("Donald");

    @Test
    @DisplayName("Should return correct matches for given search input")
    void matchTest() {
        String[] text = List.of(
                "1: test",
                "2:Donald Richard 55 $$ 666",
                "3:MacDonald Donald Mat Donald"
        ).toArray(new String[0]);

        Map<String, Set<DataSearchInfo>> actual = unit.search(text);
        List<DataSearchInfo> actualLocation = new ArrayList<>(actual.get("Donald"));

        assertNotNull(actual.get("Donald"), "should contains result");
        assertEquals(3, actual.get("Donald").size(), "should contains 3 result");
        assertEquals(3, actualLocation.get(0).lineOffset, "wrong lineOffset for given value");
        assertEquals(10, actualLocation.get(0).charOffset, "wrong charOffset for given value");
        assertEquals(2, actualLocation.get(1).lineOffset, "wrong lineOffset for given value");
        assertEquals(0, actualLocation.get(1).charOffset, "wrong charOffset for given value");
        assertEquals(3, actualLocation.get(2).lineOffset, "wrong lineOffset for given value");
        assertEquals(21, actualLocation.get(2).charOffset, "wrong charOffset for given value");
    }
}
