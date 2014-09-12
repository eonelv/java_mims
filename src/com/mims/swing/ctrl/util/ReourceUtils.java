/**
 * 
 */
package com.mims.swing.ctrl.util;

import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * ÃèÊö:.<p>
 *
 * @author ÀîÍþ 
 *
 * @Date: 2011-8-14
 * 
 */
public class ReourceUtils
{
	private static String DEFAULT_PATH = "com.mims.swing.res.";
	
	private static URL getResource(String path, String name) {
		String file = path.replace(".", "/");
		file = file + name;
		
        ClassLoader cl = ReourceUtils.class.getClassLoader();
        if (cl==null) {
            return ClassLoader.getSystemResource(file);
        }
        return cl.getResource(file);
    }
	
	public static Icon getIcon(String name){
		return getIcon(null,name);
	}
	
	public static Icon getIcon(String path, String name){
		if (path == null){
			path = ReourceUtils.DEFAULT_PATH;
		}
		path = path+".";
		URL url = getResource(path, name);
		
		Icon icon = new ImageIcon(url);
		return icon;
	}
}
