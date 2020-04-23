package com.demo.service;
 
import java.util.List;

import com.demo.model.DataExportTaskInfo;

public interface HistoryDataExportService {
	public DataExportTaskInfo selectByPk(long id); 
	public void insertDataExportTaskInfo(DataExportTaskInfo dataExportTaskInfo);
	public void updateByPk(DataExportTaskInfo dataExportTaskInfo);
	public List<DataExportTaskInfo>  getDataExportTaskInfoByStatus(String status);
}
