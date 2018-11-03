package ca.mcgill.ecse223.resto.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.controller.RestoAppController;

public class CreateTableFrame extends JPanel {
	
	public CreateTableFrame(RestoAppPage app) {
	    JFrame frame = new JFrame("CreateTableFrame");
	    frame.setResizable(false);
	    frame.setAlwaysOnTop(true);
	    
	    
		JLabel tableNumberLabel;
		JTextField tableNumberBox;
		JLabel xCoordLabel;
		JLabel yCoordLabel;
		JTextField xCoordBox;
		JTextField yCoordBox;
		JLabel widthLabel;
		JLabel lengthLabel;
		JTextField widthBox;
		JTextField lengthBox;
		JLabel numberOfSeatLabel;
		JTextField numberOfSeatBox;
		tableNumberLabel = new JLabel();
		tableNumberLabel.setText("Table Num.:");
		tableNumberBox = new JTextField();
		xCoordLabel = new JLabel();
		xCoordLabel.setText("X coord.:");
		yCoordLabel = new JLabel();
		yCoordLabel.setText("Y coord.:");
		xCoordBox = new JTextField();
		yCoordBox = new JTextField();
		widthLabel = new JLabel();
		widthLabel.setText("Width:");
		lengthLabel = new JLabel();
		lengthLabel.setText("Length:");
		widthBox = new JTextField();
		lengthBox = new JTextField();
		numberOfSeatLabel = new JLabel();
		numberOfSeatLabel.setText("Seat qty.:");
		numberOfSeatBox = new JTextField();
		
		MyButton create = new MyButton("Create Table");
		create.setBorder(new RoundedBorder(10));
		
		create.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(tableNumberBox.getText() == ("") || 
							xCoordBox.getText() == ("") || 
							yCoordBox.getText() == ("") || 
							 widthBox.getText() == ("") || 
							lengthBox.getText() == ("") || 
							numberOfSeatBox.getText() == ("")) {
						 throw new InvalidInputException("Invalid Inputs");
						}
					RestoAppController.addTable(Integer.parseInt(tableNumberBox.getText()),
							Integer.parseInt(xCoordBox.getText()), 
							Integer.parseInt(yCoordBox.getText()), 
							Integer.parseInt(widthBox.getText()), 
							Integer.parseInt(lengthBox.getText()), 
							Integer.parseInt(numberOfSeatBox.getText()));
					tableNumberBox.setText("");
					xCoordBox.setText("");
					yCoordBox.setText("");
					widthBox.setText("");
					lengthBox.setText("");
					numberOfSeatBox.setText("");
					frame.dispose();
					app.refreshData();
						
		
				} catch (Exception ex) {
			        JOptionPane.showMessageDialog(null, "Invalid Inputs: " + ex.getMessage(), null, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		MyButton close = new MyButton("Close");
		close.setBorder(new RoundedBorder(10));
	    close.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				
			}
		});
	    
		


		
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
										.addComponent(tableNumberLabel)
										.addComponent(xCoordLabel)
										.addComponent(yCoordLabel)
										.addComponent(widthLabel)
										.addComponent(lengthLabel)
										.addComponent(numberOfSeatLabel)
										)
								.addGroup(layout.createParallelGroup()
										.addComponent(tableNumberBox)
										.addComponent(xCoordBox)
										.addComponent(yCoordBox)
										.addComponent(widthBox)
										.addComponent(lengthBox)
										.addComponent(numberOfSeatBox)
										))
						.addGroup(
								layout.createSequentialGroup()
									.addComponent(create)
									.addComponent(close)
								)
						);
					layout.setVerticalGroup(
					   layout.createSequentialGroup()
					      .addGroup(layout.createParallelGroup()
						           .addComponent(tableNumberLabel)
						           .addComponent(tableNumberBox))
					      .addGroup(layout.createParallelGroup()
						           .addComponent(xCoordLabel)
						           .addComponent(xCoordBox)
						           )
					      .addGroup(layout.createParallelGroup()
					           .addComponent(yCoordLabel)
					           .addComponent(yCoordBox))
					      .addGroup(layout.createParallelGroup()
						           .addComponent(widthLabel)
						           .addComponent(widthBox))
					      .addGroup(layout.createParallelGroup()
						           .addComponent(lengthLabel)
						           .addComponent(lengthBox))
					      .addGroup(layout.createParallelGroup()
						           .addComponent(numberOfSeatLabel)
						           .addComponent(numberOfSeatBox))
					      .addGroup(layout.createParallelGroup()
					    		  .addComponent(create)
					    		  .addComponent(close)
					    		  )

					);
			
			
		    frame.pack();
		    frame.setVisible(true);
	}
}
	
