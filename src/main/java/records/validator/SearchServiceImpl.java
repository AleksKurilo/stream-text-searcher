package records.validator;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Builder
class SearchServiceImpl {

    private final String search;
    private static final Map<String, Set<DataSearchInfo>> result = new HashMap<>();

    public Map<String, Set<DataSearchInfo>> search(String[] lines) {
        log.info("Searching: {}", search);
        for (String line : lines) {
            int lineOffset = Integer.parseInt(line.split(":")[0]);
            Map<String, Set<DataSearchInfo>> match = match(lineOffset, line);

            match.forEach((key, value) -> {
                result.computeIfPresent(key, (key1, value1) -> add(value1, value));
                result.putIfAbsent(key, value);
            });

        }
        return result;
    }

    public Map<String, Set<DataSearchInfo>> match(int lineOffset, String source) {
        Map<String, Set<DataSearchInfo>> result = new HashMap<>();

            Map<String, Set<DataSearchInfo>> map = searching(lineOffset, search, source);

            if (!map.isEmpty()) {
                result.putAll(map);
            }

        return result;
    }

    private Map<String, Set<DataSearchInfo>> searching(int lineOffset, String search, String source) {
        Set<DataSearchInfo> dataSearchInfos = new HashSet<>();
        dataSearchInfos.addAll(searchInLine(lineOffset, search, source));
        Map<String, Set<DataSearchInfo>> result = new HashMap<>();

        if (!dataSearchInfos.isEmpty()) {
            result.put(search, dataSearchInfos);
        }

        return result;
    }

    private Set<DataSearchInfo> searchInLine(int lineOffset, String search, String line) {
        int matherCount = (int) Arrays.stream(line.split(" "))
                .filter(word -> word.equals(search)).count();

        if (matherCount == 0) {
            return Collections.emptySet();
        }

        List<Integer> charOffsetList = new ArrayList<>();

        for (int i = 0; i < matherCount; i++) {
            int charOffset = line.indexOf(search);

            if (charOffsetList.contains(charOffset)) {
                int index = line.indexOf(search, charOffset + 1);
                charOffsetList.add(index);
            } else {
                charOffsetList.add(charOffset);
            }
        }

        return charOffsetList.stream()
                .map(charOffset -> DataSearchInfo.builder()
                        .lineOffset(lineOffset)
                        .charOffset(charOffset)
                        .build()
                )
                .collect(Collectors.toSet());

    }

    private static Set<DataSearchInfo> add(Set<DataSearchInfo> target, Set<DataSearchInfo> value) {
        target.addAll(value);
        return target;
    }
}
