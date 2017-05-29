package dao;

import java.util.List;

import model.Ontology;

public interface OntologyDao {

    public List<Ontology> queryAll();

    public void create(Ontology d);
    public Ontology read(int id);
    public void update(Ontology d);
    public void delete(Ontology d);

}