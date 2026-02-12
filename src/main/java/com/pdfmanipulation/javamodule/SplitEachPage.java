package com.pdfmanipulation.javamodule;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SplitEachPage {

    public static void split(String inputPdfPath, String outputDir) throws IOException {
        File pdffile = new File(inputPdfPath);

        PDDocument document = Loader.loadPDF(pdffile);

        Splitter splitter = new Splitter();
        List<PDDocument> pages = splitter.split(document);

        int index = 1;
        for (PDDocument page : pages) {
            String outputPath = outputDir + "/split-" + index++ + ".pdf";
            page.save(outputPath);
            page.close();
        }

        document.close();
        System.out.println("PDF split successfully.");
    }
}
