package ca.mcgill.ecse223.resto.view;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.github.lgooddatepicker.components.*;

import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.controller.RestoAppController;
import ca.mcgill.ecse223.resto.model.Table;

public class CreateReservationFrame extends JPanel {

public CreateReservationFrame(RestoAppPage app) {
    JFrame frame = new JFrame("ReserveTableFrame");
    frame.setResizable(false);
    frame.setAlwaysOnTop(true);
  //Date date, Time time, int numberInParty, String contactName, String contactEmail, String contactPhone, List<Table>
    //COMPONENTS
    
    DatePicker datePicker = new DatePicker();
    TimePicker timePicker = new TimePicker();

    JLabel date = new JLabel("Date");
    JLabel time = new JLabel("Time");
    JLabel numberInParty = new JLabel("# People");
    JLabel contactName = new JLabel("Name");
    JLabel contactEmail = new JLabel("Email");
    JLabel contactPhone = new JLabel("Phone");
    JLabel tables = new JLabel("Tables");
    JLabel tablesDesc = new JLabel("(enter the table number(s) seperated by a comma)");

    JTextField textNumberInParty  = new JTextField();
    JTextField textContactName = new JTextField();
    JTextField textContactEmail = new JTextField();
    JTextField textContactPhone = new JTextField();
    JTextField textTables = new JTextField();

    MyButton btnReserve = new MyButton("Reserve");
    MyButton btnClose = new MyButton("Close");
    
    //END COMPONENTS
    
    //INIT
    
    //END INIT
    
    btnClose.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.dispose();			
		}
	});
    
    btnReserve.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				LocalDate aLocalDate = datePicker.getDate();
				Date aDate = new Date(aLocalDate.getYear()-1900, aLocalDate.getMonthValue()-1, aLocalDate.getDayOfMonth());
				LocalTime aLocalTime = timePicker.getTime();
				Time aTime = new Time(aLocalTime.getHour(), aLocalTime.getMinute(), aLocalTime.getSecond());
				
			    LocalDateTime aDateTime = LocalDateTime.of(aLocalDate, aLocalTime);
			    if (aDateTime.isBefore(LocalDateTime.now())) {
			    	throw new InvalidInputException("Cannot reserve in the past");
			    }
				
				int numberInParty=0;
				try{
					numberInParty = Integer.parseInt(textNumberInParty.getText());
				} catch (NumberFormatException ex) {
			        JOptionPane.showMessageDialog(null, "Please enter the number of people in integer", null, JOptionPane.ERROR_MESSAGE);
				}
				String contactName = textContactName.getText();
				String contactEmail = textContactEmail.getText();
				String contactPhone = textContactPhone.getText();
				List<Table> tableList = RestoAppController.getCurrentTables();
				ArrayList<Table> selectedTables = new ArrayList<Table>();
				
				String[] tables = textTables.getText().split(",");
				ArrayList<Integer> tableNumbers = new ArrayList<Integer>();
				for (String n: tables) {
					int number = -1;
					try {
						number = Integer.parseInt(n);
					} catch (NumberFormatException ex) {
				        JOptionPane.showMessageDialog(null, "Invalid table number", null, JOptionPane.ERROR_MESSAGE);
				        return;
					}
					tableNumbers.add(number);
					
				}
				
				for (Table t: tableList) {
					if (tableNumbers.contains(t.getNumber())){
						selectedTables.add(t);
					}
				}
				
				if( selectedTables.size() < tableNumbers.size()) {
					throw new InvalidInputException("One or more selected table doesn't exist");
				}
				
				RestoAppController.addReservation(aDate, aTime, numberInParty, contactName, contactEmail, contactPhone, selectedTables);				
				frame.dispose();
				app.refreshData();

			} catch (InvalidInputException ex) {
		        JOptionPane.showMessageDialog(null, ex.getMessage(), null, JOptionPane.ERROR_MESSAGE);
			}  catch (Exception ex) {
		        JOptionPane.showMessageDialog(null, "Unknown exception: " + ex.getMessage(), null, JOptionPane.ERROR_MESSAGE);
			}
//			 
			
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
								.addComponent(date)
								.addComponent(time)
								.addComponent(contactName)
								.addComponent(contactEmail)
								.addComponent(contactPhone)
								.addComponent(numberInParty)
								.addComponent(tables))
						.addGroup(layout.createParallelGroup()
								.addComponent(datePicker)
								.addComponent(timePicker)
								.addComponent(textContactName)
								.addComponent(textContactEmail)
								.addComponent(textContactPhone)
								.addComponent(textNumberInParty)
								.addComponent(textTables)
								.addComponent(tablesDesc)
								))
				.addGroup(
						layout.createSequentialGroup()
							.addComponent(btnReserve)
							.addComponent(btnClose)
						)
				);
			layout.setVerticalGroup(
			   layout.createSequentialGroup()
			      .addGroup(layout.createParallelGroup()
				           .addComponent(date)
				           .addComponent(datePicker))
			      .addGroup(layout.createParallelGroup()
				           .addComponent(time)
				           .addComponent(timePicker)
				           )
			      .addGroup(layout.createParallelGroup()
			           .addComponent(contactName)
			           .addComponent(textContactName))
			      .addGroup(layout.createParallelGroup()
				           .addComponent(contactEmail)
				           .addComponent(textContactEmail))
			      .addGroup(layout.createParallelGroup()
				           .addComponent(contactPhone)
				           .addComponent(textContactPhone))
			      .addGroup(layout.createParallelGroup()
				           .addComponent(numberInParty)
				           .addComponent(textNumberInParty))
			      .addGroup(layout.createParallelGroup()
			    		  .addComponent(tables)
			    		  .addComponent(textTables))
			      .addGroup(layout.createParallelGroup()
			    		  .addComponent(tablesDesc))
			      .addGroup(layout.createParallelGroup()
			    		  .addComponent(btnReserve)
			    		  .addComponent(btnClose)
			    		  )

			);
	
    // Set the x, y, width and height properties
    frame.pack();
    frame.setVisible(true);
}
}