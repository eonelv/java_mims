package com.mims.app;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.mims.swing.ctrl.JFButton;
import com.mims.swing.layout.FlexLayout;
import com.mims.swing.layout.LayoutProperty;

public class SettingUI extends JFrame 
{
	private static final long serialVersionUID = 1L;

	private JButton btnNext;
	
	private JLabel labelDBPath;
	private JLabel labelAccount;
	private JLabel labelPass;
	private JLabel labelClientID;
	private JLabel labelGoodsCode;
	private JLabel labelMachineNum;
	private JLabel labelSOAPInterface;
	
	private JTextField txtDBPath;
	private JTextField txtAccount;
	private JPasswordField txtPass;
	private JTextField txtClientID;
	private JTextField txtGoodsCode;
	private JTextField txtMachineNum;
	private JTextField txtSOAPInterface;
	
	private boolean isDBOK = false;
	
	public SettingUI()
	{
		super();
		setSize(1000, 700);
		JFrame.setDefaultLookAndFeelDecorated(false);
		JDialog.setDefaultLookAndFeelDecorated(false);
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		int width = tk.getScreenSize().width;
		int height = tk.getScreenSize().height;
		setTitle("sala - intown - setting");
		setLocation(width / 2 - 400, height / 2 - 300);
		
		init();
	}
	
