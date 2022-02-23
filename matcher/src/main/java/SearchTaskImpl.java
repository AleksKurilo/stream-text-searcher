import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import utils.TextUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Builder
class SearchTaskImpl implements SearchTask {

    private final String search;
    private static final String SEARCH_PATTERN = "\\b{search}\\W|\\b{search}\\b";
    private static final Map<String, Set<DataSearchInfo>> result = new HashMap<>();

    /**
     * Search {@link String} in the array of {@link String}.
     *
     * @param lines text where will be searching.
     * @return map of matches with information with their location in the text.
     */
    @Override
    public Map<String, Set<DataSearchInfo>> search(final String[] lines) {
        log.info("Searching: {}", search);
        for (String line : lines) {
            int lineOffset = Integer.parseInt(line.split(":")[0]);
            Map<String, Set<DataSearchInfo>> match = match(lineOffset, search, line);

            match.forEach((key, value) -> {
                result.computeIfPresent(key, (key1, value1) -> mergeSets(value1, value));
                result.putIfAbsent(key, value);
            });

        }

        return result;
    }

    private Map<String, Set<DataSearchInfo>> match(int lineOffset, String search, String source) {
        Set<DataSearchInfo> dataSearchInfos = new HashSet<>(matchInLine(lineOffset, search, source));
        Map<String, Set<DataSearchInfo>> resultMap = new HashMap<>();

        if (!dataSearchInfos.isEmpty()) {
            resultMap.put(search, dataSearchInfos);
        }

        return resultMap;
    }

    private Set<DataSearchInfo> matchInLine(int lineOffset, String search, String line) {
        String formattedLine = TextUtils.buildUnnumberedLine(line);
        List<Integer> charOffsetList = indexOfList(search, formattedLine);
        return charOffsetList.stream()
                .map(charOffset -> DataSearchInfo.builder()
                        .lineOffset(lineOffset)
                        .charOffset(charOffset)
                        .build()
                )
                .collect(Collectors.toSet());
    }

    private Set<DataSearchInfo> mergeSets(Set<DataSearchInfo> target, Set<DataSearchInfo> value) {
        target.addAll(value);
        return target;
    }

   private List<Integer> indexOfList(String search, String text) {
        List<Integer> result = new ArrayList<>();
        Pattern findWordPattern = Pattern.compile(SEARCH_PATTERN.replace("{search}", search));
        Matcher matcher = findWordPattern.matcher(text);
        while (matcher.find()) {
            int offsetStart = matcher.start();
            result.add(offsetStart);
        }

        return result;
    }
}
