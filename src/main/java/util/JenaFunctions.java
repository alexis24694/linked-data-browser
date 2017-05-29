package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.jena.graph.Triple;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFactory;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.sparql.expr.E_Regex;
import org.apache.jena.sparql.expr.Expr;
import org.apache.jena.sparql.expr.ExprVar;
import org.apache.jena.sparql.syntax.ElementFilter;
import org.apache.jena.sparql.syntax.ElementGroup;
import org.apache.jena.sparql.syntax.ElementTriplesBlock;

import model.Property;
import model.Resource;
import model.Statement;

public class JenaFunctions {
	/**
	 * @param args
	 */
	
	public static List<Statement> queryRegex(String regexWord, String sparqlService){
		
		Query stringQuery = QueryFactory.create("select * where {?s ?p ?o . FILTER  (regex(?s, '"+regexWord+"', 'i')  || regex(?o, '"+regexWord+"', 'i'))} LIMIT 100");
		
        QueryExecution exec = QueryExecutionFactory.sparqlService(sparqlService, stringQuery);
		
		ResultSet results = ResultSetFactory.copyResults(exec.execSelect() );
        //ResultSetFormatter.out( results );
        
        List<Statement> viewResults = new ArrayList<Statement>();
        
        while(results.hasNext()){
        	QuerySolution binding = results.nextSolution();   
        	Resource subject = new Resource(binding.get("?s").toString());
        	Property predicate = new Property(binding.get("?p").toString());
        	Resource object = new Resource(binding.get("?o").toString());
        	Statement queryStatement = new Statement(subject,predicate,object);
        	viewResults.add(queryStatement);  
        }
        
        
		return viewResults;
	}
	
	public static Query createARQSelectRegexQuery(String searchTerm){
		Query q = QueryFactory.make();
		
		ExprVar subjectVar = new ExprVar("?s");
		ExprVar predicateVar = new ExprVar("?p");
		ExprVar objectVar = new ExprVar("?o");
		
		ElementTriplesBlock triples = new ElementTriplesBlock();

		triples.addTriple( new Triple( subjectVar.getAsNode(), predicateVar.getAsNode() ,objectVar.getAsNode()));
		
        Expr e = new E_Regex(objectVar,searchTerm,"i");
        ElementFilter filter = new ElementFilter(e);
        ElementGroup body = new ElementGroup();  
        body.addElement(triples);
        body.addElement(filter);

        
        q.setQueryPattern(body);
        q.setQuerySelectType();
        q.addResultVar(subjectVar);
        q.addResultVar(predicateVar);
        q.addResultVar(objectVar);
        
        System.out.println(q);
        return q;
	}
	
	public static Map<Statement, Integer> mapURIs(List<Statement> list){
		
		Map<Statement, Integer> map = new HashMap<Statement, Integer>();
		
		for (Statement temp : list) {
			Integer count = map.get(temp);
			map.put(temp, (count == null) ? 1 : count + 1);
		}
		
		Map<Statement, Integer> sortedMap = sortByValue(map);
		
		
		for (Map.Entry<Statement, Integer> entry : sortedMap.entrySet()) {
			if(entry.getValue() > 1)
			System.out.println("Key : " + entry.getKey().object + " Value : "
				+ entry.getValue());
			
		}
		
		return sortedMap;

	}
	
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
	    return map.entrySet()
	              .stream()
	              .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
	              .collect(Collectors.toMap(
	                Map.Entry::getKey, 
	                Map.Entry::getValue, 
	                (e1, e2) -> e1, 
	                LinkedHashMap::new
	              ));
	}

	public static List<Statement> getNavigationResults(String navigationURI, String sparqlService){				
		System.out.println("Navegacion a: " + navigationURI);
		
		List<Statement> viewResults = new ArrayList<Statement>();
		
		String queryString = "select distinct ?s ?p ?o where {?s ?p ?o filter (?s = <"+ navigationURI+">)} LIMIT 100";
		Query navigationQuery = QueryFactory.create(queryString);
		
		QueryExecution exec = QueryExecutionFactory.sparqlService(sparqlService, navigationQuery);
		ResultSet results = ResultSetFactory.copyResults(exec.execSelect());
		//ResultSetFormatter.out( results );
		
		while(results.hasNext()){
        	QuerySolution binding = results.nextSolution();   
        	Resource subject = new Resource(binding.get("?s").toString());
        	Property predicate = new Property(binding.get("?p").toString());
        	Resource object = new Resource(binding.get("?o").toString());
        	Statement queryStatement = new Statement(subject,predicate,object);
        	viewResults.add(queryStatement);
        	
        }
		
		return viewResults;
	}
	
	public static List<Statement> getRelatedPublications(String searchTerm){				
		
		List<Statement> viewResults = new ArrayList<Statement>();
		
		String queryString = "PREFIX akt:  <http://www.aktors.org/ontology/portal#> PREFIX iai:  <http://www.iai.uni-sb.de/resist#> SELECT ?uriS ?titleS ?uriO ?titleO ?urlS ?urlO WHERE { ?uriS iai:is-related-to ?uriO . ?uriS akt:has-title ?titleS . ?uriO akt:has-title ?titleO . ?uriS akt:has-web-address ?urlS . ?uriO akt:has-web-address ?urlO . FILTER  (regex(?titleS, '"+searchTerm+"', 'i'))} LIMIT 100";
		Query navigationQuery = QueryFactory.create(queryString);
		//navigationQuery.setPrefix("iai", "http://www.iai.uni-sb.de/resist#");
		
		QueryExecution exec = QueryExecutionFactory.sparqlService("http://ieee.rkbexplorer.com/sparql/", navigationQuery);
		ResultSet results = ResultSetFactory.copyResults(exec.execSelect());
		
		while(results.hasNext()){
        	QuerySolution binding = results.nextSolution();   
        	Resource subject = new Resource(binding.get("?uriS").toString());
        	subject.setName(binding.get("?titleS").toString());
        	subject.setUrl(binding.get("?urlS").toString());
        	
        	Property predicate = new Property("is-related-to");
        	
        	Resource object = new Resource(binding.get("?uriO").toString());
        	object.setName(binding.get("?titleO").toString());
        	object.setUrl(binding.get("?urlO").toString());
        	
        	Statement queryStatement = new Statement(subject,predicate,object);
        	viewResults.add(queryStatement);
        	
        }
		
		return viewResults;
	}
}