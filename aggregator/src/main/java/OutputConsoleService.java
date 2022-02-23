import java.util.Map;
import java.util.Set;

class OutputConsoleService implements OutputService {

    private static final String MESSAGE_PATTERN = "%s --> %s";

    /**
     * Print data to the console.
     */
    @Override
    public void print(Map<String, Set<DataSearchInfo>> map) {
        map.forEach((key, value) -> System.out.println(String.format(MESSAGE_PATTERN, key, value)));
    }
}
