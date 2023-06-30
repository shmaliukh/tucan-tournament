package xyz.vshmaliukh.tucantournament;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.web.multipart.MultipartFile;
import xyz.vshmaliukh.tucantournament.exceptions.MultipartFileToFileException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@UtilityClass
public class Utils {

    public static File convertMultipartFileToFile(MultipartFile multipartFile) throws MultipartFileToFileException {
        if (multipartFile != null) {
            File convertedFile;
            String originalFileName = multipartFile.getOriginalFilename();
            if (StringUtils.isNotBlank(originalFileName)) {
                convertedFile = TempDirectoryUtil.generateFile(originalFileName);
            } else {
                throw new MultipartFileToFileException("problem to convert MultipartFile to File // MultipartFile name is empty");
            }
            createConvertedFileFromMultipartFile(multipartFile, convertedFile);
            return convertedFile;
        }
        throw new MultipartFileToFileException("problem to convert MultipartFile to File // file is null");
    }

    private static void createConvertedFileFromMultipartFile(MultipartFile multipartFile, File convertedFile) {
        try {
            boolean isConvertedFileIsCreated = convertedFile.createNewFile();
            if (isConvertedFileIsCreated) {
                saveMultipartFileToFile(multipartFile, convertedFile);
            } else {
                throw new MultipartFileToFileException("problem to create local File copy from MultipartFile");
            }
        } catch (IOException ioe) {
            throw new MultipartFileToFileException("problem to create local File copy from MultipartFile", ioe);
        }
    }

    private static void saveMultipartFileToFile(MultipartFile multipartFile, File convertedFile) {
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(multipartFile.getBytes());
        } catch (IOException e) {
            throw new MultipartFileToFileException("problem to save data from MultipartFile to local File", e);
        }
    }

    static final class TempDirectoryUtil {

        private TempDirectoryUtil() {
        }

        public static File generateFile(String fileName) {
            return new File(SystemUtils.JAVA_IO_TMPDIR, fileName + System.nanoTime());
        }

    }

}


