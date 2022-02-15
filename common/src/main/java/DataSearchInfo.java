import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class DataSearchInfo {
    int lineOffset;
    int charOffset;
}
