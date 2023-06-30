package xyz.vshmaliukh.tucantournament.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ParseStatsFromFileException extends RuntimeException {

    public ParseStatsFromFileException(String message) {
        super(message);
    }

    public ParseStatsFromFileException(String message, Throwable cause) {
        super(message, cause);
    }

}