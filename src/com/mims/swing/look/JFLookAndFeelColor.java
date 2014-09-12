/**
 * 
 */
package com.mims.swing.look;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述:.
 * <p>
 * 
 * @author 李威
 * 
 * @Date: 2011-8-24
 * 
 */
public class JFLookAndFeelColor
{
	private Map defaults = null;

	public Object[] getSysColors(String stytle)
	{
		return (Object[]) defaults.get(stytle);
	}

	private static JFLookAndFeelColor lookAndFeel = null;

	public static String GREEN = "green";

	public static String YELLOW = "yellow";

	public static String BLUE = "blue";

	public static String RED = "red";

	public static String WIN7 = "Win7";

	public static String JF_RED = "JF_RED";
	
	public static String JF_BLUE = "JF_BLUE";
	
	public static String JF_BLUE_D = "JF_BLUE_D";

	private JFLookAndFeelColor()
	{
		defaults = new HashMap();

		Object[] uiDefaults =
		{
				"TOOLBAR_BACKGROUND",
				new Color(111, 129, 251),
				// "TOOLBAR_BACKGROUND", new Color(150,193,252),
				"MENUBAR_BACKGROUND",
				new Color(152, 165, 252),
				"MAIN_TABBED_BACKGROUND",
				new Color(152, 165, 252),
				"MAIN_TABBED_BACKGROUND_SELECTED",
				new Color(232, 233, 232),
				"TABLE_HEADER_BG_COLOR",
				Color.BLUE,
				"BTN_MOUSEOVER_COLOR_TOP",
				new Color(240, 157, 83),
				"BTN_MOUSEOVER_COLOR_BOTTOM",
				new Color(240, 157, 83),
				"TABBED_MORMAL_COLOR",
				new Color(92, 106, 126),// tabbed_normal_color
				"TABBED_SERARATOR_NORMAL_COLOR",
				Color.BLACK,// tabbed_separator_normal_color
				"DEFAULT_TABLE_GRID_COLOR",
				new Color(81, 98, 249),
				// 编辑器颜色
				"EDITOR_FOCUS_COLOR",
				new Color(152, 165, 252),
				"EDITOR_HIGH_LIGHT_COLOR",
				new Color(252, 254, 216),
				"EDITOR_NORMAL_COLOR",
				new Color(165, 168, 175),
				"EDITOR_DISABLE_COLOR",
				new Color(100, 111, 94),
				"PANEL_BACKGROUND",
				new Color(236, 236, 232),
				// 按钮色彩
				"BUTTON_COLOR_1",
				new Color(111, 129, 251),
				"BUTTON_COLOR_2",
				new Color(146, 159, 252),
				// 按钮边框颜色
				"BUTTON_BORDER_COLOR",
				new Color(162, 77, 74),
				"BUTTON_INNER_BORDER_COLOR",
				new Color(162, 77, 74),
				// 按钮颜色
				"BUTTON_COLO_TOP", new Color(242, 242, 242),
				"BUTTON_COLOR_CENTER", new Color(221, 221, 221),
				"BUTTON_COLOR_BOTTOM", new Color(207, 207, 207),

				"BUTTON_COLO_TOP_MOUSEOVER", new Color(234, 246, 253),
				"BUTTON_COLOR_CENTER_MOUSEOVE", new Color(190, 230, 253),
				"BUTTON_COLOR_BOTTOM_MOUSEOVE", new Color(167, 217, 243),

				"BUTTON_COLO_TOP_MOUSEPRESS", new Color(229, 244, 252),
				"BUTTON_COLOR_CENTER_MOUSEPRESS", new Color(152, 209, 239),
				"BUTTON_COLOR_BOTTOM_MOUSEPRESS", new Color(109, 182, 221), };

		defaults.put(JFLookAndFeelColor.GREEN, uiDefaults);

		uiDefaults = new Object[]
		{
				"TOOLBAR_BACKGROUND",
				new Color(50, 117, 178),// new Color(80,168,231),
				"MENUBAR_BACKGROUND",
				new Color(15, 61, 111),// new Color(50,117,178),
				"MAIN_TABBED_BACKGROUND",
				new Color(50, 117, 178),
				"MAIN_TABBED_BACKGROUND_SELECTED",
				new Color(232, 233, 232),
				"TABLE_HEADER_BG_COLOR",
				new Color(126, 233, 217),
				"BTN_MOUSEOVER_COLOR_TOP",
				new Color(166, 245, 254),
				"BTN_MOUSEOVER_COLOR_BOTTOM",
				new Color(126, 233, 217),
				"COLOR_MENU",
				new Color(133, 160, 199),
				"COLOR_MENU1",
				new Color(160, 181, 211),
				"TABBED_MORMAL_COLOR",
				new Color(160, 181, 211),// tabbed_normal_color
				"TABBED_SERARATOR_NORMAL_COLOR",
				Color.BLACK,// tabbed_separator_normal_color
				"DEFAULT_TABLE_GRID_COLOR",
				new Color(81, 98, 249),
				// 编辑器颜色
				"EDITOR_FOCUS_COLOR",
				new Color(50, 117, 178),
				"EDITOR_HIGH_LIGHT_COLOR",
				new Color(252, 254, 216),
				"EDITOR_NORMAL_COLOR",
				new Color(165, 168, 175),
				"EDITOR_DISABLE_COLOR",
				new Color(100, 111, 94),
				"PANEL_BACKGROUND",
				new Color(236, 236, 232),
				// 按钮色彩
				"BUTTON_COLOR_1",
				new Color(111, 129, 251),
				"BUTTON_COLOR_2",
				new Color(146, 159, 252),
				// 按钮边框颜色
				"BUTTON_BORDER_COLOR",
				new Color(162, 77, 74),
				"BUTTON_INNER_BORDER_COLOR",
				new Color(162, 77, 74),
				// 按钮颜色
				"BUTTON_COLO_TOP", new Color(242, 242, 242),
				"BUTTON_COLOR_CENTER", new Color(221, 221, 221),
				"BUTTON_COLOR_BOTTOM", new Color(207, 207, 207),

				"BUTTON_COLO_TOP_MOUSEOVER", new Color(234, 246, 253),
				"BUTTON_COLOR_CENTER_MOUSEOVE", new Color(190, 230, 253),
				"BUTTON_COLOR_BOTTOM_MOUSEOVE", new Color(167, 217, 243),

				"BUTTON_COLO_TOP_MOUSEPRESS", new Color(229, 244, 252),
				"BUTTON_COLOR_CENTER_MOUSEPRESS", new Color(152, 209, 239),
				"BUTTON_COLOR_BOTTOM_MOUSEPRESS", new Color(109, 182, 221), };
		defaults.put(JFLookAndFeelColor.BLUE, uiDefaults);

		uiDefaults = new Object[]
		{
				"TOOLBAR_BACKGROUND",
				new Color(67, 75, 241),
				"MENUBAR_BACKGROUND",
				new Color(152, 165, 252),
				"MAIN_TABBED_BACKGROUND",
				new Color(152, 165, 252),
				"MAIN_TABBED_BACKGROUND_SELECTED",
				new Color(232, 233, 232),
				"TABLE_HEADER_BG_COLOR",
				new Color(126, 233, 217),
				"BTN_MOUSEOVER_COLOR_TOP",
				new Color(166, 245, 254),
				"BTN_MOUSEOVER_COLOR_BOTTOM",
				new Color(126, 233, 217),
				"COLOR_MENU",
				new Color(133, 160, 199),
				"COLOR_MENU1",
				new Color(160, 181, 211),
				"TABBED_MORMAL_COLOR",
				new Color(160, 181, 211),// tabbed_normal_color
				"TABBED_SERARATOR_NORMAL_COLOR",
				Color.BLACK,// tabbed_separator_normal_color
				"DEFAULT_TABLE_GRID_COLOR",
				new Color(81, 98, 249),
				// 编辑器颜色
				"EDITOR_FOCUS_COLOR",
				new Color(152, 165, 252),
				"EDITOR_HIGH_LIGHT_COLOR",
				new Color(252, 254, 216),
				"EDITOR_NORMAL_COLOR",
				new Color(165, 168, 175),
				"EDITOR_DISABLE_COLOR",
				new Color(100, 111, 94),
				"PANEL_BACKGROUND",
				new Color(236, 236, 232),
				// 按钮色彩
				"BUTTON_COLOR_1",
				new Color(111, 129, 251),
				"BUTTON_COLOR_2",
				new Color(146, 159, 252),
				// 按钮边框颜色
				"BUTTON_BORDER_COLOR",
				new Color(162, 77, 74),
				"BUTTON_INNER_BORDER_COLOR",
				new Color(162, 77, 74),
				// 按钮颜色
				"BUTTON_COLO_TOP", new Color(242, 242, 242),
				"BUTTON_COLOR_CENTER", new Color(221, 221, 221),
				"BUTTON_COLOR_BOTTOM", new Color(207, 207, 207),

				"BUTTON_COLO_TOP_MOUSEOVER", new Color(234, 246, 253),
				"BUTTON_COLOR_CENTER_MOUSEOVE", new Color(190, 230, 253),
				"BUTTON_COLOR_BOTTOM_MOUSEOVE", new Color(167, 217, 243),

				"BUTTON_COLO_TOP_MOUSEPRESS", new Color(229, 244, 252),
				"BUTTON_COLOR_CENTER_MOUSEPRESS", new Color(152, 209, 239),
				"BUTTON_COLOR_BOTTOM_MOUSEPRESS", new Color(109, 182, 221), };
		defaults.put(JFLookAndFeelColor.YELLOW, uiDefaults);

		uiDefaults = new Object[]
		{
				"TOOLBAR_BACKGROUND",
				new Color(162, 77, 74),// new Color(208, 167, 164),
				"MENUBAR_BACKGROUND",
				new Color(160, 9, 11),// new Color(162, 77, 74),
				"MAIN_TABBED_BACKGROUND",
				new Color(188, 35, 35),// new Color(208, 167, 164),
				"MAIN_TABBED_BACKGROUND_SELECTED",
				new Color(232, 233, 232),// new Color(126, 233, 217),
				"TABLE_HEADER_BG_COLOR",
				new Color(208, 167, 164),
				"BTN_MOUSEOVER_COLOR_TOP",
				new Color(166, 245, 254),
				"BTN_MOUSEOVER_COLOR_BOTTOM",
				new Color(232, 233, 232),// new Color(126, 233, 217),
				"COLOR_MENU",
				new Color(133, 160, 199),
				"COLOR_MENU1",
				new Color(160, 181, 211),
				"TABBED_MORMAL_COLOR",
				new Color(160, 181, 211),// tabbed_normal_color
				"TABBED_SERARATOR_NORMAL_COLOR",
				Color.BLACK,// tabbed_separator_normal_color
				"DEFAULT_TABLE_GRID_COLOR",
				new Color(81, 98, 249),
				// 编辑器颜色
				"EDITOR_FOCUS_COLOR",
				new Color(188, 35, 35),
				"EDITOR_HIGH_LIGHT_COLOR",
				new Color(252, 254, 216),
				"EDITOR_NORMAL_COLOR",
				new Color(165, 168, 175),
				"EDITOR_DISABLE_COLOR",
				new Color(100, 111, 94),
				"PANEL_BACKGROUND",
				new Color(236, 236, 232),
				// 按钮色彩
				"BUTTON_COLOR_1",
				new Color(111, 129, 251),
				"BUTTON_COLOR_2",
				new Color(146, 159, 252),
				// 按钮边框颜色
				"BUTTON_BORDER_COLOR",
				new Color(162, 77, 74),
				"BUTTON_INNER_BORDER_COLOR",
				new Color(162, 77, 74),
				// 按钮颜色
				"BUTTON_COLO_TOP", new Color(242, 242, 242),
				"BUTTON_COLOR_CENTER", new Color(221, 221, 221),
				"BUTTON_COLOR_BOTTOM", new Color(207, 207, 207),

				"BUTTON_COLO_TOP_MOUSEOVER", new Color(234, 246, 253),
				"BUTTON_COLOR_CENTER_MOUSEOVE", new Color(190, 230, 253),
				"BUTTON_COLOR_BOTTOM_MOUSEOVE", new Color(167, 217, 243),

				"BUTTON_COLO_TOP_MOUSEPRESS", new Color(229, 244, 252),
				"BUTTON_COLOR_CENTER_MOUSEPRESS", new Color(152, 209, 239),
				"BUTTON_COLOR_BOTTOM_MOUSEPRESS", new Color(109, 182, 221), };
		defaults.put(JFLookAndFeelColor.RED, uiDefaults);

		// JF_BLUE
		uiDefaults = new Object[]
		{
				"TOOLBAR_BACKGROUND_SIDE",
				new Color(59, 119, 172),
				"TOOLBAR_BACKGROUND_CENTER",
				new Color(87, 145, 191),
				"MENUBAR_BACKGROUND_1",
				new Color(104, 149, 191),
				"MENUBAR_BACKGROUND_2",
				new Color(57, 115, 170),
				"MENUBAR_BACKGROUND_3",
				new Color(24, 93, 150),
				"MENUBAR_BACKGROUND_4",
				new Color(87, 145, 191),
				
				"MAIN_TABBED_BACKGROUND_1",
				new Color(185, 208, 227),
				"MAIN_TABBED_BACKGROUND_2",
				new Color(185, 208, 227),
				"MAIN_TABBED_BACKGROUND_3",
				new Color(175, 199, 217),
				"MAIN_TABBED_BACKGROUND_4",
				new Color(177, 201, 220),
				
				"MAIN_TABBED_BACKGROUND_SELECTED",
				new Color(232, 233, 232),
				"TABLE_HEADER_BG_COLOR",
				new Color(185, 208, 227),
				"BTN_MOUSEOVER_COLOR_TOP",
				new Color(166, 245, 254),
				"BTN_MOUSEOVER_COLOR_BOTTOM",
				new Color(232, 233, 232),
				"COLOR_MENU",
				new Color(133, 160, 199),
				"COLOR_MENU1",
				new Color(160, 181, 211),
				"TABBED_MORMAL_COLOR",
				new Color(160, 181, 211),
				"TABBED_SERARATOR_NORMAL_COLOR",
				Color.BLACK,
				"DEFAULT_TABLE_GRID_COLOR",
				new Color(81, 98, 249),
				// 编辑器颜色
				"EDITOR_FOCUS_BORDER_COLOR",
				new Color(167, 217, 243),
				"EDITOR_HIGH_LIGHT_COLOR",
				new Color(252, 254, 216),
				"EDITOR_NORMAL_COLOR",
				new Color(165, 168, 175),
				"EDITOR_DISABLE_COLOR",
				new Color(100, 111, 94),
				"PANEL_BACKGROUND",
				new Color(236, 236, 232),
				// 按钮色彩
				"BUTTON_COLOR_1",
				new Color(111, 129, 251),
				"BUTTON_COLOR_2",
				new Color(146, 159, 252),
				// 按钮边框颜色
				"BUTTON_BORDER_COLOR",
				new Color(60, 127, 177),
				"BUTTON_INNER_BORDER_COLOR",
				new Color(43, 209, 252),
				// 按钮颜色
				"BUTTON_COLO_TOP", new Color(242, 242, 242),
				"BUTTON_COLOR_CENTER", new Color(221, 221, 221),
				"BUTTON_COLOR_BOTTOM", new Color(207, 207, 207),

				"BUTTON_COLO_TOP_MOUSEOVER", new Color(234, 246, 253),
				"BUTTON_COLOR_CENTER_MOUSEOVE", new Color(190, 230, 253),
				"BUTTON_COLOR_BOTTOM_MOUSEOVE", new Color(167, 217, 243),

				"BUTTON_COLO_TOP_MOUSEPRESS", new Color(229, 244, 252),
				"BUTTON_COLOR_CENTER_MOUSEPRESS", new Color(152, 209, 239),
				"BUTTON_COLOR_BOTTOM_MOUSEPRESS", new Color(109, 182, 221),
				
				"COMBOBOX_SELECTEDITEM_BACKGROUND",new Color(37,94,162),
				"COMBOBOX_LIST_BORDER_COLOR",new Color(92,133,179),
		};
		defaults.put(JFLookAndFeelColor.JF_BLUE, uiDefaults);
		
		// JF_RED
		uiDefaults = new Object[]
		{
				"TOOLBAR_BACKGROUND_SIDE",
				new Color(157, 66, 68),
				"TOOLBAR_BACKGROUND_CENTER",
				new Color(177, 100, 103),
				"MENUBAR_BACKGROUND_1",
				new Color(177, 115, 155),
				"MENUBAR_BACKGROUND_2",
				new Color(154, 71, 71),
				"MENUBAR_BACKGROUND_3",
				new Color(133, 40, 42),
				"MENUBAR_BACKGROUND_4",
				new Color(152, 62, 64),
				
				"MAIN_TABBED_BACKGROUND_1",
				new Color(224, 193, 194),
				"MAIN_TABBED_BACKGROUND_2",
				new Color(224, 193, 194),
				"MAIN_TABBED_BACKGROUND_3",
				new Color(214, 183, 184),
				"MAIN_TABBED_BACKGROUND_4",
				new Color(216, 185, 186),
				
				"MAIN_TABBED_BACKGROUND_SELECTED",
				new Color(232, 233, 232),// new Color(126, 233, 217),
				"TABLE_HEADER_BG_COLOR",
				new Color(208, 167, 164),
				"BTN_MOUSEOVER_COLOR_TOP",
				new Color(166, 245, 254),
				"BTN_MOUSEOVER_COLOR_BOTTOM",
				new Color(232, 233, 232),// new Color(126, 233, 217),
				"COLOR_MENU",
				new Color(133, 160, 199),
				"COLOR_MENU1",
				new Color(160, 181, 211),
				"TABBED_MORMAL_COLOR",
				new Color(160, 181, 211),// tabbed_normal_color
				"TABBED_SERARATOR_NORMAL_COLOR",
				Color.BLACK,// tabbed_separator_normal_color
				"DEFAULT_TABLE_GRID_COLOR",
				new Color(81, 98, 249),
				// 编辑器颜色
				"EDITOR_FOCUS_BORDER_COLOR",
				new Color(167, 217, 243),
				"EDITOR_HIGH_LIGHT_COLOR",
				new Color(252, 254, 216),
				"EDITOR_NORMAL_COLOR",
				new Color(165, 168, 175),
				"EDITOR_DISABLE_COLOR",
				new Color(100, 111, 94),
				"PANEL_BACKGROUND",
				new Color(236, 236, 232),
				// 按钮色彩
				"BUTTON_COLOR_1",
				new Color(111, 129, 251),
				"BUTTON_COLOR_2",
				new Color(146, 159, 252),
				// 按钮边框颜色
				"BUTTON_BORDER_COLOR",
				new Color(60, 127, 177),
				"BUTTON_INNER_BORDER_COLOR",
				new Color(43, 209, 252),
				// 按钮颜色
				"BUTTON_COLO_TOP", new Color(242, 242, 242),
				"BUTTON_COLOR_CENTER", new Color(221, 221, 221),
				"BUTTON_COLOR_BOTTOM", new Color(207, 207, 207),

				"BUTTON_COLO_TOP_MOUSEOVER", new Color(234, 246, 253),
				"BUTTON_COLOR_CENTER_MOUSEOVE", new Color(190, 230, 253),
				"BUTTON_COLOR_BOTTOM_MOUSEOVE", new Color(167, 217, 243),

				"BUTTON_COLO_TOP_MOUSEPRESS", new Color(229, 244, 252),
				"BUTTON_COLOR_CENTER_MOUSEPRESS", new Color(152, 209, 239),
				"BUTTON_COLOR_BOTTOM_MOUSEPRESS", new Color(109, 182, 221),
				
				"COMBOBOX_SELECTEDITEM_BACKGROUND",new Color(37,94,162),
				"COMBOBOX_LIST_BORDER_COLOR",new Color(92,133,179),
			};
		defaults.put(JFLookAndFeelColor.JF_RED, uiDefaults);

		uiDefaults = new Object[]
		{
				"TOOLBAR_BACKGROUND",
				new Color(167, 217, 243),
				"MENUBAR_BACKGROUND_TOP",
				new Color(235, 239, 248),
				"MENUBAR_BACKGROUND_BOTTOM",
				new Color(214, 221, 239),
				"MAIN_TABBED_BACKGROUND",
				new Color(167, 217, 243),
				"MAIN_TABBED_BACKGROUND_SELECTED",
				new Color(232, 233, 232),
				"TABLE_HEADER_BG_COLOR",
				new Color(208, 167, 164),
				"BTN_MOUSEOVER_COLOR_TOP",
				new Color(166, 245, 254),
				"BTN_MOUSEOVER_COLOR_BOTTOM",
				new Color(232, 233, 232),
				"COLOR_MENU",
				new Color(133, 160, 199),
				"COLOR_MENU1",
				new Color(160, 181, 211),
				"TABBED_MORMAL_COLOR",
				new Color(160, 181, 211),
				"TABBED_SERARATOR_NORMAL_COLOR",
				Color.BLACK,
				"DEFAULT_TABLE_GRID_COLOR",
				new Color(81, 98, 249),
				// 编辑器颜色
				"EDITOR_FOCUS_COLOR",
				new Color(167, 217, 243),
				"EDITOR_HIGH_LIGHT_COLOR",
				new Color(252, 254, 216),
				"EDITOR_NORMAL_COLOR",
				new Color(165, 168, 175),
				"EDITOR_DISABLE_COLOR",
				new Color(100, 111, 94),
				"PANEL_BACKGROUND",
				new Color(236, 236, 232),
				// 按钮色彩
				"BUTTON_COLOR_1",
				new Color(111, 129, 251),
				"BUTTON_COLOR_2",
				new Color(146, 159, 252),
				// 按钮边框颜色
				"BUTTON_BORDER_COLOR",
				new Color(60, 127, 177),
				"BUTTON_INNER_BORDER_COLOR",
				new Color(43, 209, 252),
				// 按钮颜色
				"BUTTON_COLO_TOP", new Color(242, 242, 242),
				"BUTTON_COLOR_CENTER", new Color(221, 221, 221),
				"BUTTON_COLOR_BOTTOM", new Color(207, 207, 207),

				"BUTTON_COLO_TOP_MOUSEOVER", new Color(234, 246, 253),
				"BUTTON_COLOR_CENTER_MOUSEOVE", new Color(190, 230, 253),
				"BUTTON_COLOR_BOTTOM_MOUSEOVE", new Color(167, 217, 243),

				"BUTTON_COLO_TOP_MOUSEPRESS", new Color(229, 244, 252),
				"BUTTON_COLOR_CENTER_MOUSEPRESS", new Color(152, 209, 239),
				"BUTTON_COLOR_BOTTOM_MOUSEPRESS", new Color(109, 182, 221), };
		defaults.put(JFLookAndFeelColor.WIN7, uiDefaults);
		
		createBlueSkin();
	}
	
