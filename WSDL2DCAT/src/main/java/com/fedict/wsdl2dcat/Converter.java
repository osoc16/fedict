/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fedict.wsdl2dcat;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author Miguel
 * @author Umut
 */
public class Converter {

    // Default paths
    private static final String WSDLPATH = "\\Input\\WSDL\\";
    private static final String XSDPATH = "\\Input\\XSD\\";
    private static final String DCATPATH = "\\Output\\DCAT\\";
    private static final String XSLPATH = "\\Transformation\\XSL\\";
    private static final String FAMILIESPATH = "\\Input\\FAMILIES\\";

    private final String outputDir;
    private final String inputDir;
    private final String stylesheetDir;
    private final String fileType;

    private final String inputDirFamilies;
    private final String fileTypeFamilies;

    /**
     * Default constructor. Sets default output and stylesheet directory.
     */
    public Converter() {
        String currentPath = System.getProperty("user.dir") + "\\src\\files";
        final File jarFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        if (jarFile.isFile()) {
            currentPath = jarFile.getParent();
            try {
                extractFilesFromJAR();
            } catch (Exception ex) {
                Logger.getLogger(Converter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.inputDir = currentPath + WSDLPATH;
        this.outputDir = currentPath + DCATPATH;
        this.stylesheetDir = currentPath + XSLPATH;
        this.fileType = "wsdl";
        this.inputDirFamilies = currentPath + FAMILIESPATH;
        this.fileTypeFamilies = "xml";
    }

    public void convertToDCAT() {
        convertToDCAT(this.inputDir, this.fileType, this.outputDir, this.stylesheetDir);
    }

    public void convertToDCAT(String inputDir) {
        convertToDCAT(inputDir, this.fileType, this.outputDir, this.stylesheetDir);
    }

    public void convertToDCAT(String inputDir, String fileType) {
        convertToDCAT(inputDir, fileType, this.outputDir, this.stylesheetDir);
    }

    public void convertToDCAT(String inputDir, String fileType, String outputDir) {
        convertToDCAT(inputDir, fileType, outputDir, this.stylesheetDir);
    }

    public void convertToDCAT(String inputDir, String fileType, String outputDir, String stylesheetDir) {
        convertToDCAT(inputDir, fileType, outputDir, stylesheetDir, fileType);
    }

    /**
     * Converts files with file type to DCAT files
     *
     * @param inputDir directory from where the files will be read
     * @param fileType file type of the converted files
     * @param outputDir directory where the converted files will be stored
     * @param stylesheetDir directory where the XSL files are stored
     * @param stylesheetFileName name of the stylesheet that should be used
     */
    public void convertToDCAT(String inputDir, String fileType, String outputDir, String stylesheetDir, String stylesheetFileName) {
        OutputStream DCATfile = null;
        try {
            createDirectoryIfNeeded(inputDir);
            createDirectoryIfNeeded(outputDir);
            File[] files = new File(inputDir).listFiles();
            int count = getCountOfType(files, fileType);
            if (count == 0) {
                System.out.println("No " + fileType + " file found in directory: " + inputDir);
            } else {
                System.out.println("Found " + count + " " + fileType + " file(s).");
                System.out.println("Conversion has been started.");

                for (File file : files) {
                    if (file.isFile() && getExtension(file).equals(fileType)) {
                        String DCATfileName = outputDir + "\\" + removeExtension(file) + "_" + stylesheetFileName + ".dcat";
                        System.out.println("DCATFilename: " + DCATfileName);
                        String StylesheetFileName = stylesheetDir + "\\" + stylesheetFileName + "2dcat.xsl";
                        TransformerFactory tFactory = new net.sf.saxon.TransformerFactoryImpl();
                        Source xslDoc = new StreamSource(StylesheetFileName);
                        Source xmlDoc = new StreamSource(file);
                        DCATfile = new FileOutputStream(DCATfileName);
                        Transformer trasform = tFactory.newTransformer(xslDoc);
                        trasform.transform(xmlDoc, new StreamResult(DCATfile));
                        System.out.println("DEBUG: File should be made: " + DCATfileName);
                    }
                }
                System.out.println("File(s) have been converted to DCAT.");
                System.out.println("DCAT files can be found in: \n" + outputDir);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(WSDL2DCAT.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(WSDL2DCAT.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(WSDL2DCAT.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void convertFamiliesToDCAT() {
        convertFamiliesToDCAT(this.inputDirFamilies, this.fileTypeFamilies, this.outputDir, this.stylesheetDir);

    }

    public void convertFamiliesToDCAT(String inputDir) {
        convertFamiliesToDCAT(inputDir, this.fileTypeFamilies, this.outputDir, this.stylesheetDir);

    }

    public void convertFamiliesToDCAT(String inputDir, String fileType) {
        convertFamiliesToDCAT(inputDir, fileType, this.outputDir, this.stylesheetDir);

    }

    public void convertFamiliesToDCAT(String inputDir, String fileType, String outputDir) {
        convertFamiliesToDCAT(inputDir, fileType, outputDir, this.stylesheetDir);
    }

    public void convertFamiliesToDCAT(String inputDir, String fileType, String outputDir, String stylesheetDir) {
        convertToDCAT(inputDir, fileType, outputDir, stylesheetDir, "prefix_catalog_xml");
        convertToDCAT(inputDir, fileType, outputDir, stylesheetDir, "dataset_xml");
        convertToDCAT(inputDir, fileType, outputDir, stylesheetDir, "distribution_xml");
    }

    /**
     * Exports all resources in JAR file to the local file path.
     *
     * @throws Exception
     */
    private void extractFilesFromJAR() throws IOException, Exception {
        final File jarFilePath = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        String jarDirectory = jarFilePath.getParent();
        JarFile jarFile = new JarFile(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());

        createDirectoryIfNeeded(jarDirectory + WSDLPATH);
        createDirectoryIfNeeded(jarDirectory + XSDPATH);
        createDirectoryIfNeeded(jarDirectory + XSLPATH);
        createDirectoryIfNeeded(jarDirectory + FAMILIESPATH);
        createDirectoryIfNeeded(jarDirectory + DCATPATH);

        BufferedOutputStream out = null;
        Enumeration<JarEntry> entries = jarFile.entries();

        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String name = entry.getName();

            if ((name.startsWith("Input") || name.startsWith("Output") || name.startsWith("Transformation"))
                    && (name.endsWith(".wsdl") || name.endsWith(".xsd") || name.endsWith(".dcat") || name.endsWith(".xsl") || name.endsWith(".xml"))) {
                File file = new File(name);
                out = new BufferedOutputStream(new FileOutputStream(file));
                try (BufferedInputStream is = new BufferedInputStream(jarFile.getInputStream(entry))) {
                    byte[] buffer = new byte[4096];
                    int bytesRead = 0;
                    while ((bytesRead = is.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }
                }
                out.flush();
                out.close();
            }
        }
    }

    /**
     * Creates directory if the directory doesn't exist
     *
     * @param directoryName Directory name to check
     */
    private static void createDirectoryIfNeeded(String directoryName) {
        Path path = Paths.get(directoryName);

        // if the directory does not exist, create it
        if (!Files.exists(path)) {
            System.out.println("Needed directory not found.");
            System.out.println("Creating directory: " + directoryName);
            try {
                Files.createDirectories(path);
            } catch (IOException ex) {
                Logger.getLogger(Converter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Counts the amount of files according to given type
     *
     * @param files Files to work with
     * @param type The type of files
     * @return Amount of files according to type
     */
    private int getCountOfType(File[] files, String type) {
        int count = 0;
        for (File file : files) {
            if (file.isFile() && getExtension(file).equals(type)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Gets the extension from a file
     *
     * @param file file to get the extension
     * @return Extension of the file
     */
    private String getExtension(File file) {
        return file.getName().substring(file.getName().lastIndexOf(".") + 1);
    }

    /**
     * Removes extension from a file
     *
     * @param file file to remove the extension from
     * @return name of the file without the extension
     */
    private String removeExtension(File file) {
        String fname = file.getName();
        int pos = fname.lastIndexOf(".");
        if (pos > 0) {
            fname = fname.substring(0, pos);
        }
        return fname;
    }
}
