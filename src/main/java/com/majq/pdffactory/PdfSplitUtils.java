package com.majq.pdffactory;

import com.itextpdf.kernel.pdf.*;

import java.io.*;

/**
 * @author Mr.X
 * @since 2018/11/15 9:27
 * @version 1.0.0
 * 完成pdf分割
 */
public class PdfSplitUtils {
    /**
     * 源文件路径
     */
    private String srcPath = "";
    /**
     * 生成文件存放路径
     */
    private String descPath = "";

    public PdfSplitUtils(String srcPath, String descPath) {
        this.srcPath = srcPath;
        this.descPath = descPath;
    }

    /**
     * 分割PDF
     * @exception IOException 读取源文件异常
     */
    public void splitPdfByPageNum(int pagesNum) throws IOException {
        PdfDocument pdfDocIn = new PdfDocument(new PdfReader(srcPath));
        PdfDocument pdfDocOut = new PdfDocument(new PdfWriter(descPath));
        PdfPage pdfPage;
        for(int i = 1;i<=pdfDocIn.getNumberOfPages();i++){
            pdfPage = pdfDocIn.getPage(i);
            pdfDocOut.addPage(i,pdfPage);
            if(i%pagesNum == 0);
        }
    }

    private void writePdf(PdfDocument pdfDocument) throws FileNotFoundException {
        if(null != pdfDocument){
            PdfWriter writer = new PdfWriter(descPath);

        }
    }


}
