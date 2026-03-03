package site.wtfu.framework.rime.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * Copyright https://wtfu.site Inc. All Rights Reserved.
 *
 * @date 2025/4/1
 *                          @since  1.0
 *                          @author 12302
 *
 */
public class IOUtils {

    public static String writeByteToFile(byte[] data) {
        try {
            File tempFile = File.createTempFile("tempfile", ".tmp");
            tempFile.deleteOnExit();
            try (FileOutputStream fos = new FileOutputStream(tempFile)) { fos.write(data);}

            return tempFile.getAbsolutePath();
        } catch (IOException e) { throw new RuntimeException();}
    }

}
