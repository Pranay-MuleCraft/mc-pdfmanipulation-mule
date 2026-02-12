package com.pdfmanipulation.javamodule;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

public class ReadAsBinary {

    /**
     * @param fileContent  The raw file content from Mule (payload)
     * @param splitPoints  The list of page numbers to split at
     * @return             A list of binary files (byte arrays) to be written by Mule
     */
    public static List<byte[]> pdfSplit(byte[] fileContent, List<Integer> splitPoints) {
        List<byte[]> splitFiles = new ArrayList<>();

        if (fileContent == null || splitPoints == null) return splitFiles;

        Collections.sort(splitPoints);

        try (PDDocument sourceDoc = Loader.loadPDF(fileContent)) {
            int totalPages = sourceDoc.getNumberOfPages();

            for (int i = 0; i < splitPoints.size(); i++) {
                int startPage = splitPoints.get(i);
                
                int endPage;
                if (i == splitPoints.size() - 1) {
                    endPage = totalPages;
                } else {
                    endPage = splitPoints.get(i + 1) - 1;
                }

                if (startPage > totalPages) continue;
                if (endPage > totalPages) endPage = totalPages;

                splitFiles.add(createSplitFileToBytes(sourceDoc, startPage, endPage));
            }

        } catch (IOException e) {
            System.err.println("Error processing PDF: " + e.getMessage());
            e.printStackTrace();
        }
        return splitFiles;
    }

    private static byte[] createSplitFileToBytes(PDDocument sourceDoc, int start, int end) throws IOException {
        if (start > end) return null;
        
        try (PDDocument newDoc = new PDDocument();
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
             
            for (int p = start; p <= end; p++) {
                PDPage page = sourceDoc.getPage(p - 1);
                newDoc.importPage(page);
            }
            newDoc.save(baos);
            return baos.toByteArray();
        }
    }
}