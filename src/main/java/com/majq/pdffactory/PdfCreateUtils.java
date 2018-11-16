package com.majq.pdffactory;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.PdfCanvasConstants;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.UnitValue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class PdfCreateUtils {
    private static final String dest = "C:\\马俊强\\pdfdir\\hello.pdf";
    private static final String data = "C:\\马俊强\\pdfdir\\data.csv";
    private static final Color grayColor = new DeviceCmyk(0.f, 0.f, 0.f, 0.875f);
    private static final Color greenColor = new DeviceCmyk(1.f, 0.f, 1.f, 0.176f);
    private static final Color blueColor = new DeviceCmyk(1.f, 0.156f, 0.f, 0.118f);

    public static void main(String[] args) throws IOException {
        textState();
    }

    private static void createPdf1() throws IOException {
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
        document.add(new Paragraph("isText is : ").setFont(font));
        List list = new List().setSymbolIndent(12).setListSymbol("\u2022").setFont(font);
        list.add(new ListItem("Never gonna give you up"))
                .add(new ListItem("Never gonna let you down"))
                .add(new ListItem("Never gonna run around and desert you"))
                .add(new ListItem("Never gonna make you cry"))
                .add(new ListItem("Never gonna say goodbye"))
                .add(new ListItem("Never gonna tell a lie and hurt you"));
        document.add(list);
        //add an image
        Image image = new Image(ImageDataFactory.create("C:\\Users\\Mr.X\\Desktop\\tenor.gif"));
        Paragraph p = new Paragraph("This is an image: ").add(image);
        document.add(p);
        document.close();
    }

    private static void createPdf2() throws IOException {
        PdfWriter pdfWriter = new PdfWriter(dest);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        Document document = new Document(pdfDocument, PageSize.A4.rotate());
        document.setMargins(20, 20, 20, 20);
        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        Table table = new Table(new float[]{1, 2, 4, 4, 2, 1, 4, 1, 4});
        table.setWidth(UnitValue.createPercentValue(100));
        BufferedReader br = new BufferedReader(new FileReader(data));
        String line = br.readLine();
        process(table, line, bold, true);
        while ((line = br.readLine()) != null) {
            process(table, line, font, false);
        }
        br.close();
        document.add(table);
        document.close();
    }

    private static void process(Table table, String line, PdfFont font, boolean isHeader) {
        StringTokenizer tokenizer = new StringTokenizer(line, ",");
        while (tokenizer.hasMoreTokens()) {
            if (isHeader) {
                table.addHeaderCell(new Cell().add(new Paragraph(tokenizer.nextToken()).setFont(font)));
            } else {
                table.addCell(new Cell().add(new Paragraph(tokenizer.nextToken()).setFont(font)));
            }
        }
    }

    private static void canvas1() {
        try (PdfDocument pdfDocument = new PdfDocument(new PdfWriter(dest))) {
            PageSize ps = PageSize.A4.rotate();
            PdfPage page = pdfDocument.addNewPage(ps);
            PdfCanvas canvas = new PdfCanvas(page);
            canvas.concatMatrix(1, 0, 0, 1, ps.getWidth() / 2, ps.getHeight() / 2);
            drawAxes(canvas, ps);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void drawAxes(PdfCanvas canvas, PageSize ps) {
        //draw X axis
        canvas.setLineWidth(0.5f).setStrokeColor(blueColor);
        canvas.moveTo(-(ps.getWidth() / 2 - 15), 0)
                .lineTo(ps.getWidth() / 2 - 15, 0)
                .stroke();
        //draw x axis arrow
        canvas.setLineJoinStyle(PdfCanvasConstants.LineJoinStyle.ROUND)
                .moveTo(ps.getWidth() / 2 - 25, -10)
                .lineTo(ps.getWidth() / 2 - 15, 0)
                .lineTo(ps.getWidth() / 2 - 25, 10)
                .stroke()
                .setLineJoinStyle(PdfCanvasConstants.LineJoinStyle.MITER);
        //draw Y axis
        canvas.setLineWidth(0.5f).setStrokeColor(grayColor);
        canvas.moveTo(0, -(ps.getHeight() / 2 - 15))
                .lineTo(0, ps.getHeight() / 2 - 15)
                .stroke();
        //draw Y axis arrow
        canvas.saveState().setLineJoinStyle(PdfCanvasConstants.LineJoinStyle.ROUND)
                .moveTo(-10, ps.getHeight() / 2 - 25)
                .lineTo(0, ps.getHeight() / 2 - 15)
                .lineTo(10, ps.getHeight() / 2 - 25)
                .stroke()
                .restoreState();
        //draw X serif
//        for(int i = -((int)ps.getWidth()/2-61);i<((int)ps.getWidth()/2-60);i+=40){
//            canvas.moveTo(i,5).lineTo(i,-5);
//        }
//        //draw Y serif
//        for(int i = -((int)ps.getHeight()/2-57);i<((int)ps.getHeight()/2-56);i+=40){
//            canvas.moveTo(5,i).lineTo(-5,i);
//        }
        canvas.stroke();
    }

    private static void drawGridLines() {
        try (PdfDocument pdfDocument = new PdfDocument(new PdfWriter(dest))) {
            PageSize pageSize = PageSize.A4.rotate();
            PdfPage page = pdfDocument.addNewPage(pageSize);
            PdfCanvas pdfCanvas = new PdfCanvas(page);
            pdfCanvas.concatMatrix(1, 0, 0, 1, pageSize.getWidth() / 2, pageSize.getHeight() / 2);
            drawGrid(pdfCanvas, pageSize);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void drawGrid(PdfCanvas canvas, PageSize ps) {
        //draw axes
        drawAxes(canvas, ps);
        //draw grid lines
        int N = 30;
        canvas.setLineWidth(0.5f).setStrokeColor(greenColor);
        //横线数量
        int xLines = new Double(Math.floor(ps.getHeight() - 30) / N).intValue();
        for (int i = 0; i < xLines; i++) {
            canvas.moveTo(-(ps.getWidth() / 2 - N), -(ps.getHeight() / 2 - N) + i * N)
                    .lineTo(ps.getWidth() / 2 - N, -(ps.getHeight() / 2 - N) + i * N)
                    .stroke();
        }
        //竖线数量
        int yLines = new Double(Math.floor(ps.getWidth() - 30) / N).intValue();
        for (int i = 0; i < yLines; i++) {
            canvas.moveTo(-(ps.getWidth() / 2 - N) + i * N, -(ps.getHeight() / 2 - N))
                    .lineTo(-(ps.getWidth() / 2 - N) + i * N, ps.getHeight() / 2 - N)
                    .stroke();
        }
        canvas.setLineWidth(2).setStrokeColor(blueColor).setLineDash(10, 10, 8)
                .moveTo(-240, -240).lineTo(240, 240).stroke();
    }

    private static void textState() {
        try (PdfDocument pdf = new PdfDocument(new PdfWriter(dest))) {
            PageSize ps = PageSize.A4;
            PdfPage page = pdf.addNewPage(ps);
            PdfCanvas canvas = new PdfCanvas(page);
            canvas.rectangle(0, 0, ps.getWidth(), ps.getHeight());
            java.util.List<String> text = new ArrayList();
            text.add("         Episode V         ");
            text.add("  THE EMPIRE STRIKES BACK  ");
            text.add("It is a dark time for the");
            text.add("Rebellion. Although the Death");
            text.add("Star has been destroyed,");
            text.add("Imperial troops have driven the");
            text.add("Rebel forces from their hidden");
            text.add("base and pursued them across");
            text.add("the galaxy.");
            text.add("Evading the dreaded Imperial");
            text.add("Starfleet, a group of freedom");
            text.add("fighters led by Luke Skywalker");
            text.add("has established a new secret");
            text.add("base on the remote ice world");
            text.add("of Hoth...");
            canvas.concatMatrix(1, 0, 0, 1, 0, ps.getHeight());
            canvas.beginText()
                    .setFontAndSize(PdfFontFactory.createFont(StandardFonts.COURIER_BOLD), 14)
                    .setLeading(14 * 1.2f)
                    .moveText(70, -40);
            for (String s : text)
                canvas.newlineShowText(s);
            canvas.endText();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
