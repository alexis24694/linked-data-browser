package dao;

import java.util.List;

import model.Property;

public interface PropertyDao {

    public List<Property> queryAll();

    public void create(Property d);
    public Property read(int id);
    public void update(Property d);
    public void delete(Property d);

}