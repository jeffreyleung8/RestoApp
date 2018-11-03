package ca.mcgill.ecse223.resto.view;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.*;
import com.github.lgooddatepicker.components.*;

import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.controller.RestoAppController;
import ca.mcgill.ecse223.resto.model.Order;
import ca.mcgill.ecse223.resto.model.OrderItem;
import ca.mcgill.ecse223.resto.model.Table;
import ca.mcgill.ecse223.resto.model.Table.Status;

public class CancelOrderItemFrame extends JPanel {

	public int selectedOrderItem;

	public CancelOrderItemFrame(RestoAppPage app, Order order) {
		JFrame frame = new JFrame("CancelOrderItem");
		frame.setResizable(false);
		frame.setAlwaysOnTop(true);
		selectedOrderItem = 0;

		JLabel OrderItemLabel = new JLabel("Select Order Item");
		DefaultComboBoxModel<OrderItem> OrderItemModel = new DefaultComboBoxModel<OrderItem>();

		JComboBox<OrderItem> OrderItemBox = new JComboBox<OrderItem>();
		MyButton cancel = new MyButton("Cancel");
		MyButton Close = new MyButton("Close");

		OrderItemBox.setModel(OrderItemModel);
		OrderItemBox.setEditable(true);
		OrderItemBox.setMaximumSize(new Dimension(150, 20));
		OrderItemBox.getEditor().getEditorComponent().setBackground(Color.WHITE);


		for (OrderItem OI : order.getOrderItems()){
			OrderItemModel.addElement(OI);
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
					RestoAppController.CancelOrderItem((OrderItem) OrderItemBox.getSelectedItem());
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
						.addGroup(layout.createParallelGroup().addComponent(OrderItemBox)

				)).addGroup(layout.createSequentialGroup().addComponent(cancel).addComponent(Close)));

		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup().addComponent(OrderItemLabel).addComponent(OrderItemBox))

				.addGroup(layout.createParallelGroup().addComponent(cancel).addComponent(Close))

		);

		frame.pack();
		frame.setVisible(true);
	}
}