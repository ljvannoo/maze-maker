/*
 * FileWrapper.java
 *
 * Created on March 15, 2003, 11:21 PM
 */

package com.dap.blackmud.utils;
/**
 *
 * @author  a1146
 * @version 
 */
import java.io.*;
import java.util.StringTokenizer;
import java.util.Vector;

public class FileWrapper extends java.lang.Object {

    /** Creates new FileWrapper */
    public FileWrapper(File file) {
        thefile = file;
    }
    
    public Character readCharacterField() {
        Character achar=new Character('e');
        int byteread=0;
        
        try
        {
            while(byteread != -1)
            {
                byteread = reader.read();
                if(byteread == -1)
                    break;
                
                achar = new Character((char)byteread);
                if(!achar.isWhitespace(achar.charValue()))
                    break;
            }
        }
        catch(IOException ioe)
        {
            System.out.println("IO error in FileWrapper::readField() - '" + ioe +"'");
            return new Character('e');
        }
        if(byteread==-1)
            return new Character('e');
        return achar;
    }
    
    public String readLine() {
        String line = new String();
        Character achar = new Character('e');
        int byteread=0;
        
        try
        {
            while(byteread != -1)
            {
                byteread = reader.read();
                if(byteread == 13 || byteread == -1 || achar.getType((char)byteread)==achar.LINE_SEPARATOR || achar.getType((char)byteread)==achar.PARAGRAPH_SEPARATOR)
                    break;
                
                //System.out.println("Char: '"+(char)byteread+"', Value: "+byteread);
                achar = new Character((char)byteread);
                line = line.concat(achar.toString());
            }
        }
        catch(IOException ioe)
        {
            System.out.println("IO error in FileWrapper::readLine() - '" + ioe +"'");
            return new String("eof");
        }
        if(byteread==-1 && line.length() < 2)
            return new String("eof");
        line = line.trim();
        return line;
    }
    
    public String readLine(int num) {
        String line = new String();
        Character achar = new Character('e');
        int byteread=0;
        int totalbytes=0;
        
        try
        {
            while(byteread != -1)
            {
                if(totalbytes>num)
                    break;
                
                byteread = reader.read();
                if(byteread == 13 || byteread == -1 || achar.getType((char)byteread)==achar.LINE_SEPARATOR || achar.getType((char)byteread)==achar.PARAGRAPH_SEPARATOR)
                    break;
                
                //System.out.println("Char: '"+(char)byteread+"', Value: "+byteread);
                achar = new Character((char)byteread);
                line = line.concat(achar.toString());
                totalbytes++;
            }
        }
        catch(IOException ioe)
        {
            System.out.println("IO error in FileWrapper::readLine() - '" + ioe +"'");
            return new String("eof");
        }
        if(byteread==-1 && line.length() < 2)
            return new String("eof");
        line = line.trim();
        return line;
    }
    
    public String readField() {
        String line = new String();
        Character achar;
        int byteread=0;
        
        try
        {
            while(byteread != -1)
            {
                byteread = reader.read();
                if(byteread == 126 || byteread == -1)
                    break;
                
                if(byteread==13)
                    byteread=(int)' ';
                
                if(byteread != 10)
                {
                    achar = new Character((char)byteread);
                    line = line.concat(achar.toString());
                }
            }
        }
        catch(IOException ioe)
        {
            System.out.println("IO error in FileWrapper::readField() - '" + ioe +"'");
            return new String("eof");
        }
        if(byteread==-1 && line.length() < 2)
            return new String("eof");
        line = line.trim();
        return line;
    }
    
    public Integer readNumber() {
        String line = new String();
        Character achar = new Character('e');
        Integer thenumber = new Integer(0);
        int byteread=0;
        
        try
        {
            while(byteread != -1)
            {
                byteread = reader.read();
                if(byteread == -1)
                    break;
                
                achar = new Character((char)byteread);
                //System.out.println("--     '"+achar.charValue()+"'");
                if(!achar.isWhitespace((char)byteread))
                    break;
            }
            while(byteread != -1)
            {
                //System.out.println(" -     '"+achar.charValue()+"'");
                if(!achar.isWhitespace((char)byteread) && (achar.isDigit((char)byteread) || byteread==45))
                    line = line.concat(achar.toString());
                else
                    break;
                byteread = reader.read();
                if(byteread == -1)
                    break;
                
                achar = new Character((char)byteread);
            }
        }
        catch(IOException ioe)
        {
            System.out.println("IO error in FileWrapper::readNumber() - '" + ioe +"'");
            return new Integer(-1);
        }
        //System.out.println(" -   '"+line+"'");
        thenumber=new Integer(thenumber.parseInt(line));
        return thenumber;
    }
    
    public String readToWhiteSpace() {
        String line = new String();
        Character achar = new Character('e');
        int byteread=0;
        
        try
        {
            while(byteread != -1)
            {
                byteread = reader.read();
                if(byteread == -1)
                    break;
                
                achar = new Character((char)byteread);
                if(!achar.isWhitespace(achar.charValue()))
                    break;
            }
            while(byteread != -1)
            {   
                line = line.concat(achar.toString());
                
                byteread = reader.read();
                if(byteread == -1)
                    break;
                
                achar = new Character((char)byteread);
                if(achar.isWhitespace(achar.charValue()))
                    break;
            }
        }
        catch(IOException ioe)
        {
            System.out.println("IO error in FileWrapper::readField() - '" + ioe +"'");
            return new String();
        }
        if(byteread==-1 && line.length() < 2)
            return new String("eof");
        line = line.trim();
        return line;
    }
    
    public boolean open(char newmode) {
        mode = newmode;
        try
        {
            if(mode == 'r')
            {
                reader = new InputStreamReader(new FileInputStream(thefile),"ASCII");
                //System.out.println("Encoding: "+reader.getEncoding());
            }
            else
                writer = new OutputStreamWriter(new FileOutputStream(thefile),"ASCII");
        }
        catch(IOException ioe)
        {
            System.out.println("IO error in FileWrapper::open() - '" + ioe +"'");
            return false;
        }
        return true;
    }
 
    public void close() {
        try
        {
            if(mode == 'r')
                reader.close();
            else
                writer.close();
        }
        catch(IOException ioe)
        {
            System.out.println("IO error in FileWrapper::close() - '" + ioe +"'");
        }
    }
    
    public void writeString(java.lang.String string) {
        try
        {
            for(int i=0;i<string.length();i++)
            {
                writer.write(string.charAt(i));
            }
        }
        catch(IOException ioe)
        {
            System.out.println("IO error in FileWrapper::writeString() - '" + ioe +"'");
        }
    }
    
    public static String wrapString(String text, int lineLength, String EOL) {
		StringTokenizer tokenizer = new StringTokenizer(text);
		String wrappedString = "";
		Vector lines = new Vector();
		String newLine = "";
		String nextToken = null;
		
		while(tokenizer.hasMoreElements()) {
			nextToken = tokenizer.nextToken();
			if((newLine.length() + nextToken.length() + 1) < lineLength) {
				if(newLine.length() != 0)
					newLine += " ";
				newLine += nextToken;
			} else {
				lines.add(newLine);
				newLine = nextToken;
			}
		}
		
		if(newLine.length() > 0)
			lines.add(newLine);
		
		for(int i = 0; i < lines.size(); i++) {
			wrappedString += lines.get(i)+EOL;
		}
		return wrappedString;
	}
    
    public String getPath() {
        return thefile.getPath();
    }
    
    private char mode;
    private File thefile;
    private InputStreamReader reader;
    private OutputStreamWriter writer;
}
