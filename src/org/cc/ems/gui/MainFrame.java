package org.cc.ems.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.cc.ems.entity.Employee;
import org.cc.ems.service.EmployeeService;
import org.cc.ems.util.ScreenUtil;
import org.cc.ems.util.XMLUtil;

import javax.swing.JTable;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.ImageIcon;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.Toolkit;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {

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
	private JMenuBar menuBar;
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
		setResizable(false);
		
		BorderLayout borderLayout=new BorderLayout();
		getContentPane().setLayout(borderLayout);
		
		searchInputField=new JTextField(15);
		searchInputField.setToolTipText("输入雇员名字过滤");
		searchInputField.setHorizontalAlignment(SwingConstants.LEFT);
		searchInputField.setFont(new Font("宋体", Font.PLAIN, 12));
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
		table = new JTable(model);
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
		
		ActionListener search=new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String keyword=searchInputField.getText();
				List<Employee> list=null;
				if(keyword.trim().length()==0){
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
		
		
		addButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MyDialog dialog=new MyDialog(null, 0);
				dialog.setLocation(MainFrame.this.getLocation().x+(FRAME_WIDTH-dialog.getWidth())/2,MainFrame.this.getLocation().y+(FRAME_HEIGHT-dialog.getHeight())/2);
				dialog.setVisible(true);
				
				DefaultTableModel model=new DefaultTableModel(EmployeeService.getInstance().toArrayData(),EmployeeService.getInstance().getHeaders());
				table.setModel(model);
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
					dialog.setLocation(MainFrame.this.getLocation().x+(FRAME_WIDTH-dialog.getWidth())/2,MainFrame.this.getLocation().y+(FRAME_HEIGHT-dialog.getHeight())/2);
					dialog.setVisible(true);
					
					DefaultTableModel model=new DefaultTableModel(EmployeeService.getInstance().toArrayData(),EmployeeService.getInstance().getHeaders());
					table.setModel(model);
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
