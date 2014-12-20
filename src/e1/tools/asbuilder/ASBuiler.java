package e1.tools.asbuilder;

import java.io.IOException;

import e1.tools.asbuilder.utils.FileUtils;

public class ASBuiler 
{

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		/**
		 * 源代码路径
		 */
		String srcCodeBase = args[0];
		/**
		 * 编译环境Home目录
		 */
		String asBuilderHome = args[1];
		
		/**
		 * 项目ID
		 */
		String projectID = args[2];
		
		/**
		 * 是否外网包
		 */
		String patch = args[3];
		
		/**
		 * 是否更新代码
		 */
		String update = args[4];
		
		/**
		 * Ant属性文件输出目录
		 */
		String propOutputPath = args[5];
		
		/**
		 * 源资源目录
		 */
		String resBase = args[6];
		
		/**
		 * 版本输出目录
		 */
		String fileOutputPath = args[7];
		
		/**
		 * ResList输出目录
		 */
		String reslistPath = args[8];
		
		boolean isPatch = patch.equalsIgnoreCase("1");
		boolean isUpdate = update.equalsIgnoreCase("1");
		
		CreateAMF createAmf = new CreateAMF();
		int partCount = 1;
		try 
		{
			partCount = createAmf.process(resBase);
		} 
		catch (Exception e1) 
		{
			System.out.println("Create AMF error!" + e1.getMessage());
			e1.printStackTrace();
			return;
		}
		
		System.out.println("SysBuildMsg=2. updatting version");
		int version = FileUtils.updateVersion(srcCodeBase, partCount);
		
		System.out.println("SysBuildMsg=3. creatting ant properties");
		FileUtils.createProperty(asBuilderHome, "", "", projectID, isPatch, isUpdate, propOutputPath, version);
		
		MD5Check MD5Check = new MD5Check();
		try 
		{
			MD5Check.process(resBase, fileOutputPath, isPatch, reslistPath, version);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

}
