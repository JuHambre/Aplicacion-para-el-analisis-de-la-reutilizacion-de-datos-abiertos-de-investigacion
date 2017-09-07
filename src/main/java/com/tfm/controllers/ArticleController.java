package com.tfm.controllers;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tfm.models.Article;
import com.tfm.models.ArticleRepository;

@Controller
public class ArticleController {
		
	@Autowired
	ArticleRepository articleRepository;
	
	private final String url = "http://www.ebi.ac.uk/europepmc/webservices/rest/search?query=doi&format=json";
	
	private List<Article> listaArticulos = new ArrayList <Article>();	
	
	@RequestMapping(value="/getData", method=RequestMethod.GET)
	public String getData() throws Exception
	{
		String data = getConnection();
		processData(data);						
		
		return "article";
	}
	
	//Obtenemos la conexion
	public String getConnection() throws Exception
	{
		//Conexion API
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);
		HttpResponse response = client.execute(request);
		
		//Convertimlos la respuesta en String
		String data = EntityUtils.toString(response.getEntity());
		
		return data;
	}
	
	//Procesamos la informacion recibida
	public void processData(String data) throws Exception
	{
		//Convertimos el String en Nodos para su lectura comodamente
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(data);
		
		JsonNode nodeSon = node.get("resultList");
		JsonNode nodeSon1 =nodeSon.get("result");
		
		//Leemos los campos que nos interesan
		for(JsonNode j: nodeSon1)
		{
			//Debemos comprobar que los campos existan o sino explota por null
			if(j.get("doi") != null)
			{
				//Creamos un objeto por cada articulo que luego guardaremos en BD
				Article articulo = new Article();
				
				//Rellenamos los diferentes campos
				articulo.setDoi(j.get("doi").asText(""));
				if(j.get("title") != null)
					articulo.setTitle(j.get("title").asText(""));
				if(j.get("authorString") != null)
					articulo.setAuthor(j.get("authorString").asText(""));
				if(j.get("journalTitle") != null)
					articulo.setJournalTitle(j.get("journalTitle").asText(""));			
				if(j.get("citedByCount") != null)
					articulo.setNumberCites(j.get("citedByCount").asInt(0));			
				if(j.get("firstPublicationDate") != null)	
					articulo.setPublicationDate(j.get("firstPublicationDate").asText(""));
				
				listaArticulos.add(articulo);
			}
		}
		
		//Guardamos en BD
		this.articleRepository.saveAll(listaArticulos);
	}
}
