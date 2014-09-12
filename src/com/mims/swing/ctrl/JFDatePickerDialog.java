/**
 * 
 */
package com.mims.swing.ctrl;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JDialog;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.mims.swing.layout.FlexLayout;
import com.mims.swing.layout.LayoutProperty;
import com.mims.swing.look.JFBorders;

/**
 * 描述:.
 * <p>
 * 
 * @author 李威
 * 
 * @Date: 2011-9-12
 * 
 */
public class JFDatePickerDialog extends JDialog
{

	JFPanel mainPanel = null;

	private JFDatePicker datePicker = null;

	/**
	 * 星期显示面板
	 */
	private JFPanel weekTitlePanel;

	private JFPanel topBtnPanel;

	private JFPanel bottomBtnPanel;

	private JFPanel dayPanel;

	private JFComboBox comboMonth = null;

	private JFButton btnSubmit = null;

	private JFButton btnCancel = null;

	private JFButton btnToday = null;

	private JFLabel labelLastSelected = null;

	private JFLabel labelToday = null;

	private Color defaultForeColor = null;

	private Calendar dateTotay = Calendar.getInstance();

	private Calendar dateData = dateTotay;

	/**
	 * 年份微调按钮
	 */
	private JFSpinner yearSpinner;

	/**
	 * 当前日期格式
	 */
	private DateFormat dateFmt;

	private int displayPattern;

