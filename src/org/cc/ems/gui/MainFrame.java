package org.cc.ems.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.plaf.basic.BasicMenuUI.ChangeHandler;
import javax.swing.table.DefaultTableModel;

import org.cc.ems.entity.Employee;
import org.cc.ems.service.EmployeeService;
import org.cc.ems.util.ScreenUtil;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private int FRAME_WIDTH=800;
	private int FRAME_HEIGHT=600;
	
	public MainFrame() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int res=JOptionPane.showConfirmDialog(MainFrame.this,"是否保存？","退出提示",JOptionPane.YES_NO_OPTION);
				if(res==JOptionPane.OK_OPTION) service.save();
				System.exit(0);
			}
		});
		
		initLayout();
		initListener();
		
		setVisible(true);
	}	
	
	private JTextField searchInputField;
	private JButton searchButton;
	
	private JButton addButton;
	private JButton delButton;
	private JButton modifyButton;
	protected JTable table;
	private JMenuBar menuBar_1;
	private JMenu menu;
	private JMenu menu_1;
	private JMenu menu_2;
	private JMenu menu_3;
	private JMenuItem menuItem;
	private JMenuItem menuItem_1;
	private JMenuItem menuItem_2;
	private JMenuItem menuItem_3;
	private JMenuItem menuItem_4;
	private JMenuItem menuItem_5;
	private JSeparator separator;
	
	private EmployeeService service;
	
	private void initLayout(){
		
		service=EmployeeService.getInstance();
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/org/cc/ems/resource/icon.png")));
		setTitle("雇员管理系统（EMS） - Demo");
		setSize(FRAME_WIDTH,FRAME_HEIGHT);
		setLocation(ScreenUtil.getInstance().getCenterLocation(FRAME_WIDTH,FRAME_HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setResizable(false);
		
		BorderLayout borderLayout=new BorderLayout();
		getContentPane().setLayout(borderLayout);
		
		searchInputField=new JTextField(15);
		searchInputField.setToolTipText("输入雇员名字过滤，拼音也好使");
		JPanel panel=new JPanel();
		
		panel.add(searchInputField);
		getContentPane().add(panel,BorderLayout.NORTH);
		
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
		getContentPane().add(panel,BorderLayout.SOUTH);
		
		DefaultTableModel model=new DefaultTableModel();
		model.setDataVector(EmployeeService.getInstance().list2Array(EmployeeService.getInstance().list()),EmployeeService.getInstance().getHeaders());
		
		//反正这破代码已经乱成这怂样了，破罐子破摔，就用匿名子类吧...
		table = new JTable(model){

			int rowIndex;
			int columnIndex;
			String value;
			
			@Override
			public void editingStopped(ChangeEvent e2) {
				super.editingStopped(e2);
				String v=(String) table.getValueAt(rowIndex,columnIndex);
				String id=(String) table.getValueAt(rowIndex,0);
				Employee e=service.get(id);
				
				if(columnIndex==1){
					//name
					if(v.trim().length()==0){
						table.setValueAt(value,rowIndex,columnIndex);
						JOptionPane.showMessageDialog(MainFrame.this,"姓名不能为空！","提示信息",JOptionPane.WARNING_MESSAGE);
						return;
					}
					e.setName(v);
				}else if(columnIndex==2){
					//在选择的时候拦截掉
					//position
//System.out.println("active..");					
//					modifyButton.doClick();
				}else if(columnIndex==3){
					//旷工
					try {
						e.setAbsenteeism(Integer.parseInt(v));
					} catch (NumberFormatException e4) {
						table.setValueAt(value,rowIndex,columnIndex);
						JOptionPane.showMessageDialog(MainFrame.this,"旷工天数必须为整数","警告",JOptionPane.WARNING_MESSAGE);
						return;
					}
					if(e.getAbsenteeism()>31){
						table.setValueAt(value,rowIndex,columnIndex);
						JOptionPane.showMessageDialog(MainFrame.this,"科普时间：一个月最多有31天","警告",JOptionPane.WARNING_MESSAGE);
						return;
					}
					if(e.getAbsenteeism()<0){
						table.setValueAt(value,rowIndex,columnIndex);
						JOptionPane.showMessageDialog(MainFrame.this,"旷工天数不能为负","警告",JOptionPane.WARNING_MESSAGE);
						return;
					}
					if(e.getAbsenteeism()>=20){
						JOptionPane.showMessageDialog(MainFrame.this,"这个员工可以开掉了...","彩蛋",JOptionPane.WARNING_MESSAGE);
					}
				}else if(columnIndex==4){
					//工资
					try {
						if(v.contains("￥")) v=v.replaceAll("￥","");
						e.setSalary(Double.parseDouble(v));
					} catch (NumberFormatException e3) {
//						e2.printStackTrace();
						table.setValueAt(value,rowIndex,columnIndex);
						JOptionPane.showMessageDialog(MainFrame.this,"工资 必须为数字","警告",JOptionPane.WARNING_MESSAGE);
						return;
					}
					if(e.getSalary()<0){
						table.setValueAt(value,rowIndex,columnIndex);
						JOptionPane.showMessageDialog(MainFrame.this,"工资不能为负","警告",JOptionPane.WARNING_MESSAGE);
						return;
					}
					if(e.getSalary()<1000){
						JOptionPane.showMessageDialog(MainFrame.this,"做人要厚道...","彩蛋",JOptionPane.WARNING_MESSAGE);
					}
					if(!v.contains("￥")){
						table.setValueAt("￥"+v,rowIndex,columnIndex);
					}
				}
				table.setValueAt(String.format("￥%.2f",e.getFinallySalary()),rowIndex,5);
				service.modify(e);
			}
			
			@Override
			public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) {
				super.changeSelection(rowIndex, columnIndex, toggle, extend);
				
				this.rowIndex=rowIndex;
				this.columnIndex=columnIndex;
				this.value=(String) table.getValueAt(rowIndex,columnIndex);
				
				//不用手敲字
				if(columnIndex==2){
					modifyButton.doClick();
					return;
				}else if(columnIndex==4){
					this.value=this.value.substring(1);
					table.setValueAt(this.value,rowIndex,columnIndex);
				}
				
				//如此可实现单击即可编辑
				editCellAt(rowIndex,columnIndex);

				

				
			}
			
			@Override
			public boolean isCellEditable(int row, int column) {
				if(column==0 || column==5) return false;
				return true;
			}
			
		};
		table.setFont(new Font("宋体",Font.PLAIN,15));
		table.setRowHeight(30);
		table.getTableHeader().setReorderingAllowed(false);
		
		JScrollPane scrollPane=new JScrollPane(table);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		menuBar_1 = new JMenuBar();
		setJMenuBar(menuBar_1);
		
		menu = new JMenu("文件");
		menuBar_1.add(menu);
		
		menuItem = new JMenuItem("保存");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				service.save();
			}
		});
		menu.add(menuItem);
		
		separator = new JSeparator();
		menu.add(separator);
		
		menuItem_5 = new JMenuItem("退出");
		menuItem_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int res=JOptionPane.showConfirmDialog(MainFrame.this,"是否保存？","退出提示",JOptionPane.YES_NO_CANCEL_OPTION);
				if(res==JOptionPane.CANCEL_OPTION) return;
				if(res==JOptionPane.OK_OPTION) service.save();
				System.exit(0);
			}
		});
		menu.add(menuItem_5);
		
		menu_1 = new JMenu("编辑");
		menuBar_1.add(menu_1);
		
		menuItem_4 = new JMenuItem("暂未实现");
		menu_1.add(menuItem_4);
		
		menu_3 = new JMenu("工具");
		menuBar_1.add(menu_3);
		
		menuItem_3 = new JMenuItem("暂未实现");
		menu_3.add(menuItem_3);
		
		menu_2 = new JMenu("帮助");
		menuBar_1.add(menu_2);
		
		menuItem_1 = new JMenuItem("帮助");
		menu_2.add(menuItem_1);
		
		menuItem_2 = new JMenuItem("关于");
		menuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(MainFrame.this,"Code by cc","About author",JOptionPane.PLAIN_MESSAGE);
			}
		});
		menu_2.add(menuItem_2);
		
	}
	
	private void initListener(){
		
		final ActionListener search=new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String keyword=searchInputField.getText().trim();
				List<Employee> list=null;
				if(keyword.length()==0){
					list=service.list();
				}else{
					list=service.filter(keyword);
				}
				
				DefaultTableModel model=new DefaultTableModel(service.list2Array(list),service.getHeaders());
				table.setModel(model);
			}
		};
		
		searchInputField.addActionListener(search);
		searchButton.addActionListener(search);
		
		searchInputField.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				String keyword=searchInputField.getText().trim();
				List<Employee> list=null;
				if(keyword.length()==0){
					list=service.list();
				}else{
					list=service.filter(keyword);
				}
				
				DefaultTableModel model=new DefaultTableModel(service.list2Array(list),service.getHeaders());
				table.setModel(model);
			}
		});
		
		addButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MyDialog dialog=new MyDialog(null, 0);
				dialog.setLocation(MainFrame.this.getLocation().x+(MainFrame.this.getWidth()-dialog.getWidth())/2,MainFrame.this.getLocation().y+(MainFrame.this.getHeight()-dialog.getHeight())/2);
				dialog.setVisible(true);
				
				search.actionPerformed(e);
				
