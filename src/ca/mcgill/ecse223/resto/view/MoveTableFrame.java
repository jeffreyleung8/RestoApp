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

public class MoveTableFrame extends JPanel {

public MoveTableFrame(RestoAppPage app, Table t) {
    JFrame frame = new JFrame("MoveTableFrame");
    frame.setResizable(false);
    frame.setAlwaysOnTop(true);
    
    //COMPONENTS
    JLabel lblX = new JLabel("X value");
    JLabel lblY = new JLabel("Y value");
    JTextField txtX = new JTextField();
    JTextField txtY = new JTextField();
    MyButton btnMove = new MyButton("Move");
    MyButton btnRotate = new MyButton("Rotate");
    btnMove.setBorder(new RoundedBorder(10));
    MyButton btnClose = new MyButton("Close");
    btnClose.setBorder(new RoundedBorder(10));
    
    //END COMPONENTS
    
    //INIT
    txtX.setText(String.valueOf(t.getX()));
    txtY.setText(String.valueOf(t.getY()));
    //END INIT
    
    btnClose.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.dispose();
			// TODO Auto-generated method stub
			
		}
	});
    
    btnMove.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				int x = Integer.parseInt(txtX.getText());
				int y = Integer.parseInt(txtY.getText());

				RestoAppController.moveTable(t, x, y);
				
				frame.dispose();
				app.refreshData();
			} catch (NumberFormatException ex) {
		        JOptionPane.showMessageDialog(null, "Please enter coordinates in integer", null, JOptionPane.ERROR_MESSAGE);

			} catch (InvalidInputException ex) {
		        JOptionPane.showMessageDialog(null, ex.getMessage(), null, JOptionPane.ERROR_MESSAGE);
			}  catch (Exception ex) {
		        JOptionPane.showMessageDialog(null, "Unknown exception: " + ex.getMessage(), null, JOptionPane.ERROR_MESSAGE);
			}
			 
			
		}
	});
    
    
    btnRotate.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			try {


				RestoAppController.rotateTable(t);
				
				frame.dispose();
				app.refreshData();
			} catch (NumberFormatException ex) {
		        JOptionPane.showMessageDialog(null, "Please enter coordinates in integer", null, JOptionPane.ERROR_MESSAGE);

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
								.addComponent(lblX)
								.addComponent(lblY))
						.addGroup(layout.createParallelGroup()
								.addComponent(txtX)
								.addComponent(txtY)))
				.addGroup(
						layout.createSequentialGroup()
							.addComponent(btnMove)
						)
				.addGroup(
						layout.createSequentialGroup()
							.addComponent(btnRotate)
							.addComponent(btnClose)
						)
				);
			layout.setVerticalGroup(
			   layout.createSequentialGroup()
			      .addGroup(layout.createParallelGroup()
			           .addComponent(lblX)
			           .addComponent(txtX))
			      .addGroup(layout.createParallelGroup()
				           .addComponent(lblY)
				           .addComponent(txtY))
			      .addGroup(layout.createParallelGroup()
			    		  .addComponent(btnRotate)
			    		  )
			      .addGroup(layout.createParallelGroup()
			    		  .addComponent(btnMove)
			    		  .addComponent(btnClose)
			    		  )
			);
	
    // Set the x, y, width and height properties
    frame.pack();
    frame.setVisible(true);
}
}