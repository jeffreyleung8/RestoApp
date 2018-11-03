package ca.mcgill.ecse223.resto.controller;

import java.sql.Date;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import com.sun.javafx.scene.control.skin.SeparatorSkin;

import ca.mcgill.ecse223.resto.application.RestoAppApplication;

import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.model.Table;
import ca.mcgill.ecse223.resto.model.Table.Status;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Seat;
import ca.mcgill.ecse223.resto.model.Bill;
import ca.mcgill.ecse223.resto.model.Menu;
import ca.mcgill.ecse223.resto.model.MenuItem;
import ca.mcgill.ecse223.resto.model.MenuItem.ItemCategory;
import ca.mcgill.ecse223.resto.model.Order;
import ca.mcgill.ecse223.resto.model.OrderItem;
import ca.mcgill.ecse223.resto.model.PricedMenuItem;
import ca.mcgill.ecse223.resto.model.Reservation;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Seat;
import ca.mcgill.ecse223.resto.model.Table;
import ca.mcgill.ecse223.resto.model.TakeOut;



public class RestoAppController {
	
	public RestoAppController() {
	}
	
	public static List<Table> getCurrentTables() {
		return RestoAppApplication.getRestoApp().getCurrentTables();
	}
	
	public static List<Table> getTables() {
		return RestoAppApplication.getRestoApp().getTables();
	}
	
	public static List<Order> getCurrentOrders() {
		return RestoAppApplication.getRestoApp().getCurrentOrders();
	}
	
	public static void deleteTable(Table table) throws InvalidInputException{
		boolean hasReservation = table.hasReservations();
		RestoApp restoApp = RestoAppApplication.getRestoApp();
		String error = null;
		
		if (hasReservation) {
			error = "Table has reservations";
		}
		for (Order currentOrder : restoApp.getCurrentOrders()) {
			List<Table> tableList = currentOrder.getTables();
			if (tableList.contains(table)) {
				error = "Table in use";
				break;
			}
		}
		if (error != null) {
			throw new InvalidInputException(error);
		}
		
		restoApp.removeCurrentTable(table);
		
		try {
			RestoAppApplication.save();
		}
		catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}
	
	public static void updateTable(Table table, int newNumber, int numberofSeats) throws InvalidInputException{
	
		if(newNumber < 0 || numberofSeats < 0) {
			throw new InvalidInputException("Cannot has negative number of seats or table number");
		}
		
		if(numberofSeats > 8) {
			throw new InvalidInputException("There are too many seats for the table");
		}
		
		if(table == null) {
			throw new InvalidInputException("There is no table");
		}
		
		RestoApp restoApp = RestoAppApplication.getRestoApp();
		boolean reserved = table.hasReservations();
		
		if(reserved) {
			throw new InvalidInputException("Table is reserved");
		}
		
		for (Order currentOrder : restoApp.getCurrentOrders()) {
			List<Table> tableList = currentOrder.getTables();
			if (tableList.contains(table)) {
				throw new InvalidInputException("Table in use");
			}
		}
		
		if(table.getNumber() == (newNumber)) {
			table.setNumber(newNumber);
			
		}
		else if(!table.setNumber(newNumber)) {
			throw new InvalidInputException("Cannot set due to duplicate number");
		}
			table.setNumber(newNumber);	
		
		
		int n = table.numberOfCurrentSeats();
		if(n < numberofSeats) {
			for(int i = 0; i < numberofSeats - n; i++) {
				Seat seat = table.addSeat();
				table.addCurrentSeat(seat);
			}
		}
		else {
			for(int i = 0; i < n - numberofSeats; i++) {
				Seat seat = table.getCurrentSeat(0);
				table.removeCurrentSeat(seat);
				i++;
			}
		}
		
		try {
			RestoAppApplication.save();
		}
		catch (Exception e){
			System.out.println(e.getMessage());
			throw e;
		}
}
	
