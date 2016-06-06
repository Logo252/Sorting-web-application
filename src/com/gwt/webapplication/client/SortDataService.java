package com.gwt.webapplication.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("inputData")
public interface SortDataService extends RemoteService {
	
	List<List<String> >  sortData(String  inputData);
	
	boolean saveData(String inputData, List<List<String> >sortedDataArray);
	
}
