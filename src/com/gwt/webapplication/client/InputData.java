package com.gwt.webapplication.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;

/**
 * Entry point class.
 * @author Arnas Grigaliunas.
 */
public class InputData implements EntryPoint {
	
	/**
	 * Create a remote service proxy to talk to the server-side SortDataService.
	 */
	private static SortDataServiceAsync sortDataService = GWT.create(SortDataService.class);
	
	
	
    /**
     * A composite of the TextArea, flexTable, and button widgets.
     */
	private static class CustomWidget extends Composite implements ClickHandler, Window.ClosingHandler{ 
	    
	    // Create textArea, button and flexTable widgets
	    private Button processButton = new Button("Process"); 
	    private TextArea textArea = new TextArea(); 
	    private FlexTable flexTable = new FlexTable();
	    
	    // Create horizontal panel for widgets
	    private HorizontalPanel panel = new HorizontalPanel();
	    
	    // Input data
	    private String input = null;
	    
	    // Result data
	    private List<List<String> > sortedDataArray = new ArrayList<>();
	    
	    /**
	     * Constructs the custom widget.
	     */
	    public CustomWidget() {
	    	
	    	// Set style to the flex table
    	   	flexTable.addStyleName("cw-FlexTable");
    	    
	    	// Set textarea width and height
			textArea.setCharacterWidth(50);
			textArea.setVisibleLines(25);
		      
			// Add textarea, button and grid widgets to the horizontal panel
			panel.add(textArea);
			panel.add(flexTable);
			panel.add(processButton);
			
			// Set style for the panel
			panel.setStyleName("style");
			
			// Set the panel to be wrapped by the composite
			initWidget(panel);
			
			processButton.addClickHandler(this);
		    Window.addWindowClosingHandler(this);
		    
	    }
	    
	    /**
		 * Fired when the user reloads or closes application.
		 * @param closingEvent 	Event occurs if app reloads or closes.
		 */
	    @Override
		public void onWindowClosing(Window.ClosingEvent closingEvent) {
	    	
	    	// Get data from text area
	    	input = textArea.getValue();
	    	
	    	// Save all data to the server before web reloads or closes
	    	saveDataBeforeReload(input, sortedDataArray);
        	
    
        }
 
		/**
		 * Fired when the user clicks on the processButton.
		 * @param event		Occurs if process button is clicked
		 */
		@Override
		public void onClick(ClickEvent event) {
		
		
			
			// Remove all rows from table before looking at new data 
			flexTable.removeAllRows();
			
			input = textArea.getValue();
	
			// Get sorted input data from the server if client something types in text area
			if (input.trim().length() > 0) 
				getSortedData(input);
		
		}
		
		
	     /**
	      * Fill the table with sorted data.
	      * @param sortedData 	Result from the server.
	      */
	     private void fillTable(List<List<String> > sortedData) {
	    	 
	    	sortedDataArray = sortedData;
	    	
	    	// Initial number of rows 
    	    int numRows = 0;    
    	    
    	    // Initial number of columns
	    	int numColumns = sortedDataArray.get(0).size();  
	    	
	    	int i = 0;
	    	
	    	
	    	// Check list to find which has most number of elements (most columns)
	    	for (List<String> line : sortedDataArray )
			{	
	    		 if (numColumns < line.size() )
	    			 numColumns = line.size();
	    		 
			}
	    	
    		// Fill the table with sorted data
    		for (List<String> line : sortedDataArray )
			{	
    			
    			
    			for (String column : line ){
    				 
    				 // Puts data to a cell of the Table
					 flexTable.setText(numRows, i, column.toString() ); 
	
    				 i++; 
    			}
    			
    			// If number of columns is not zero
    			if ( i!= 0){
    				
    				// Add additional cell to the row 
        		    while( i < numColumns){
    					 flexTable.addCell(numRows);		
    					 i++;
    				} 
        			
        			i = 0; 
        			numRows++;
        			
    			}
    		
		      
			}	
	    
	     }
		
        /**
		 * Saves last input and result data to the server.
		 * @param inputData			Data from text area.
		 * @param sortedDataArray	Sorted data.		
		 */
	    private void saveDataBeforeReload(String inputData, List<List<String> > sortedDataArray){
	    	
    	    // Initialize the service proxy.
		    if (sortDataService == null) {
		    	sortDataService = GWT.create(SortDataService.class);
		    }
		    
		    // Set up the callback object.
		    AsyncCallback<Boolean> callBack = new AsyncCallback< Boolean>() {
		    	
		    	 @Override
				public void onFailure(Throwable caught) {
		    	      
		    	      }

	    	      @Override
				public void onSuccess(Boolean response) {
	    	    	  
	    	    	  
	    	      	}
	    	     
		    };
		    
		    // Make the call to the sort data service.
		    sortDataService.saveData(inputData, sortedDataArray, callBack);
		    
		    
	    }
		    
	    /**
		 * Get sorted data from the server.
		 * @param inputData		Data from text are.
	     */
		private void getSortedData(String inputData){
			
		    // Initialize the service proxy.
		    if (sortDataService == null) {
		    	sortDataService = GWT.create(SortDataService.class);
		    }
		    
	
		    // Set up the callback object
		    AsyncCallback< List< List<String>> >  callBack = new AsyncCallback< List<List<String>> >() {
		    	
		    	 @Override
				public void onFailure(Throwable caught) {
		    
		    	      }

	    	      @Override
				public void onSuccess(List<List<String> > result) {
	    	    	  fillTable(result);
	    	      }
	    	     
		    };
		    
		    // Make the call to the sort data service
		    sortDataService.sortData(inputData, callBack);
		      
		}
		
	  }
	 
	/**
	 * This is the entry point method.
	 */
	@Override
	public void onModuleLoad() {
		
		// Create a custom widget and add it to the root panel
	    CustomWidget widget = new CustomWidget();
	    RootPanel.get().add(widget);
	    
	}
	
}
