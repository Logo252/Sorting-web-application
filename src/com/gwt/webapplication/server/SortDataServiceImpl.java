package com.gwt.webapplication.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.gwt.webapplication.client.SortDataService;


/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class SortDataServiceImpl extends RemoteServiceServlet implements
		SortDataService {
	
    // Create array list of strings
    private List<List<String> >sortedDataArray = new ArrayList<>();
    
    // Last input and result data in case page reloads
    private String lasInputData =  null;
    private List<List<String> > lastSortedDataArray = new ArrayList<>();
    
    
    /**
     * Saves last input and result data and sends back response to the client.
     * @param inputData			Data from the text area.
     * @param sortedDataArray 	Sorted data.
     * @return 					Response from the server.
     */
    @Override
	public  boolean saveData(String inputData, List<List<String> >sortedDataArray) {
    	
    	// Saving data to the server
    	lasInputData = inputData;
    	lastSortedDataArray = sortedDataArray;
    	
    	return true;
	}

	/**
     * Sorts data and sends back to the client.
     * @param inputData 	Data from the text area.
     * @return 				Sorted data.
     */
	@Override
	public List<List<String> >  sortData(String inputData){
		
		// Clear data array for new data to sort
		sortedDataArray.clear();
		
		// Call method to split unsorted data to arrays
		splitDataToArrays(inputData);
				
		// Sort array list of strings 
		Collections.sort(sortedDataArray, new DataComparator() );
		
		return sortedDataArray; 
		
	}
	
    /**
     * Splits input data to arrays.
     * @param data 	 Data from the text area.
     */
	private void splitDataToArrays(String data){
		
		// Split data in lines
		String[] lines = data.split("\\r?\\n"); // ? - finds no or exactly one \\r symbol
		
		// String array of one input line
		String[] columns;
		
		// Create new array list of strings
		List<String> newArray = new ArrayList<>();
		
		for (String line : lines ){	
			
			// If line consists of one or more white spaces (line does not have any words or numbers) 
			// Then set line to empty
			if (line.matches("\\s+")) // + - one or more times
				line = "";
			
			// Split string (\s+ - repeats the white space character capture at least once, and as many time as possible)
			columns = line.split("\\s+");
			
			for (String column : columns ){
				
				// Add column to array list
				newArray.add(column);
			}
			
			// Add new created list of strings to the array
			sortedDataArray.add( new ArrayList<String>(newArray) );
			
			// Clear all newArray elements
			newArray.clear();
			
		}
		
	}
		
	
	
}
