/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/

package ca.mcgill.ecse223.resto.model;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.*;

// line 84 "../../../../../RestoAppPersistence.ump"
// line 42 "../../../../../RestoApp.ump"
public class Order implements Serializable
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextNumber = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Order Attributes
  private Date date;
  private Time time;

  //Autounique Attributes
  private int number;

  //Order Associations
  private List<Table> tables;
  private List<OrderItem> orderItems;
  private List<Seat> seats;
  private RestoApp restoApp;
  private List<Bill> bills;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Order(Date aDate, Time aTime, RestoApp aRestoApp, Table... allTables)
  {
    date = aDate;
    time = aTime;
    number = nextNumber++;
    tables = new ArrayList<Table>();
    boolean didAddTables = setTables(allTables);
    if (!didAddTables)
    {
      throw new RuntimeException("Unable to create Order, must have at least 1 tables");
    }
    orderItems = new ArrayList<OrderItem>();
    seats = new ArrayList<Seat>();
    boolean didAddRestoApp = setRestoApp(aRestoApp);
    if (!didAddRestoApp)
    {
      throw new RuntimeException("Unable to create order due to restoApp");
    }
    bills = new ArrayList<Bill>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setTime(Time aTime)
  {
    boolean wasSet = false;
    time = aTime;
    wasSet = true;
    return wasSet;
  }

  public Date getDate()
  {
    return date;
  }

  public Time getTime()
  {
    return time;
  }

  public int getNumber()
  {
    return number;
  }

  public Table getTable(int index)
  {
    Table aTable = tables.get(index);
    return aTable;
  }

  /**
   * only from currentTables for currentOrders
   */
  public List<Table> getTables()
  {
    List<Table> newTables = Collections.unmodifiableList(tables);
    return newTables;
  }

  public int numberOfTables()
  {
    int number = tables.size();
    return number;
  }

  public boolean hasTables()
  {
    boolean has = tables.size() > 0;
    return has;
  }

  public int indexOfTable(Table aTable)
  {
    int index = tables.indexOf(aTable);
    return index;
  }

  public OrderItem getOrderItem(int index)
  {
    OrderItem aOrderItem = orderItems.get(index);
    return aOrderItem;
  }

  public List<OrderItem> getOrderItems()
  {
    List<OrderItem> newOrderItems = Collections.unmodifiableList(orderItems);
    return newOrderItems;
  }

  public int numberOfOrderItems()
  {
    int number = orderItems.size();
    return number;
  }

  public boolean hasOrderItems()
  {
    boolean has = orderItems.size() > 0;
    return has;
  }

  public int indexOfOrderItem(OrderItem aOrderItem)
  {
    int index = orderItems.indexOf(aOrderItem);
    return index;
  }

  public Seat getSeat(int index)
  {
    Seat aSeat = seats.get(index);
    return aSeat;
  }

  public List<Seat> getSeats()
  {
    List<Seat> newSeats = Collections.unmodifiableList(seats);
    return newSeats;
  }

  public int numberOfSeats()
  {
    int number = seats.size();
    return number;
  }

  public boolean hasSeats()
  {
    boolean has = seats.size() > 0;
    return has;
  }

  public int indexOfSeat(Seat aSeat)
  {
    int index = seats.indexOf(aSeat);
    return index;
  }

  public RestoApp getRestoApp()
  {
    return restoApp;
  }

  public Bill getBill(int index)
  {
    Bill aBill = bills.get(index);
    return aBill;
  }

  public List<Bill> getBills()
  {
    List<Bill> newBills = Collections.unmodifiableList(bills);
    return newBills;
  }

  public int numberOfBills()
  {
    int number = bills.size();
    return number;
  }

  public boolean hasBills()
  {
    boolean has = bills.size() > 0;
    return has;
  }

  public int indexOfBill(Bill aBill)
  {
    int index = bills.indexOf(aBill);
    return index;
  }

  public boolean isNumberOfTablesValid()
  {
    boolean isValid = numberOfTables() >= minimumNumberOfTables();
    return isValid;
  }

  public static int minimumNumberOfTables()
  {
    return 1;
  }

  public boolean addTable(Table aTable)
  {
    boolean wasAdded = false;
    if (tables.contains(aTable)) { return false; }
    tables.add(aTable);
    if (aTable.indexOfOrder(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aTable.addOrder(this);
      if (!wasAdded)
      {
        tables.remove(aTable);
      }
    }
    return wasAdded;
  }

  public boolean removeTable(Table aTable)
  {
    boolean wasRemoved = false;
    if (!tables.contains(aTable))
    {
      return wasRemoved;
    }

    if (numberOfTables() <= minimumNumberOfTables())
    {
      return wasRemoved;
    }

    int oldIndex = tables.indexOf(aTable);
    tables.remove(oldIndex);
    if (aTable.indexOfOrder(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aTable.removeOrder(this);
      if (!wasRemoved)
      {
        tables.add(oldIndex,aTable);
      }
    }
    return wasRemoved;
  }

  public boolean setTables(Table... newTables)
  {
    boolean wasSet = false;
    ArrayList<Table> verifiedTables = new ArrayList<Table>();
    for (Table aTable : newTables)
    {
      if (verifiedTables.contains(aTable))
      {
        continue;
      }
      verifiedTables.add(aTable);
    }

    if (verifiedTables.size() != newTables.length || verifiedTables.size() < minimumNumberOfTables())
    {
      return wasSet;
    }

    ArrayList<Table> oldTables = new ArrayList<Table>(tables);
    tables.clear();
    for (Table aNewTable : verifiedTables)
    {
      tables.add(aNewTable);
      if (oldTables.contains(aNewTable))
      {
        oldTables.remove(aNewTable);
      }
      else
      {
        aNewTable.addOrder(this);
      }
    }

    for (Table anOldTable : oldTables)
    {
      anOldTable.removeOrder(this);
    }
    wasSet = true;
    return wasSet;
  }

  public boolean addTableAt(Table aTable, int index)
  {  
    boolean wasAdded = false;
    if(addTable(aTable))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTables()) { index = numberOfTables() - 1; }
      tables.remove(aTable);
      tables.add(index, aTable);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTableAt(Table aTable, int index)
  {
    boolean wasAdded = false;
    if(tables.contains(aTable))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTables()) { index = numberOfTables() - 1; }
      tables.remove(aTable);
      tables.add(index, aTable);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTableAt(aTable, index);
    }
    return wasAdded;
  }

  public static int minimumNumberOfOrderItems()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public OrderItem addOrderItem(int aQuantity, PricedMenuItem aPricedMenuItem, Seat... allSeats)
  {
    return new OrderItem(aQuantity, aPricedMenuItem, this, allSeats);
  }

  public boolean addOrderItem(OrderItem aOrderItem)
  {
    boolean wasAdded = false;
    if (orderItems.contains(aOrderItem)) { return false; }
    Order existingOrder = aOrderItem.getOrder();
    boolean isNewOrder = existingOrder != null && !this.equals(existingOrder);
    if (isNewOrder)
    {
      aOrderItem.setOrder(this);
    }
    else
    {
      orderItems.add(aOrderItem);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeOrderItem(OrderItem aOrderItem)
  {
    boolean wasRemoved = false;
    //Unable to remove aOrderItem, as it must always have a order
    if (!this.equals(aOrderItem.getOrder()))
    {
      orderItems.remove(aOrderItem);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addOrderItemAt(OrderItem aOrderItem, int index)
  {  
    boolean wasAdded = false;
    if(addOrderItem(aOrderItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOrderItems()) { index = numberOfOrderItems() - 1; }
      orderItems.remove(aOrderItem);
      orderItems.add(index, aOrderItem);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveOrderItemAt(OrderItem aOrderItem, int index)
  {
    boolean wasAdded = false;
    if(orderItems.contains(aOrderItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOrderItems()) { index = numberOfOrderItems() - 1; }
      orderItems.remove(aOrderItem);
      orderItems.add(index, aOrderItem);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addOrderItemAt(aOrderItem, index);
    }
    return wasAdded;
  }

  public static int minimumNumberOfSeats()
  {
    return 0;
  }

  public boolean addSeat(Seat aSeat)
  {
    boolean wasAdded = false;
    if (seats.contains(aSeat)) { return false; }
    seats.add(aSeat);
    if (aSeat.indexOfOrder(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aSeat.addOrder(this);
      if (!wasAdded)
      {
        seats.remove(aSeat);
      }
    }
    return wasAdded;
  }

  public boolean removeSeat(Seat aSeat)
  {
    boolean wasRemoved = false;
    if (!seats.contains(aSeat))
    {
      return wasRemoved;
    }

    int oldIndex = seats.indexOf(aSeat);
    seats.remove(oldIndex);
    if (aSeat.indexOfOrder(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aSeat.removeOrder(this);
      if (!wasRemoved)
      {
        seats.add(oldIndex,aSeat);
      }
    }
    return wasRemoved;
  }

  public boolean addSeatAt(Seat aSeat, int index)
  {  
    boolean wasAdded = false;
    if(addSeat(aSeat))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSeats()) { index = numberOfSeats() - 1; }
      seats.remove(aSeat);
      seats.add(index, aSeat);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveSeatAt(Seat aSeat, int index)
  {
    boolean wasAdded = false;
    if(seats.contains(aSeat))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSeats()) { index = numberOfSeats() - 1; }
      seats.remove(aSeat);
      seats.add(index, aSeat);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addSeatAt(aSeat, index);
    }
    return wasAdded;
  }

  public boolean setRestoApp(RestoApp aRestoApp)
  {
    boolean wasSet = false;
    if (aRestoApp == null)
    {
      return wasSet;
    }

    RestoApp existingRestoApp = restoApp;
    restoApp = aRestoApp;
    if (existingRestoApp != null && !existingRestoApp.equals(aRestoApp))
    {
      existingRestoApp.removeOrder(this);
    }
    restoApp.addOrder(this);
    wasSet = true;
    return wasSet;
  }

  public static int minimumNumberOfBills()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Bill addBill(RestoApp aRestoApp, Seat... allIssuedForSeats)
  {
    return new Bill(this, aRestoApp, allIssuedForSeats);
  }

  public boolean addBill(Bill aBill)
  {
    boolean wasAdded = false;
    if (bills.contains(aBill)) { return false; }
    Order existingOrder = aBill.getOrder();
    boolean isNewOrder = existingOrder != null && !this.equals(existingOrder);
    if (isNewOrder)
    {
      aBill.setOrder(this);
    }
    else
    {
      bills.add(aBill);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeBill(Bill aBill)
  {
    boolean wasRemoved = false;
    //Unable to remove aBill, as it must always have a order
    if (!this.equals(aBill.getOrder()))
    {
      bills.remove(aBill);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addBillAt(Bill aBill, int index)
  {  
    boolean wasAdded = false;
    if(addBill(aBill))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBills()) { index = numberOfBills() - 1; }
      bills.remove(aBill);
      bills.add(index, aBill);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBillAt(Bill aBill, int index)
  {
    boolean wasAdded = false;
    if(bills.contains(aBill))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBills()) { index = numberOfBills() - 1; }
      bills.remove(aBill);
      bills.add(index, aBill);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addBillAt(aBill, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    ArrayList<Table> copyOfTables = new ArrayList<Table>(tables);
    tables.clear();
    for(Table aTable : copyOfTables)
    {
      aTable.removeOrder(this);
    }
    while (orderItems.size() > 0)
    {
      OrderItem aOrderItem = orderItems.get(orderItems.size() - 1);
      aOrderItem.delete();
      orderItems.remove(aOrderItem);
    }
    
    ArrayList<Seat> copyOfSeats = new ArrayList<Seat>(seats);
    seats.clear();
    for(Seat aSeat : copyOfSeats)
    {
      aSeat.removeOrder(this);
    }
    RestoApp placeholderRestoApp = restoApp;
    this.restoApp = null;
    if(placeholderRestoApp != null)
    {
      placeholderRestoApp.removeOrder(this);
    }
    for(int i=bills.size(); i > 0; i--)
    {
      Bill aBill = bills.get(i - 1);
      aBill.delete();
    }
  }

  // line 90 "../../../../../RestoAppPersistence.ump"
   public static  void reinitializeAutouniqueNumber(List<Order> orders){
    nextNumber = 0; 
    for (Order order : orders) {
      if (order.getNumber() > nextNumber) {
        nextNumber = order.getNumber();
      }
    }
    nextNumber++;
  }

  // line 99 "../../../../../RestoAppPersistence.ump"
   public String toString(){
    List<Table> tables = this.getTables();
    boolean isTakeOut = true;
    
	   String tableList = " : Tables ";
	   for (Table table : tables) {
		   if (isTakeOut == true){
			   if (table instanceof TakeOut) {
				   tableList = " : Take Out";
				   break;
			   } else {
				   isTakeOut = false;
			   }
		   }
			   
		   tableList += "#" + table.getNumber() + " ";
	   }
	   
	return "Order # " + this.getNumber() + tableList;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 87 "../../../../../RestoAppPersistence.ump"
  private static final long serialVersionUID = -3900912597282882073L ;

  
}