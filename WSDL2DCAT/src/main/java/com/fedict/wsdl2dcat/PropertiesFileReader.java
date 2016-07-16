/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fedict.wsdl2dcat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Miguel
 */
public class PropertiesFileReader {

    public boolean readPropertiesFile(Converter converter, String filename) {
        Properties prop = new Properties();
        File file = new File(filename);
        FileInputStream fileInputStream = null;
        boolean propertyHasValue = true;
        try {
            if (!file.exists()) {
                Logger.getLogger(WSDL2DCAT.class.getName()).log(Level.INFO, filename + "Sorry, unable to find properties file {0}", filename);
                return false;
            }
            fileInputStream = new FileInputStream(file);
            //load a properties file from class path, inside static method
            prop.load(new FileInputStream(file));

            //get the property value and add it to the converter
            String outputdir = prop.getProperty("outputdir", "");
            String inputdir = prop.getProperty("inputdir", "");
            String transformationdir = prop.getProperty("transformationdir", "");

            if (outputdir.equals("") && !inputdir.equals("") && !transformationdir.equals("")) {
                propertyHasValue = false;
            }

            System.out.println(inputdir);
            System.out.println(outputdir);
            System.out.println(transformationdir);
            converter.setInputDir(inputdir);
            converter.setOutputDir(outputdir);
            converter.setStylesheetDir(transformationdir);

        } catch (IOException ex) {
            Logger.getLogger(WSDL2DCAT.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException ex) {
                    Logger.getLogger(WSDL2DCAT.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        return propertyHasValue;
    }
}
