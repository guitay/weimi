package com.tiancom.pas.lucene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.search.vectorhighlight.BaseFragmentsBuilder;
import org.apache.lucene.search.vectorhighlight.FastVectorHighlighter;
import org.apache.lucene.search.vectorhighlight.FieldQuery;
import org.apache.lucene.search.vectorhighlight.FragListBuilder;
import org.apache.lucene.search.vectorhighlight.FragmentsBuilder;
import org.apache.lucene.search.vectorhighlight.ScoreOrderFragmentsBuilder;
import org.apache.lucene.search.vectorhighlight.SimpleFragListBuilder;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.tiancom.pas.lucene.bean.SearchResult;

public class LucServiceImpl implements LucService {

	// private String indexPath;

//	private String indexPath = "f:/lu-index";
//	private String docsPath = "f:/lu-docs";
	
	private LuceneConfiguration conf;

	public LuceneConfiguration getConf() {
		return conf;
	}

	public void setConf(LuceneConfiguration conf) {
		this.conf = conf;
	}

	public void createIndex() {
		final File indexDir = new File(conf.getIndexPath());
		final File docDir = new File(conf.getDocPath());
		if (checkIndexAndDocDir(indexDir, docDir)) {
			Date start = new Date();
			try {
				System.out.println("Indexing to directory '" + conf.getIndexPath() + "'...");
				Directory dir = FSDirectory.open(new File(conf.getIndexPath()));
				Analyzer analyzer = new CJKAnalyzer(Version.LUCENE_47);
				IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_47, analyzer);
				iwc.setOpenMode(OpenMode.CREATE);
				IndexWriter writer = new IndexWriter(dir, iwc);
				indexDocs(writer, docDir);
				writer.close();

				Date end = new Date();
				System.out.println(end.getTime() - start.getTime() + " total milliseconds");
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println(" caught a " + e.getClass() + "\n with message: " + e.getMessage());
			}
		}
	}

	public void createIndex(Document doc) {
		try {
			System.out.println("----->indexPath:"+conf.getIndexPath());
			Directory dir = FSDirectory.open(new File(conf.getIndexPath()));
			Analyzer analyzer = new CJKAnalyzer(Version.LUCENE_47);
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_47, analyzer);
			iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
			IndexWriter writer = new IndexWriter(dir, iwc);
			writer.addDocument(doc);
			writer.commit();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void indexDocs(IndexWriter writer, File file) throws IOException {
		if (file.canRead()) {
			if (file.isDirectory()) {
				String[] files = file.list();
				if (files != null) {
					for (int i = 0; i < files.length; i++) {
						indexDocs(writer, new File(file, files[i]));
					}
				}
			} else {
				FileInputStream fis = null;
				try {
					fis = new FileInputStream(file);

					Document doc = new Document();
					Field pathField = new StringField("path", file.getPath(), Field.Store.YES);
					doc.add(pathField);
					doc.add(new LongField("modified", file.lastModified(), Field.Store.NO));
					doc.add(new TextField("contents", new BufferedReader(new InputStreamReader(fis, "UTF-8"))));

					if (writer.getConfig().getOpenMode() == OpenMode.CREATE) {
						System.out.println("adding" + file);
						writer.addDocument(doc);
					} else {
						System.out.println("updating " + file);
						writer.updateDocument(new Term("path", file.getPath()), doc);
					}

				} catch (FileNotFoundException fnfe) {
					fnfe.printStackTrace();
					return;
				} finally {
					fis.close();
				}
			}
		}
	}

	public void deleteAllIndex() {
		try {
			Directory dir = FSDirectory.open(new File(conf.getIndexPath()));
			Analyzer analyzer = new CJKAnalyzer(Version.LUCENE_47);
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_47, analyzer);
			iwc.setOpenMode(OpenMode.CREATE);
			IndexWriter writer = new IndexWriter(dir, iwc);
			writer.deleteAll();
			writer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteIndex(Term term) {
		try {
			Directory dir = FSDirectory.open(new File(conf.getIndexPath()));
			Analyzer analyzer = new CJKAnalyzer(Version.LUCENE_47);
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_47, analyzer);
			iwc.setOpenMode(OpenMode.CREATE);
			IndexWriter writer = new IndexWriter(dir, iwc);
			writer.deleteDocuments(term);
			writer.commit();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void updateIndex() {
		final File indexDir = new File(conf.getIndexPath());
		final File docDir = new File(conf.getDocPath());
		if (checkIndexAndDocDir(indexDir, docDir)) {
			Date start = new Date();
			try {
				System.out.println("Indexing to directory '" + conf.getIndexPath() + "'...");
				Directory dir = FSDirectory.open(new File(conf.getIndexPath()));
				// Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_47);
				Analyzer analyzer = new CJKAnalyzer(Version.LUCENE_47);
				IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_47, analyzer);
				iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);

				IndexWriter writer = new IndexWriter(dir, iwc);
				indexDocs(writer, docDir);
				writer.close();

				Date end = new Date();
				System.out.println(end.getTime() - start.getTime() + " total milliseconds");

			} catch (IOException e) {
				System.out.println(" caught a " + e.getClass() + "\n with message: " + e.getMessage());
			}
		}
	}

	public void updateIndex(Term term, Document doc) {
		try {
//			this.deleteIndex(term);
			Directory dir = FSDirectory.open(new File(conf.getIndexPath()));
			Analyzer analyzer = new CJKAnalyzer(Version.LUCENE_47);
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_47, analyzer);
			iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
			IndexWriter writer = new IndexWriter(dir, iwc);
			writer.updateDocument(term, doc);
			writer.commit();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean checkIndexAndDocDir(final File indexDir, final File docDir) {
		if (!indexDir.exists() || !indexDir.canRead()) {
			System.out.println("Index directory '" + indexDir.getAbsolutePath()
					+ "' does not exist or is not readable, please check the path");
			return false;
		}
		if (!docDir.exists() || !docDir.canRead()) {
			System.out.println("Document directory '" + docDir.getAbsolutePath()
					+ "' does not exist or is not readable, please check the path");
			return false;
		}

		return true;
	}

	@Override
	public void search(String field, String queryString, int hitsPerPage) throws Exception {
		Analyzer analyzer = new CJKAnalyzer(Version.LUCENE_47);
		QueryParser parser = new QueryParser(Version.LUCENE_47, field, analyzer);
		Query query = parser.parse(queryString);
		FastVectorHighlighter highLighter = this.getHighlighter();
		FieldQuery fieldQuery = highLighter.getFieldQuery(query);
		
		IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(conf.getIndexPath())));
		IndexSearcher searcher = new IndexSearcher(reader);
		
		System.out.println("------------------------------------------------------------");
		System.out.println("Searching for: " + query.toString(field));

		Date start = new Date();
		TopDocs results = searcher.search(query, null, hitsPerPage);
		Date end = new Date();
		System.out.println("Time: " + (end.getTime() - start.getTime()) + "ms");
//		doPagingSearch(results, searcher, hitsPerPage);
		doPagingSearch(results, searcher,fieldQuery,highLighter, hitsPerPage, queryString, analyzer);
		reader.close();
	}

	public void search(Map<String, String> keyWords, int hitsPerPage) throws Exception{
		IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(conf.getIndexPath())));
		IndexSearcher searcher = new IndexSearcher(reader);
		// :Post-Release-Update-Version.LUCENE_XY:
		// Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_47);
		Analyzer analyzer = new CJKAnalyzer(Version.LUCENE_47);

		Iterator it = keyWords.entrySet().iterator();
		BooleanQuery bq = new BooleanQuery();
		
		FastVectorHighlighter highLighter = this.getHighlighter();
		FieldQuery fieldQuery = highLighter.getFieldQuery(bq);
		
		System.out.println("------------------------------------------------------------");
		while (it.hasNext()) {
			Entry en = (Entry) it.next();
			String field = (String) en.getKey();
			String keyword = (String) en.getValue();
			QueryParser parser = new QueryParser(Version.LUCENE_47, field, analyzer);
			Query query = parser.parse(keyword);
			bq.add(query, Occur.SHOULD);
			System.out.println("Searching for: field=" + field + ";keyword=" + keyword);
		}

		Date start = new Date();
		TopDocs results = searcher.search(bq, null, hitsPerPage);
		Date end = new Date();
		System.out.println("Time: " + (end.getTime() - start.getTime()) + "ms");
