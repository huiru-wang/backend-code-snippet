package com.example.codesnippet.designpattern.decorator;

public class ByteReader implements Reader {
    @Override
    public void read(String filename) {
        System.out.println("read from" + filename + " as bytes");
    }
}
