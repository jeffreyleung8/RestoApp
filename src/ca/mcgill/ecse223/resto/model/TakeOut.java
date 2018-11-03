/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/

package ca.mcgill.ecse223.resto.model;
import java.util.*;

// line 1 "../../../../../TakeOut.ump"
public class TakeOut extends Table
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TakeOut(int aNumber, int aX, int aY, int aWidth, int aLength, RestoApp aRestoApp)
  {
    super(aNumber, aX, aY, aWidth, aLength, aRestoApp);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }

  // line 6 "../../../../../TakeOut.ump"
   public  TakeOut(RestoApp aRestoApp){
    super(0, 0, 0, 0, 0, aRestoApp);
  }

}