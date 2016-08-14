package org.cc.ems.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.cc.ems.entity.CommonEmployee;
import org.cc.ems.entity.Director;
import org.cc.ems.entity.Employee;
import org.cc.ems.entity.Manager;
import org.cc.ems.service.EmployeeService;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MyDialog extends JDialog {
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_3;
	private JTextField textField_4;

	/**
	 * Create the dialog.
	 */
	public MyDialog(final Employee e,final int operType) {
		setTitle("雇员信息");
		setModal(true);
		setBounds(100, 100, 219, 300);
		{
			JPanel panel = new JPanel();
			getContentPane().add(panel, BorderLayout.CENTER);
			panel.setLayout(new GridLayout(6, 2, 0, 0));
			
			JPanel panel_1 = new JPanel();
			panel.add(panel_1);
			
			JLabel lblNewLabel = new JLabel("编号：");
			panel_1.add(lblNewLabel);
			
			textField = new JTextField();
			textField.setToolTipText("一旦设定之后便不可更改 ");
			panel_1.add(textField);
			textField.setColumns(10);
			
			JPanel panel_2 = new JPanel();
			panel.add(panel_2);
			
			JLabel label = new JLabel("姓名：");
			panel_2.add(label);
			
			textField_1 = new JTextField();
			textField_1.setColumns(10);
			panel_2.add(textField_1);
			
			JPanel panel_3 = new JPanel();
			panel.add(panel_3);
			panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			
			JLabel label_1 = new JLabel("职位：");
			panel_3.add(label_1);
			
			final JComboBox comboBox = new JComboBox();
			comboBox.setModel(new DefaultComboBoxModel(new String[] {"普通员工", "经理", "董事"}));
			comboBox.setSelectedIndex(0);
			panel_3.add(comboBox);
			
			JPanel panel_4 = new JPanel();
			panel.add(panel_4);
			
			JLabel label_2 = new JLabel("工资：");
			panel_4.add(label_2);
			
			textField_3 = new JTextField();
			textField_3.setColumns(10);
			panel_4.add(textField_3);
			
			JPanel panel_5 = new JPanel();
			panel.add(panel_5);
			
			JLabel label_3 = new JLabel("旷工：");
			panel_5.add(label_3);
			
			textField_4 = new JTextField();
			textField_4.setColumns(10);
			panel_5.add(textField_4);
			
			JPanel panel_6 = new JPanel();
			panel.add(panel_6);
			
			if(operType==1){
				textField.setEditable(false);
				textField.setText(e.getId());
				textField_1.setText(e.getName());
				textField_3.setText(Double.toString(e.getSalary()));
				textField_4.setText(Integer.toString(e.getAbsenteeism()));
				if("普通员工".equals(e.getPosition())){
					comboBox.setSelectedIndex(0);
				}else if("经理".equals(e.getPosition())){
					comboBox.setSelectedIndex(1);
				}else if("董事".equals(e.getPosition())){
					comboBox.setSelectedIndex(2);
				}else {
					comboBox.setSelectedIndex(-1);
				}
			}
			
			JButton btnNewButton = new JButton("确认");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e1) {
					
					//更新
					if(operType==1){
						String name=textField_1.getText().trim();
						if(name.length()==0){
							JOptionPane.showMessageDialog(MyDialog.this,"请输入名字","警告",JOptionPane.WARNING_MESSAGE);
							return;
						}
						e.setName(name);
						
						switch(comboBox.getSelectedIndex()){
						case 0:
							e.setPosition("普通员工");
							break;
						case 1:
							e.setPosition("经理");
							break;
						case 2:
							e.setPosition("董事");
							break;
						default:
							JOptionPane.showMessageDialog(MyDialog.this,"职位不合法的值","警告",JOptionPane.WARNING_MESSAGE);
							return;
						}
						
						try {
							e.setSalary(Double.parseDouble(textField_3.getText().trim()));
						} catch (NumberFormatException e2) {
//							e2.printStackTrace();
							JOptionPane.showMessageDialog(MyDialog.this,"工资 必须为数字","警告",JOptionPane.WARNING_MESSAGE);
							return;
						}
						
						try {
							e.setAbsenteeism(Integer.parseInt(textField_4.getText().trim()));
						} catch (NumberFormatException e4) {
							JOptionPane.showMessageDialog(MyDialog.this,"旷工天数必须为整数","警告",JOptionPane.WARNING_MESSAGE);
							return;
						}
						
						EmployeeService.getInstance().modify(e);
					}else{
						//添加
						Employee e=null;
						
						switch(comboBox.getSelectedIndex()){
						case 0:
							e=new CommonEmployee();
							e.setPosition("普通员工");
							break;
						case 1:
							e=new Manager();
							e.setPosition("经理");
							break;
						case 2:
							e=new Director();
							e.setPosition("董事");
							break;
						default:
							JOptionPane.showMessageDialog(MyDialog.this,"职位不合法的值","警告",JOptionPane.WARNING_MESSAGE);
							return;
						}
						
						String id=textField.getText().trim();
						if(EmployeeService.getInstance().get(id)!=null){
							JOptionPane.showMessageDialog(MyDialog.this,"此员工编号已经存在，请换一个编号！","警告",JOptionPane.WARNING_MESSAGE);
							return;
						}else if(id.length()==0){
							JOptionPane.showMessageDialog(MyDialog.this,"请指定员工编号（一旦指定，不可更改）","警告",JOptionPane.WARNING_MESSAGE);
							return;
						}
						e.setId(id);
						
						String name=textField_1.getText().trim();
						if(name.length()==0){
							JOptionPane.showMessageDialog(MyDialog.this,"请输入名字","警告",JOptionPane.WARNING_MESSAGE);
							return;
						}
						e.setName(name);
						
						try {
							e.setSalary(Double.parseDouble(textField_3.getText().trim()));
						} catch (NumberFormatException e2) {
							JOptionPane.showMessageDialog(MyDialog.this,"工资 必须为数字","警告",JOptionPane.WARNING_MESSAGE);
							return;
						}
						
						try {
							e.setAbsenteeism(Integer.parseInt(textField_4.getText().trim()));
						} catch (NumberFormatException e4) {
							JOptionPane.showMessageDialog(MyDialog.this,"旷工天数必须为整数","警告",JOptionPane.WARNING_MESSAGE);
							return;
						}
						
						EmployeeService.getInstance().add(e);
					}
					MyDialog.this.dispose();
				}
			});
			panel_6.add(btnNewButton);
			
			JButton btnNewButton_1 = new JButton("取消");
			btnNewButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					MyDialog.this.dispose();;
				}
			});
			panel_6.add(btnNewButton_1);
			
			setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		}
	}

}
