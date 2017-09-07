package com.tfm.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.http.client.utils.DateUtils;

@Entity
public class Article {

	@Id
	private String doi;
	
	private String title;
	private String author;
	private String journalTitle;
	
	@Temporal(TemporalType.DATE)
	private Date publicationDate;
	
	private int numberCites;

	public String getDoi() {
		return doi;
	}

	public void setDoi(String doi) {
		this.doi = doi;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getJournalTitle() {
		return journalTitle;
	}

	public void setJournalTitle(String journalTitle) {
		this.journalTitle = journalTitle;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(String publicationDate) {
		this.publicationDate = DateUtils.parseDate(publicationDate, new String[]{"yyyy-MM-dd"});
	}

	public int getNumberCites() {
		return numberCites;
	}

	public void setNumberCites(int numberCites) {
		this.numberCites = numberCites;
	}	
}
