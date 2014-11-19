package e1.tools.asbuilder;

import java.util.Iterator;
import java.util.Map;

import e1.tools.asbuilder.utils.DB;

public class QueryProject 
{
	public static void main(String[] args) 
	{
		Map projects = DB.queryProjects(args[0]);

		Iterator it = projects.keySet().iterator();

		while (it.hasNext()) 
		{
			Integer id = (Integer) it.next();
			String pname = (String) projects.get(id);
		}
	}
}
