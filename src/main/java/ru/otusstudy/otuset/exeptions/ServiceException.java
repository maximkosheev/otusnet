package ru.otusstudy.otuset.exeptions;

public class ServiceException extends RuntimeException {
    public ServiceException(String message) {
        super(message);
    }
}
