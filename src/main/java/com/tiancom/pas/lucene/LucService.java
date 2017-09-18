package com.tiancom.pas.lucene;

import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;

import com.tiancom.pas.lucene.bean.SearchResult;

public interface LucService {
	
	public static final String KEY="luc.service"; 

	public void createIndex();

	public void createIndex(Document doc);

	public void deleteAllIndex();

	public void deleteIndex(Term term);

	public void updateIndex();

	public void updateIndex(Term term, Document doc) throws Exception;
	
	public void search(String field, String queryString, int hitsPerPage) throws Exception;
	
	public List<SearchResult> search(String keyword,String[] fields,int hitsPerPage) throws Exception;
}
