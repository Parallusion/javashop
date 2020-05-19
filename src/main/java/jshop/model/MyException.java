package jshop.model;

public class MyException extends Exception{
    public MyException(String message, String args) {
        super(message + args); //Arrays.toString(args)
    }
}
