package com.mims.swing.layout;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.ButtonModel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.border.AbstractBorder;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class JMenuItemBorder  extends AbstractBorder implements UIResource {
    protected static Insets borderInsets = new Insets( 2, 2, 2, 2 );

    public void paintBorder( Component c, Graphics g, int x, int y, int w, int h ) {
        JMenuItem b = (JMenuItem) c;
	    ButtonModel model = b.getModel();

	    g.translate( x, y );

	    if ( c.getParent() instanceof JMenuBar ) {
	        if ( model.isArmed() || model.isSelected() ) {        	
	        	
	        	g.setColor( Color.LIGHT_GRAY );
	        	
	            g.fillRect(0, 0, w, h);
	            g.drawLine( 2, 0, w-3, 0 );
	            g.drawLine( 0, 2, 0, h-3);
	            g.drawLine( w-1, 2, w-1, h-2);
	            g.drawLine( 2, h-1, w-3, h-1 );
	            
	            g.drawLine( 2, 0, 0, 2 );
	            g.drawLine( w-3, 0, w-1, 2 );
	            g.drawLine( 0, h-3, 2, h-1 );
	            g.drawLine( w-3, h-1, w-1, h-2 );
	        }
	    } else {
	        if (  model.isArmed() || ( c instanceof JMenu && model.isSelected() ) ) {	        	
//	            g.setColor( MetalLookAndFeel.getPrimaryControlHighlight() );
	        	g.setColor( Color.LIGHT_GRAY );
	            
	            g.drawLine( 2, 0, w-3, 0 );
	            g.drawLine( 0, 2, 0, h-3);
	            g.drawLine( w-1, 2, w-1, h-2);
	            g.drawLine( 2, h-1, w-3, h-1 );
	            
	            g.drawLine( 2, 0, 0, 2 );
	            g.drawLine( w-3, 0, w-1, 2 );
	            g.drawLine( 0, h-3, 2, h-1 );
	            g.drawLine( w-3, h-1, w-1, h-2 );
	        } else {
//	            g.setColor( MetalLookAndFeel.getPrimaryControlHighlight() );
//	            g.drawLine( 2, 0, w-3, 0 );
//	            g.drawLine( 0, 2, 0, h-3);
//	            g.drawLine( w-1, 2, w-1, h-2);
//	            g.drawLine( 2, h-1, w-3, h-1 );
//	            
//	            g.drawLine( 2, 0, 0, 2 );
//	            g.drawLine( w-3, 0, w-1, 2 );
//	            g.drawLine( 0, h-3, 2, h-1 );
//	            g.drawLine( w-3, h-1, w-1, h-2 );
	            
	        }
	    }

	    g.translate( -x, -y );
    }

    public Insets getBorderInsets( Component c ) {
        return borderInsets;
    }

    public Insets getBorderInsets(Component c, Insets newInsets) {
	    newInsets.top = borderInsets.top;
	    newInsets.left = borderInsets.left;
	    newInsets.bottom = borderInsets.bottom;
	    newInsets.right = borderInsets.right;
	    return newInsets;
    }	
}
