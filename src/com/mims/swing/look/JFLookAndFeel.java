/**
 * 
 */
package com.mims.swing.look;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.JComponent;
import javax.swing.LookAndFeel;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalLookAndFeel;


/**
 * ÃèÊö:.<p>
 *
 * @author ÀîÍþ 
 *
 * @Date: 2011-8-21
 * 
 */
public class JFLookAndFeel extends MetalLookAndFeel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6471905907839618881L;

	protected void initClassDefaults(UIDefaults table)
    {
        super.initClassDefaults(table);
        final String jfPackageName = "com.mims.swing.ctrl.";

        Object[] uiDefaults = {
                   "JFButtonUI", jfPackageName + "JFButtonUI",
//                 "CheckBoxUI", jfPackageName + "MetalCheckBoxUI",
//              "DesktopIconUI", jfPackageName + "MetalDesktopIconUI",
//              "FileChooserUI", jfPackageName + "MetalFileChooserUI",
//            "InternalFrameUI", jfPackageName + "MetalInternalFrameUI",
//       "PopupMenuSeparatorUI", jfPackageName + "MetalPopupMenuSeparatorUI",
//              "ProgressBarUI", jfPackageName + "MetalProgressBarUI",
//              "RadioButtonUI", jfPackageName + "MetalRadioButtonUI",
//               "ScrollPaneUI", jfPackageName + "MetalScrollPaneUI",
//                "SeparatorUI", jfPackageName + "MetalSeparatorUI",
//                   "SliderUI", jfPackageName + "MetalSliderUI",
//                "SplitPaneUI", jfPackageName + "MetalSplitPaneUI",
                   "JFSeparatorUI",jfPackageName + "JFSeparatorUI",
                   "JFSpinnerUI", jfPackageName + "JFSpinnerUI",
               "JFTabbedPaneUI", jfPackageName + "JFTabbedPaneUI",
                  "JFMenuItemUI", jfPackageName + "JFMenuItemUI",
                  "JFMenuUI", jfPackageName + "JFMenuUI",
                  "JFPopupMenuUI", jfPackageName + "JFPopupMenuUI",
                  "JFToolButtonUI", jfPackageName + "JFToolButtonUI",
                  "JFToolBarUI", jfPackageName + "JFToolBarUI",
                  "JFMenuBarUI", jfPackageName + "JFMenuBarUI",
                  "JFLabelContainerUI", jfPackageName + "JFLabelContainerUI",
                  "JFTabbedPaneButtonUI", jfPackageName + "JFTabbedPaneButtonUI",
                  "JFTextFieldUI", jfPackageName + "JFTextFieldUI",
                  "JFComboBoxUI", jfPackageName + "JFComboBoxUI",
                  "JFListUI", jfPackageName + "JFListUI",
                  "JFScrollBarUI", jfPackageName + "JFScrollBarUI",
                  "JFPanelUI", jfPackageName + "JFPanelUI",
                  "JFLabelUI",jfPackageName + "JFLabelUI",
                  "JFTreeUI",jfPackageName + "JFTreeUI",
                  "JFCheckBoxMenuItemUI",jfPackageName + "JFCheckBoxMenuItemUI",
                  "JFOptionPaneUI", jfPackageName + "JFOptionPaneUI",
                  "JFFileChooserUI",jfPackageName + "JFFileChooserUI",
                  "JFDatePickerUI", jfPackageName + "JFDatePickerUI",
//                     "TreeUI", jfPackageName + "MetalTreeUI",
//                 "RootPaneUI", jfPackageName + "MetalRootPaneUI",
        };

        table.putDefaults(uiDefaults);
    }
	
	protected void initSystemColorDefaults(UIDefaults table)
    {
		super.initSystemColorDefaults(table);
		table.putDefaults(JFLookAndFeelColor.getInstance().getSysColors(JFLookAndFeelColor.JF_BLUE_D));
    }
	
	public static Object getUIOfType(ComponentUI ui, Class klass) {
        if (klass.isInstance(ui)) {
            return ui;
        }
        return null;
    }
	
	public static void playSound(JComponent c, Object actionKey) {
        LookAndFeel laf = UIManager.getLookAndFeel();
        if (laf instanceof JFLookAndFeel) {
            ActionMap map = c.getActionMap();
            if (map != null) {
                Action audioAction = map.get(actionKey);
                if (audioAction != null) {
                    // pass off firing the Action to a utility method
                    ((JFLookAndFeel)laf).playSound(audioAction);
                }
            }
        }
    }

	public String getName() {
        return "Metal";
    }

    public String getID() {
        return "Metal";
    }

    public String getDescription() {
        return "The Java(tm) Look and Feel";
    }

    public boolean isNativeLookAndFeel() {
        return false;
    }

    public boolean isSupportedLookAndFeel() {
        return true;
    }
    
    public boolean getSupportsWindowDecorations() {
        return true;
    }
	
}
