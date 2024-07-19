package com.virtual.luna;

import io.javalin.Javalin;

/**
 * 一个简单的轻量级的 web 服务，非常的好用
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        var app = Javalin.create(/*config*/)
                .get("/", ctx -> ctx.result("Hello World"))
                .start(7070);
    }
}
