import lombok.Builder;
import lombok.Data;

/**
 * The current class is a model for the representation of location information of searching data in text.
 */
@Data
@Builder
public class DataSearchInfo {
    int lineOffset;
    int charOffset;

    @Override
    public String toString() {
        return "[lineOffset=" + lineOffset + ", " + "charOffset=" + charOffset + "]";
    }
}
