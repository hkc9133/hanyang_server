package com.hanyang.startup.hanyangstartup.common.exception;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class CustomException extends Exception{
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static class RentalTimeDuplicate extends DuplicateKeyException {
        public RentalTimeDuplicate(String msg) {
            super(msg);
        }
    }

    public static class RentalScheduleDuplicate extends DuplicateKeyException {
        public RentalScheduleDuplicate(String msg) {
            super(msg);
        }
    }


}

