package com.dap.blackmud.utils;
import java.io.File;

import javax.swing.filechooser.FileFilter;

public class CustomFileFilter extends FileFilter {
	private String filterTitle;
    private String filterExtension;
    
    //Accept all directories and all gif, jpg, tiff, or png files.
    public CustomFileFilter(String title, String extension) {
        super();
        this.filterTitle = title;
        this.filterExtension = extension;
    }
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        String extension = getExtension(f);
        if (extension != null) {
            if (extension.equals(filterExtension)) {
                    return true;
            } else {
                return false;
            }
        }
        return false;
    }
    //The description of this filter
    public String getDescription() {
        return filterTitle;
    }
    
    /*
     * Get the extension of a file.
     */  
    private String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');
        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
}
