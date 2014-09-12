package com.mims.swing.ctrl;

import javax.swing.JComboBox;
import javax.swing.plaf.basic.BasicComboPopup;

import com.mims.swing.look.JFBorders;

public class TestComboBoxPop extends BasicComboPopup
{

	public TestComboBoxPop(JComboBox combo)
	{
		super(combo);
	}

	protected void configurePopup() {
		super.configurePopup();

//        setBorder(new LineBorder(Color.BLACK, 2));
        setBorder(new JFBorders.ListSelectedBorder());
        setOpaque( true );
        add( scroller );
        setDoubleBuffered( true );
        setFocusable( true );
    }
}
