import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * The current class is a model for the representation of location information of searching data in text.
 */
@Data
@Builder
@ToString
public class DataSearchInfo {
    int lineOffset;
    int charOffset;
}
