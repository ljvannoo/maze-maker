package com.dap.blackmud.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.StringTokenizer;

/**
 *  A Properties File manager
 */
public class Configuration extends Properties
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String  filename;
    private String  basePropFileName;
    private Class   parentClass;
    private boolean fileExists;
    private boolean localExists;

    // private InputStream mine;
    public Configuration(Class parent, String basePropFileName, String appName)
    {
        localExists = true;
        this.basePropFileName = basePropFileName;
        filename = new String(System.getProperty("user.dir") + "/"
                              + basePropFileName + ".properties");
        fileExists = (new File(filename)).exists();
        if (!fileExists) {
            filename = new String(System.getProperty("user.home")
                                  + File.separatorChar + appName
                                  + File.separatorChar + basePropFileName
                                  + ".properties");
            fileExists = (new File(filename)).exists();
            if (!fileExists)
            {
                filename = new String(getCodeBase() + basePropFileName
                                      + ".properties");
                fileExists = (new File(filename)).exists();
                if (!fileExists)
                {
                    localExists = false;
                    filename = new String("properties/" + basePropFileName
                                          + ".properties");
                    parentClass = parent;
                }
            }
        }
        // mine = parentClass.getResourceAsStream("config.properties");
        loadProperties();
    }

    public boolean loadProperties()
    {
        InputStream fp;
        try
        {
            if (localExists)
                fp = new FileInputStream(filename);
            else
                fp = parentClass.getResourceAsStream(filename);
            load(fp);
            fp.close();
        }
        catch (java.io.IOException e)
        {
            if (e instanceof java.io.FileNotFoundException)
            {
                System.out.println("File '" + filename + "' not found!");
                return false;
            }
            return false;
        }
        return true;
    }

    public String get(String key)
    {
        return (String) super.get(key);
    }

    public String put(String key, String value)
    {
        return (String) super.put(key, value);
    }

    public boolean saveProperties(String appName)
    {
        try
        {
            if (!localExists)
            {
                String path = System.getProperty("user.home")
                                      + File.separatorChar + appName;
                String fileName = path
                                      + File.separatorChar + basePropFileName
                                      + ".properties";
                File filePath = new File(path);
                File file = new File(fileName);
                filePath.mkdirs();
                file.createNewFile();//returns false if the file already exists, that's ok
            }
            System.out.println("Writing login properties to:" + filename);
            store(new FileOutputStream(filename), "Proxy Login Properties");
        }
        catch (java.io.IOException e)
        {
            if (e instanceof java.io.FileNotFoundException)
            {
                System.out.println("File '" + filename + "' not found!");
                return false;
            }
            return false;
        }
        return true;
    }

    public String getCodeBase()
    {
        // this obtains the combined package + class name
        String pcName = new String(getClass().getName());
        // this obtains the actual file name of the class file
        String pcFile = new String((pcName.replace('.', File.separatorChar))
                                   + ".class");
        // obtain the $CLASSPATH from the environment
        String classpath = System.getProperty("java.class.path");
        // split the $CLASSPATH into its components with StringTokenizer
        Enumeration enumer = new StringTokenizer(classpath,
                                               System.getProperty("path.separator"));
        while (enumer.hasMoreElements())
        {
            String dir = (String) enumer.nextElement();
            String filename = dir + File.separatorChar + pcFile;
            File file = new File(filename);
            if (file.exists())
            {
                // return absolute path upto *and including* last file separator
                return (filename.substring(0,
                                           (1 + filename.lastIndexOf(File.separatorChar))));
            }
        }
        // if we havent found the class file have to return a null
        return null;
    }
} // class
