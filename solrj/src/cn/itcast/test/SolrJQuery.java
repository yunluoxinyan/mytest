package cn.itcast.test;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

public class SolrJQuery {
	
	@Test
	public void testQuery() throws Exception {
		HttpSolrServer httpSolrServer = new HttpSolrServer("http://localhost:8180/solr");
		
		SolrQuery query = new SolrQuery();
		
		//设置查询条件
		query.setQuery("钻石");
		
		query.setFilterQueries("product_catalog_name:幽默杂货");
		
		query.setSort("product_price", ORDER.asc);
		
		query.setStart(0);
		
		query.setRows(10);
		
		query.setFields("id","product_name","product_catalog_name","product_price");
		
		query.set("df", "product_keywords");
		
		query.setHighlight(true);
		
		query.addHighlightField("product_name");
		
		query.setHighlightSimplePre("<em>");
		
		query.setHighlightSimplePost("</em>");
		
		QueryResponse response = httpSolrServer.query(query);
		
		SolrDocumentList results = response.getResults();
		
		System.out.println("总条数：" + results.getNumFound());
		
		for (SolrDocument solrDocument : results) {
			System.out.println("id:" + solrDocument.get("id"));
			
			String product_name = "";
			
			Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
			
			List<String> list = highlighting.get(solrDocument.get("id")).get("product_name");
			
			if(list != null ){
				product_name = list.get(0);
			} else {
				product_name = (String) solrDocument.get("product_name");
			}
			System.out.println(product_name);
			System.out.println("product_catalog_name:" + solrDocument.get("product_catalog_name"));
			System.out.println("product_price:" + solrDocument.get("product_price"));
		}
	}
}
