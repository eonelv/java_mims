package e1.tools.asbuilder.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class FileUtils 
{
	private static StringBuilder log = new StringBuilder("");

	public static int updateVersion(String path) 
	{
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = format.format(new Date());

		int version = -1;
		File file = new File(path + "/src/Version.as");

		StringBuilder sbAs = new StringBuilder("");

		BufferedWriter writer = null;

		version = DB.getVersion(path + "/src");
		try {
			sbAs.append("/**").append("\n");
			sbAs.append(
					" * This Class is generated by 'Version Builder'. Never change it by hand.")
					.append("\n");
			sbAs.append(" * @Time ").append(now).append("\n");
			sbAs.append(" */").append("\n");
			sbAs.append("package").append("\n");
			sbAs.append("{").append("\n");
			sbAs.append("\tpublic class Version").append("\n");
			sbAs.append("\t{").append("\n");
			sbAs.append("\t\tpublic function Version(){}").append("\n");
			sbAs.append(
					"\t\tstatic public const VERSION:String = \"V: " + version
							+ " \";").append("\n");
			sbAs.append("\t}").append("\n");
			sbAs.append("}");

			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file)));
			writer.write(sbAs.toString());
			writer.flush();
		} catch (FileNotFoundException e) {
			log.append("ERROR:" + e.toString()).append("\r\n");
			try {
				writer.close();
			} catch (IOException e1) {
				log.append("ERROR:" + e1.toString()).append("\r\n");
			}
		} catch (IOException e) {
			log.append("ERROR:" + e.toString()).append("\r\n");
			try {
				writer.close();
			} catch (IOException e1) {
				log.append("ERROR:" + e1.toString()).append("\r\n");
			}
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				log.append("ERROR:" + e.toString()).append("\r\n");
			}
		}
		return version;
	}

	public static void createBat(String path, String pid) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = format.format(new Date());

		File file = new File(path + "/temp.bat");

		StringBuilder sbAs = new StringBuilder("");

		BufferedWriter writer = null;
		Map projectInfo = DB.queryProject(path, pid, false);
		try {
			sbAs.append("set antfile=" + projectInfo.get("antfile"));
			sbAs.append("\r\n");
			sbAs.append("set basepath=" + projectInfo.get("basepath"));
			sbAs.append("\r\n");
			sbAs.append("set SVN_LIB=" + projectInfo.get("svnlib"));
			sbAs.append("\r\n");
			sbAs.append("set SVN_MAIN=" + projectInfo.get("svnmain"));
			sbAs.append("\r\n");
			writer = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(file),
							"utf-8"));
			writer.write(sbAs.toString());
			writer.flush();

			System.out.println("JAVA::build.bat鍒涘缓瀹屾垚");
		} catch (FileNotFoundException e) {
			log.append("ERROR:" + e.toString()).append("\r\n");
			try {
				/* 136 */writer.close();
			} catch (IOException e1) {
				/* 140 */log.append("ERROR:" + e1.toString()).append("\r\n");
			}
		} catch (IOException e) {
			/* 130 */log.append("ERROR:" + e.toString()).append("\r\n");
			try {
				/* 136 */writer.close();
			} catch (IOException e1) {
				/* 140 */log.append("ERROR:" + e1.toString()).append("\r\n");
			}
		} finally {
			try {
				/* 136 */writer.close();
			} catch (IOException e) {
				/* 140 */log.append("ERROR:" + e.toString()).append("\r\n");
			}
		}
	}

	public static void createProperty(String path, String updateFilePath,
			String swfPath, String pid, boolean isPatch, boolean isUpdate, String outputPath,
			int version) 
	{
		File file = null;

		StringBuilder sbAs = new StringBuilder("");

		BufferedWriter writer = null;

		Map projectInfo = DB.queryProject(path, pid, true);
		try {
			sbAs.setLength(0);
			sbAs.append("PROJECT_NAME=" + projectInfo.get("pname"));
			sbAs.append("\r\n");
			sbAs.append("PROJECT_VERSION=" + projectInfo.get("pvname"));
			sbAs.append("\r\n");

			sbAs.append("PROJECT_SHAR_BASE="
					+ projectInfo.get("psharebase"));
			sbAs.append("\r\n");
			sbAs.append("PROJECT_SHAR_PATH="
					+ projectInfo.get("psharebase") +
					projectInfo.get("pnameen") + "\\\\"
					+ projectInfo.get("pvnameen") + "\\\\");
			sbAs.append("\r\n");
			sbAs.append("PROJECT_LOG_FILE="
					+ projectInfo.get("plogname"));
			sbAs.append("\r\n");

			sbAs.append("MAIL_TO=" + projectInfo.get("pmailto"));
			sbAs.append("\r\n");
			sbAs.append("MAIL_FROM=asbuilder@eone.com");
			sbAs.append("\r\n");
			sbAs.append("MAIL_USER=asbuilder@eone.com");
			sbAs.append("\r\n");
			sbAs.append("MAIL_PASS=123456");
			sbAs.append("\r\n");
			sbAs.append("MAIL_HOST=192.168.0.10");
			sbAs.append("\r\n");
			sbAs.append("MAIL_PORT=25");
			sbAs.append("\r\n");
			sbAs.append("PNUMBER=" + projectInfo.get("pnumber"));
			sbAs.append("\r\n");
			sbAs.append("VERSION=" + version);
			sbAs.append("\r\n");
			if (isPatch)
			{
				sbAs.append("MAIN_APP=" + version);
			}
			else
			{
				sbAs.append("MAIN_APP=" + 1);
			}
			sbAs.append("\r\n");

			processProperty(sbAs, updateFilePath, isPatch);
			processUpdate(sbAs, isUpdate);
			if (processFiles(new File(swfPath))) 
			{
				sbAs.append("ISZIP=1");
				sbAs.append("\r\n");
			} else {
				sbAs.append("ISZIP=0");
				sbAs.append("\r\n");
			}
			file = new File(outputPath + "/build.properties");
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file), "utf-8"));
			writer.write(sbAs.toString());
			writer.flush();

			System.out.println("JAVA::build.properties创建完成");
		} catch (FileNotFoundException e) {
			log.append("ERROR:" + e.toString()).append("\r\n");
			try {
				writer.close();
			} catch (IOException e1) {
				log.append("ERROR:" + e1.toString()).append("\r\n");
			}
		} catch (IOException e) {
			log.append("ERROR:" + e.toString()).append("\r\n");
			try {
				writer.close();
			} catch (IOException e1) {
				log.append("ERROR:" + e1.toString()).append("\r\n");
			}
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				log.append("ERROR:" + e.toString()).append("\r\n");
			}
		}
	}

	private static boolean processFiles(File file) {
		String name = file.getName();
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				boolean hasSwf = processFiles(files[i]);
				if (hasSwf) {
					return true;
				}

			}

		} else if (name.indexOf(".swf") != -1) {
			return true;
		}

		return false;
	}

	private static void processProperty(StringBuilder param, String filePath,
			boolean isPatch) {
		param.append("ISBUILD=1");
		param.append("\r\n");
		if (isPatch) 
		{
			param.append("ISPATCH=1");
			param.append("\r\n");
			return;
		}

		param.append("ISPATCH=0");
		param.append("\r\n");
	}
	
	private static void processUpdate(StringBuilder param, boolean isUpdate) 
	{
		if (isUpdate) 
		{
			param.append("ISUPDATE=1");
			param.append("\r\n");
			return;
		}

		param.append("ISUPDATE=0");
		param.append("\r\n");
	}
}