package ca.mcgill.ecse223.resto.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.controller.RestoAppController;
import ca.mcgill.ecse223.resto.model.Bill;
import ca.mcgill.ecse223.resto.model.Order;
import ca.mcgill.ecse223.resto.model.OrderItem;
import ca.mcgill.ecse223.resto.model.Seat;
import ca.mcgill.ecse223.resto.model.Table;

public class BillTableFrame extends JFrame {
	
	public BillTableFrame(Table table) {
		// TODO Auto-generated constructor stub
		JFrame viewOrder = new JFrame("Bill");
	    viewOrder.setResizable(false);
	    viewOrder.setAlwaysOnTop(true);
	    String orderList = "Bill for table : " + table.getNumber();
	    //COMPONENTS
	    JLabel order = new JLabel();
	    //JLabel orderDisplay = new JLabel("Orders");
	    MyButton exitViewOrder = new MyButton("Close");
	    //END COMPONENTS
	    //END INIT
	    
	    DefaultListModel<String> listModel = new DefaultListModel<String>();
	    JList<String> list = new JList<String>(listModel); 


	    
	    list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	    list.setLayoutOrientation(JList.VERTICAL);
	    list.setVisibleRowCount(-1);

	    JScrollPane listScroller = new JScrollPane(list);
	    
	    exitViewOrder.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				viewOrder.dispose();
			}
		});
	    Double total = 0.0;
	    Order o = table.getOrder(table.numberOfOrders()-1);
	    for(OrderItem oi : o.getOrderItems()) {
	    	double price = (oi.getPricedMenuItem().getPrice());
    		total += price;
    		String s = oi.getPricedMenuItem().getMenuItem().getName() + " " + String.valueOf(price);
    		listModel.addElement(s);

	    }
	    try {
			RestoAppController.issueBill(table.getCurrentSeats());
		} catch (InvalidInputException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	    listModel.addElement("Total : " + String.valueOf(total));
		order.setText(orderList);

	    java.awt.Container contentPane = viewOrder.getContentPane();
	    
	    GroupLayout layout = new GroupLayout(contentPane);
		contentPane.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(
				layout.createParallelGroup()
				.addComponent(order)
				.addComponent(list)
				.addComponent(exitViewOrder)
				);

		
		layout.setVerticalGroup(
				   layout.createSequentialGroup()
				   .addComponent(order)
					.addComponent(list)
					.addComponent(exitViewOrder)
					);
	    
	    viewOrder.pack();
	    viewOrder.setVisible(true);
	}
}
