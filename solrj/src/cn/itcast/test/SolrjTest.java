package cn.itcast.test;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Before;
import org.junit.Test;

public class SolrjTest {
	
	HttpSolrServer httpSolrServer = null;
	
	@Before
	public void init() {
		String baseURL = "http://localhost:8180/solr";
		
		httpSolrServer = new HttpSolrServer(baseURL);
	}
	
	@Test
	public void testCreateAndUpdateIndex() throws Exception{
		
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", "001");
		document.addField("content", "Hello World!");
		
		httpSolrServer.add(document);
		
		httpSolrServer.commit();
	}
	
	@Test
	public void testDeleteIndex() throws Exception{
		
		httpSolrServer.deleteByQuery("*:*");
		//httpSolrServer.deleteById("001");
		
		httpSolrServer.commit();
	}
	
	@Test
	public void testQuery() throws Exception{
		SolrQuery query = new SolrQuery();
		query.setQuery("*:*");
		
		QueryResponse response = httpSolrServer.query(query);
		
		SolrDocumentList results = response.getResults();
		
		System.out.println("总条数" + results.getNumFound());
		
		for (SolrDocument solrDocument : results) {
			System.out.println("=====================");
			System.out.println("id:" + solrDocument.get("id"));
			System.out.println("content:" + solrDocument.get("content"));
		}
	}
}
