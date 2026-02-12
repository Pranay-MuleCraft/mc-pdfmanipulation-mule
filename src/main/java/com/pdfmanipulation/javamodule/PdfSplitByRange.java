package com.pdfmanipulation.javamodule;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.io.RandomAccessReadBuffer;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class PdfSplitByRange {

    /**
     * Splits a PDF InputStream into a List of Byte Arrays.
     * Compatible with PDFBox 3.0.1
     *
     * @param pdfContent The input PDF as an InputStream (Mule Payload).
     * @param splitSize  The number of pages per split.
     * @return A List of byte arrays, where each item is a standalone PDF file.
     */
    public static List<byte[]> splitPdf(InputStream pdfContent, int splitSize) throws IOException {
        List<byte[]> splitFiles = new ArrayList<>();

        try (PDDocument document = Loader.loadPDF(new RandomAccessReadBuffer(pdfContent))) {

            Splitter splitter = new Splitter();
            splitter.setSplitAtPage(splitSize);

            List<PDDocument> pages = splitter.split(document);
            
            for (PDDocument pd : pages) {
                try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                    pd.save(baos);
                    splitFiles.add(baos.toByteArray());
                } finally {
                    pd.close();
                }
            }
        }
        
        return splitFiles;
    }
}