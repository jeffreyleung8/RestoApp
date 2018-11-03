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
import ca.mcgill.ecse223.resto.model.Table;

public class UpdateTableFrame extends JPanel {

public UpdateTableFrame(RestoAppPage app, Table table) {
    JFrame frame = new JFrame("UpdateTableFrame");
    frame.setResizable(false);
    frame.setAlwaysOnTop(true);
    
   
    //COMPONENTS
    JLabel label_TableNumber = new JLabel("Table Number");
    JLabel label_NumberOfSeats = new JLabel("Number of Seats");
    JTextField text_TableNumber = new JTextField();
    JTextField text_NumberOfSeats = new JTextField();
    MyButton button_Update = new MyButton("Update");
    MyButton button_Close = new MyButton("Close");
    
    //END COMPONENTS
    
    //INIT
    text_TableNumber.setText(String.valueOf(table.getNumber()));
    text_NumberOfSeats.setText(String.valueOf(table.numberOfCurrentSeats()));
    //END INIT
    
    button_Close.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.dispose();
			
		}
	});
    
    button_Update.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				int TableNumber = Integer.parseInt(text_TableNumber.getText());
				int NumberOfSeats = Integer.parseInt(text_NumberOfSeats.getText());

				RestoAppController.updateTable(table, TableNumber, NumberOfSeats);
				app.refreshData();
				frame.dispose();
				
			} catch (NumberFormatException ex) {
		        JOptionPane.showMessageDialog(null, "Please enter valid Table Number and Number of Seats", null, JOptionPane.ERROR_MESSAGE);

			} catch (InvalidInputException ex) {
		        JOptionPane.showMessageDialog(null, ex.getMessage(), null, JOptionPane.ERROR_MESSAGE);
			}  catch (Exception ex) {
		        JOptionPane.showMessageDialog(null, "Unknown exception: " + ex.getMessage(), null, JOptionPane.ERROR_MESSAGE);
			}
			 
			
		}
	});
    
    
//    frame.setDefaultCloseOperation(JFrame.);

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
								.addComponent(label_TableNumber)
								.addComponent(label_NumberOfSeats))
						.addGroup(layout.createParallelGroup()
								.addComponent(text_TableNumber)
								.addComponent(text_NumberOfSeats)))
				.addGroup(
						layout.createSequentialGroup()
							.addComponent(button_Update)
							.addComponent(button_Close)
						)
				);
			layout.setVerticalGroup(
			   layout.createSequentialGroup()
			      .addGroup(layout.createParallelGroup()
			           .addComponent(label_TableNumber)
			           .addComponent(text_TableNumber))
			      .addGroup(layout.createParallelGroup()
				           .addComponent(label_NumberOfSeats)
				           .addComponent(text_NumberOfSeats))
			      .addGroup(layout.createParallelGroup()
			    		  .addComponent(button_Update)
			    		  .addComponent(button_Close)
			    		  )
			);
	
    frame.pack();
    frame.setVisible(true);
	}
}