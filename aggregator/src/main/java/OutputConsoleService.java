import java.util.Map;
import java.util.Set;

class OutputConsoleService implements OutputService{

    /**
     * Print data to the console.
     *
     * @param map
     */
    @Override
    public void print(Map<String, Set<DataSearchInfo>> map) {
        map.forEach((key, value) -> {
            System.out.println(key + "-->");
            value.forEach(dataSearchInfo -> {
                System.out.println("\t lineOffset: " + dataSearchInfo.lineOffset);
                System.out.println("\t charOffset: " + dataSearchInfo.charOffset);
            });
        });
    }
}
