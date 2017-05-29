package model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="statement")
public class Statement implements Comparable<Statement>{
	
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
	public int id;
	
	@ManyToOne
    @JoinColumn (name="dataset")
	public Dataset dataset;
	
	@ManyToOne
    @JoinColumn (name="subject")
	public Resource subject;
	
	@ManyToOne
    @JoinColumn (name="predicate")
	public Property predicate;
	
	@ManyToOne
    @JoinColumn (name="object")
	public Resource object;	
	
	public Statement(){}
	
	public Statement(Resource subject, Property predicate, Resource object){
		this.subject = subject;
		this.predicate = predicate;
		this.object = object;
	}
	
	@Override
	public int compareTo(Statement s) {
		// TODO Auto-generated method stub
		int subjectDifference = Math.abs(subject.getName().compareTo(s.getSubject().getName()));

		int predicateDifference = Math.abs(predicate.getName().compareTo(s.getPredicate().getName()));

		int objectDifference = Math.abs(object.getName().compareTo(s.getObject().getName()));

		return Math.max(Math.max(subjectDifference, predicateDifference),objectDifference);
		
	}
	
	@Override
	public boolean equals(Object  s){
		return (subject.getName().equals(((Statement) s).getSubject().getName()) && 
				predicate.getName().equals(((Statement) s).getPredicate().getName()) && 
				object.getName().equals(((Statement) s).getObject().getName()));
	}
	
	@Override
    public int hashCode() {
        return Objects.hash(subject, predicate, object);
    }
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Dataset getDataset() {
		return dataset;
	}

	public void setDataset(Dataset dataset) {
		this.dataset = dataset;
	}

	public Resource getSubject(){
		return subject;
	}
	
	public Property getPredicate(){
		return predicate;
	}
	
	public Resource getObject(){
		return object;
	}
	
	
}