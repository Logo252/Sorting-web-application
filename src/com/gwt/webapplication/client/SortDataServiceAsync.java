package com.gwt.webapplication.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * The async counterpart of SortDataService.
 */
public interface SortDataServiceAsync {

	void  sortData(String inputData, AsyncCallback< List<List<String> > > callback);
	
	void saveData(String inputData, List<List<String> >sortedDataArray, AsyncCallback<Boolean> callback);
	
}
