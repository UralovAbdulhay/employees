package com.example.demo.tests.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.stream.Stream;

public class PdfTest {
    public static void main(String[] args) throws IOException, DocumentException, URISyntaxException {

        Document document = new Document();
//        Path path = Paths.get(ClassLoader.getSystemResource("files/employee/22-02-2022/jpg/1aa9aa71-86d0-496a-a27c-32d0db5dfa27.jpg").toURI());
        File file = new File("files\\pdf\\test.pdf");
        file.getParentFile().mkdirs();
        file.createNewFile();
        PdfWriter.getInstance(document, new FileOutputStream(file));

        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);


        Image img = Image.getInstance(new File("files/employee/jpg/22-02-2022/b360279d-3ea2-45f8-8c1a-c5fec8029374.jpg").toURL());
        img.scaleToFit(160, 240);

        Paragraph paragraph = new Paragraph(new Chunk(img, 0, 0, true));

//        paragraph.trimToSize();


        Paragraph textParagraph = new Paragraph();

        textParagraph.add(new Chunk("Full name: Abdulhay Uralov"));
        textParagraph.add(Chunk.NEWLINE);
        textParagraph.add(new Chunk("Tel: +998 99 007 56 03"));
        textParagraph.add(Chunk.NEWLINE);
        textParagraph.add(new Chunk("Email: AbdulhayUralov@gmail.com"));
        textParagraph.add(Chunk.NEWLINE);
        textParagraph.add(new Chunk("Birth Date: 23.02.2020"));
        textParagraph.add(Chunk.NEWLINE);


        PdfPTable table = new PdfPTable(new float[]{3, 5});

        table.addCell(paragraph);
        table.addCell(textParagraph);
        for (PdfPRow row : table.getRows()) {
            for (PdfPCell cell : row.getCells()) {
                cell.setBorder(0);

//                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
            }
        }

        table.setWidthPercentage(100);
        document.add(table);
        document.add(new Paragraph(Chunk.NEWLINE));


        PdfPTable attendanceTable = new PdfPTable(new float[]{1, 3, 3});
        addTableHeader(attendanceTable);

        for (int i = 0; i < 50; i++) {

        addRows(attendanceTable);
        }
        addCustomRows(attendanceTable);

        document.add(attendanceTable);


        document.close();


    }


    private static void addTableHeader(PdfPTable table) {
        Stream.of("column header 1", "column header 2", "column header 3")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(1);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private static void addRows(PdfPTable table) {
        table.addCell("row 1, col 1");
        table.addCell("row 1, col 2");
        table.addCell("row 1, col 3");
    }


    private static void addCustomRows(PdfPTable table)
            throws URISyntaxException, BadElementException, IOException {

//        Path path = Paths.get(ClassLoader.getSystemResource("Java_logo.png").toURI());
//        Image img = Image.getInstance(path.toAbsolutePath().toString());
        Image img = Image.getInstance(new File("files/employee/22-02-2022/jpg/image_2021-11-27_12-43-13.png").toURL());

        img.getAbsoluteX();
        img.scalePercent(10);

        PdfPCell imageCell = new PdfPCell(img);
        imageCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        imageCell.setVerticalAlignment(Element.ALIGN_CENTER);
        table.addCell(imageCell);

        PdfPCell horizontalAlignCell = new PdfPCell(new Phrase("row 2, col 2"));
        horizontalAlignCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        horizontalAlignCell.setVerticalAlignment(Element.ALIGN_CENTER);
        table.addCell(horizontalAlignCell);

        PdfPCell verticalAlignCell = new PdfPCell(new Phrase("row 2, col 3"));
        verticalAlignCell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        table.addCell(verticalAlignCell);
    }


}
