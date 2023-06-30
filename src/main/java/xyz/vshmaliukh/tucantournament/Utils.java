package xyz.vshmaliukh.tucantournament;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.web.multipart.MultipartFile;
import xyz.vshmaliukh.tucantournament.exceptions.MultipartFileToFileException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Author: vshmaliukh
 * Utility class that provides helper methods for common operations.
 */
@UtilityClass
public class Utils {

    /**
     * Converts a MultipartFile to a local tmt File.
     *
     * @param multipartFile the MultipartFile to convert
     * @return the converted File
     * @throws MultipartFileToFileException if there is an error converting the MultipartFile to File
     */
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

    /**
     * Creates a local File from a MultipartFile.
     *
     * @param multipartFile the MultipartFile
     * @param convertedFile the File to create
     * @throws MultipartFileToFileException if there is an error creating the local File
     */
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

    /**
     * Saves the content of a MultipartFile to a local File.
     *
     * @param multipartFile the MultipartFile
     * @param convertedFile the File to save to
     * @throws MultipartFileToFileException if there is an error saving the data from MultipartFile to File
     */
    private static void saveMultipartFileToFile(MultipartFile multipartFile, File convertedFile) {
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(multipartFile.getBytes());
        } catch (IOException e) {
            throw new MultipartFileToFileException("problem to save data from MultipartFile to local File", e);
        }
    }

    /**
     * Utility class for generating temporary files.
     */
    static final class TempDirectoryUtil {

        private TempDirectoryUtil() {
        }

        /**
         * Generates a temporary File in the system's default temporary directory.
         *
         * @param fileName the name of the temporary file
         * @return the temporary File
         */
        public static File generateFile(String fileName) {
            return new File(SystemUtils.JAVA_IO_TMPDIR, fileName + System.nanoTime());
        }

    }

}


