package constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Class contains common default values and constants.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConfigurationDefaults {

    public static final int LINE_LIMIT = 1000;
    public static final int MAX_TOTAL_LINE = 1000_000_000;
}