	/**
	 * 默认的日期时间格式
	 */
	private static final String DEFAULT_DFT = "yyyy-MM-dd HH:mm:ss";
	private static final int INVALID_DATE = -1;
	private static final String LONG_DATE_PATTERN = "(.)*yyyy(.)*M{1,2}(.)*d{1,2}(.)*";
	private static final int LONG_DATE = 6;
	private static final String MEDIUM_DATE_PATTERN = "(.)*yy(.)*M{1,2}(.)*d{1,2}(.)*";
	private static final int MEDIUM_DATE = 7;
	private static final String SHORT_DATE_PATTERN = "(.)*M{1,2}(.)*d{1,2}(.)*";
	private static final int SHORT_DATE = 8;
	private static final String LONG_DATE_TIME_PATTERN = "(.)*yyyy(.)*M{1,2}(.)*d{1,2}(.)*(H{1,2}|h{1,2})(.)*m{1,2}(.)*s{1,2}(.)*";
	private static final int LONG_DATE_TIME = 0;
	// private static final String
	// LONG_DATETIME_PATTERN1="(.)*yyyy(.)*M{1,2}(.)*d{1,2}(.)*h{1,2}(.)*m{1,2}(.)*s{1,2}(.)*";
	private static final String LONG_DATE_MEDIUM_TIME_PATTERN = "(.)*yyyy(.)*M{1,2}(.)*d{1,2}(.)*(H{1,2}|h{1,2})(.)*m{1,2}(.)*";
	private static final int LONG_DATE_MEDIUM_TIME = 1;
	// private static final String
	// LONG_DATE_MEDIUM_TIME_PATTERN1="(.)*yyyy(.)*M{1,2}(.)*d{1,2}(.)*h{1,2}(.)*m{1,2}(.)*";
	private static final String MEDIUM_DATE_LONG_TIME_PATTERN = "(.)*yy(.)*M{1,2}(.)*d{1,2}(.)*(H{1,2}|h{1,2})(.)*m{1,2}(.)*s{1,2}(.)*";
	private static final int MEDIUM_DATE_LONG_TIME = 2;
	// private static final String
	// MEDIUM_DATE_LONG_TIME_PATTERN1="(.)*yy(.)*M{1,2}(.)*d{1,2}(.)*h{1,2}(.)*m{1,2}(.)*s{1,2}(.)*";
	private static final String MEDIUM_DATE_MEDIUM_TIME_PATTERN = "(.)*yy(.)*M{1,2}(.)*d{1,2}(.)*(H{1,2}|h{1,2})(.)*m{1,2}(.)*";
	private static final int MEDIUM_DATE_MEDIUM_TIME = 3;
	// private static final String
	// MEDIUM_DATE_MEDIUM_TIME_PATTERN1="(.)*yy(.)*M{1,2}(.)*d{1,2}(.)*h{1,2}(.)*m{1,2}(.)*";
	private static final String SHORT_DATE_LONG_TIME_PATTERN = "(.)*M{1,2}(.)*d{1,2}(.)*(H{1,2}|h{1,2})(.)*m{1,2}(.)*s{1,2}(.)*";
	private static final int SHORT_DATE_LONG_TIME = 4;
	private static final String SHORT_DATE_MEDIUM_TIME_PATTERN = "(.)*M{1,2}(.)*d{1,2}(.)*(H{1,2}|h{1,2})(.)*m{1,2}(.)*";
	private static final int SHORT_DATE_MEDIUM_TIME = 5;
	/**
	 * 平年每个月天数
	 */
	private static final int[] DAYS_PER_MONTH =
	{ 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	private static final String[] TITLES =
	{ "<html><font color='blue'>日</font></html>",
			"<html><font color='blue'>一</font></html>",
			"<html><font color='blue'>二</font></html>",
			"<html><font color='blue'>三</font></html>",
			"<html><font color='blue'>四</font></html>",
			"<html><font color='blue'>五</font></html>",
			"<html><font color='blue'>六</font></html>" };

	private static final JFLabel[] TITLE_LABELS = new JFLabel[TITLES.length];

	private static JFLabel[][] dayLabels = new JFLabel[6][7];

	static
	{
		for (int i = 0; i < dayLabels.length; i++)
		{
			for (int j = 0; j < dayLabels[0].length; j++)
			{
				dayLabels[i][j] = new JFLabel("" + ((i + 1) * (j + 1)));
				dayLabels[i][j].setHorizontalAlignment(JFLabel.CENTER);
			}
		}

		for (int i = 0; i < TITLE_LABELS.length; i++)
		{
			TITLE_LABELS[i] = new JFLabel(TITLES[i]);
			TITLE_LABELS[i].setHorizontalAlignment(JFLabel.CENTER);
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2228268926046378299L;

	public JFDatePickerDialog(JFDatePicker datePicker)
	{
		this(datePicker, "yyyy-MM-dd");
	}

	public JFDatePickerDialog(JFDatePicker datePicker, String pattern)
	{
		super();

		this.datePicker = datePicker;
		setSize(280, 250);
		init(pattern);
		initActions();

	}
	
	@Override
	public void setVisible(boolean b)
	{
		// TODO Auto-generated method stub
		super.setVisible(b);
		yearSpinner.requestFocus(true);
	}

	private void initDays()
	{
		labelLastSelected = null;
		int first = getFirstDayOfMonth();
		int dayForMonth = getDaysForMonth();
		int text = 0;
		for (int i = 0; i < dayLabels.length; i++)
		{
			for (int j = 0; j < dayLabels[0].length; j++)
			{
				if (defaultForeColor == null)
				{
					defaultForeColor = dayLabels[i][j].getForeground();
				}
				dayLabels[i][j].setEnabled(true);
				dayLabels[i][j].setForeground(defaultForeColor);
				dayLabels[i][j].setBorder(null);
				dayPanel.add(dayLabels[i][j]);

				dayLabels[i][j].setOpaque(false);

				if ((i == 0 && j < first - 1) || (text >= dayForMonth))
				{
					dayLabels[i][j].setEnabled(false);
					dayLabels[i][j].setText("");
				} else
				{
					dayLabels[i][j].setText("" + ++text);
					if (j == 0 || j == dayLabels[0].length - 1)
					{
						dayLabels[i][j].setBackground(Color.LIGHT_GRAY);
						dayLabels[i][j].setOpaque(true);
					}
				}

				if (dayLabels[i][j].isEnabled())
				{
					dayLabels[i][j].addMouseListener(new ChooseDayListener());
					dayLabels[i][j].addMouseListener(new MouseHandler());
				}
			}
		}
		getLabelToday();
	}

	private void init(String pattern)
	{
		displayPattern = analysePattern(pattern);

		if (INVALID_DATE == displayPattern)
		{
			throw new IllegalArgumentException("指定的日期时间格式无效");
		}

		dateFmt = new SimpleDateFormat(pattern);

		mainPanel = new JFPanel();

		mainPanel.setBorder(new JFBorders.JFComboBoxListBorder());
		topBtnPanel = new JFPanel();
		bottomBtnPanel = new JFPanel();

		weekTitlePanel = new JFPanel();
		dayPanel = new JFPanel();
		weekTitlePanel.setLayout(new GridLayout(1, 7, 0, 0));
		dayPanel.setLayout(new GridLayout(6, 7, 0, 0));

		for (int i = 0; i < TITLE_LABELS.length; i++)
		{
			weekTitlePanel.add(TITLE_LABELS[i]);
		}
		initDays();

		labelToday.setBorder(new JFBorders.JFComboBoxListBorder());
		labelToday.setForeground(Color.RED);

		mainPanel.setLayout(new FlexLayout());
		mainPanel.putClientProperty(LayoutProperty.LAYOUT_PARENT_PROPERTY,
				new LayoutProperty(0, 0, 280, 200));
		topBtnPanel.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 5, 260, 25, LayoutProperty.TOP
						| LayoutProperty.RIGHT | LayoutProperty.LEFT));
		mainPanel.add(topBtnPanel);

		weekTitlePanel.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(5, 30, 270, 20, LayoutProperty.TOP
						| LayoutProperty.RIGHT | LayoutProperty.LEFT));
		mainPanel.add(weekTitlePanel);

		dayPanel.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(5, 60, 270, 105, LayoutProperty.TOP
						| LayoutProperty.RIGHT | LayoutProperty.LEFT
						| LayoutProperty.BOTTOM));
		mainPanel.add(dayPanel);

