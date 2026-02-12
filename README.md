# mc-pdfmanipulation-mule
A MuleSoft project demonstrating advanced PDF manipulation using the Java Module and Apache PDFBox 3. Features three architectural patterns: Off-Heap File Splitting, In-Memory Binary processing, and Automated Stream Chunking.

# Mule 4 PDF Processing with Apache PDFBox 3

This project demonstrates how to extend MuleSoft's native capabilities to handle complex binary file manipulations. It integrates the **Mule Java Module** with **Apache PDFBox 3.0.1** to solve common PDF processing challenges that DataWeave cannot handle alone.

## 🚀 Key Features

This repository implements **three distinct architectural patterns** for PDF processing:

1.  **Off-Heap Processing (File Paths):** Triggers Java to read/write directly from disk, bypassing the Mule Runtime heap to prevent OutOfMemory errors on massive files.
2.  **Custom Logic Splitting (Binary Payload):** Converts Mule Streams to Byte Arrays to allow random access, enabling splitting at specific, non-sequential page numbers (e.g., Split at pg 5 and pg 10).
3.  **Stream Chunking (Automated Batching):** A "Mule-native" approach using File Listeners to automatically ingest documents and break them into manageable chunks (e.g., 3 pages per file) for downstream processing.

## 🛠️ Technology Stack

* **Mule Runtime:** 4.4+
* **Java:** 8 / 11 / 17
* **Library:** [Apache PDFBox 3.0.1](https://pdfbox.apache.org/)
* **Module:** Mule Java Module

## 📂 Project Structure

* `src/main/mule`: Contains the three Mule flows (`split-pdf-each-pageFlow`, `read-pdf-as-binaryFlow`, `split-pdf-by-rangeFlow`).
* `src/main/java/com/pdfsplit/javamodule`: Contains the custom Java logic (`SplitEachPage.java`, `ReadAsBinary.java`, `PdfSplitByRange.java`).

## ⚙️ Setup & Usage

### Prerequisites
Add the following dependency to your `pom.xml`:
```xml
<dependency>
    <groupId>org.apache.pdfbox</groupId>
    <artifactId>pdfbox</artifactId>
    <version>3.0.1</version>
</dependency>
