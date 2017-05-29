package dao;

import java.util.List;

import model.Dataset;

public interface DatasetDao {

    public List<Dataset> queryAll();

    public void create(Dataset d);
    public Dataset read(int id);
    public void update(Dataset d);
    public void delete(Dataset d);

}