package dao;

import java.util.List;

import model.Resource;

public interface ResourceDao {

    public List<Resource> queryAll();

    public void create(Resource d);
    public Resource read(int id);
    public void update(Resource d);
    public void delete(Resource d);

}