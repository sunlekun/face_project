package com.demo.dao;
 
import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.demo.model.DataExportTaskInfo;
@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=false,isolation=Isolation.DEFAULT)
public interface HistoryDataExportDao {
	public DataExportTaskInfo selectByPk(long id); 
	public void insertDataExportTaskInfo(DataExportTaskInfo dataExportTaskInfo);
	public void updateByPk(DataExportTaskInfo dataExportTaskInfo);
	public List<DataExportTaskInfo>  getDataExportTaskInfoByStatus(String status);
}