	private void createBlueSkin(){
		// JF_BLUE
		Object[] uiDefaults = new Object[]
		{
				"TOOLBAR_BACKGROUND_SIDE",
				new Color(59, 119, 172),
				"TOOLBAR_BACKGROUND_CENTER",
				new Color(87, 145, 191),
				"MENUBAR_BACKGROUND_1",
				new Color(104, 149, 191),
				"MENUBAR_BACKGROUND_2",
				new Color(57, 115, 170),
				"MENUBAR_BACKGROUND_3",
				new Color(24, 93, 150),
				"MENUBAR_BACKGROUND_4",
				new Color(87, 145, 191),
				
				//主页签背景色
				"MAIN_TABBED_BACKGROUND_1",
				new Color(185, 208, 227),
				"MAIN_TABBED_BACKGROUND_2",
				new Color(185, 208, 227),
				"MAIN_TABBED_BACKGROUND_3",
				new Color(175, 199, 217),
				"MAIN_TABBED_BACKGROUND_4",
				new Color(177, 201, 220),
				
				//主页签选中颜色
				"MAIN_TABBED_BACKGROUND_SELECTED_1",
				new Color(245, 245, 245),
				"MAIN_TABBED_BACKGROUND_SELECTED_2",
				new Color(243, 243, 243),
				"MAIN_TABBED_BACKGROUND_SELECTED_3",
				new Color(221, 220, 220),
				"MAIN_TABBED_BACKGROUND_SELECTED_4",
				new Color(242, 241, 241),
				
				//普通页签背景色
				"TABBED_BACKGROUND_SELECTED_1",
				new Color(243, 244, 245),
				"TABBED_BACKGROUND_SELECTED_2",
				new Color(228, 230, 232),
				"TABBED_BACKGROUND_SELECTED_3",
				new Color(214, 218, 221),
				"TABBED_BACKGROUND_SELECTED_4",
				new Color(214, 218, 221),
				
				//普通页签选中背景色
				"TABBED_BACKGROUND_1",
				new Color(225, 225, 225),
				"TABBED_BACKGROUND_2",
				new Color(246, 246, 246),
				"TABBED_BACKGROUND_3",
				new Color(236, 236, 236),
				"TABBED_BACKGROUND_4",
				new Color(244, 244, 244),
				
				//表格背景及选中颜色
				"TABLE_HEADER_BG_COLOR",
				new Color(201, 222, 240),
				"TABLE_ROW_SELECTED_COLOR",
				new Color(223, 239, 245),
				"TABLE_COLUMN_INDEX",
				new Color(215, 231, 244),
				
				"BTN_MOUSEOVER_COLOR_TOP",
				new Color(166, 245, 254),
				"BTN_MOUSEOVER_COLOR_BOTTOM",
				new Color(232, 233, 232),
				"COLOR_MENU",
				new Color(133, 160, 199),
				"COLOR_MENU1",
				new Color(160, 181, 211),
				"TABBED_MORMAL_COLOR",
				new Color(160, 181, 211),
				"TABBED_SERARATOR_NORMAL_COLOR",
				Color.BLACK,
				"DEFAULT_TABLE_GRID_COLOR",
				new Color(81, 98, 249),
				// 编辑器颜色
				"EDITOR_FOCUS_BORDER_COLOR",
				new Color(59, 119, 172),
				"EDITOR_HIGH_LIGHT_COLOR",
				new Color(252, 254, 216),
				"EDITOR_NORMAL_COLOR",
				new Color(165, 168, 175),
				"EDITOR_DISABLE_COLOR",
				new Color(100, 111, 94),
				
				//Panel颜色组
				"MAIN_PANEL_BACKGROUND",
				new Color(231,237,242),
				"PANEL_BACKGROUND",
				new Color(236, 236, 232),
				// 按钮色彩
				"BUTTON_COLOR_1",
				new Color(111, 129, 251),
				"BUTTON_COLOR_2",
				new Color(146, 159, 252),
				// 按钮边框颜色
				"BUTTON_BORDER_COLOR",
				new Color(60, 127, 177),
				"BUTTON_INNER_BORDER_COLOR",
				new Color(43, 209, 252),
				// 按钮颜色
				"BUTTON_COLOR_TOP", new Color(242, 242, 242),
				"BUTTON_COLOR_CENTER", new Color(221, 221, 221),
				"BUTTON_COLOR_BOTTOM", new Color(207, 207, 207),
				
				//工具栏按钮颜色
				"TOOL_BUTTON_COLOR_TOP", new Color(219, 239, 247),
				"TOOL_BUTTON_COLOR_BOTTOM", new Color(151, 190, 225),
				"TOOL_BUTTON_COLOR_BORDER", new Color(17, 82, 133),
				
				//弹出菜单颜色组
				"POPUP_MENU_BACKGROUND", new Color(225, 241, 251),
				"POPUP_MENU_ICON_BACKGROUND_1", new Color(204, 230, 250),
				"POPUP_MENU_ICON_BACKGROUND_2", new Color(160, 202, 228),
				"POPUP_MENU_SELECTED_BACKGROUND", new Color(70, 138, 191),

				"BUTTON_COLOR_TOP_MOUSEOVER", new Color(234, 246, 253),
				"BUTTON_COLOR_CENTER_MOUSEOVE", new Color(190, 230, 253),
				"BUTTON_COLOR_BOTTOM_MOUSEOVE", new Color(167, 217, 243),

				"BUTTON_COLOR_TOP_MOUSEPRESS", new Color(229, 244, 252),
				"BUTTON_COLOR_CENTER_MOUSEPRESS", new Color(152, 209, 239),
				"BUTTON_COLOR_BOTTOM_MOUSEPRESS", new Color(109, 182, 221),
				
				"COMBOBOX_SELECTEDITEM_BACKGROUND",new Color(37,94,162),
				"COMBOBOX_LIST_BORDER_COLOR",new Color(92,133,179),
		};
		defaults.put(JFLookAndFeelColor.JF_BLUE_D, uiDefaults);
	}

	public static JFLookAndFeelColor getInstance()
	{
		if (lookAndFeel == null)
		{
			synchronized (JFLookAndFeelColor.class)
			{
				if (lookAndFeel == null)
				{
					lookAndFeel = new JFLookAndFeelColor();
				}
			}
		}
		return lookAndFeel;
	}
}
