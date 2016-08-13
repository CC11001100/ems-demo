package org.cc.ems.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.cc.ems.entity.Emploee;
import org.cc.ems.util.ScreenUtil;
import org.cc.ems.util.XMLUtil;

import javax.swing.JTable;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.ImageIcon;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.Toolkit;

public class MainFrame extends JFrame {

	private int FRAME_WIDTH=800;
	private int FRAME_HEIGHT=600;
	
	public MainFrame() {
		
		initLayout();
		initListener();
		
		setVisible(true);
	}	
	
	private JTextField searchInputField;
	private JButton searchButton;
	
	private JButton addButton;
	private JButton delButton;
	private JButton modifyButton;
	private JTable table;
	
	private void initLayout(){
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/org/cc/ems/resource/icon.png")));
		setTitle("雇员管理系统（EMS） - Demo");
		setSize(FRAME_WIDTH,FRAME_HEIGHT);
		setLocation(ScreenUtil.getInstance().getCenterLocation(FRAME_WIDTH,FRAME_HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		BorderLayout borderLayout=new BorderLayout();
		setLayout(borderLayout);
		
		searchInputField=new JTextField(15);
		searchInputField.setToolTipText("输入雇员名字过滤");
		searchInputField.setHorizontalAlignment(SwingConstants.LEFT);
		searchInputField.setFont(new Font("宋体", Font.PLAIN, 12));
		JPanel panel=new JPanel();
		panel.add(searchInputField);
		add(panel,BorderLayout.NORTH);
		
		searchButton = new JButton("搜索");
		searchButton.setIcon(new ImageIcon(MainFrame.class.getResource("/org/cc/ems/resource/search.png")));
		panel.add(searchButton);
		
		
		addButton=new JButton("添加雇员");
		delButton=new JButton("删除雇员");
		delButton.setToolTipText("可选中多个批量删除哦~");
		modifyButton=new JButton("修改雇员");
		modifyButton.setToolTipText("直接双击就可以修改哦~");
		
		panel=new JPanel();
		GridLayout layout=new GridLayout(1,3);
		layout.setHgap(1);
		panel.setLayout(layout);
		panel.add(addButton);
		panel.add(delButton);
		panel.add(modifyButton);
		add(panel,BorderLayout.SOUTH);
		
		List<Emploee> list=XMLUtil.getInstance().read("d:/employeeDB.xml");
		String data[][]=new String[list.size()*5][5];
		for(int i=0;i<list.size()*5;i++){
			Emploee e=list.get(i%list.size());
			data[i][0]=e.getId();
			data[i][1]=e.getName();
			data[i][2]=e.getPosition();
			data[i][3]=Integer.toString(e.getAbsenteeism());
			data[i][4]=String.format("￥%.2f",e.getSalary());
		}
		DefaultTableModel model=new DefaultTableModel();
		String headers[]=new String[]{"编号","姓名","职位","旷工","工资"};
		model.setDataVector(data,headers);
		table = new JTable(model);
		table.setFont(new Font("宋体",Font.PLAIN,15));
		table.setRowHeight(30);
		
		JScrollPane scrollPane=new JScrollPane(table);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
	}
	
	private void initListener(){
		
	}
	
}