//		doPagingSearch(results, searcher,fieldQuery,highLighter, hitsPerPage, null);
		reader.close();
	}


	public List<SearchResult> search(String keyword,String[] fields,int hitsPerPage) throws Exception{
		Analyzer analyzer = new CJKAnalyzer(Version.LUCENE_47);
		QueryParser queryParser = new MultiFieldQueryParser(Version.LUCENE_42,
				fields, analyzer);
		Query query = queryParser.parse(keyword);
		
		IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(conf.getIndexPath())));
		IndexSearcher searcher = new IndexSearcher(reader);
		
		Date start = new Date();
		TopDocs results = searcher.search(query, null, hitsPerPage);
		Date end = new Date();
		System.out.println("Time: " + (end.getTime() - start.getTime()) + "ms");
		
		ScoreDoc[] hits = results.scoreDocs;
		int numTotalHits = results.totalHits;
		System.out.println(numTotalHits + " total matching documents");
		
		List<SearchResult> srs = new ArrayList<SearchResult>();
		
		for (ScoreDoc scoreDoc:hits) {
			System.out.println("############################");
			Document doc = searcher.doc(scoreDoc.doc);
			String wtid = doc.get("wtid");
			String bzwt = doc.get("bzwt");
			String dnda = doc.get("dnda");
			String wjm = doc.get("wjm");
			
			System.out.println(" wjm: " + wjm);
			bzwt = this.highLighter(query, analyzer, "bzwt", bzwt);
			System.out.println(" Bzwt: " + bzwt);
			dnda = this.highLighter(query, analyzer, "dnda", dnda);
			System.out.println(scoreDoc.doc +" dnda:"+ dnda);
			
			SearchResult sr = new SearchResult();
			sr.setId(wtid);
			sr.setContent(dnda);
			sr.setTitle(bzwt);
			sr.setPath(wjm);
			srs.add(sr);
		}
		reader.close();
		
		return srs;
	}

	
	/**
	 * 高亮显示
	 * @param query 查询对象
	 * @param analyzer 分析器
	 * @param fieldName 索引字段
	 * @param cont 索引内容
	 * @return 索引高亮处理结果
	 * @throws Exception
	 */
	private String highLighter(Query query,Analyzer analyzer,String fieldName, String cont) throws Exception{
		SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter(
				"<span class=\"keyword-highlight\">",
				"</span>");
		Highlighter highlighter = new Highlighter(simpleHTMLFormatter,
				new QueryScorer(query));
//		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_42);
		String[] str=highlighter.getBestFragments(analyzer, fieldName, cont, 1);
		
		return str==null||str.length<=0?cont:str[0];
	}

	
	
	
	private void doPagingSearch(TopDocs results, IndexSearcher searcher,FieldQuery fieldQuery,FastVectorHighlighter highLighter, int hitsPerPage, String keyword, Analyzer analyzer) throws Exception {
		ScoreDoc[] hits = results.scoreDocs;
		int numTotalHits = results.totalHits;
		System.out.println(numTotalHits + " total matching documents");
		
		for (ScoreDoc scoreDoc:hits) {
			System.out.println("############################");
			Document doc = searcher.doc(scoreDoc.doc);
//			String snippet = highLighter.getBestFragment(fieldQuery, searcher.getIndexReader(), scoreDoc.doc, "dnda",50);
			String path = doc.get("wjm");
			System.out.println(" path: " + path);
			String bzwt = highLighter.getBestFragment(fieldQuery, searcher.getIndexReader(), scoreDoc.doc, "bzwt",50);
			System.out.println(" Bzwt: " + bzwt);
			String dnda = highLighter.getBestFragment(fieldQuery, searcher.getIndexReader(), scoreDoc.doc, "dnda",50);
			System.out.println(scoreDoc.doc +" dnda:"+ dnda);
		}
	}

	private FastVectorHighlighter getHighlighter() {
		FragListBuilder fragListBuilder = new SimpleFragListBuilder();
		FragmentsBuilder fragmentsBuilder = new ScoreOrderFragmentsBuilder(BaseFragmentsBuilder.COLORED_PRE_TAGS,
				BaseFragmentsBuilder.COLORED_POST_TAGS);
		return new FastVectorHighlighter(true, true, fragListBuilder, fragmentsBuilder);
	}

	public void myAnalyzer() {
		try {
			File file = new File("f:\\lu-docs\\bbb.txt");
			FileReader stopWords = new FileReader(file);
			Reader reader = new FileReader(file);
			Analyzer a = new CJKAnalyzer(Version.LUCENE_47);
			// Analyzer a = new ChineseAnalyzer();
			TokenStream ts = a.tokenStream("", reader);

			OffsetAttribute offsetAttribute = ts.getAttribute(OffsetAttribute.class);
			CharTermAttribute charTermAttribute = ts.addAttribute(CharTermAttribute.class);
			ts.reset();
			int n = 0;
			while (ts.incrementToken()) {
				int startOffset = offsetAttribute.startOffset();
				int endOffset = offsetAttribute.endOffset();
				String term = charTermAttribute.toString();
				n++;
				System.out.println("词条" + n + "的内容为 ：" + term);
			}
			System.out.println("== 共有词条 " + n + " 条 ==");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void myAnalyzer2() {
		try {
			// File file = new File("f:\\lu-docs\\bbb.txt");
			// FileReader stopWords = new FileReader(file);
			Reader reader = new StringReader("abcde 测试答案内容");
			Analyzer a = new CJKAnalyzer(Version.LUCENE_47);
			// Analyzer a = new ChineseAnalyzer();
			TokenStream ts = a.tokenStream("", reader);

			OffsetAttribute offsetAttribute = ts.getAttribute(OffsetAttribute.class);
			CharTermAttribute charTermAttribute = ts.addAttribute(CharTermAttribute.class);
			ts.reset();
			int n = 0;
			while (ts.incrementToken()) {
				int startOffset = offsetAttribute.startOffset();
				int endOffset = offsetAttribute.endOffset();
				String term = charTermAttribute.toString();
				n++;
				System.out.println("词条" + n + "的内容为 ：" + term);
			}
			System.out.println("== 共有词条 " + n + " 条 ==");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 获取一个高亮器
	 */
	private Highlighter getHighlighter(String searchKey,String fieldName) throws Exception
	{
		Formatter formatter = new SimpleHTMLFormatter("<span>", "</span>"); 
		Analyzer analyzer = new CJKAnalyzer(Version.LUCENE_47);
		QueryParser qp = new QueryParser(Version.LUCENE_36,fieldName, analyzer);
		Query query= qp.parse(searchKey);
        QueryScorer fragmentScorer = new QueryScorer(query);
        
        Highlighter highlighter = new Highlighter(formatter, fragmentScorer);  
        Fragmenter fragmenter = new SimpleSpanFragmenter(fragmentScorer);
        highlighter.setTextFragmenter(fragmenter);
        
        return highlighter;
	}
	

	public static void main(String[] args) throws Exception {
		LucServiceImpl ls = new LucServiceImpl();
		// QAService qs = new QAServiceImpl();
		// QA qa = new QA();
		// qa.setBzwt("这是一个问题标题");
		// qa.setDnda("<span>abc de</span><br><span>测试答案内容</span>");
		// qa.setDwda("<span>abcde</span><br><span>测试对外对外对外答案内容112233445566
		// String</span>");
		// qa.setFlid(1111);
		// qs.saveQA(qa);
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("dwda", "当今银行业");
//		map.put("dnda", "域名");
//		map.put("bzwt", "域名");
//		ls.search(map, 10);
		String[] fields = {"bzwt","dnda"};
		ls.search("域名",fields , 10);
//		ls.search("dnda", "域名", 10);

		// ls.myAnalyzer2();

	}


}
