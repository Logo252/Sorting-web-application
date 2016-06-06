package com.gwt.webapplication.server;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

/**
 * The Data comparator of array lists.
 */
public class DataComparator implements Comparator<Object> {
	
	// Create initial column for sorting, starting to compare at first column
	private int column = 0;				
	
	// Two array lists
	private List<String> arrayList1;
	private List<String> arrayList2;
	
	// Create two empty strings
	private String element1 = null;
	private String element2 = null;

	/*
	 *  Implement the Comparable interface method compare.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int compare(Object o1, Object o2)
	{	 
		// Cast lists of strings
		arrayList1 = (List<String>)o1; 
		arrayList2 = (List<String>)o2;
			
		return compareArraysLists(column);
	}
	
	/**
	 * Compares numbers.
	 * @param element1	 First number.
	 * @param element2	 Second number.
	 * @param newColumn	 Number of column.
	 * @return			 Comparing value.
	 */
	private int compareNumbers(String element1, String elemet2, int newColumn){
		
		int value = new BigDecimal(element1.toString() ).compareTo( new BigDecimal(elemet2.toString() ) );
		newColumn++;
		
		// If strings are equal and both arrayLists have one more column to compare
		// Then compare again by next column
		if (  (value == 0) && (  ( (arrayList1.size()-1) >= newColumn) && ( (arrayList2.size()-1) >= newColumn)  )  ) {
			return compareArraysLists(newColumn);
		}
		else
			return value;
		
	}
	
	/**
	 * Checks if a string can be converted to the number.
	 * @param string	Input of type int.	
	 * @return			Response is it number or not.
	 */
	public boolean isNumber(String string) {  
		
		// ? - 0 or 1 times, \d - any digit, * - 0 or more times, + - 1 or more times
	    return string.matches("[-+]?\\d*\\.?\\d+");  
	}  
	
	/**
	 * Compares strings.
	 * @param element1		First string.
	 * @param element2		Second string.
	 * @return				Comparig value.
	 */
	private int compareStrings(String element1, String element2, int newColumn){
		
		int value = element1.compareTo(element2);
		newColumn++;
		
		// If strings are equal and both arrayLists have one more column to compare
		// Then compare again by next column
		if (  (value == 0) && (  ( (arrayList1.size()-1) >= newColumn) && ( (arrayList2.size()-1) >= newColumn)  )  ) 
			return compareArraysLists(newColumn);
		else
			return value;
		
	}
	
	/**
	 * Compare arrayLists.
	 * @param newColumn		Number of column.
	 * @return				Comparing value.
	 */
	private int compareArraysLists(int newColumn){	
		
		// Create initial return comparing value 
		int value = 0; 
		
		// Get elements by column
		element1 = arrayList1.get(newColumn);
		element2 = arrayList2.get(newColumn);
				
		// Compare all possible variants
		// Compare numbers
		if ( ( isNumber(element1) ) && ( isNumber(element2)  ) )
		{	
			value = compareNumbers(element1, element2, newColumn);
			
		} // Compare number and string
		else if (  isNumber(element1)  ){ 
			
			// element1 < element2 - number has to be above string in the table
			value = -1; 
			
		} // Compare string and number
		else if (   isNumber(element2)  ){ 
			
			 // element1 > element2  - string has to be below number in the table
			value = 1;
			
		} // Compare strings
		else 
		{
			value = compareStrings(element1, element2, newColumn);
			
		} 
		
		
		return value;
		
	}
	
	
}
