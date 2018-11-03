package ca.mcgill.ecse223.resto.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.controller.RestoAppController;
import ca.mcgill.ecse223.resto.model.MenuItem;
import ca.mcgill.ecse223.resto.model.Order;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Seat;
import ca.mcgill.ecse223.resto.model.Table;


public class DisplayMenuPage extends JPanel {
	
	private static final long serialVersionUID = -3257644828027805214L;
	
	private JLabel errorMessage;
	private String error = null;
	
	private JLabel menuName;
	
	private JPanel grid;
	private JPanel description;
	
	private JLabel price;
	private JLabel gap;
	
	private JLabel itemName;
	
	private JComboBox<Order> orderList;
	private JLabel selectOrderLabel;
	private Integer selectedOrder = -1;
	
	private JLabel selectSeatLabel;
	private JTextField seatList;
	
	private JLabel quantityLabel;
	private JTextField quantityField;

	private MyButton addOrder;
	
	private MenuItem selectedMenuItem = null;

	private DefaultComboBoxModel orderModel = new DefaultComboBoxModel<Order>();

	private BufferedImage img;

	public DisplayMenuPage(RestoAppPage app) {
		super();
		init(app);
		try {
            img = ImageIO.read(new File(
                    "pictures/menu2.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
		for (Table table : RestoAppController.getCurrentTables()) {
			System.out.println(table.getNumber());
			System.out.println(table.getCurrentSeats().size());
			System.out.println(table.getSeats().size());
		}
	}

	public void init(RestoAppPage app) {
		this.setSize(200, 250);
		setBackground(Color.WHITE);
		
		//JLabel menu name
		//menuName = new JLabel("Menu",JLabel.CENTER);
		menuName = new JLabel();
		menuName.setForeground(Color.white);
		Font font = new Font("Century Gothic", Font.BOLD, 20);
		menuName.setFont(font);
		menuName.setHorizontalTextPosition(JLabel.CENTER);
		
		//JPanel(grid)
		grid = new JPanel();
		grid.setOpaque(false);
		grid.setLayout(new GridLayout(3,3));
		grid.setBackground( Color.WHITE );

		//JPanel description
		description = new JPanel();
		description.setBackground( Color.WHITE );
		
		//Gap
		gap = new JLabel("    ");
		gap.setVisible(false);
		
		//Item Name
		itemName = new JLabel("Item Selected");
		itemName.setVisible(false);
		
		//Price
		price = new JLabel("Price of selected item");
		price.setVisible(false);
		
		//Table JLabel
		selectOrderLabel = new JLabel("Select Order");
		selectOrderLabel.setVisible(false);
		
		//Table JComboBox
		orderList = new JComboBox<Order>();
		orderList.setEditable(true);
        orderList.getEditor().getEditorComponent().setBackground(Color.WHITE);
		orderList.setMaximumSize(new Dimension(150,20));
		orderList.setVisible(false);
		orderList.setModel(orderModel);
		orderList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
		        JComboBox<String> cb = (JComboBox<String>) evt.getSource();
		        selectedOrder = cb.getSelectedIndex();
			}
		});
		
		//Seat JLabel
		selectSeatLabel = new JLabel("Enter Seats (separated with a coma)");
		selectSeatLabel.setVisible(false);
		
		//Seat JComboBox
		seatList = new JTextField();
		seatList.setVisible(false);
		seatList.setMaximumSize(new Dimension(150,20));
		
		quantityLabel = new JLabel("Quantity");
		quantityLabel.setVisible(false);
		quantityField = new JTextField();
		quantityField.setVisible(false);
		quantityField.setMaximumSize(new Dimension(150,20));
		
		//Order MyButton
		addOrder = new MyButton("Add Order");
		addOrder.setBorder(new RoundedBorder(10));
		addOrder.setVisible(false);
		addOrder.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				error =null;
				try{
					
					addOrderButtonActionPerformed(evt, (Order) orderList.getSelectedItem(), app);
				}
				catch(NullPointerException ex){
					errorMessage.setText("Error");
				}
			}
		});
		
		GroupLayout bottomPanel = new GroupLayout(description);
		description.setLayout(bottomPanel);
		bottomPanel.setAutoCreateGaps(true);
		bottomPanel.setAutoCreateContainerGaps(true);
		bottomPanel.setHorizontalGroup(
				bottomPanel.createSequentialGroup()
				.addComponent(itemName)
				.addComponent(price)
				.addComponent(gap)
				.addComponent(selectOrderLabel)
				.addComponent(orderList)
				.addComponent(selectSeatLabel)
				.addComponent(seatList)
				.addComponent(quantityLabel)
				.addComponent(quantityField)
				.addComponent(addOrder)
		);
		bottomPanel.setVerticalGroup(
				bottomPanel.createParallelGroup()
				.addComponent(itemName)
				.addComponent(price)
				.addComponent(gap)
				.addComponent(selectOrderLabel)
				.addComponent(orderList)
				.addComponent(selectSeatLabel)
				.addComponent(seatList)
				.addComponent(quantityLabel)
				.addComponent(quantityField)
				.addComponent(addOrder)
		);
		
		//Final Layout
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(
				layout.createParallelGroup()
				.addComponent(menuName)	
				.addComponent(grid)
				.addComponent(description)
		);
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addComponent(menuName)
				.addComponent(grid)
				.addComponent(description)
		);
		setVisible(true);
		updateModel();
	}
	
	protected void addOrderButtonActionPerformed(ActionEvent evt, Order order, RestoAppPage app) {
		// TODO Auto-generated method stub
		//To implement
		try {
			List<Table> tableList = order.getTables();
			List<Seat> seats = order.getSeats();
//			for (Table table : tableList) {
//				for(Seat seat : table.getSeats()) {
//					seats.add(seat);
//				}
//			}
			List<Seat> selectedSeats = new ArrayList<Seat>();
			String[] seatsNumbers = seatList.getText().split(",");
			ArrayList<Integer> seatNumbers = new ArrayList<Integer>();
			for (String n: seatsNumbers) {
				int number = -1;
				try {
					number = Integer.parseInt(n)-1;
				} catch (NumberFormatException ex) {
					throw new InvalidInputException();
				}
				seatNumbers.add(number);
				
			}
			
			for(int seatNumber : seatNumbers) {
				if (seatNumber < 0 || seatNumber > seats.size()) {
					JOptionPane.showMessageDialog(null, "One or more entered seats doesn't exist", null, JOptionPane.ERROR_MESSAGE);
			        return;
				}
				selectedSeats.add(seats.get(seatNumber));
			}
			
			if( selectedSeats.size() < seatNumbers.size()) {
				JOptionPane.showMessageDialog(null, "One or more entered seats doesn't exist", null, JOptionPane.ERROR_MESSAGE);
		        return;
			}
			
			
			RestoAppController.orderMenuItem(Integer.parseInt(quantityField.getText()), selectedMenuItem, order, selectedSeats);
			for (Seat seat : selectedSeats) {
				System.out.println(RestoAppController.isAvailable(seat));
			}
			app.refreshData();
			updateModel();
			
		}catch(Exception ex){
			JOptionPane.showMessageDialog(null, "Invalid Inputs", null, JOptionPane.ERROR_MESSAGE);
		}
		
		
	}

	public void updateMenu(List<MenuItem> menu){
		grid.removeAll();

		for(MenuItem item : menu){
			MyButton button = new MyButton(item.getName());
			button.setBorder(new RoundedBorder(10));
			button.putClientProperty("price",item.getCurrentPricedMenuItem().getPrice());
			button.putClientProperty("item", item);
			grid.add(button);
			button.addActionListener(new java.awt.event.ActionListener() {
				@SuppressWarnings("deprecation")
				@Override
				public void actionPerformed(ActionEvent evt) {
					itemName.setText("Selected item: " + button.getLabel() + " ");
					price.setText(button.getClientProperty("price")+"$");
					itemName.setVisible(true);
					price.setVisible(true);
					gap.setVisible(true);
					selectOrderLabel.setVisible(true);
					orderList.setVisible(true);
					selectSeatLabel.setVisible(true);
					seatList.setVisible(true);
					quantityLabel.setVisible(true);
					quantityField.setVisible(true);
					addOrder.setVisible(true);
					selectedMenuItem = (MenuItem) button.getClientProperty("item");
				}
			});
		}
		updateModel();
	}
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // paint the background image and scale it to fill the entire space
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
    }
	
	public JLabel getMenuLabel(){
		return menuName;
	}
	public JPanel getGrid(){
		return grid;
	}
	public void updateModel() {
		quantityField.setText("");
		seatList.setText("");
		orderModel.removeAllElements();
		for (Order order : RestoAppController.getCurrentOrders()) {
			orderModel.addElement(order);
		}
		selectedOrder = -1;
		orderList.setSelectedIndex(selectedOrder);
	}
	
}

