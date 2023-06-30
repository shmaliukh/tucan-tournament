package xyz.vshmaliukh.tucantournament.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Author: vshmaliukh
 * Exception thrown when there is an error converting a MultipartFile to a File.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MultipartFileToFileException extends RuntimeException {

    public MultipartFileToFileException(String message) {
        super(message);
    }

    public MultipartFileToFileException(String message, Throwable cause) {
        super(message, cause);
    }

}