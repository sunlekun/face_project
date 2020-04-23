package com.demo.service.impl;
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.demo.dao.HistoryDataExportDao;
import com.demo.model.DataExportTaskInfo;
import com.demo.service.HistoryDataExportService;
 
@Service 
public class HistoryDataExportServiceImpl implements HistoryDataExportService{
	@Autowired
    @Qualifier("historyDataExportDao")
    private HistoryDataExportDao historyDataExportDao;
	public DataExportTaskInfo selectByPk(long id){
		return  historyDataExportDao.selectByPk(id);
	}
	public void insertDataExportTaskInfo(DataExportTaskInfo dataExportTaskInfo){
		historyDataExportDao.insertDataExportTaskInfo(dataExportTaskInfo);
	}
	public void updateByPk(DataExportTaskInfo dataExportTaskInfo){
		historyDataExportDao.updateByPk(dataExportTaskInfo);
	}
	@Override
	public List<DataExportTaskInfo> getDataExportTaskInfoByStatus(String status) {
		 
		return historyDataExportDao.getDataExportTaskInfoByStatus(status);
	}
}
