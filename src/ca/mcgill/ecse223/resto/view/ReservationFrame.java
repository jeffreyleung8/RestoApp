package ca.mcgill.ecse223.resto.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import ca.mcgill.ecse223.resto.model.Reservation;
import ca.mcgill.ecse223.resto.model.Table;

public class ReservationFrame extends JFrame {
	
	public ReservationFrame(Reservation res) {
		// TODO Auto-generated constructor stub
		JFrame viewOrder = new JFrame("Reservation");
	    viewOrder.setResizable(false);
	    viewOrder.setAlwaysOnTop(true);
	    JTextField reservation = new JTextField();
	    JTextField contactName = new JTextField();
	    JTextField tableNumber = new JTextField();
	    JTextField phoneNumber = new JTextField();
	    JTextField email = new JTextField();
	    JTextField numberInParty = new JTextField();
	    JTextField date = new JTextField();
	    JTextField time = new JTextField();
	    
	    reservation.setEditable(false);
	    contactName.setEditable(false);
	    tableNumber.setEditable(false);
	    phoneNumber.setEditable(false);
	    email.setEditable(false);
	    numberInParty.setEditable(false);
	    date.setEditable(false);
	    time.setEditable(false);
	    
	    //COMPONENTS
	    JLabel lblReservation = new JLabel("Reservation Number ");
	    reservation.setText(String.valueOf(res.getReservationNumber()));
	    
	    JLabel lblContactName = new JLabel("Name : ");
	    contactName.setText("" + res.getContactName());
	    
	    List<Table> tables = res.getTables();
	    String tableNumbers = "";
	    
	    for(Table table : tables) {
	    	tableNumbers += "#" + String.valueOf(table.getNumber()) + " ";
	    }
	    JLabel lblTableNumber = new JLabel("Table Numbers ");
	    tableNumber.setText(tableNumbers);
	    
	    JLabel lblPhoneNumber = new JLabel("Phone Number : ");
	    phoneNumber.setText("" + res.getContactPhoneNumber());
	    JLabel lblEmail = new JLabel("Email : ");
	    email.setText("" + res.getContactEmailAddress());
	    
	    JLabel lblNumberInParty = new JLabel("Number in party : ");
	    numberInParty.setText("" + res.getNumberInParty());
	    JLabel lblDate = new JLabel("Date : ");
	    date.setText( String.valueOf(res.getDate()));
	    JLabel lblTime = new JLabel("Time : ");
	    time.setText(String.valueOf(res.getTime()));
	    
	    
	   
	    
	    
	    
	    
	    
	    
	    //JLabel orderDisplay = new JLabel("Orders");
	    MyButton exitViewOrder = new MyButton("Close");
	    //END COMPONENTS
	    //END INIT
	    
	    exitViewOrder.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				viewOrder.dispose();
			}
		});

	    java.awt.Container contentPane = viewOrder.getContentPane();
	    
	    GroupLayout layout = new GroupLayout(contentPane);
		contentPane.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(
				layout.createParallelGroup()
					.addGroup(layout.createSequentialGroup()
								.addComponent(lblReservation)
								.addComponent(reservation)
							)
					.addGroup(layout.createSequentialGroup()
							.addComponent(lblContactName)
							.addComponent(contactName)
							)
					.addGroup(layout.createSequentialGroup()
							.addComponent(lblTableNumber)
							.addComponent(tableNumber)
							)
					.addGroup(layout.createSequentialGroup()
							.addComponent(lblPhoneNumber)
							.addComponent(phoneNumber)
							)
					.addGroup(layout.createSequentialGroup()
							.addComponent(lblEmail)
							.addComponent(email)
							)
					.addGroup(layout.createSequentialGroup()
							.addComponent(lblDate)
							.addComponent(date)
							)
					.addGroup(layout.createSequentialGroup()
							.addComponent(lblTime)
							.addComponent(time)
							)
					.addGroup(layout.createSequentialGroup()
							.addComponent(lblNumberInParty)
							.addComponent(numberInParty)
							)
					.addGroup(layout.createSequentialGroup()
							.addComponent(exitViewOrder)
							)
					);

		
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addGroup(
						layout.createParallelGroup()
						.addComponent(lblReservation)
						.addComponent(reservation))
				
				.addGroup(
						layout.createParallelGroup()
						.addComponent(lblContactName)
						.addComponent(contactName))
				.addGroup(layout.createParallelGroup()
						.addComponent(lblTableNumber)
						.addComponent(tableNumber)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(lblPhoneNumber)
						.addComponent(phoneNumber)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(lblEmail)
						.addComponent(email)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(lblDate)
						.addComponent(date)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(lblTime)
						.addComponent(time)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(lblNumberInParty)
						.addComponent(numberInParty)
						)
				
				.addComponent(exitViewOrder)
				);
		layout.linkSize(SwingConstants.VERTICAL, new java.awt.Component[] {reservation, contactName, tableNumber, phoneNumber, email, date, time, numberInParty});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {reservation, contactName, tableNumber, phoneNumber, email, date, time, numberInParty});
		layout.linkSize(SwingConstants.VERTICAL, new java.awt.Component[] {lblReservation, lblContactName, lblTableNumber, lblPhoneNumber, lblEmail,lblDate,lblTime, lblNumberInParty});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {lblReservation, lblContactName, lblTableNumber, lblPhoneNumber, lblEmail,lblDate,lblTime, lblNumberInParty});

	    
	    viewOrder.pack();
	    viewOrder.setVisible(true);
	}
}
