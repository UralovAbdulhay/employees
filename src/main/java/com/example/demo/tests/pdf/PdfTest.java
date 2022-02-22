package com.example.demo.tests.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

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
        Chunk chunk = new Chunk("Hello World", font);

        Image img = Image.getInstance(new File("files/employee/22-02-2022/jpg/image_2021-11-27_12-43-13.png").toURL());
        document.add(img);

        document.add(chunk);
        document.close();


    }
}
