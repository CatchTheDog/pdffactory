package com.majq.pdffactory;


import java.io.*;

public class PdfToMobiUtils {

    public static void main(String[] args){
        try(DataInputStream dataInputStream = new DataInputStream(new FileInputStream("C:/Users/Mr.X/Downloads/Java核心技术.卷I.基础知识(原书第10版)_1.mobi"));
            DataOutputStream dataOutputStream = new DataOutputStream(System.out)
        ){
            byte [] buffer = new byte [1024];
            dataInputStream.read(buffer,0,64);
            //dataOutputStream.write(buffer,0,64);
            printBuffer(buffer,0,64);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printBuffer(byte [] buffer,int start,int end){
        for(int i = start;i<end;i++){
         System.out.println(buffer[i]);
        }
    }
}
