package ca.mcgill.ecse223.resto.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import ca.mcgill.ecse223.resto.application.RestoAppApplication;
import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.controller.RestoAppController;
import ca.mcgill.ecse223.resto.model.MenuItem;
import ca.mcgill.ecse223.resto.model.MenuItem.ItemCategory;
import ca.mcgill.ecse223.resto.model.Order;
import ca.mcgill.ecse223.resto.model.Table;
import ca.mcgill.ecse223.resto.model.TakeOut;

public class RestoAppPage extends JFrame{
	
	private static final long serialVersionUID = -3496706717743749508L;
	private DefaultComboBoxModel<Table> model = new DefaultComboBoxModel<Table>();
	private DefaultComboBoxModel<Table> model2 = new DefaultComboBoxModel<Table>();
	private DefaultComboBoxModel<Order> model3 = new DefaultComboBoxModel<Order>();
	
	private JLabel errorMessage;
	private String error = null;
	private JPanel leftMenu;
	private JScrollPane leftScrollMenu;
	
	// Scroll bar
	JScrollPane scrollPane1 = new JScrollPane();
	// End scroll bar
	
	//ADD TABLE
	private MyButton createTable;
	//END ADD TABLE
	
	//ADD EXISTING TABLE
	private JLabel existingTableLabel;
	private JComboBox<Table> existingTableList;
	private MyButton addExistingTable;
	private Integer selectedExistingTable = -1;
	
	//END EXISTING TABLE
	
	//DELETE TABLE
	private JLabel selectTableLabel;
	private JComboBox<Table> currentTableList;
	private MyButton deleteTable;
	private Integer selectedTable = -1;
	//END DELETE TABLE
	private MyButton billTable;
	//UPDATE TABLE
	private MyButton updateTable;
	//END UPDATE TABLE
	
	//MOVE TABLE
	private MyButton moveTable;
	//END MOVE TABLE
	
	//DISPLAY MENU
	String list[] = {"Appetizer", "Main", "Dessert", "AlcoholicBeverage", "NonAlcoholicBeverage"};
	private JLabel selectMenuLabel;
	private JComboBox<String>itemCategoryList;
	private MyButton displayMenu;
	private Integer selectedMenu=-1;
	//END DISPLAY MENU
	
	//AddReservation
	private MyButton createReservation;
	private MyButton viewReservation;
	private MyButton deleteReservation;
	
	private JLabel reservationLabel = new JLabel();
	
	//END ADD RESERVATION
	
	//VIEW ORDER
	private MyButton viewOrder;
	private MyButton viewOrder2;
	
	private JLabel viewOrderLabel = new JLabel();
	//END VIEW ORDER
	
	//IssueBill
	private JLabel issueBillLabel = new JLabel();
	private MyButton issueBill;
	//END ISSUEBILL
	
	//START ORDER
	private JLabel otherFeatures = new JLabel();
	private JLabel orderTables = new JLabel();
	private MyButton startOrder;
	private MyButton startTakeOutOrder;
    private JLabel tablesDesc = new JLabel();
    private JTextField textTables = new JTextField();
	//END START ORDER
    
    //END ORDER
    private JLabel orderLabel;
	private JComboBox<Order> currentOrderList;
	private MyButton endOrder;
	private Integer selectedOrder = -1;
	private HashMap<Integer, Order> orders;
	
	//START CANCEL ORDER
	private MyButton cancelTable;
	private MyButton cancelOrderItem;
	//END CANCEL ORDER
	

	//UPDATE MENU ITEM
	private JLabel updateLabel;
	private MyButton updateMenuItem;
	//END UPDATE MENU ITEM

	//JPANELS
	private DisplayMenuPage menu;
	private JScrollPane scrollDisplayMenuPage;
	private TableVisualizer restoMap = new TableVisualizer();
	
	public RestoAppPage() {
		initComponents();
		refreshData();
	}
	
	private void initComponents() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//ERROR MESSAGE
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		//END ERROR MESSAGE
		
