package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import model.Dataset;
import model.Statement;
import service.DatasetManager;
import util.JenaFunctions;
import util.LanguageProcessor;

@Controller
public class Application {
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired 
	DatasetManager datasetManager;
	
	@RequestMapping(value="/usetag.htm")
    public ModelAndView handleRequestUseTag(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		
		return new ModelAndView("usetag");
		
	}
	
	@RequestMapping(value="maintemplate.htm")
    public ModelAndView handleRequestMainTemplate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		
		return new ModelAndView("test/maintemplate");
		
	}
	
	@RequestMapping(value="/main.htm")
    public ModelAndView handleRequestPrincipal(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		
		return new ModelAndView("main");
		
	}
	
	@RequestMapping(value="/management.htm")
    public ModelAndView handleRequestManagement(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		
		return new ModelAndView("management/profile/profile");
		
	}
	
	@RequestMapping(value="/result.htm")
	public ModelAndView result(HttpServletRequest request, HttpServletResponse response){
	
		return new ModelAndView("result");
	}
	
	@RequestMapping(value="/search.htm")
	public ModelAndView searchConcept(HttpServletRequest request, HttpServletResponse response){
		
		
		List<Dataset> dsList = datasetManager.queryAll();
		
		for (Dataset d: dsList){
			System.out.println(d.name);
		}

		String searchQuery = (String) request.getParameter("search");
		System.out.println(searchQuery);
		
		List<String> nounList = LanguageProcessor.processSearchQuery(searchQuery);
		
		//Publicaciones cientificas
		List<Statement> viewResults = new ArrayList<Statement>();
		for(String queryTerm: nounList){
			List<Statement> result = JenaFunctions.queryRegex(queryTerm,"http://ieee.rkbexplorer.com/sparql/");
			viewResults.addAll(result);
		}
		Map<Statement, Integer> mappedResults = JenaFunctions.mapURIs(viewResults);
		
		
		//DBpedia
		List<Statement> viewConceptsResults = new ArrayList<Statement>();
		for(String queryTerm: nounList){
			List<Statement> result = JenaFunctions.queryRegex(queryTerm,"http://dbpedia.org/sparql");
			viewConceptsResults.addAll(result);
		}
		Map<Statement, Integer> mappedConceptsResults = JenaFunctions.mapURIs(viewConceptsResults);
		
		
		//Relaciones en IEEE
		List<Statement> viewAcademicRelatedResults = new ArrayList<Statement>();
		for(String queryTerm: nounList){
			List<Statement> result = JenaFunctions.getRelatedPublications(queryTerm);
			viewAcademicRelatedResults.addAll(result);
		}
		Map<Statement, Integer> mappedAcademidRelatedResults = JenaFunctions.mapURIs(viewAcademicRelatedResults);
		
			
		List<Map<Statement,Integer>> mapList = new ArrayList<Map<Statement,Integer>>();
		
		mapList.add(mappedConceptsResults);
		mapList.add(mappedResults);
		mapList.add(mappedAcademidRelatedResults);
		
		return new ModelAndView("result", "mapList", mapList);
	}
	
	// Request Mapping
	   @RequestMapping(value = "/navigate.htm", method = RequestMethod.POST)
	   @ResponseBody
	   public ResponseEntity<String> processReloadData(@RequestBody String body) {

	     // Get your request
	     JSONObject request = new JSONObject(body);
	     String navigationURI = request.getString("navigationURI");
	     List<Statement> results = JenaFunctions.getNavigationResults(navigationURI,"http://dbpedia.org/sparql");
	     
	     // Get the new data in a JSONObject
	     JSONObject table = new JSONObject();
	     // build the response...
	     JSONArray array = new JSONArray();
	     for(Statement statement:results){
	    	 JSONObject item = new JSONObject();
	    	 item.put("subject", statement.subject.getName());
	    	 item.put("predicate", statement.predicate.getName());
	    	 item.put("object", statement.object.getName());
	    	 
	    	 System.out.println(statement.subject.getName() + " " + statement.predicate.getName() + " " + statement.object.getName());
	    	 
	    	 array.put(item);
	     }
	     
	     table.put("statements",array);
	     
	     // Send the response back to your client
	     HttpHeaders headers = new HttpHeaders();
	     headers.add("Content-Type", "application/json; charset=utf-8");
	     return new ResponseEntity<String>(table.toString(),
	                headers, HttpStatus.OK);
	   }
	
	
}
