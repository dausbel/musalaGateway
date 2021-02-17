package com.musala.exercise.gatewayperipherical.exceptions;

public class DeviceLimitException extends Exception {

    public DeviceLimitException() {
        super("Only 10 devices are  allowed.");
    }
}
