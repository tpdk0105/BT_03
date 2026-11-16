package vn.iotstar.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import jakarta.servlet.http.Part;

public class UploadUtil {

    public static String savePart(Part part, String fallbackValue) throws IOException {
        if (part != null && part.getSize() > 0) {
            File dir = new File(Constant.DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String submitted = part.getSubmittedFileName();
            if (submitted != null && !submitted.trim().isEmpty()) {
                String filename = Paths.get(submitted).getFileName().toString();
                int index = filename.lastIndexOf(".");
                String ext = index >= 0 ? filename.substring(index + 1) : "bin";
                String fname = System.currentTimeMillis() + "_" + Math.abs(filename.hashCode()) + "." + ext;
                part.write(Constant.DIR + File.separator + fname);
                return fname;
            }
        }
        return (fallbackValue != null && !fallbackValue.trim().isEmpty()) ? fallbackValue.trim() : null;
    }
}
