package com.mims.swing.layout;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JToolBar;
import javax.swing.border.AbstractBorder;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class JButtonBorder  extends AbstractBorder implements UIResource {
    protected static Insets borderInsets = new Insets( 2, 2, 2, 2 );

    public void paintBorder( Component c, Graphics g, int x, int y, int w, int h ) {
        JButton b = (JButton) c;
	    ButtonModel model = b.getModel();

	    g.translate( x, y );

	    if ( c.getParent() instanceof JToolBar ) {
	        if ( model.isArmed() || model.isSelected() ) {
//	            g.setColor( MetalLookAndFeel.getControlDarkShadow() );
//	            g.drawLine( 0, 0, w - 2, 0 );
//	            g.drawLine( 0, 0, 0, h - 1 );
//	            g.drawLine( w - 2, 2, w - 2, h - 1 );
//
//	            g.setColor( MetalLookAndFeel.getPrimaryControlHighlight() );
//	            g.drawLine( w - 1, 1, w - 1, h - 1 );
//
//	            g.setColor( MetalLookAndFeel.getMenuBackground() );
//	            g.drawLine( w - 1, 0, w - 1, 0 );
	        	
	        	
	        	g.setColor( MetalLookAndFeel.getPrimaryControlHighlight() );
	        	
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
//	            g.setColor( MetalLookAndFeel.getPrimaryControlDarkShadow() );
//	            g.drawLine( 0, 0, w - 1, 0 );
//
//	            g.setColor( MetalLookAndFeel.getPrimaryControlHighlight() );
//	            g.drawLine( 0, h - 1, w - 1, h - 1 );
	        	
	            g.setColor( MetalLookAndFeel.getPrimaryControlHighlight() );
//	            g.drawLine( 0, 0, 0, h - 1 );
//	        	g.setColor(Color.RED);
	            g.drawLine( 2, 0, w-3, 0 );
	            g.drawLine( 0, 2, 0, h-3);
	            g.drawLine( w-1, 2, w-1, h-2);
	            g.drawLine( 2, h-1, w-3, h-1 );
	            
	            g.drawLine( 2, 0, 0, 2 );
	            g.drawLine( w-3, 0, w-1, 2 );
	            g.drawLine( 0, h-3, 2, h-1 );
	            g.drawLine( w-3, h-1, w-1, h-2 );
	        } else {
	        	g.setColor(Color.RED);
	        	g.fillRect(1, 1, w-2, h-2);
	            g.setColor( MetalLookAndFeel.getPrimaryControlHighlight() );
//	            g.drawLine( 0, 0, 0, h - 1 );
//	        	g.setColor(Color.RED);
	            g.drawLine( 2, 0, w-3, 0 );
	            g.drawLine( 0, 2, 0, h-3);
	            g.drawLine( w-1, 2, w-1, h-2);
	            g.drawLine( 2, h-1, w-3, h-1 );
	            
	            g.drawLine( 2, 0, 0, 2 );
	            g.drawLine( w-3, 0, w-1, 2 );
	            g.drawLine( 0, h-3, 2, h-1 );
	            g.drawLine( w-3, h-1, w-1, h-2 );
	            
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
