package ca.mcgill.ecse223.resto.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.controller.RestoAppController;
import ca.mcgill.ecse223.resto.model.MenuItem;
import ca.mcgill.ecse223.resto.model.MenuItem.ItemCategory;


public class UpdateMenuItemPage {
	
	private DefaultComboBoxModel model = new DefaultComboBoxModel<MenuItem>();
	private DefaultComboBoxModel model1 = new DefaultComboBoxModel<String>();

	private int selectedItem = -1;
	private String selectedCategory;
	private int categoryIsSelected =-1;
	private HashMap<String, Double> menuItems;
	private HashMap<String, String> itemCats;

	private JLabel errorMessage;
	private String error = null;
	
	
	private Boolean show = false;
	
	private JComboBox menuItemList;
	private JComboBox categoryList;

	private JLabel selectItem;
	private JLabel itemName;
	private JLabel itemPrice;
	private JLabel itemCategory;
	private ItemCategory cat;
	
	private JTextField name  = new JTextField();
	private JTextField price = new JTextField();

	private MyButton btnRemove;
	private MyButton btnModify;
	private MyButton btnAddItem;
	private MyButton btnClose; 
	
	public UpdateMenuItemPage(RestoAppPage app){
		JFrame frame = new JFrame("Update Menu Item");
		frame.setResizable(false);
		frame.setAlwaysOnTop(true);

		errorMessage = new JLabel();
		
		//START COMPONENTS
		menuItems = new HashMap<String,Double>();
		itemCats = new HashMap<String,String>();
		
		selectItem = new JLabel("Select Menu Item");
		itemName = new JLabel("Name");
		itemPrice = new JLabel("Price");
		itemCategory = new JLabel("Category");
		
		model1.addElement("Appetizer");
		model1.addElement("Main");
		model1.addElement("Dessert");
		model1.addElement("AlcoholicBeverage");
		model1.addElement("NonAlcoholicBeverage");
		
		name  = new JTextField();
		price = new JTextField();
		
		categoryList = new JComboBox();
		categoryList = new JComboBox<String>();
		categoryList.setModel(model1);
		categoryList.setEditable(true);
		categoryList.setSelectedIndex(-1);
		categoryList.getEditor().getEditorComponent().setBackground(Color.WHITE);
		categoryList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
		        JComboBox<String> cb = (JComboBox<String>) evt.getSource();
		        categoryIsSelected = cb.getSelectedIndex();
		        selectedCategory = (String) cb.getSelectedItem();
		        if(selectedCategory.equals("Dessert")){
		        	cat = ItemCategory.Dessert;
		        }
		        if(selectedCategory.equals("AlcoholicBeverage")){
		        	cat = ItemCategory.AlcoholicBeverage;
		        }
		        if(selectedCategory.equals("Appetizer")){
		        	cat = ItemCategory.Appetizer;
		        }
		        if(selectedCategory.equals("Main")){
		        	cat = ItemCategory.Main;
		        }
		        if(selectedCategory.equals("NonAlcoholicBeverage")){
		        	cat = ItemCategory.NonAlcoholicBeverage;
		        }
			}
		});
		
		cat = null;
		menuItemList = new JComboBox<MenuItem>();
		menuItemList.setModel(model);
		menuItemList.setEditable(true);
		menuItemList.getEditor().getEditorComponent().setBackground(Color.WHITE);
		
