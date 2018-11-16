package com.majq.pdffactory;


import java.io.*;

/**
 * .mobi 文件 生成需要使用kindlegen
 */
public class PdfToMobiUtils {

    public static void main(String[] args) {
        analyzeFile();
    }

    private static void analyzeFile() {
        try (DataInputStream dataInputStream = new DataInputStream(new FileInputStream("C:\\Users\\Mr.X\\Desktop\\Java核心技术（卷2）：高级特性（原书第9版）.mobi"));
             DataOutputStream dataOutputStream = new DataOutputStream(System.out)
        ) {
            byte[] buffer = new byte[1024];
            int n = 32;
            dataInputStream.read(buffer, 0, n);
            //dataOutputStream.write(buffer,0,n);
            printBuffer(buffer, 0, n);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 磁盘存储内容都是内容补码
     *
     * @param buffer
     * @param start
     * @param end
     */
    private static void printBuffer(byte[] buffer, int start, int end) {
        for (int i = start; i < end; i++) {
            System.out.print(buffer[i] & 0xff);
            System.out.print(" " + Integer.toBinaryString(buffer[i] & 0xff) + " ");
            System.out.println();
        }
    }
}
