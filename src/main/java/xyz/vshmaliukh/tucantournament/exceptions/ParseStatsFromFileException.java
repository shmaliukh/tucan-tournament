package xyz.vshmaliukh.tucantournament.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Author: vshmaliukh
 * Exception thrown when there is an error parsing player match stats from a File.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ParseStatsFromFileException extends RuntimeException {

    public ParseStatsFromFileException(String message) {
        super(message);
    }

    public ParseStatsFromFileException(String message, Throwable cause) {
        super(message, cause);
    }

}