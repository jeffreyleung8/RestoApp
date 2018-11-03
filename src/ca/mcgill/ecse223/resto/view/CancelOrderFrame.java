package ca.mcgill.ecse223.resto.view;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.swing.*;
import javax.swing.event.*;
import com.github.lgooddatepicker.components.*;

import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.controller.RestoAppController;
import ca.mcgill.ecse223.resto.model.Order;
import ca.mcgill.ecse223.resto.model.OrderItem;
import ca.mcgill.ecse223.resto.model.Seat;
import ca.mcgill.ecse223.resto.model.Table;
import ca.mcgill.ecse223.resto.model.Table.Status;

public class CancelOrderFrame extends JPanel {

	public int selectedOrderItem;

	public CancelOrderFrame(RestoAppPage app, Order order) {
		JFrame frame = new JFrame("CancelOrderItem");
		frame.setResizable(false);
		frame.setAlwaysOnTop(true);
		selectedOrderItem = 0;

		JLabel OrderItemLabel = new JLabel("Select Seat");
		DefaultComboBoxModel<String> stringModel = new DefaultComboBoxModel<String>();

		JComboBox<String> stringBox = new JComboBox<String>();
		MyButton cancel = new MyButton("Cancel Seat");
		MyButton cancelOrder = new MyButton("Cancel Order");
		MyButton Close = new MyButton("Close");

		stringBox.setModel(stringModel);
		stringBox.setEditable(true);
		stringBox.setMaximumSize(new Dimension(150, 20));
		stringBox.getEditor().getEditorComponent().setBackground(Color.WHITE);

		int index =1;
		for (ListIterator<Seat> iter = order.getSeats().listIterator(); iter.hasNext(); ) {
			stringModel.addElement("Seat #" + index);
			index++;
			iter.next();
		}

		// END COMPONENTS

		// INIT

		// END INIT

		Close.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});

		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					RestoAppController.CancelSeatOrder(order.getSeat(stringBox.getSelectedIndex()), order);
					frame.dispose();
					app.refreshData();

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), null, JOptionPane.ERROR_MESSAGE);
				}
				//

			}
		});
		
		
		cancelOrder.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					RestoAppController.CancelOrder(order);;
					frame.dispose();
					app.refreshData();

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), null, JOptionPane.ERROR_MESSAGE);
				}
				//
			}
		});

		// frame.setDefaultCloseOperation(JFrame.);

		java.awt.Container contentPane = frame.getContentPane();

		GroupLayout layout = new GroupLayout(contentPane);
		contentPane.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup().addComponent(OrderItemLabel))
						.addGroup(layout.createParallelGroup().addComponent(stringBox)))
				.addGroup(layout.createSequentialGroup()
						.addComponent(cancel))
				.addGroup(layout.createSequentialGroup()
						.addComponent(cancelOrder)
						.addComponent(Close)));

		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(OrderItemLabel)
						.addComponent(stringBox))
				.addGroup(layout.createParallelGroup()
						.addComponent(cancel))
				.addGroup(layout.createParallelGroup()
						.addComponent(cancelOrder)
						.addComponent(Close))

		);

		frame.pack();
		frame.setVisible(true);
	}
}