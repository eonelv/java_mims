package e1.tools;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 禅道bug查询
 * 
 * @author lv
 *
 */
public class BugReport 
{

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		String driver = "com.mysql.jdbc.Driver";

		// URL指向要访问的数据库名scutcs
		String url = "jdbc:mysql://192.168.0.10:3306/zentao";

		// MySQL配置时的用户名
		String user = "ouyang";

		// Java连接MySQL配置时的密码
		String password = "ouyang";
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		try 
		{
			// 加载驱动程序
			Class.forName(driver);
	
			// 连续数据库
			conn = DriverManager.getConnection(url, user, password);
	
			if (!conn.isClosed())
			{
				System.out.println("Succeeded connecting to the Database!");
			}
	
			// statement用来执行SQL语句
	
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			Calendar calendar = Calendar.getInstance();
			Calendar calendarCreate = Calendar.getInstance();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String queryDate = null;
			String createDateBegin = null;
			String createDateEnd = null;
			while (true)
			{
				String cmd = null;
				System.out.println("输入P 回车查询前一天bug解决数量");
				System.out.println("输入N 回车查询后一天bug解决数量");
				System.out.println("输入C 回车查询当天bug解决数量");
				System.out.println("输入产品编号 回车查询当月bug新增数量");
				cmd = reader.readLine();
				if (cmd.equalsIgnoreCase("P") || cmd.equalsIgnoreCase("N") || cmd.equalsIgnoreCase("C"))
				{
					if (cmd.equalsIgnoreCase("P"))
					{
						calendar.add(Calendar.DAY_OF_MONTH, -1);
					}
					else if (cmd.equalsIgnoreCase("N"))
					{
						calendar.add(Calendar.DAY_OF_MONTH, 1);
					}
					
					queryDate = format.format(calendar.getTime());
					// 要执行的SQL语句
					String sql = "SELECT resolvedBy as name, count(1) as count FROM zt_bug WHERE Date(resolvedDate) = ? group by resolvedBy";
//					String sql = "SELECT resolvedBy, id FROM zt_bug WHERE Date(resolvedDate) = ? and resolvedBy = 'carreyzhou' order by id";
					statement = conn.prepareStatement(sql);
					statement.setString(1, queryDate);
					
					rs = statement.executeQuery();
					System.out.println("Bug解决数量查询，查询日期为" + queryDate);
					while (rs.next())
					{
						System.out.print(rs.getString("name") + "\t");
						System.out.println(rs.getString("count"));
					}
				}
				else
				{
					Integer projectID = 0;
					try
					{
						projectID = Integer.parseInt(cmd);
					}
					catch (NumberFormatException e)
					{
						System.out.format("%s\t%s\n", "输入参数错误", cmd);
						continue;
					}
					calendarCreate.set(Calendar.DAY_OF_MONTH, 1);
					createDateBegin = format.format(calendarCreate.getTime());
					calendarCreate.add(Calendar.MONTH, 1);
					createDateEnd = format.format(calendarCreate.getTime());
					
					String sql = "SELECT DATE_FORMAT(openedDate, '%Y-%m-%d') as date, count(1) as count FROM zt_bug WHERE Date(openedDate) >= ? and Date(openedDate) < ? and product = ? group by DATE_FORMAT(openedDate, '%Y-%m-%d')";
					statement = conn.prepareStatement(sql);
					statement.setString(1, createDateBegin);
					statement.setString(2, createDateEnd);
					statement.setInt(3, projectID.intValue());
					
					rs = statement.executeQuery();
					System.out.println("当月bug，查询日期为" + createDateBegin);
					while (rs.next())
					{
						System.out.print(rs.getString("date") + "\t");
						System.out.println(rs.getString("count"));
					}
				}
				
				System.out.println("----------------------------------------");
				System.out.println("");
			}
			
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			try 
			{
				rs.close();
				statement.close();
				conn.close();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	private void queryResolved(String cmd)
	{
		
	}

}
