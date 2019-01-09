package com.dap.blackmud.tools.mazemaker.utils;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

import com.dap.blackmud.world.RoomTemplate;
import com.dap.blackmud.world.RoomTemplateList;

public class ListCellRenderer extends JLabel implements javax.swing.ListCellRenderer{

    private static final long serialVersionUID = 1L;
    
    private RoomTemplateList roomTemplates = null;
    private ImageIcon defaultIcon = null;
    private ImageIcon checkedIcon = null;
    
    public ListCellRenderer(RoomTemplateList templates) {
        super();
        roomTemplates = templates;
        defaultIcon = new ImageIcon(getClass().getResource("/images/dot.png"));
        checkedIcon = new ImageIcon(getClass().getResource("/images/dot_checked.png"));
    }
    
    /* (non-Javadoc)
     * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
     */
    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {
        if(!(value instanceof RoomTemplate)) {
            setText(value.toString());
            return this;
        }
        RoomTemplate data = (RoomTemplate)value;
        setText(" "+data.toString());
        
        if(roomTemplates.isDefaultTemplate(data))
        	setIcon(checkedIcon);
        else
        	setIcon(defaultIcon);
        
        if(isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
            setOpaque(true);
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
            setOpaque(false);
        }
        	
        return this;
    }

}
