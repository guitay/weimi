package com.tiancom.pas.qa;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.Field.TermVector;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.Term;

import com.tiancom.pas.common.framework.ibatis.IBaseDAO;
import com.tiancom.pas.common.result.ResultBean;
import com.tiancom.pas.common.result.ResultCommon;
import com.tiancom.pas.common.result.ResultListData;
import com.tiancom.pas.common.result.code.NewBeeCode;
import com.tiancom.pas.lucene.JTidyHtmlHandler;
import com.tiancom.pas.lucene.LucService;
import com.tiancom.pas.lucene.LucServiceImpl;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class QAServiceImpl implements QAService {

	private IBaseDAO ibaseDAO;

	private LucService luService = new LucServiceImpl();

	public IBaseDAO getIbaseDAO() {
		return ibaseDAO;
	}

	public void setIbaseDAO(IBaseDAO ibaseDAO) {
		this.ibaseDAO = ibaseDAO;
	}

	public LucService getLuService() {
		return luService;
	}

	public void setLuService(LucService luService) {
		this.luService = luService;
	}

	public ResultCommon saveQA(QA qa) {
		ResultCommon rc = new ResultCommon(NewBeeCode.SUCCESS);
		try {
			Integer wtid = qa.getWtid();
			
			if (wtid == null) {
//				String wjm = conf+"f:\\lu-outputfile\\" + String.valueOf(System.currentTimeMillis()) + ".html";
//				qa.setWjm(wjm);
				// qa.setWtid(88);//模拟插入后生成wtid
				ibaseDAO.insert("QA_insertQa", qa);
				Document doc = getDocument(qa);
				luService.createIndex(doc);
//				outputHtml(qa.getDnda(), wjm);
			} else {
//				if(qa.getWjm()==null||qa.getWjm().equals("")) {
//					String wjm = "f:\\lu-outputfile\\" + String.valueOf(System.currentTimeMillis()) + ".html";
//					qa.setWjm(wjm);
//				}
				ibaseDAO.updateByPrimaryKey("QA_UpdateQa", qa);
				Document doc = getDocument(qa);
				Term term = new Term("wtid", String.valueOf(qa.getWtid()));
				luService.updateIndex(term, doc);
//				outputHtml(qa.getDnda(), qa.getWjm());
			}
		} catch (Exception e) {
			e.printStackTrace();
			rc = new ResultCommon(NewBeeCode.ERROR);
		}
		return rc;
	}

	private void outputHtml(String content, String outPutfilePath) throws Exception {
		String ftlPath = "com/tiancom/pas/freemaker/content.ftl";
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(ftlPath);
		InputStreamReader ftlReader = new InputStreamReader(is, "utf-8");
		File opf = new File(outPutfilePath);
		if (!opf.exists()) {
			opf.createNewFile();
		}
		FileWriter out = new FileWriter(outPutfilePath);
		Configuration cfg = new Configuration();
		cfg.setDefaultEncoding("UTF-8");

		Template template = new Template("name", ftlReader, cfg);
		Map params = new HashMap();
		params.put("content", content);
		template.process(params, out);
		out.write(content);
	}

	private Document getDocument(QA qa) {
		Document doc = new Document();
		doc.add(new LongField("wtid", qa.getWtid(), Field.Store.YES));
		
		if(qa.getFlid()!=null) {
			doc.add(new LongField("flid", qa.getFlid(), Field.Store.NO));
		}
//		doc.add(new TextField("bzwt", new BufferedReader(new StringReader(qa.getBzwt()))));
		doc.add(new TextField("bzwt", qa.getBzwt(),Store.YES));
		JTidyHtmlHandler jhh = new JTidyHtmlHandler();
		String dnda = jhh.getDocument(qa.getDnda());
		if(qa.getDwda()!=null) {
			String dwda = jhh.getDocument(qa.getDwda());
			doc.add(new TextField("dwda", new BufferedReader(new StringReader(dwda))));
		}
		doc.add(new Field("dnda", dnda, Store.YES, Index.ANALYZED,
                TermVector.WITH_POSITIONS_OFFSETS));
//		doc.add(new TextField("dnda", dnda, Store.YES));
//		doc.add(new TextField("dnda", new BufferedReader(new StringReader(dnda))));
		doc.add(new StringField("wjm", qa.getWjm(), Store.YES));

		return doc;
	}

	public ResultListData<QA> queryByCondition(Integer flid, String bzwt) {
		ResultListData<QA> rld = new ResultListData<QA>(NewBeeCode.SUCCESS);
		try {
			Map map = new HashMap();
			map.put("flid", flid);
			map.put("bzwt", bzwt);
			List<QA> list = ibaseDAO.selectInfoByPara("QA_selectQaByCondition", map);
			rld.setRows(list);
		} catch (Exception e) {
			e.printStackTrace();
			rld = new ResultListData<QA>(NewBeeCode.ERROR);
		}
		return rld;
	}

	public ResultCommon deleteQAByWtid(Integer wtid) {
		ResultCommon rc = new ResultCommon(NewBeeCode.SUCCESS);
		try {
			Map map = new HashMap();
			map.put("wtid", wtid);
			ibaseDAO.deleteByPrimaryKey("QA_deleteQaByKey", map);
		} catch (Exception e) {
			e.printStackTrace();
			rc = new ResultCommon(NewBeeCode.ERROR);
		}
		return rc;
	}

	@Override
	public ResultBean<QA> queryByKey(Integer wtid) {
		ResultBean<QA> rc = new ResultBean(NewBeeCode.SUCCESS);
		try {
			Map map = new HashMap();
			map.put("wtid", wtid);
			QA qa = (QA)ibaseDAO.selectByPrimaryKey("QA_selectByKEY", map);
			rc.setRow(qa);
		} catch (Exception e) {
			e.printStackTrace();
			rc = new ResultBean(NewBeeCode.ERROR);
		}
		return rc;
	}

	public void rebuildAllIndex() {
		List<QA> qas = this.queryByCondition(null, null).getRows();
		for(QA q :qas) {
			Document doc = getDocument(q);
			luService.createIndex(doc);
		}
	}

}
