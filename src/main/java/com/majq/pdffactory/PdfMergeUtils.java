package com.majq.pdffactory;

import com.itextpdf.forms.PdfPageFormCopier;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.PdfMerger;

import java.io.File;
import java.io.IOException;

/**
 * @author Mr.X
 * @version 1.0.0
 * @since 2018/11/16 16:18
 * <em>PDF合并工具</em> 用于合并多个PDF文档，替换PDF中的某一部分等
 */
public class PdfMergeUtils {
	/**
	 * 合并后文件存放位置
	 */
	private final String dest;
	/**
	 * 源文件路径
	 */
	private final String[] src;
	/**
	 * 源文件对象
	 */
	private final File destFile;

	public PdfMergeUtils(String dest, String... src) throws IOException {
		this.dest = dest;
		this.src = src;
		destFile = new File(dest);
		if (!destFile.exists())
			destFile.createNewFile();
	}

	public static void main(String[] args) throws IOException {
		String src1 = "C:\\马俊强\\pdfdir\\Java核心技术 卷I 基础知识（原书第10版）.pdf";
		String src2 = "C:\\马俊强\\pdfdir\\Java核心技术（卷2）：高级特性（原书第9版）.pdf";
		String dest = "C:\\马俊强\\pdfdir\\Java核心技术.pdf";
		PdfMergeUtils mergeUtils = new PdfMergeUtils(dest, src1, src2);
		mergeUtils.mergePdfByCopy();
	}

	/**
	 * @throws IOException 创建源文件异常
	 */
	public void mergPdfByMerger() throws IOException {
		PdfDocument srcDocument = null;
		PdfDocument destDocument = null;
		try {
			destDocument = new PdfDocument(new PdfWriter(this.dest));
			PdfMerger pdfMerger = new PdfMerger(destDocument);
			for (int i = 0; i < this.src.length; i++) {
				srcDocument = new PdfDocument(new PdfReader(this.src[i]));
				mergeByMerger(pdfMerger, srcDocument);
			}
		} finally {
			if (null != destDocument)
				destDocument.close();
			if (null != srcDocument)
				srcDocument.close();
		}
	}

	/**
	 * 将源文件写入到目标文件中
	 * 暂时先支持全部内容，后续可以支持部分内容合并
	 *
	 * @param pdfMerger   合并文档
	 * @param pdfDocument 源文档
	 */
	private void mergeByMerger(PdfMerger pdfMerger, PdfDocument pdfDocument) {
		pdfMerger.merge(pdfDocument, 1, pdfDocument.getNumberOfPages());
	}

	/**
	 * 合并PDF
	 *
	 * @throws IOException
	 */
	public void mergePdfByCopy() throws IOException {
		PdfDocument srcDocument = null;
		PdfDocument destDocument = null;
		try {
			destDocument = new PdfDocument(new PdfWriter(dest));
			for (int i = 0; i < src.length; i++) {
				srcDocument = new PdfDocument(new PdfReader(src[i]));
				mergerByCopy(destDocument, srcDocument);
			}
		} finally {
			if (null != destDocument)
				destDocument.close();
			if (null != srcDocument)
				srcDocument.close();
		}
	}

	/**
	 * pdf合并
	 *
	 * @param destDocument 目标文档
	 * @param srcDocument  源文档
	 */
	private void mergerByCopy(PdfDocument destDocument, PdfDocument srcDocument) {
		srcDocument.copyPagesTo(1, srcDocument.getNumberOfPages(), destDocument, 1, new PdfPageFormCopier());
	}
}