//				DefaultTableModel model=new DefaultTableModel(EmployeeService.getInstance().toArrayData(),EmployeeService.getInstance().getHeaders());
//				table.setModel(model);
			}
		});
		
		
		
		modifyButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int rs[]=table.getSelectedRows();
				
				if(rs.length==0){
					JOptionPane.showMessageDialog(MainFrame.this,"请单击选中某行进行操作\n或者直接双击单元格就可以编辑","提示",JOptionPane.WARNING_MESSAGE);
				}else if(rs.length>1){
					JOptionPane.showMessageDialog(MainFrame.this,"不能批量修改，只能选中一行...好吧，有点逊","提示",JOptionPane.WARNING_MESSAGE);
				}else{
					String id=(String) table.getValueAt(rs[0],0);
					MyDialog dialog=new MyDialog(service.get(id), 1);
					dialog.setLocation(MainFrame.this.getLocation().x+(MainFrame.this.getWidth()-dialog.getWidth())/2,MainFrame.this.getLocation().y+(MainFrame.this.getHeight()-dialog.getHeight())/2);
					dialog.setVisible(true);
					
					search.actionPerformed(e);
//					DefaultTableModel model=new DefaultTableModel(EmployeeService.getInstance().toArrayData(),EmployeeService.getInstance().getHeaders());
//					table.setModel(model);
				}
				
			}
		});
		
		
		delButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int cs[]=table.getSelectedRows();
				
				if(cs.length==0){
					JOptionPane.showMessageDialog(MainFrame.this,"请选中一行或多行删除","提示信息",JOptionPane.WARNING_MESSAGE);
				}else{
					int res=JOptionPane.showConfirmDialog(MainFrame.this,String.format("确认删除选中的%d条记录？",cs.length),"确认操作",JOptionPane.OK_CANCEL_OPTION);
					if(res!=JOptionPane.OK_OPTION) return; 
					
					DefaultTableModel model=(DefaultTableModel) table.getModel();
					for(int i=cs.length-1;i>=0;i--){
						String id=(String) model.getValueAt(cs[i],0);
						service.del(id);
						model.removeRow(cs[i]);
					}
//					model=new DefaultTableModel(service.toArrayData(),service.getHeaders());
//					table.setModel(model);
				}
			}
		});
		
	}
	
}