	public static void addCurrentTable(Table table) throws InvalidInputException{
		if (table == null) {
			throw new InvalidInputException("Invalid Table");
		}
		RestoApp restoApp = RestoAppApplication.getRestoApp(); 
		for(Table t : restoApp.getCurrentTables()) {
			if (t == table) {
				continue;
			}
			if (t.checkOverlap(table.getX(),table.getY(),table.getLength(), table.getWidth())) {
				throw new InvalidInputException("Table overlaps with another current Table");
			}
		}
		restoApp.addCurrentTable(table);
		try {
			RestoAppApplication.save();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
		
	}
	
	public static void addTable(int number, int x, int y, int width, int length, int numberOfSeat) throws InvalidInputException{
		if(x < 0 || y < 0 || width <= 0 || length <= 0 || numberOfSeat >= 8) {
			throw new InvalidInputException("Table specifications invalid");
		}
		
		RestoApp restoApp = RestoAppApplication.getRestoApp();
		List<Table> currentTables = restoApp.getCurrentTables();
		//Checks for overlap with existing tables and if declared table number is already in use
		for(Table t : currentTables) {
			if (t.checkOverlap(x,y,length, width)) {
				throw new InvalidInputException("Position overlaps with existing table");
			}
			if(t.getNumber() == number) {
				throw new InvalidInputException("Table number already exists");
			}
		}
		Table specificTable;
		
		try {
			specificTable = new Table(number, x, y, width, length, restoApp);
		} catch (Exception e) {
			throw new InvalidInputException("Table already exists");
		}
		
		try {
			restoApp.addTable(specificTable);
			restoApp.addCurrentTable(specificTable);
		} catch (Exception e) {
			throw new RuntimeException();
		}
		for(int i = 0; i < numberOfSeat; i++) {
			Seat seat = specificTable.addSeat();
			specificTable.addCurrentSeat(seat);
		}
		//save
		try {
			RestoAppApplication.save();
		} catch (Exception e) {

			System.out.println(e.getMessage());
			throw e;
		}
	}

	public static void moveTable(Table table, int x, int y) throws InvalidInputException {
		
		if (x <0 || y < 0) {
			throw new InvalidInputException("Invalid position");
		}
		
		for (Order currentOrder : getCurrentOrders()) {
			List<Table> tableList = currentOrder.getTables();
			if (tableList.contains(table)) {
				throw new InvalidInputException("Table in use");
			}
		}
		
		RestoApp restoApp = RestoAppApplication.getRestoApp();
		int width = table.getWidth();
		int length = table.getLength();
		List<Table> currentTables = restoApp.getCurrentTables();
		
		
		for(Table t : currentTables) {
			if (t == table) {
				continue;
			}
			if (t.checkOverlap(x,y,length, width)) {
				throw new InvalidInputException("Table overlap");
			}
		}
		table.setX(x);
		table.setY(y);
		try {
			RestoAppApplication.save();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
		
	}

	public static List<MenuItem> displayMenu(ItemCategory itemCategory) throws InvalidInputException{
		if(itemCategory == null){
			throw new InvalidInputException("Item Category is null");
		}
		List<MenuItem> listItems = new ArrayList<MenuItem>();
		RestoApp restoApp = RestoAppApplication.getRestoApp();
		Menu menu = restoApp.getMenu();
		for(MenuItem menuItem : menu.getMenuItems()){
			boolean current = menuItem.hasCurrentPricedMenuItem();
			ItemCategory category = menuItem.getItemCategory();
			if(current && category.equals(itemCategory)){
				listItems.add(menuItem);	
			}	
		}
		return listItems;
	}
	
	public static List<ItemCategory> getItemCategory(){
		ItemCategory [] itemcategories = ItemCategory.values();
		List <ItemCategory> itemCategories = new ArrayList <ItemCategory>();
		for (ItemCategory aItemcategory:itemcategories) {
			itemCategories.add(aItemcategory);
		}
		RestoAppApplication.save();
		return itemCategories;
	}

	public static void addReservation(Date date, Time time, int numberInParty, String contactName, String contactEmail, String contactPhone, List<Table> tables) throws InvalidInputException{
		if(tables == null){
			throw new InvalidInputException("Select at least one table");
		}
		if(date == null){
			throw new InvalidInputException("Select a date");
		}
		if(time == null) {
			throw new InvalidInputException("Select a time");
		}
		
		if(contactName.isEmpty() || contactEmail.isEmpty() || contactPhone.isEmpty()){
			throw new InvalidInputException("Invalid contact info");
		}

		RestoApp restoApp = RestoAppApplication.getRestoApp();
		List<Table> currentTables = restoApp.getCurrentTables();
		int seatCapacity = 0;
		for(Table table : tables){
			if(!currentTables.contains(table)){
				throw new InvalidInputException("No Tables exist");
			}
			seatCapacity += table.numberOfCurrentSeats();
			List<Reservation> reservations= table.getReservations();
			for(Reservation reservation: reservations){
				if(reservation.checkOverlap(date,time)){
					throw new InvalidInputException("Reservation overlap");
				}
			}
		}
		if(seatCapacity < numberInParty){
			throw new InvalidInputException("Not enough seats");
		}
		Table[] tableArray;
		tableArray = new Table[tables.size()];
		int i = 0;
		for(Table table: tables){
			tableArray[i]=table;
			i++;
		}
		Reservation res = new Reservation(date,time,numberInParty,contactName,contactEmail,contactPhone, restoApp,tableArray);
		
		//save
		try {
			RestoAppApplication.save();
		} catch (Exception e) {

			System.out.println(e.getMessage());
			throw e;
		}
	}

	public static boolean isAvailable(Table table){
		boolean isFree = true;
		RestoApp restoApp = RestoAppApplication.getRestoApp();
		for (Order currentOrder : restoApp.getCurrentOrders()) {
			if (currentOrder.getTables().contains(table)) {
				isFree = false;
				break;
			}
		}
		return isFree;
	}
	
	public static boolean isAvailable(Seat seat){
		boolean isFree = true;
		RestoApp restoApp = RestoAppApplication.getRestoApp();
		for (Order currentOrder : restoApp.getCurrentOrders()) {
			for (OrderItem item : currentOrder.getOrderItems()) {
				if (item.getSeats().contains(seat)) {
					isFree = false;
					break;
				}
			}
		}
		return isFree;
	}
	
	public static void startOrder(TakeOut takeOut) throws InvalidInputException{
		if (takeOut == null) {
			throw new InvalidInputException("Invalid Input");
		}
		RestoApp restoApp = RestoAppApplication.getRestoApp();

		Order newOrder = null;
		if (takeOut.startOrder()) {
			newOrder = takeOut.getOrder(takeOut.numberOfOrders()-1);
			newOrder.addSeat(takeOut.getSeat(0));
			restoApp.addCurrentOrder(newOrder);
		} else {
			throw new InvalidInputException();
		}

		try {
			RestoAppApplication.save();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}
	
	public static void startOrder(List<Table> tables) throws InvalidInputException{
		if (tables == null) {
			throw new InvalidInputException("Invalid Input");
		}
		RestoApp restoApp = RestoAppApplication.getRestoApp();
		List<Table> currentTables = restoApp.getCurrentTables();
		for (Table table : tables) {
			if (!currentTables.contains(table)) {
				throw new InvalidInputException("Table not in CurrentTables list");
			}
		}
		boolean orderCreated = false;
		Order newOrder = null;
		for (Table table : tables) {
			if (orderCreated) {
				table.addToOrder(newOrder);
			}
			else {
				Order lastOrder = null;
				if (table.numberOfOrders() > 0) {
					lastOrder = table.getOrder(table.numberOfOrders()-1);
				}
				table.startOrder();
				if (table.numberOfOrders() > 0 && !table.getOrder(table.numberOfOrders()-1).equals(lastOrder)) {
					orderCreated = true;
					newOrder = table.getOrder(table.numberOfOrders()-1);
				}
			}
		}
		if(orderCreated == false) {
			throw new InvalidInputException();
		}
		for (Table table : tables) {
			for (Seat seat :table.getCurrentSeats()) {
				newOrder.addSeat(seat);
			}
		}
		restoApp.addCurrentOrder(newOrder);
		try {
			RestoAppApplication.save();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}
  
	public static void endOrder(Order order) throws InvalidInputException{
		if (order == null) {
			throw new InvalidInputException("Invalid Order");
		}
		RestoApp restoApp = RestoAppApplication.getRestoApp();
		if (!restoApp.getCurrentOrders().contains(order)) {
			throw new InvalidInputException("Order not active");
		}
		List<Table> tablesList = order.getTables();
		Table[] tables = tablesList.toArray(new Table[tablesList.size()]);

		try {
			//List<OrderItem> orderItems = order.getOrderItems();
			boolean orderedDone = true;
			for (Table table : tables) {
				if(table.getStatusFullName() == "Ordered") {
					//System.out.println("Table" + table.getNumber());
					if (table.numberOfOrders() > 0 && table.getOrder(table.numberOfOrders()-1).equals(order)) {
						table.endOrder(order);
						if(table.getStatusFullName() != "Available") {
							orderedDone = false;
							break;
						}
					}
					
				}
			}
			if(orderedDone) {
				for (Table table : tables) {
					if(table.getStatusFullName() == "NothingOrdered") {
						//System.out.println("Table" + table.getNumber());
						if (table.numberOfOrders() > 0 && table.getOrder(table.numberOfOrders()-1).equals(order)) {
							table.endOrder(order);
						}
					}
				}
			}
			//System.out.println("available now");
			//restoApp.removeCurrentOrder(order);
			//System.out.println("removed");
			
			if (allTablesAvailableOrDifferentCurrentOrder(tablesList, order)) {
				//System.out.println("can remove current Order");
//				for(Seat seat : order.getSeats()) {
//					while(seat.hasOrderItems()) {
//						seat.getOrderItem(seat.numberOfOrderItems()-1).delete();
//					}
//				}
				restoApp.removeCurrentOrder(order);
				RestoAppApplication.save();
				//System.out.println("removed");
				
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
		
	}
	
	public static boolean allTablesAvailableOrDifferentCurrentOrder(List<Table> tables, Order order) {
		boolean canRemove = true;
		for (Table table : tables) {
			if (table.getStatusFullName() != "Available") {
				
				canRemove = false;
				break;
				
			}
		}
		return canRemove;
	}
	
	public static List<MenuItem> getPricedMenu(){
		List<MenuItem> listItems = new ArrayList<MenuItem>();
		RestoApp restoApp = RestoAppApplication.getRestoApp();
		Menu menu = restoApp.getMenu();
		for(MenuItem menuItem : menu.getMenuItems()){
			boolean current = menuItem.hasCurrentPricedMenuItem();
			if(current){
				listItems.add(menuItem);	
			}	
		}
		return listItems;
	}
	
	public static void updateMenuItem(String name, String newName, Double price, ItemCategory category) throws InvalidInputException{
		if(name == null){
			throw new InvalidInputException("Select an item");
		}
		if(price < 0){
			throw new InvalidInputException("Invalid price");
		}
		
		RestoApp restoApp = RestoAppApplication.getRestoApp();
		Menu menu = restoApp.getMenu();
		
		if(!newName.equals(name)){
			if(!menu.checkItemName(newName)){
				throw new InvalidInputException("Name already exists");
			}
		}

		for(MenuItem menuItem : menu.getMenuItems()){

			if(menuItem.getName().equals(name)){
				if(newName != null){
					menuItem.setName(newName);
				}
				if(price != null && price != menuItem.getCurrentPricedMenuItem().getPrice()){
					menuItem.setCurrentPricedMenuItem(menuItem.addPricedMenuItem(price, restoApp));
				}
				if(category!= null){
					menuItem.setItemCategory(category);
				}
				break;
			}
		}
		try {
			RestoAppApplication.save();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}

	public static void removeItem(String name) throws InvalidInputException{
		if(name == null){
			throw new InvalidInputException("Select an item");
		}
		
		RestoApp restoApp = RestoAppApplication.getRestoApp();
		Menu menu = restoApp.getMenu();

		for(MenuItem menuItem : menu.getMenuItems()){
			if(menuItem.getName().equals(name)){
				menuItem.setCurrentPricedMenuItem(null);
			}
		}
		try {
			RestoAppApplication.save();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
		
	}
	
	public static void addNewItem(String name, Double price, ItemCategory category) throws InvalidInputException{
		if(name == null){
			throw new InvalidInputException("Invalid name input");
		}
		if(price < 0 || price == null){
			throw new InvalidInputException("Invalid price input");
		}
		if(category == null){
			throw new InvalidInputException("Invalid category input");
		}
		RestoApp restoApp = RestoAppApplication.getRestoApp();
		Menu menu = restoApp.getMenu();
		
		if(!menu.checkItemName(name)){
			throw new InvalidInputException("Name already exists");
		}
		
		MenuItem newItem = new MenuItem (name, menu);
		newItem.setItemCategory(category);
		newItem.setCurrentPricedMenuItem(newItem.addPricedMenuItem(price, restoApp));

		try {
			RestoAppApplication.save();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}
	public static void orderMenuItem(int quantity, MenuItem menuItem, Order order, List<Seat> seats) throws InvalidInputException{
		if (quantity <= 0 || menuItem == null || seats == null) {
			throw new InvalidInputException("Invalid Inputs");
		}
		
		List<Table> tables = order.getTables();
		boolean itemCreated = false;
		OrderItem orderItem = null;
		
		if (!menuItem.hasCurrentPricedMenuItem()) {
			throw new InvalidInputException("Menuitem doesn't have price");
		}
		PricedMenuItem pmi = menuItem.getCurrentPricedMenuItem();
		
				
		for (Seat seat : seats) {
			Table table = seat.getTable();
			if (!tables.contains(table) || !table.getCurrentSeats().contains(seat) || table.getStatusFullName() == "Available") {
				throw new InvalidInputException("Order and seats don't match");
			}
			
			if (itemCreated) {
				if (orderItem != null) {
					table.addToOrderItem(orderItem, seat);
				}
				
			}
			else {
				table.orderItem(quantity, order, seat, pmi);
				itemCreated = true;
				orderItem = order.getOrderItem(order.numberOfOrderItems()-1);
			}
		}
		
		try {
			RestoAppApplication.save();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}
	
	//VIEW ORDER
	public static List<OrderItem> getOrderItems(Table table) throws InvalidInputException{
		RestoApp restoApp = RestoAppApplication.getRestoApp();
		List<Table> currentTables = restoApp.getCurrentTables();
		if (!currentTables.contains(table)) {
			throw new InvalidInputException("Table not a current table");
		}
		//check to see if table is in use or not
		String status = table.getStatusFullName();
		if (status == "Available") {
			throw new InvalidInputException("Table is still available");
		}
		
		List<Seat> currentSeats = table.getCurrentSeats();
		//create new list of orders
		List<OrderItem> result = new ArrayList<OrderItem>();
		Order lastOrder = null;
		for(Seat seat : currentSeats) {
			//for every seat, get the order items associated with it
			List<OrderItem> orderItems = seat.getOrderItems();
			for( OrderItem orderItem : orderItems) {
				Order order = orderItem.getOrder();
				//for every orderItem,
				//if the last order is the same as the current order and result doesnt have the order item
				if(lastOrder.equals(order) && result.contains(orderItem)) {
					result.add(orderItem);
					lastOrder = order;
				}
			}
		}
		return result;
	}
	//END VIEW ORDER
	
	//begin issue bill
	
	public static void issueBill(List<Seat> seats) throws InvalidInputException{
		if (seats==null || seats.isEmpty()) {
			throw new InvalidInputException("Invalid input");	
		}
		RestoApp restoApp = RestoAppApplication.getRestoApp();
		List<Table> currentTables = restoApp.getCurrentTables();
		Order lastOrder = null;
		
		for (Seat seat : seats) {
			Table table = seat.getTable();
			
			List<Seat> currentSeats = table.getCurrentSeats();
			if (!currentSeats.contains(seat)) {
				throw new InvalidInputException("Seat not a current seat");
			}
			if (lastOrder==null) {
				if (table.numberOfOrders()>0) {
					lastOrder=table.getOrder(table.numberOfOrders()-1);
				}
				else {
					throw new InvalidInputException("Invalid order");	
				}	
			}
			else {
				Order comparedOrder = null;
				if(table.numberOfOrders()>0) {
					comparedOrder=table.getOrder(table.numberOfOrders()-1);
				}
				else {
					throw new InvalidInputException("Invalid order");
				}
				if(!comparedOrder.equals(lastOrder)) {
					throw new InvalidInputException("Invalid order");
				}
			}
		}
		if(lastOrder==null) {
			throw new InvalidInputException("Invalid order");
		}
		Boolean billCreated=false;
		Bill newBill=null;
		for (Seat seat : seats) {
			Table table = seat.getTable();
			if (billCreated) {
				table.addToBill(newBill, seat);
			}
			else {
				Bill lastBill=null;
				if (lastOrder.numberOfBills()>0) {
					lastBill=lastOrder.getBill(lastOrder.numberOfBills()-1);
				}
				table.billForSeat(lastOrder, seat);
				
				if (lastOrder.numberOfBills()>0 && !lastOrder.getBill(lastOrder.numberOfBills()-1).equals(lastBill)) {
					billCreated=true;
					newBill=lastOrder.getBill(lastOrder.numberOfBills()-1);
				}
			}
		}
		if (billCreated=false) {
			throw new InvalidInputException("Bill not created");
		}	
		try {
			RestoAppApplication.save();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}
	//end issue bill

	public static String getTableNumber(Table table) {
		return String.valueOf(table.getNumber());
	}
	
	public static String getTableNumberOfSeats(Table table) {
		return String.valueOf(table.numberOfCurrentSeats());
	}

	public static List<Reservation> getReservations() {
		return RestoAppApplication.getRestoApp().getReservations();
	}

	public static void deleteReservation(Reservation res) {
		if (res != null) {
			res.delete();
		}
		try {
			RestoAppApplication.save();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}
	
//	public static void numberOfAvailableSeat(Table table) {
//		table.getCurrentSeats();
//		number
//	}

	//public void rotateTable(Table table);
	
	public static void CancelOrderItem(OrderItem aOrderItem) throws InvalidInputException {
		if(aOrderItem == null) {
			throw new InvalidInputException("Invalid Order Item");	
		}
		 
		 Order order = aOrderItem.getOrder();
		 List<Table> tables = order.getTables();
		 boolean isUsed = false;
		 for (Table table: tables) {
			 if(table.getOrder(table.numberOfOrders() - 1).equals(order)) {
				 isUsed |= true;
			 }
		 }
		 if (!isUsed) {
			 throw new InvalidInputException("Invalid Order Item");
		 }
		 ArrayList<Table> tableList = new ArrayList<Table>();
		 for (Seat seat: aOrderItem.getSeats()) {
			 Table t= seat.getTable();
			 if (!tableList.contains(t)) {
				 tableList.add(t);
			 }
		 }
		 
		 for(Table table: tableList) {
			 table.cancelOrderItem(aOrderItem);
		 }
		 
		 try {
				RestoAppApplication.save();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				throw e;
			} 
	}
	
	public static void CancelSeatOrder(Seat seat, Order order) throws InvalidInputException {
		
		if(seat == null) {
			throw new InvalidInputException("Invalid Seat");	
		}
		
		if(order == null) {
			throw new InvalidInputException("Invalid Order");	
		}
		 
		 if (seat.getOrder(seat.getOrders().size() -1) != order) {
			 throw new InvalidInputException("Invalid Order");
		 }
		 
		 OrderItem[] orderItems = order.getOrderItems().toArray(new OrderItem[order.getOrderItems().size()]);
		 for (OrderItem OI: orderItems) {
			 if (OI.getSeats().contains(seat)){
				 
				 ArrayList<Table> tableList = new ArrayList<Table>();
				 for (Seat orderedSeat: OI.getSeats()) {
					 Table t= orderedSeat.getTable();
					 if (!tableList.contains(t)) {
						 tableList.add(t);
					 }
				 }

				 for(Table table: tableList) {
					 table.cancelOrderItem(OI);
				 }
			 }
//			 table.cancelOrderItem(aOrderItem);
		 }
		 
		 try {
				RestoAppApplication.save();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				throw e;
			}
	}
	
	public static void CancelOrder(Order order) throws InvalidInputException {
		
		if(order == null) {
			throw new InvalidInputException("Invalid Order");
		}
	
		List<Table> tables = order.getTables();
		
		for (Table table: tables) {
			if (table.getOrder(table.numberOfOrders() - 1) == order) {
				table.cancelOrder();
			}
		}
		
		try {
			RestoAppApplication.save();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}

	public static void rotateTable(Table table) throws InvalidInputException{
		

		for (Order currentOrder : getCurrentOrders()) {
			List<Table> tableList = currentOrder.getTables();
			if (tableList.contains(table)) {
				throw new InvalidInputException("Table in use");
			}
		}
		
		RestoApp restoApp = RestoAppApplication.getRestoApp();
		int width = table.getLength();
		int length = table.getWidth();
		List<Table> currentTables = restoApp.getCurrentTables();
		
		
		for(Table t : currentTables) {
			if (t == table) {
				continue;
			}
			if (t.checkOverlap(table.getX(),table.getY(),length, width)) {
				throw new InvalidInputException("Table overlap");
			}
		}
		table.setWidth(width);
		table.setLength(length);
		try {
			RestoAppApplication.save();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
		
	}
}
