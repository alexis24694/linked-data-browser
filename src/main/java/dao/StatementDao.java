package dao;

import java.util.List;

import model.Statement;

public interface StatementDao {

    public List<Statement> queryAll();

    public void create(Statement d);
    public Statement read(int id);
    public void update(Statement d);
    public void delete(Statement d);

}