	private void init()
	{
//		try 
//		{
//			encrypt.init("intown");
//		} 
//		catch (UnsupportedEncodingException e1) 
//		{
//			return;
//		}
		
		JPanel contentPanel = (JPanel) getContentPane();
		contentPanel.setLayout(new FlexLayout());
		contentPanel.putClientProperty(LayoutProperty.LAYOUT_PARENT_PROPERTY,
				new LayoutProperty(0, 0, 800, 600));
		
		btnNext = new JFButton("下一步");
		btnNext.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String textAccount = txtAccount.getText().trim();
				char[] textPass = txtPass.getPassword();
				if (textAccount.length() == 0 || textPass.length == 0)
				{
					return;
				}
				initDB();
			}
		});
		
		btnNext.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(660, 550, 100, 25, LayoutProperty.BOTTOM
						| LayoutProperty.RIGHT));
		
		labelDBPath = new JLabel("数  据  库： ");
		labelDBPath.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 42, 100, 25, LayoutProperty.TOP
						| LayoutProperty.LEFT));
		txtDBPath = new JTextField();
		txtDBPath.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(120, 42, 600, 25, LayoutProperty.TOP
						| LayoutProperty.LEFT | LayoutProperty.RIGHT_RESIZE));
		
		labelAccount = new JLabel("账        号： ");
		labelAccount.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 72, 100, 25, LayoutProperty.TOP
						| LayoutProperty.LEFT));
		txtAccount = new JTextField();
		txtAccount.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(120, 72, 600, 25, LayoutProperty.TOP
						| LayoutProperty.LEFT | LayoutProperty.RIGHT_RESIZE));
		
		labelPass = new JLabel("密        码： ");
		labelPass.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 102, 100, 25, LayoutProperty.TOP
						| LayoutProperty.LEFT));
		txtPass = new JPasswordField();
		txtPass.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(120, 102, 600, 25, LayoutProperty.TOP
						| LayoutProperty.LEFT | LayoutProperty.RIGHT_RESIZE));
		
		labelClientID = new JLabel("店铺编号： ");
		labelClientID.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 132, 100, 25, LayoutProperty.TOP
						| LayoutProperty.LEFT));
		txtClientID = new JTextField();
		txtClientID.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(120, 132, 600, 25, LayoutProperty.TOP
						| LayoutProperty.LEFT | LayoutProperty.RIGHT_RESIZE));
		
		labelGoodsCode = new JLabel("商品代码： ");
		labelGoodsCode.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 162, 100, 25, LayoutProperty.TOP
						| LayoutProperty.LEFT));
		txtGoodsCode = new JTextField();
		txtGoodsCode.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(120, 162, 600, 25, LayoutProperty.TOP
						| LayoutProperty.LEFT | LayoutProperty.RIGHT_RESIZE));
		
		labelMachineNum = new JLabel("收银机号： ");
		labelMachineNum.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 192, 100, 25, LayoutProperty.TOP
						| LayoutProperty.LEFT));
		txtMachineNum = new JTextField();
		txtMachineNum.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(120, 192, 600, 25, LayoutProperty.TOP
						| LayoutProperty.LEFT | LayoutProperty.RIGHT_RESIZE));
		
		labelSOAPInterface = new JLabel("接        口： ");
		labelSOAPInterface.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 222, 100, 25, LayoutProperty.TOP
						| LayoutProperty.LEFT));
		txtSOAPInterface = new JTextField();
		txtSOAPInterface.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(120, 222, 600, 25, LayoutProperty.TOP
						| LayoutProperty.LEFT | LayoutProperty.RIGHT_RESIZE));
		
		
		contentPanel.add(btnNext);
		contentPanel.add(labelDBPath);
		contentPanel.add(labelAccount);
		contentPanel.add(labelPass);
		
		contentPanel.add(labelClientID);
		contentPanel.add(labelGoodsCode);
		contentPanel.add(labelMachineNum);
		contentPanel.add(labelSOAPInterface);
		
		contentPanel.add(txtDBPath);
		contentPanel.add(txtAccount);
		contentPanel.add(txtPass);
		
		contentPanel.add(txtClientID);
		contentPanel.add(txtGoodsCode);
		contentPanel.add(txtMachineNum);
		contentPanel.add(txtSOAPInterface);
		
		initData();
	}
	
	
	private void loadDB()
	{
//		isDBOK = false;
//		try 
//		{
//			DBManager.db.getConnection();
//			isDBOK = true;
//		} 
//		catch (ClassNotFoundException e) 
//		{
//			e.printStackTrace();
//			JFOptionPane.showConfirmDialog(SettingUI.this, e.getMessage(), "数据库路径错误", JFOptionPane.YES_OPTION);
//		} 
//		catch (SQLException e) 
//		{
//			e.printStackTrace();
//			JFOptionPane.showConfirmDialog(SettingUI.this, e.getMessage(), "数据库路径错误", JFOptionPane.YES_OPTION);
//		}
	}
	
	private void initData()
	{
//		Config.config.loadCfg();
//		
//		loadDB();
//		
//		if (!isDBOK)
//		{
//			txtDBPath.setText(Config.config.DB);
//			txtAccount.setText(Config.config.SOAP_ACCOUNT);
//			txtPass.setText(Config.config.SOAP_PASS);
//			txtClientID.setText(Config.config.CLIENT_ID);
//			txtGoodsCode.setText(Config.config.GOODS_CODE);
//			txtMachineNum.setText(Config.config.MACHINE_NUM);
//			txtSOAPInterface.setText(Config.config.SOAP_HOST);
//			return;
//		}
//		try 
//		{
//			DBManager.db.initAccount(Config.config);
//
//			String name = encrypt.doDecrypt(Config.config.SOAP_ACCOUNT);
//			String pass = encrypt.doDecrypt(Config.config.SOAP_PASS);
//			Config.config.SOAP_ACCOUNT = name;
//			Config.config.SOAP_PASS = pass;
//		}
//		catch (SQLException e) 
//		{
//			e.printStackTrace();
//		} 
//		catch (UnsupportedEncodingException e) 
//		{
//			e.printStackTrace();
//		}
		txtDBPath.setText("1");
		txtAccount.setText("1");
		txtPass.setText("1");
		txtClientID.setText("1");
		txtGoodsCode.setText("1");
		txtMachineNum.setText("1");
		txtSOAPInterface.setText("1");
	}
	
	private void initDB()
	{		
//		DBManager.db.close(null, null, DBManager.db.conn);
//		
//		Config.config.DB = txtDBPath.getText().trim();
//		Config.config.SOAP_ACCOUNT = txtAccount.getText().trim();
//		Config.config.SOAP_PASS = new String(txtPass.getPassword());
//		
//		Config.config.CLIENT_ID = txtClientID.getText().trim();
//		Config.config.GOODS_CODE = txtGoodsCode.getText().trim();
//		Config.config.MACHINE_NUM = txtMachineNum.getText().trim();
//		
//		Config.config.SOAP_HOST = txtSOAPInterface.getText().trim();
//		
//		String name = encrypt.doEncrypt(Config.config.SOAP_ACCOUNT);
//		String pass = encrypt.doEncrypt(Config.config.SOAP_PASS);
//		Config.config.SOAP_ACCOUNT = name;
//		Config.config.SOAP_PASS = pass;
//		
//		loadDB();
//		
//		if (!isDBOK)
//		{
//			return;
//		}
//		Config.config.writeCfg();
//		
//		Statement sm = null;
//		try 
//		{
//			sm = DBManager.db.conn.createStatement();
//			sm.execute("create table t_intown_report(orderid varchar(44) not null primary key, isreport int, isdebug int, je varchar(44))");
//		} 
//		catch (SQLException e1) 
//		{
//			e1.printStackTrace();
//		}
//		
//		try 
//		{
//			sm.execute("create table t_intown_properties(id varchar(44) not null primary key, va varchar(44))");
//		} 
//		catch (SQLException e) 
//		{
//			e.printStackTrace();
//		}
//
//		try 
//		{
//			sm.executeUpdate("delete from t_intown_properties");
//			
//			PreparedStatement sm1 = DBManager.db.conn.prepareStatement("insert into t_intown_properties(id, va) values (?,?)");
//			sm1.setString(1, "SOAP_ACCOUNT");
//			sm1.setString(2, Config.config.SOAP_ACCOUNT);
//			sm1.addBatch();
//			
//			sm1.setString(1, "SOAP_PASS");
//			sm1.setString(2, Config.config.SOAP_PASS);
//			sm1.addBatch();
//			
//			sm1.setString(1, "TEST_ORDER_ID");
//			sm1.setString(2, "1");
//			sm1.addBatch();
//			
//			sm1.setString(1, "CLIENT_ID");
//			sm1.setString(2, Config.config.CLIENT_ID);
//			sm1.addBatch();
//			
//			sm1.setString(1, "GOODS_CODE");
//			sm1.setString(2, Config.config.GOODS_CODE);
//			sm1.addBatch();
//			
//			sm1.setString(1, "MACHINE_NUM");
//			sm1.setString(2, Config.config.MACHINE_NUM);
//			sm1.addBatch();
//			
//			sm1.executeBatch();
//			
//			DBManager.db.close(null, sm1, null);
//		} 
//		catch (SQLException e) 
//		{
//			e.printStackTrace();
//		}
//		DBManager.db.close(null, sm, DBManager.db.conn);
//		
//		int result = JFOptionPane.showConfirmDialog(SettingUI.this,"数据初始化成功", "数据初始化成功", JFOptionPane.YES_NO_OPTION);
//		if (result == JFOptionPane.YES_OPTION)
//		{
//			System.exit(0);
//			return;
//		}
		System.out.println("初始化数据上报表成功");
	}
}
