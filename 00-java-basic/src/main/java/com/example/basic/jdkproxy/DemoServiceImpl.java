package com.example.basic.jdkproxy;

public class DemoServiceImpl implements DemoService{

    @Override
    public String doSomething(String task) {
        return "do task: [ " + task + " ]";
    }
}
