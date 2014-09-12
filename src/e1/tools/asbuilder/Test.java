package e1.tools.asbuilder;

import net.sf.json.JSONObject;
import e1.tools.asbuilder.utils.EJSON;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		boolean[] boolArray = new boolean[]{true,false,true};      
		EJSON json = new EJSON(null);
		json.setID("liwei");
		json.setVersion(1);
		json.setSize(1);
		System.out.println(json.toString());
		
		JSONObject arra = JSONObject.fromObject("{\"1\":{\"id\":1,\"userAddPercentLev4\":0,\"userAddPercentLev5\":0}}");
		System.out.println(arra.toString());
	}

}
