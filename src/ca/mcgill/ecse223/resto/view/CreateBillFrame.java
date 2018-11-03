package ca.mcgill.ecse223.resto.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Box;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;

import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.controller.RestoAppController;
import ca.mcgill.ecse223.resto.application.RestoAppApplication;
import ca.mcgill.ecse223.resto.model.Table;
import ca.mcgill.ecse223.resto.model.Seat;
import ca.mcgill.ecse223.resto.model.Order;
import ca.mcgill.ecse223.resto.model.OrderItem;
import ca.mcgill.ecse223.resto.model.Reservation;
import ca.mcgill.ecse223.resto.model.RestoApp;

public class CreateBillFrame extends JPanel {
	
	private JFrame frame;
	private JLabel errorMessage;
	private String error = null;
	
	private JComboBox<Order> orderList;
	private JLabel selectOrderLabel;
	private Integer selectedOrder = -1;
	
	private JLabel selectSeatLabel;
	private JTextField seatList;
	
	private DefaultComboBoxModel orderModel = new DefaultComboBoxModel<Order>();
	
	private MyButton issueBill;
	private MyButton close;
	
	private static final long serialVersionUID = 1L;
	
	public CreateBillFrame(RestoAppPage app) {
		frame = new JFrame("CreateBillFrame");
		frame.setResizable(false);
	    frame.setAlwaysOnTop(true);
	
		//JLabel select order
		selectOrderLabel = new JLabel("Select Order");
		
		//JComboBox select order
		orderList = new JComboBox<Order>();
		orderList.setEditable(true);
        orderList.getEditor().getEditorComponent().setBackground(Color.WHITE);
		orderList.setMaximumSize(new Dimension(150,20));
		orderList.setModel(orderModel);
		
		orderModel.removeAllElements();
		for (Order order : RestoAppController.getCurrentOrders()) {
			orderModel.addElement(order);
		}
		selectedOrder = -1;
		orderList.setSelectedIndex(selectedOrder);
		
		orderList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
		        JComboBox<String> cb = (JComboBox<String>) evt.getSource();
		        selectedOrder = cb.getSelectedIndex();
			}
		});
		
		//JLabel seat
		selectSeatLabel = new JLabel("Enter Seats");
		
		//JTextField seat
		seatList = new JTextField();
		seatList.setMaximumSize(new Dimension(150,20));
		
		//MyButton issueBill
		issueBill = new MyButton("Issue Bill");
		issueBill.setBorder(new RoundedBorder(10));
		issueBill.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				error =null;
				try{
					issueBillButtonActionPerformed(evt, (Order) orderList.getSelectedItem(), app);
//					seatList.setText("");
//					orderModel.removeAllElements();
//					for (Order order : RestoAppController.getCurrentOrders()) {
//						orderModel.addElement(order);
//					}
//					selectedOrder = -1;
//					orderList.setSelectedIndex(selectedOrder);

				}
				catch(NullPointerException ex){
					errorMessage.setText("Error");
				}
			}
		});
		
		//MyButton close
		close = new MyButton("Close");
		close.setBorder(new RoundedBorder(10));
	    close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		
		//layout
		java.awt.Container contentPane = frame.getContentPane();
	    
		GroupLayout layout = new GroupLayout(contentPane);
		contentPane.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(
				layout.createParallelGroup()
				.addGroup(				
					layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
								.addComponent(selectOrderLabel)
								.addComponent(selectSeatLabel))
						.addGroup(layout.createParallelGroup()
								.addComponent(orderList)
								.addComponent(seatList)))
				.addGroup(
					layout.createSequentialGroup()
						.addComponent(issueBill)
						.addComponent(close))
		);
		layout.setVerticalGroup(
				 layout.createSequentialGroup()
				      .addGroup(layout.createParallelGroup()
					           .addComponent(selectOrderLabel)
					           .addComponent(orderList))
				      .addGroup(layout.createParallelGroup()
					           .addComponent(selectSeatLabel)
					           .addComponent(seatList))
				      .addGroup(layout.createParallelGroup()
				    		  .addComponent(issueBill)
				    		  .addComponent(close))
		);	
		frame.pack();
	    frame.setVisible(true);
	}
	
	protected void issueBillButtonActionPerformed(ActionEvent evt, Order order, RestoAppPage app) {
		// TODO Auto-generated method stub
		//To implement
		try {
			//create ArrayList of seats associated with the order
			List<Table> tableList = order.getTables();
			List<Seat> seats = order.getSeats();

			//create ArrayList to store selected seats
			List<Seat> selectedSeats = new ArrayList<Seat>();
			String[] seatsNumbers = seatList.getText().split(",");
			//create ArrayList to store selected seat numbers
			ArrayList<Integer> seatNumbers = new ArrayList<Integer>();
			
			for (String n: seatsNumbers) {
				int number = -1;
				try {
					number = Integer.parseInt(n);
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Invalid Number", null, JOptionPane.ERROR_MESSAGE);
			        throw new InvalidInputException();
				}
				seatNumbers.add(number);
			}
			for(int seatNumber : seatNumbers) {
				//invalid index
				if (seatNumber > seats.size() || seatNumber <= 0) {
					JOptionPane.showMessageDialog(null, "One or more entered seats doesn't exist", null, JOptionPane.ERROR_MESSAGE);
			        throw new InvalidInputException();
				}
				selectedSeats.add(seats.get(seatNumber - 1));
			}
			if( selectedSeats.size() < seatNumbers.size()) {
				JOptionPane.showMessageDialog(null, "One or more entered seats doesn't exist", null, JOptionPane.ERROR_MESSAGE);
				throw new InvalidInputException();
			}
			for(Seat seat : selectedSeats) {
				boolean seatHasOrderItem = false;
				for(OrderItem oi : order.getOrderItems()) {
					if (oi.getSeats().contains(seat)) {
						seatHasOrderItem = true;
						break;
					}
				}
				if(!seatHasOrderItem) {
					JOptionPane.showMessageDialog(null, "Seat doesn't have orderItem", null, JOptionPane.ERROR_MESSAGE);
					throw new InvalidInputException();
				}
			}
			
			RestoAppController.issueBill(selectedSeats);
			
			int numberNumber = 0;
			for (Seat seat : selectedSeats) {
				if(seat.hasBills()) {
					if(seat.getBill(seat.numberOfBills()-1).getOrder() == order) {
						new BillFrame(seat, seatNumbers.get(numberNumber));
						numberNumber ++;
					}	
				}
				
			}
			updateModel();
			frame.dispose();
			app.refreshData();
			
		}catch(Exception ex){
			//JOptionPane.showMessageDialog(null, "Unknown exception: " + ex.getMessage(), null, JOptionPane.ERROR_MESSAGE);
		}
		
	}
	public void updateModel() {
		seatList.setText("");
		orderModel.removeAllElements();
		for (Order order : RestoAppController.getCurrentOrders()) {
			orderModel.addElement(order);
		}
		selectedOrder = -1;
		orderList.setSelectedIndex(selectedOrder);
	}
}
	
	
		

	    
		    	
	

	
