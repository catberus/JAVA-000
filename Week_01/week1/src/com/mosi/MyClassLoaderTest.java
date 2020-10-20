package com.mosi;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;

/**
 * 自定义一个Classloader，加载一个Hello.xlass 文件，执行hello 方法，此文件内
 * 容是一个Hello.class 文件所有字节（x=255-x）处理后的文件。
 */
public class MyClassLoaderTest {
    public static void main(String[] args) {

        String path = MyClassLoaderTest.class.getResource("").getPath();
//        MyClassLoader myClassLoader = new MyClassLoader(path, "class");
        MyClassLoader myClassLoader = new MyClassLoader(path, "xlass");

        try {

            Class<?> hello = myClassLoader.loadClass("Hello");
            Object obj = hello.newInstance();
            Method method = hello.getDeclaredMethod("hello");
            method.invoke(obj);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

class MyClassLoader extends ClassLoader {

    private String filePath;
    private String postfix;

    public MyClassLoader(String filePath, String postfix) {

        this.filePath = filePath;
        this.postfix = postfix;

    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        String filename = this.filePath + File.separator + name + "." + postfix;
        byte[] bytes = getClassData(filename);
        Class<?> myClass = defineClass(name, bytes, 0, bytes.length);
        return myClass;

    }

    private byte[] getClassData(String filename) {

        byte[] data = null;
        try (FileInputStream fis = new FileInputStream(filename);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            int b;
            while ((b = fis.read()) != -1) {
                baos.write(decrypht(b));
            }
            data = baos.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    private int decrypht(int b) {
//        return b;
        return 255 - b;
    }
}