package com.project.ecommerce.common;

import java.time.LocalTime;

public class ApiResponse {
    private final boolean success;

    private final String message;

    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess(){return success;}

    public String getMessage(){return message;}

    public String getTimeStamp(){return LocalTime.now().toString(); }
}
