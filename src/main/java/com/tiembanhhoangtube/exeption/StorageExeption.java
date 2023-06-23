package com.tiembanhhoangtube.exeption;

public class StorageExeption extends RuntimeException{
    public StorageExeption(String message) {
        super(message);
    }

    public StorageExeption(String message,Exception exception) {
        super(message,exception);
    }
}
