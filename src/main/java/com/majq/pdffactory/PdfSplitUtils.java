package com.majq.pdffactory;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.PageRange;
import com.itextpdf.kernel.utils.PdfSplitter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * @author Mr.X
 * @version 1.0.0
 * 完成pdf分割
 * @since 2018/11/15 9:27
 */
public class PdfSplitUtils {
    /**
     * 源文件路径
     */
    private String srcPath;
    /**
     * 生成文件存放路径
     */
    private String descPath;

    private static final String EXTENSION = ".pdf";

    public PdfSplitUtils(String srcPath, String descPath) {
        this.srcPath = srcPath;
        this.descPath = descPath;
    }

    public static void main(String[] args) throws IOException {
        PdfSplitUtils utils = new PdfSplitUtils("c:\\Java核心技术 卷I 基础知识（原书第10版）.pdf", "C:\\马俊强\\");
        utils.splitPdfByPageNum();
    }

    /**
     * 分割PDF
     *
     * @throws IOException 读取源文件异常
     */
    public void splitPdfByPageNum() throws IOException {
        PdfDocument pdfDocument = new PdfDocument(new PdfReader(srcPath));
        List<PdfDocument> splitDocuments = new PdfSplitter(pdfDocument) {
            int partNumber = 1;

            @Override
            protected PdfWriter getNextPdfWriter(PageRange documentPageRange) {
                try {
                    return new PdfWriter(descPath + PathUtils.getFileName(srcPath) + String.valueOf(partNumber++) + EXTENSION);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException();
                }
            }
        }.splitByPageCount(80);
        for (PdfDocument doc : splitDocuments)
            doc.close();
        pdfDocument.close();
    }
}
