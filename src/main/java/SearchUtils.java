import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchUtils {

    /**
     * Build collection of {@link SearchTask} from string source.
     */
    public static List<SearchTask> buildSearchTaskList(String names){
       return Arrays.asList(names.split(",")).stream()
                .map(name -> SearchTaskImpl.builder()
                        .search(name)
                        .build())
                .collect(Collectors.toList());
    }
}