//		menuItemList.addActionListener(new java.awt.event.ActionListener() {
//			public void actionPerformed(java.awt.event.ActionEvent evt) {
//		        JComboBox<String> cb = (JComboBox<String>) evt.getSource();
//		        selectedItem = cb.getSelectedIndex();
//		        
//		   }
//		});
//		
		menuItemList.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){
					if(show){
						MenuItem item = (MenuItem)menuItemList.getSelectedItem();
						name.setText(item.getName());
						if(!menuItems.isEmpty()){
							price.setText(Double.toString(menuItems.get(item.getName())));
							categoryList.setSelectedItem(itemCats.get(item.getName()));
						}
					
					}
					show = true;
					JComboBox<String> cb = (JComboBox<String>) e.getSource();
					selectedItem = cb.getSelectedIndex();
				}

			}
		});
		btnRemove = new MyButton("Remove Item");
		btnModify = new MyButton("Modify Item");
		btnAddItem = new MyButton("Add Item");
		btnClose = new MyButton("Close");
		//END COMPONENTS

		btnClose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				frame.dispose();			
			}
		});

		btnRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				error = null;
				try {
					MenuItem menuItem = (MenuItem)menuItemList.getSelectedItem();
					RestoAppController.removeItem(menuItem.getName());
					frame.dispose();
					JOptionPane.showMessageDialog(null, "Item Removed Successfully", null, JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e) {
					error = e.getMessage();
					errorMessage.setText(error);
				}
				refreshData();
			}
		});

		btnAddItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				error = null;
				try {
					Double priceItem = Double.parseDouble(price.getText());
					RestoAppController.addNewItem(name.getText(), priceItem,cat);
					frame.dispose();
					JOptionPane.showMessageDialog(null, name.getText()+" Added Successfully", null, JOptionPane.INFORMATION_MESSAGE);
				}

				catch (InvalidInputException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), null, JOptionPane.ERROR_MESSAGE);
				}  
				catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Unknown exception: " + ex.getMessage(), null, JOptionPane.ERROR_MESSAGE);
					error = ex.getMessage();
					errorMessage.setText(error);
				}

				refreshData();
			}
		});

		btnModify.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				error = null;
				try {
					MenuItem menuItem = (MenuItem)menuItemList.getSelectedItem();
					Double priceItem = Double.parseDouble(price.getText());
					RestoAppController.updateMenuItem(menuItem.getName(), name.getText(), priceItem, cat);
					frame.dispose();
					JOptionPane.showMessageDialog(null, "Item Modified Successfully", null, JOptionPane.INFORMATION_MESSAGE);

				} 
				catch (InvalidInputException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), null, JOptionPane.ERROR_MESSAGE);
				}  
				catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Unknown exception: " + ex.getMessage(), null, JOptionPane.ERROR_MESSAGE);
					error = ex.getMessage();
					errorMessage.setText(error);
				}
				
				refreshData();
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
								.addComponent(selectItem))
						.addGroup(layout.createParallelGroup()
								.addComponent(menuItemList)
								.addComponent(btnRemove))
						)
				.addGroup(
						layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
								.addComponent(itemName)
								.addComponent(itemPrice)
								.addComponent(itemCategory))
						.addGroup(layout.createParallelGroup()
								.addComponent(name)
								.addComponent(price)
								.addComponent(categoryList))
						)
				.addGroup(
						layout.createSequentialGroup()
						.addComponent(btnModify)
						.addGroup(
								layout.createParallelGroup()
								.addComponent(btnAddItem)
								.addComponent(btnClose))
						)		
				
				);
		
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {btnClose,btnRemove,menuItemList, btnAddItem, btnModify});

		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(selectItem)
						.addComponent(menuItemList))
				.addGroup(layout.createParallelGroup()
						.addComponent(btnRemove))
				.addGroup(layout.createParallelGroup()
						.addComponent(itemName)
						.addComponent(name))
				.addGroup(layout.createParallelGroup()
						.addComponent(itemPrice)
						.addComponent(price))
				.addGroup(layout.createParallelGroup()
						.addComponent(itemCategory)
						.addComponent(categoryList))
				.addGroup(layout.createParallelGroup()
						.addComponent(btnModify)
						.addGroup(
								layout.createSequentialGroup()
								.addComponent(btnAddItem)
								.addComponent(btnClose)
						))
				);
		refreshData();

		// Set the x, y, width and height properties
		frame.pack();
		frame.setVisible(true);
	}
	public void refreshData() {
		// error
				errorMessage.setText(error);
				
				if (error == null || error.length() == 0) {
					
					model.removeAllElements();
					menuItems.clear();
					for (MenuItem item : RestoAppController.getPricedMenu()) {
						model.addElement(item);
						menuItems.put(item.getName(), item.getCurrentPricedMenuItem().getPrice());
						itemCats.put(item.getName(), item.getItemCategory().toString());
					};
					
					selectedItem = -1;
					menuItemList.setSelectedIndex(selectedItem);
					categoryIsSelected=-1;
				}
				
	}
	
	
}
