package util;

import java.io.Reader;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.util.CoreMap;

public class LanguageProcessor {
	
public static List<String> processSearchQuery(String searchQuery){
		
		List<String> nounList = getPossibleProperties(searchQuery);
		List<String> lemmatizedSentenceList = getLemmatizedWords(nounList);
		return lemmatizedSentenceList;
	}
	
	public static List<String> getLemmatizedWords(List<String> wordList){
		Properties props = new Properties(); 
        props.put("annotators", "tokenize, ssplit, pos, lemma"); 
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props, false);
        
        System.out.println("Lemma of possible properties");
        
        String fullSentence = "";
        for (String word: wordList){
        	fullSentence = fullSentence + word + " ";
        }
        
        Annotation document = pipeline.process(fullSentence);
        
        List<String> lemmatizedList = new ArrayList<String>();

        for(CoreMap sentence: document.get(SentencesAnnotation.class))
        {    
            for(CoreLabel token: sentence.get(TokensAnnotation.class))
            {           
                String lemma = token.get(LemmaAnnotation.class);
                lemmatizedList.add(lemma);
                System.out.println(lemma);
            }
        }
        return lemmatizedList;
	}
	
	public static List<String> getPossibleProperties(String sentence){
		List<String> nounList = new ArrayList<String>();
        
		MaxentTagger tagger = new MaxentTagger("english-bidirectional-distsim.tagger");
		
		System.out.println("Tagging words");
		
		Reader reader = new StringReader(sentence);
		List<List<HasWord>> sentences = MaxentTagger.tokenizeText(reader);
		for (List<HasWord> s : sentences) {
		      List<TaggedWord> tSentence = tagger.tagSentence(s);
		      for(TaggedWord w: tSentence){
		    	  if(w.tag().equals("NN") || w.tag().equals("NNP") || w.tag().equals("NNS") || w.tag().equals("NNPS")){
		    		  nounList.add(w.word());
		    	  }
		    	  System.out.println(w.word() + " " + w.tag());
		      }
		}
		
		return nounList;
	}
	

}