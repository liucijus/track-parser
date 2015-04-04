package lt.overdrive.trackparser.utils;

import java.io.File;
import java.net.URISyntaxException;

public class TestResourceUtils {
    public static File getFile(String name) {
        try {
            return new File(Thread.currentThread().getContextClassLoader().getResource(name).toURI());
        } catch (URISyntaxException e) {
            return null;
        }
    }
}
