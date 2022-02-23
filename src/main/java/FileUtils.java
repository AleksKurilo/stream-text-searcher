import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import utils.TextUtils;

import java.io.InputStream;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileUtils {

    /**
     * Read and return content of file available in classpath as list.
     */
    public static List<String> readClasspathFileAsList(String fileNameInClasspath) {
        final InputStream resourceAsStream = FileUtils.class.getClassLoader().getResourceAsStream(fileNameInClasspath);
        if (resourceAsStream == null) {
            throw new RuntimeException(String.format("Can't find file '%s'", fileNameInClasspath));
        }
        return TextUtils.buildNumberedLinesArray(resourceAsStream);
    }


}
