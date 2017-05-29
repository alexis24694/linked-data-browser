package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dao.DatasetDao;
import model.Dataset;



@Component
public class DatasetManager  {
    
    @Autowired
    private DatasetDao datasetDao;

    public void setDao(DatasetDao datasetDao) {
        this.datasetDao = datasetDao;
    }
    
    public List<Dataset> queryAll() {
        return datasetDao.queryAll();
    }
	
}