		//ADD TABLE			
		createTable = new MyButton();
		createTable.setText("Create Table");
		createTable.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				error = null;
				try {
					createTableButtonActionPerformed(e);
				} catch (NullPointerException ex) {
					errorMessage.setText("Error");
				}
				
			}
		});
		//END ADD TABLE
		
		//ADD EXISTING TABLE
		existingTableLabel = new JLabel();
		existingTableList = new JComboBox<Table>();
		existingTableList.setModel(model2);
		existingTableList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
		        JComboBox<String> cb = (JComboBox<String>) evt.getSource();
		        selectedExistingTable = cb.getSelectedIndex();
			}
		});
		
		existingTableList.setEditable(true);
        existingTableList.getEditor().getEditorComponent().setBackground(Color.WHITE);
		
		addExistingTable = new MyButton();
		existingTableLabel.setText("Add Table");
		addExistingTable.setText("Add Table");
		addExistingTable.addActionListener(new java.awt.event.ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				error = null;
				try {
					Table t = (Table)existingTableList.getSelectedItem();
					if (t == null) {
						throw new NullPointerException();
					}
					addExistingTableButtonActionPerformed(evt, (Table)existingTableList.getSelectedItem());
				}
				catch (NullPointerException ex) {
					error = "Please select a valid table";
					errorMessage.setText(error);
				}
				
			}
		});
		
		//END EXISTING TABLE

		//DELETE TABLE
		selectTableLabel = new JLabel();
		currentTableList = new JComboBox<Table>();
		currentTableList.setModel(model);
		currentTableList.setEditable(true);
        currentTableList.getEditor().getEditorComponent().setBackground(Color.WHITE);
		currentTableList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
		        JComboBox<String> cb = (JComboBox<String>) evt.getSource();
		        selectedTable = cb.getSelectedIndex();
			}
		});
		deleteTable = new MyButton();
		selectTableLabel.setText("Modify Current Table");
		deleteTable.setText("Delete Table");
		deleteTable.addActionListener(new java.awt.event.ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				error = null;
				try {
					deleteTableButtonActionPerformed(evt, (Table)currentTableList.getSelectedItem());
				}
				catch (NullPointerException ex) {
					error = "Please select a valid table";
					errorMessage.setText(error);
				}
				
			}
		});
		
		
		//END DELETE TABLE
		billTable = new MyButton();
		billTable.setText("Bill Table");
		billTable.addActionListener(new java.awt.event.ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				error = null;
				try {
					BillTableButtonActionPerformed(evt, (Table)currentTableList.getSelectedItem());
				}
				catch (NullPointerException ex) {
					error = "Please select a valid table";
					errorMessage.setText(error);
				}
				
			}
		});
		
		//UPDATE TABLE
		updateTable = new MyButton();
		updateTable.setText("Update Table");
		updateTable.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				error = null;
				try {
					Table t = (Table)currentTableList.getSelectedItem();
					if (t == null) {
						throw new NullPointerException();
					}
					updateTableButtonActionPerformed(event, t);
				} catch (NullPointerException ex) {
					errorMessage.setText("Please select a valid table");
				}
				
			}
		});
		//END UPDATE TABLE
		
		//MOVE TABLE 
		moveTable = new MyButton();
		moveTable.setText("Move Table");
		moveTable.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				error = null;
				try {
					Table t = (Table)currentTableList.getSelectedItem();
					if (t == null) {
						throw new NullPointerException();
					}
					moveTableButtonActionPerformed(e, t);
				} catch (NullPointerException ex) {
					errorMessage.setText("Please select a valid table");
				}
				
			}
		});
		//END MOVE TABLE
		
		//VIEW ORDER
		viewOrderLabel.setText("");
		viewOrder = new MyButton();
		viewOrder.setText("View Order");
		viewOrder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				error = null;
				try {
					Table t = (Table)currentTableList.getSelectedItem();
					if (t == null) {
						error = "Please select a valid table";
						errorMessage.setText(error);
						throw new NullPointerException();
					}
					if(t.getStatusFullName() != "Ordered") {
						error = "Table didn't order items";
						errorMessage.setText(error);
						throw new NullPointerException();
					}
					viewOrderActionPerformed(e, t);
				}catch (NullPointerException ex) {
				}
			}
		});
		//END VIEW ORDER
		viewOrder2 = new MyButton();
		viewOrder2.setText("View Order");
		viewOrder2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				error = null;
				try {
					Order o = (Order)currentOrderList.getSelectedItem();
					if (o == null) {
						error = "Invalid Order";
						errorMessage.setText(error);
						throw new NullPointerException();
					}
					viewOrderActionPerformed2(e, o);
				}catch (NullPointerException ex) {
				}
			}
		});
				
		//DISPLAY MENU
		selectMenuLabel = new JLabel();
		itemCategoryList = new JComboBox<String>(new String[0]);
		for(String str : list){
			itemCategoryList.addItem(str);
		}
		itemCategoryList.setEditable(true);
        itemCategoryList.getEditor().getEditorComponent().setBackground(Color.WHITE);
		itemCategoryList.setSelectedIndex(-1);
		itemCategoryList.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
		       // JComboBox<String> cb = (JComboBox<String>) evt.getSource();
		        //selectedMenu =cb.getSelectedIndex();
				selectedMenu = itemCategoryList.getSelectedIndex();
			}
		});
		selectMenuLabel.setText("Display Menu");
		//selectedMenu = null;
		displayMenu = new MyButton("Display Menu");
		selectedMenu = null;
		displayMenu.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				error =null;
				try{
					displayMenuButtonActionPerformed(evt);
				}
				catch(NullPointerException ex){
					errorMessage.setText("Please select a valid menu");
				}
			}
		});
		//END DISPLAY MENU
		
		//START ORDER
		otherFeatures = new JLabel();
		otherFeatures.setText("");
		orderTables = new JLabel();
		orderTables.setText("Start Order");
		tablesDesc = new JLabel();
		tablesDesc.setText("(enter table # seperated by ',')");
		startOrder = new MyButton();
		startOrder.setText("Start Order");
		startOrder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				error = null;
				try {
					startOrderButtonActionPerformed(e);
				} catch (NullPointerException ex) {
					errorMessage.setText("Error");
				}
			}
		});
		
		startTakeOutOrder = new MyButton();
		startTakeOutOrder.setText("Start Take Out");
		startTakeOutOrder.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				error = null;
				try {
					startTakeOutOrderButtonActionPerformed(e);
				} catch (NullPointerException ex) {
					errorMessage.setText("Error");
				}
				
			}


		});
		//END START ORDER
		
		//END ORDER
		orderLabel = new JLabel();
		currentOrderList = new JComboBox<Order>();
		currentOrderList.setModel(model3);
		currentOrderList.setEditable(true);
        currentOrderList.getEditor().getEditorComponent().setBackground(Color.WHITE);
		currentOrderList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
		        JComboBox<String> cb = (JComboBox<String>) evt.getSource();
		        selectedOrder = cb.getSelectedIndex();
			}
		});
		endOrder = new MyButton();
		orderLabel.setText("Modify Order");
		endOrder.setText("End Order");
		endOrder.addActionListener(new java.awt.event.ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				error = null;
				try {
					endOrderButtonPerformed(evt, (Order)currentOrderList.getSelectedItem());
				}
				catch (NullPointerException ex) {
					error = "Please select a valid Order";
					errorMessage.setText(error);
				}
				
			}

		});
		
		//RESERVE TABLE
		reservationLabel.setText("Reservation");
		createReservation = new MyButton();
		createReservation.setText("Reserve Table");
		createReservation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				error = null;
				try {
					createReservationActionPerformed(e);
				} catch (NullPointerException ex) {
					errorMessage.setText("Error");
				}
				
			}
		});
		viewReservation = new MyButton();
		viewReservation.setText("View Reservation");
		viewReservation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				error = null;
				try {
					viewReservationActionPerformed(e);
				} catch (NullPointerException ex) {
					errorMessage.setText("Error");
				}
				
			}
		});
		
		
		deleteReservation = new MyButton();
		deleteReservation.setText("Delete Reservation");
		deleteReservation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				error = null;
				try {
					deleteReservationActionPerformed(e);
				} catch (NullPointerException ex) {
					errorMessage.setText("Error");
				}
				
			}
		});
		//RESERVE TABLE
		
		
		//ISSUEBILL
		issueBillLabel.setText("Issue Bill");
		issueBill = new MyButton();
		issueBill.setText("Issue Bill");
		
		issueBill.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				error = null;
				try {
					issueBillButtonActionPerformed(e);
				} catch (NullPointerException ex) {
					errorMessage.setText("Error");
				}
			}
		});
	
		//END ISSUEBILL
		
		//CANCEL ORDER
		cancelTable = new MyButton();
		cancelTable.setText("Cancel Order");
		cancelTable.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				error = null;
				try {
					CancelTableActionPerformed(e, (Order)currentOrderList.getSelectedItem());
				}catch(NullPointerException ex) {
					errorMessage.setText("Error");
				}
			}
		});

		cancelOrderItem = new MyButton();
		cancelOrderItem.setText("Cancel Order Item");
		cancelOrderItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				error = null;
				try {
					CancelOrderItemActionPerformed(e, (Order)currentOrderList.getSelectedItem());
				}catch(NullPointerException ex) {
					errorMessage.setText("Invalid Order");
				}
			}
		});	
		//END CANCEL ORDER
		
		//START UPDATE MENU ITEM 
		updateLabel = new JLabel("Update Menu Item");
		updateMenuItem = new MyButton();
		updateMenuItem.setText("Update Menu Item");
		updateMenuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				error = null;
				try {
					updateMenuItemActionPerformed(e);
				} catch (NullPointerException ex) {
					errorMessage.setText("Error");
				}
			}	
		});
		//END UPDATE MENU ITEM 


		// MENU
		menu = new DisplayMenuPage(this);
		scrollDisplayMenuPage = new  JScrollPane(menu);
		
		
		//LEFT MENU LAYOUT(JPANEL)
		this.getContentPane().setBackground( Color.WHITE );
		leftMenu = new JPanel();
		
		leftMenu.setBackground( Color.WHITE );
		GroupLayout layout = new GroupLayout(leftMenu);
		leftMenu.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		// LEFT MENU IN SCROLLPANE
		leftScrollMenu = new JScrollPane(leftMenu);


		//HORIZONTAL
		layout.setHorizontalGroup(
				layout.createParallelGroup()
				//ERROR MESSAGE
				.addComponent(errorMessage)
				
				//END ERROR MESSAGE
				
				//ADD TABLE HORIZONTAL
				
						
				//END ADD TABLE
				.addGroup(layout.createSequentialGroup()
						.addComponent(existingTableLabel, 40, 40, 70)
						.addGroup(layout.createParallelGroup()
								.addComponent(existingTableList, 200, 200, 200)
								.addComponent(addExistingTable)
								.addComponent(createTable)))
				
				//ADD EXISTING TABLE
				.addGroup(layout.createSequentialGroup()
						.addComponent(otherFeatures))
				//END EXISTING TABLE

				//DELETE TABLE HORIZONTAL
				.addGroup(layout.createSequentialGroup()
						.addComponent(selectTableLabel)
						.addGroup(layout.createParallelGroup()
								.addComponent(currentTableList)
								.addComponent(updateTable)
								.addComponent(deleteTable)
								.addComponent(moveTable)
								.addComponent(viewOrder)
								.addComponent(billTable)
								))
								
				//END DELETE TABLE 
				
				//VIEW ORDER
//				.addGroup(layout.createSequentialGroup()
//						.addComponent(viewOrderLabel)
//						.addGroup(layout.createParallelGroup()
//								))
//						)
				//END VIEW ORDER
				.addGroup(layout.createSequentialGroup()
						.addComponent(otherFeatures))
				
				//DISPLAY MENU HORIZONTAL
				.addGroup(layout.createSequentialGroup()
						.addComponent(selectMenuLabel)
						.addGroup(layout.createParallelGroup()
							.addComponent(itemCategoryList)
							.addComponent(displayMenu)))
				
				//OTHER FEATURES
				.addGroup(layout.createSequentialGroup()
						.addComponent(otherFeatures))
		
				.addGroup(layout.createSequentialGroup()
						.addComponent(orderTables)
						.addGroup(layout.createParallelGroup()
								.addComponent(textTables)
								.addComponent(tablesDesc)
								.addComponent(startOrder)
								.addComponent(startTakeOutOrder)))
				
				.addGroup(layout.createParallelGroup()
						.addComponent(otherFeatures))
				
				.addGroup(layout.createSequentialGroup()
						.addComponent(orderLabel)
						.addGroup(layout.createParallelGroup()
								.addComponent(currentOrderList, 200, 200, 400)
								.addComponent(cancelTable)
								.addComponent(cancelOrderItem)
								.addComponent(endOrder)
								.addComponent(deleteReservation)
								.addComponent(currentOrderList)
								.addComponent(endOrder)
								.addComponent(viewOrder2)))
				
				.addGroup(layout.createParallelGroup()
						.addComponent(otherFeatures))
				
				.addGroup(layout.createSequentialGroup()
						.addComponent(issueBillLabel,40,40,70)
						.addGroup(layout.createParallelGroup()
								.addComponent(issueBill, 200, 200, 200)
								))
				
				.addGroup(layout.createParallelGroup()
						.addComponent(otherFeatures))
				
				
				.addGroup(layout.createSequentialGroup()
						.addComponent(reservationLabel)
						.addGroup(layout.createParallelGroup()
								.addComponent(createReservation)
								.addComponent(viewReservation)
								.addComponent(deleteReservation)))
				
				.addGroup(layout.createParallelGroup()
						.addComponent(otherFeatures))
				
				.addGroup(layout.createSequentialGroup()
						.addComponent(updateLabel)
						.addComponent(updateMenuItem))	
				
				//.addComponent(tableVisualizer)
				//END DISPLAY MENU
				);
		layout.linkSize(SwingConstants.VERTICAL, new java.awt.Component[] {orderLabel, selectMenuLabel, existingTableLabel, selectTableLabel, otherFeatures, orderTables, reservationLabel, issueBillLabel, updateLabel});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {orderLabel, selectMenuLabel, existingTableLabel, selectTableLabel, otherFeatures, orderTables, reservationLabel, issueBillLabel, updateLabel});
		layout.linkSize(SwingConstants.VERTICAL, new java.awt.Component[] {currentTableList, deleteTable, currentOrderList, createTable, existingTableList, addExistingTable, billTable});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {currentTableList, deleteTable, currentOrderList, createTable, existingTableList, addExistingTable, billTable});
		layout.linkSize(SwingConstants.VERTICAL, new java.awt.Component[] {textTables, startOrder, startTakeOutOrder, endOrder, viewOrder, createTable, viewReservation, viewOrder2});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {textTables, startOrder, startTakeOutOrder, endOrder, viewOrder, createTable, viewReservation, viewOrder2});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {currentTableList, updateTable});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {currentTableList, moveTable});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {currentTableList, deleteReservation, cancelTable, cancelOrderItem});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {currentTableList, createReservation, deleteReservation});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {itemCategoryList, displayMenu, currentTableList, createReservation, deleteReservation, issueBill, updateMenuItem});
		layout.linkSize(SwingConstants.VERTICAL, new java.awt.Component[] {itemCategoryList, displayMenu});
		//layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {itemCategoryList, displayMenu});
		
		//VERTICAL
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				//ERROR MESSAGE
				.addComponent(errorMessage)
				//END ERROR MESSAGE
				
				//ADD TABLE VERTICAL
				

				//END ADD TABLE VERTICAL
				
				//ADD EXISTING TABLE
				.addGroup(layout.createParallelGroup()
						.addComponent(existingTableLabel)
						.addComponent(existingTableList))
				.addGroup(layout.createParallelGroup()
						.addComponent(addExistingTable))
				.addGroup(layout.createParallelGroup()
						.addComponent(createTable))
				
				//END EXISTING TABLE
				.addGroup(layout.createParallelGroup()
						.addComponent(otherFeatures))

				//DELETE TABLE VERTICAL
				.addGroup(layout.createParallelGroup()
						.addComponent(selectTableLabel)
						.addComponent(currentTableList))
				.addGroup(layout.createParallelGroup()
						.addComponent(deleteTable))
				
				//END DELETE TABLE
				//UPDATE TABLE
				.addGroup(layout.createParallelGroup()
						.addComponent(updateTable))
				//END UPDATE TABLE
				.addGroup(layout.createParallelGroup()
						.addComponent(moveTable))
				
				//DISPLAY MENU VERTICAL
				
				//VIEW ORDER
				.addGroup(layout.createParallelGroup()
						.addComponent(viewOrder))
				
				.addGroup(layout.createParallelGroup()
						.addComponent(billTable))
				//END VIEW ORDER
				.addGroup(layout.createParallelGroup()
						.addComponent(otherFeatures))
				
				.addGroup(layout.createParallelGroup()
						.addComponent(selectMenuLabel)
						.addComponent(itemCategoryList))

				.addGroup(layout.createParallelGroup()
						.addComponent(displayMenu))
				
				//END DISPLAY MENU
				//other features
				.addGroup(layout.createParallelGroup()
						.addComponent(otherFeatures))
				.addGroup(layout.createParallelGroup()
						.addComponent(orderTables)
						.addComponent(textTables))
				.addGroup(layout.createParallelGroup()
						.addComponent(tablesDesc))
				.addGroup(layout.createParallelGroup()
						.addComponent(startOrder))
				.addGroup(layout.createParallelGroup()
						.addComponent(startTakeOutOrder))
				
				.addGroup(layout.createParallelGroup()
						.addComponent(otherFeatures))
				
				
				.addGroup(layout.createParallelGroup()
						.addComponent(orderLabel)
						.addComponent(currentOrderList))
				.addGroup(layout.createParallelGroup()
						.addComponent(cancelTable))
				.addGroup(layout.createParallelGroup()
						.addComponent(cancelOrderItem))
				.addGroup(layout.createParallelGroup()
						.addComponent(endOrder))
				.addGroup(layout.createParallelGroup()
						.addComponent(viewOrder2))
				
				.addGroup(layout.createParallelGroup()
						.addComponent(otherFeatures))
				
				.addGroup(layout.createParallelGroup()
						.addComponent(issueBillLabel)
						.addComponent(issueBill))
				
				.addGroup(layout.createParallelGroup()
						.addComponent(otherFeatures))
				
				
				
				.addGroup(layout.createParallelGroup()
						.addComponent(reservationLabel)
						.addComponent(createReservation))
				.addGroup(layout.createParallelGroup()
						.addComponent(viewReservation))
				.addGroup(layout.createParallelGroup()
						.addComponent(deleteReservation))
				
				.addGroup(layout.createParallelGroup()
						.addComponent(otherFeatures))
				

				.addGroup(layout.createParallelGroup()
						.addComponent(updateLabel)
						.addComponent(updateMenuItem))
				
				//.addComponent(tableVisualizer)
				);
		
		//END LEFT MENU LAYOUT(JPANEL)

		
		//FINAL LAYOUT
		//this.setSize(1000,1000);
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		//setUndecorated(true); 
		setVisible(true);
		
		GroupLayout finalWindow = new GroupLayout(getContentPane());
		getContentPane().setLayout(finalWindow);
		finalWindow.setAutoCreateGaps(true);
		finalWindow.setAutoCreateContainerGaps(true);
		
		finalWindow.setHorizontalGroup(
				finalWindow.createSequentialGroup()
				.addComponent(leftScrollMenu, 200, 400, 400)
				.addGroup(finalWindow.createParallelGroup()
				.addComponent(restoMap)
				.addComponent(scrollDisplayMenuPage))
				
		);
		finalWindow.setVerticalGroup(
				finalWindow.createParallelGroup()
				.addComponent(leftScrollMenu)
				.addGroup(finalWindow.createSequentialGroup()
					.addComponent(restoMap)
					.addComponent(scrollDisplayMenuPage))
				
		);
		pack();
	}
	
	//ADD TABLE	
	protected void createTableButtonActionPerformed(ActionEvent e) {
		error = null;
		errorMessage.setText(error);
		new CreateTableFrame(this);
		refreshData();
	}
	
	protected void moveTableButtonActionPerformed(ActionEvent e, Table t) {
		error = null;
		errorMessage.setText(error);
		new MoveTableFrame(this, t);
		refreshData();
	}
	
	protected void updateTableButtonActionPerformed(ActionEvent e, Table t) {
		error = null;
		errorMessage.setText(error);
		new UpdateTableFrame(this, t);
		refreshData();
	}
	
	protected void createReservationActionPerformed(ActionEvent e){
		error = null;
		errorMessage.setText(error);
		new CreateReservationFrame(this);
		refreshData();
	}
	
	protected void issueBillButtonActionPerformed(ActionEvent e) {
		error = null;
		errorMessage.setText(error);
		new CreateBillFrame(this);
		refreshData();
		
	}
	protected void BillTableButtonActionPerformed(ActionEvent e, Table table) {
		if(table.getStatus() != Table.Status.Ordered) {
			error = "Nothing ordered";
			errorMessage.setText(error);
		}
		else {
			error = null;
			errorMessage.setText(error);
			new BillTableFrame(table);
			refreshData();
		}

		
	}
	
	protected void viewReservationActionPerformed(ActionEvent e) {
		error = null;
		errorMessage.setText(error);
		new ViewReservationFrame(this);
		refreshData();
	}
	
	protected void deleteReservationActionPerformed(ActionEvent e) {
		error = null;
		errorMessage.setText(error);
		new DeleteReservationFrame(this);
		refreshData();
	}
	
	//VIEW ORDER
	protected void viewOrderActionPerformed(ActionEvent e, Table table) {
		error = null;
		errorMessage.setText(error);
		new ViewOrderFrame(this, table);
		refreshData();
	}
	//END VIEW ORDER
	
	protected void viewOrderActionPerformed2(ActionEvent e, Order order) {
		error = null;
		errorMessage.setText(error);
		new ViewOrderFrame2(this, order);
		refreshData();
	}
	
	protected void CancelTableActionPerformed(ActionEvent e, Order order) {
//		error = null;
//		try {
//			RestoAppController.CancelOrder(order);
//		} catch (Exception ex) {
//			error = ex.getMessage();
//		}
//		refreshData();
		new CancelOrderFrame(this, order);
	}
	
	protected void CancelOrderItemActionPerformed(ActionEvent e, Order order) {
		error = null;
		errorMessage.setText(error);
		new CancelOrderItemFrame(this, order);
		refreshData();
	}
	

	protected void updateMenuItemActionPerformed(ActionEvent e){
		error = null;
		errorMessage.setText(error);
		new UpdateMenuItemPage(this);
		refreshData();
	}

	protected void addExistingTableButtonActionPerformed(ActionEvent evt, Table table) {
		error = null;
		try {
			RestoAppController.addCurrentTable(table);
		} catch (Exception e) {
			error = e.getMessage();
			errorMessage.setText(error);
		}
		
		refreshData();
	}
	
	protected void startOrderButtonActionPerformed(ActionEvent e) {
		try {
			List<Table> tableList = RestoAppController.getCurrentTables();
			List<Table> selectedTables = new ArrayList<Table>();
			String[] tables = textTables.getText().split(",");
			ArrayList<Integer> tableNumbers = new ArrayList<Integer>();
			for (String n: tables) {
				int number = -1;
				try {
					number = Integer.parseInt(n);
				} catch (NumberFormatException ex) {
					error = "Invalid table number";
					errorMessage.setText(error);
			        return;
				}
				tableNumbers.add(number);
				
			}
			
			for (Table t: tableList) {
				if (tableNumbers.contains(t.getNumber())){
					if (t.getStatusFullName() != "Available") {
						error = "Table(s) already has an oder";
						errorMessage.setText(error);
				        return;
					}
					selectedTables.add(t);
				}
			}
			
			if( selectedTables.size() < tableNumbers.size()) {
				error = "One more more selected table doesn't exist";
				errorMessage.setText(error);
		        return;
			}
			RestoAppController.startOrder(selectedTables);
			
		}catch(Exception ex){
			error =  ex.getMessage();
			errorMessage.setText(error);
		}
		
		refreshData();
	}

	private void startTakeOutOrderButtonActionPerformed(ActionEvent e) {
		try {
			TakeOut takeOut = RestoAppApplication.getRestoApp().getCurrentTakeOut();
			if (takeOut.getStatusFullName() != "Available") {
				error = "Take out already has an oder";
				errorMessage.setText(error);
		        return;
			}
			
			RestoAppController.startOrder(RestoAppApplication.getRestoApp().getCurrentTakeOut());
			
		}catch(Exception ex){
			error =  ex.getMessage();
			errorMessage.setText(error);
		}
		
		refreshData();
		
	}
	
	protected void deleteTableButtonActionPerformed(ActionEvent evt, Table table) {
		// DELETE TABLE BUTTON
		// clear error message
		error = null;
		errorMessage.setText(error);
		
		// call the controller
		try {
			RestoAppController.deleteTable(table);
		} catch (InvalidInputException e) {
			error = e.getMessage();
			errorMessage.setText(error);
		}
		
		// update visuals
		refreshData();
	}
	
	
	protected void endOrderButtonPerformed(ActionEvent evt, Order order) {
		error = null;
		errorMessage.setText(error);
		
		// call the controller
		try {
			RestoAppController.endOrder(order);
		} catch (Exception e) {
			error = e.getMessage();
			errorMessage.setText(error);
		}
		
		// update visuals
		refreshData();
		
	}
	
	
	
	protected void displayMenuButtonActionPerformed(ActionEvent evt) {
		// DISPLAY MENU BUTTON
		// clear error message
		error = null;
		errorMessage.setText(error);
		ItemCategory category;
		//call the controller
		try{
			switch(selectedMenu){
			case 0:category = ItemCategory.Appetizer;
				   menu.getMenuLabel().setText("Appetizer");
				   break;
			case 1:category = ItemCategory.Main;
			   	   menu.getMenuLabel().setText("Main");
			       break;
			case 2:category = ItemCategory.Dessert;
			       menu.getMenuLabel().setText("Dessert");
			       break;
		  	case 3:category = ItemCategory.AlcoholicBeverage;
		  	       menu.getMenuLabel().setText("Alcoholic Beverage");
		  	       break;
			case 4:category = ItemCategory.NonAlcoholicBeverage;
			       menu.getMenuLabel().setText("Non-Alcoholic Beverage");
			       break;
			default:category = null;
			}
			List<MenuItem> list = RestoAppController.displayMenu(category);
			menu.updateMenu(list);
			
			//Update JLabel view
			menu.getMenuLabel().revalidate();
			menu.getMenuLabel().repaint();
			//Update JPanel(grid) view
			menu.getGrid().revalidate();
			menu.getGrid().repaint();
			
		} catch(InvalidInputException e){
			error = e.getMessage();
		}
		
	}
	
	public void refreshData() {
		// error
				errorMessage.setText(error);
				
				if (error == null || error.length() == 0) {
					// populate page with data
				
//					tables = new HashMap<Integer, Table>();
//					currentTableList.removeAllItems();
//					Integer index = 0;
					menu.updateModel();
					textTables.setText(null);
					
					model.removeAllElements();
					for (Table table : RestoAppController.getCurrentTables()) {
						model.addElement(table);
					};
					model2.removeAllElements();
					for (Table table : RestoAppController.getTables()) {
						List<Table> currentDisplayed = RestoAppController.getCurrentTables();
						if (!currentDisplayed.contains(table)) {
							model2.addElement(table);
						}
					};
					model3.removeAllElements();
					for (Order order : RestoAppController.getCurrentOrders()) {
						model3.addElement(order);
						
						}
					};
					
					restoMap.setTables(RestoAppController.getCurrentTables());
					
					selectedExistingTable = -1;
					selectedTable = -1;
					selectedOrder = -1;
					currentTableList.setSelectedIndex(selectedTable);
					existingTableList.setSelectedIndex(selectedExistingTable);
					currentOrderList.setSelectedIndex(selectedOrder);
				}

	}