		bottomBtnPanel.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(10, 165, 260, 25, LayoutProperty.BOTTOM
						| LayoutProperty.RIGHT | LayoutProperty.LEFT));
		mainPanel.add(bottomBtnPanel);

		mainPanel.add(weekTitlePanel);
		mainPanel.add(dayPanel);
		mainPanel.add(bottomBtnPanel);

		topBtnPanel.setLayout(new FlexLayout());
		topBtnPanel.putClientProperty(LayoutProperty.LAYOUT_PARENT_PROPERTY,
				new LayoutProperty(0, 0, 280, 20));
		comboMonth = new JFComboBox(MonthItem.getItems().toArray());
		comboMonth.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(0, 0, 80, 18, LayoutProperty.BOTTOM
						| LayoutProperty.TOP | LayoutProperty.LEFT));

		topBtnPanel.add(comboMonth);

		yearSpinner = new JFSpinner(new CircleSpinnerNumberModel(
				dateTotay.get(Calendar.YEAR), 1910, 2099, 1));
		JFSpinner.NumberEditor numEditor = new JFSpinner.NumberEditor(
				yearSpinner, "####");
		yearSpinner.setEditor(numEditor);

		yearSpinner.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(160, 0, 100, 18, LayoutProperty.BOTTOM
						| LayoutProperty.TOP | LayoutProperty.LEFT));

		topBtnPanel.add(yearSpinner);

		// 底部按钮区域
		bottomBtnPanel.setLayout(new FlexLayout());
		bottomBtnPanel.putClientProperty(LayoutProperty.LAYOUT_PARENT_PROPERTY,
				new LayoutProperty(0, 0, 280, 25));
		btnSubmit = new JFButton("确定");
		btnSubmit.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(150, 3, 60, 20, LayoutProperty.BOTTOM
						| LayoutProperty.TOP | LayoutProperty.RIGHT));

		btnCancel = new JFButton("取消");
		btnCancel.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(220, 3, 60, 20, LayoutProperty.BOTTOM
						| LayoutProperty.TOP | LayoutProperty.RIGHT));

		bottomBtnPanel.add(btnSubmit);
		bottomBtnPanel.add(btnCancel);

		btnToday = new JFButton("今天");
		btnToday.putClientProperty(LayoutProperty.LAYOUT_CHILD_PROPERTY,
				new LayoutProperty(20, 3, 60, 20, LayoutProperty.BOTTOM
						| LayoutProperty.TOP | LayoutProperty.RIGHT));
		bottomBtnPanel.add(btnToday);

		add(mainPanel);

		int month = getMonth();
		comboMonth.setSelectedIndex(month);

	}

	private void initActions()
	{
		comboMonth.addItemListener(new ItemListener()
		{

			public void itemStateChanged(ItemEvent e)
			{
				MonthItem item = (MonthItem) comboMonth.getSelectedItem();
				dateTotay.set(Calendar.MONTH, item.getValue());
				initDays();
			}
		});

		yearSpinner.addChangeListener(new ChangeListener()
		{

			public void stateChanged(ChangeEvent e)
			{
				int year = ((Integer) yearSpinner.getValue()).intValue();
				dateTotay.set(Calendar.YEAR, year);
				initDays();
			}
		});

		for (int i = 0; i < dayLabels.length; i++)
		{
			for (int j = 0; j < dayLabels[0].length; j++)
			{
				dayLabels[i][j].addMouseListener(new ChooseDayListener());
			}
		}

		btnSubmit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				hide();
				dispose();
				dateTotay.set(Calendar.DAY_OF_MONTH,
						Integer.parseInt(labelLastSelected.getText()));
				((JFTextField) datePicker.getEditor().getEditorComponent())
						.setText(dateFmt.format(dateTotay.getTime()));
				datePicker.setUserObject(dateTotay.getTime());
			}
		});

		btnCancel.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				hide();
				dispose();
			}
		});

		btnToday.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				hide();
				dispose();
				dateTotay.setTime(new Date());
				((JFTextField) datePicker.getEditor().getEditorComponent())
						.setText(dateFmt.format(dateTotay.getTime()));
				datePicker.setUserObject(new Date());
			}
		});

		setUndecorated(true);
		addWindowListener(new WindowAdapter()
		{
			public void windowDeactivated(WindowEvent e)
			{
				hide();
				dispose();
			}
		});
	}

	/**
	 * 分析日期时间格式，检查的顺序是从最严格pattern检查到最松弛的pattern， 不能颠倒顺序
	 * 
	 * @param pattern
	 * @return
	 */
	private int analysePattern(String pattern)
	{
		Pattern p = Pattern.compile(SHORT_DATE_PATTERN);
		Matcher matcher = p.matcher(pattern);
		if (!matcher.matches())
			return INVALID_DATE;

		p = Pattern.compile(LONG_DATE_TIME_PATTERN);
		matcher = p.matcher(pattern);
		if (matcher.matches())
			return LONG_DATE_TIME;// 0

		p = Pattern.compile(LONG_DATE_MEDIUM_TIME_PATTERN);
		matcher = p.matcher(pattern);
		if (matcher.matches())
			return LONG_DATE_MEDIUM_TIME;// 1

		p = Pattern.compile(MEDIUM_DATE_LONG_TIME_PATTERN);
		matcher = p.matcher(pattern);
		if (matcher.matches())
			return MEDIUM_DATE_LONG_TIME;// 2

		p = Pattern.compile(MEDIUM_DATE_MEDIUM_TIME_PATTERN);
		matcher = p.matcher(pattern);
		if (matcher.matches())
			return MEDIUM_DATE_MEDIUM_TIME;// 3

		p = Pattern.compile(SHORT_DATE_LONG_TIME_PATTERN);
		matcher = p.matcher(pattern);
		if (matcher.matches())
			return SHORT_DATE_LONG_TIME;// 4

		p = Pattern.compile(SHORT_DATE_MEDIUM_TIME_PATTERN);
		matcher = p.matcher(pattern);
		if (matcher.matches())
			return SHORT_DATE_MEDIUM_TIME;// 5

		p = Pattern.compile(LONG_DATE_PATTERN);
		matcher = p.matcher(pattern);
		if (matcher.matches())
			return LONG_DATE;// 6

		p = Pattern.compile(MEDIUM_DATE_PATTERN);
		matcher = p.matcher(pattern);
		if (matcher.matches())
			return MEDIUM_DATE;// 7

		p = Pattern.compile(SHORT_DATE_PATTERN);
		matcher = p.matcher(pattern);
		if (matcher.matches())
			return SHORT_DATE;// 8

		return LONG_DATE_TIME;

	}

	public boolean isInContanier(Container container, Point screenPoint)
	{
		if (!container.isVisible())
			return false;
		Point leftTopOnScreen = container.getLocationOnScreen();
		Rectangle bounds = container.getBounds();
		if (screenPoint.x > leftTopOnScreen.x
				&& screenPoint.x < (leftTopOnScreen.x + bounds.width)
				&& screenPoint.y > leftTopOnScreen.y
				&& screenPoint.y < (leftTopOnScreen.y + bounds.height))
			return true;
		return false;
	}

	private void getLabelToday()
	{
		int dayOfMonth = dateTotay.get(Calendar.DAY_OF_MONTH);
		int dayOfWeek = dateTotay.get(Calendar.DAY_OF_WEEK);

		Calendar cal = Calendar.getInstance();
		cal.setTime((Date) dateTotay.getTime().clone());
		cal.add(Calendar.DAY_OF_MONTH, -dayOfMonth + 1);
		int first = cal.get(Calendar.DAY_OF_WEEK);
		int dayOfMonthFistDay = cal.get(Calendar.DAY_OF_MONTH);

		if (dayOfMonth - dayOfMonthFistDay < 7)
		{
			labelToday = dayLabels[0][dayOfWeek];
		} else
		{
			int temp = dayOfMonth - (8 - first) - dayOfWeek;
			temp = temp / 7;
			labelToday = dayLabels[temp + 1][dayOfWeek - 1];
		}
		setCurrentDay(labelToday, true);

	}

	private int getFirstDayOfMonth()
	{
		int dayOfMonth = dateTotay.get(Calendar.DAY_OF_MONTH);

		Calendar cal = Calendar.getInstance();
		cal.setTime((Date) dateTotay.getTime().clone());
		cal.add(Calendar.DAY_OF_MONTH, -dayOfMonth + 1);
		int first = cal.get(Calendar.DAY_OF_WEEK);
		return first;
	}

	private int getMonth()
	{
		return dateTotay.get(Calendar.MONTH);
	}

	/**
	 * 获得某年某月的天数
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	private int getDaysForMonth()
	{
		int year = dateTotay.get(Calendar.YEAR);
		int month = dateTotay.get(Calendar.MONTH);
		int days = DAYS_PER_MONTH[month]
				+ (Calendar.FEBRUARY == month && isLenient(year) ? 1 : 0);
		return days;
	}

	/**
	 * 判断是否闰年
	 * 
	 * @param year
	 * @return
	 */
	private boolean isLenient(int year)
	{
		return ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0));
	}

	private void setCurrentDay(JFLabel label, boolean isSetColor)
	{
		label.setBorder(new JFBorders.JFComboBoxListBorder());
		if (isSetColor)
		{
			label.setForeground(Color.RED);
		}
		if (null != labelLastSelected && labelLastSelected != labelToday
				&& labelLastSelected != label)
		{
			labelLastSelected.setBorder(new EmptyBorder(label.getInsets()));
			labelLastSelected.setForeground(defaultForeColor);
		}
		if (labelLastSelected != label)
		{
			labelLastSelected = label;
		}
	}

	class ChooseDayListener extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{

			JFLabel label = (JFLabel) e.getSource();
			if (!label.isEnabled())
			{
				return;
			}
			setCurrentDay(label, false);

			// datetime.set(Calendar.DAY_OF_MONTH,
			// Integer.parseInt(label.getName()));
			// datetimeField.setText(dateFmt.format(datetime.getTime()));
		}
	}

	class CircleSpinnerNumberModel extends SpinnerNumberModel
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = -1071232668283028527L;

		public CircleSpinnerNumberModel(int value, int minimum, int maximum,
				int stepSize)
		{
			super(value, minimum, maximum, stepSize);
		}

		public Object getNextValue()
		{
			if (getMaximum().compareTo(getValue()) == 0)
				return getMinimum();
			return super.getNextValue();
		}

		public Object getPreviousValue()
		{
			if (getMinimum().compareTo(getValue()) == 0)
				return getMaximum();

			return super.getPreviousValue();
		}

	}
	
	class MouseHandler extends MouseAdapter{
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2){
				hide();
				dispose();
				labelLastSelected = (JFLabel)e.getSource();
				dateTotay.set(Calendar.DAY_OF_MONTH,
						Integer.parseInt(labelLastSelected.getText()));
				((JFTextField) datePicker.getEditor().getEditorComponent())
						.setText(dateFmt.format(dateTotay.getTime()));
				datePicker.setUserObject(dateTotay.getTime());
			}
		}
	}

}
