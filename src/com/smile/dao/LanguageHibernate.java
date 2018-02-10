package com.smile.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.query.Query;

import com.smile.dataModel.Language;

public class LanguageHibernate {
	
	private static final Integer pageSize   = 20;
	private SessionFactory factory;
	
	private Integer currentPageNo   = 1;
	
	public LanguageHibernate(SessionFactory factory) {
		currentPageNo = 1;
		this.factory = factory;
	}
	
	public int getCurrentPageNo() {
		return currentPageNo;
	}
	public void setCurrentPageNo(int pageNo) {
		this.currentPageNo = pageNo;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Language> getCurrentPage() {
		
		List<Language> languages = new ArrayList<Language>();
		Session session = null;
		try {
			if (currentPageNo<=0) {
	            currentPageNo = 1;
			}
			
			int recordNo = (currentPageNo-1) * pageSize;
			session = factory.openSession();
			Query query = session.createQuery("from Language");
			query.setFirstResult(recordNo);
			query.setMaxResults(pageSize);
			languages = query.list();
			System.out.println("LanguageHibernate --> getCurrentPage() --> currentPageNo = " + currentPageNo);
			
			if (languages.size() == 0) {
				// no data (may be because beyond the range)
				// go to the last page
				long tPages = (Long)session.createQuery("SELECT count(1) FROM Language").getSingleResult();
				int totalPages = (int)tPages / pageSize;
				if ( (totalPages * pageSize) < (int)tPages) {
					totalPages++;
				}
				currentPageNo = totalPages;
				System.out.println("LanguageHibernate --> getCurrentPage() --> currentPageNo = " + currentPageNo);
				
				recordNo = (currentPageNo-1) * pageSize;
				query.setFirstResult(recordNo);
				query.setMaxResults(pageSize);
				languages = query.list();
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		
		return languages;
	}
	
	public List<Language> getFirstPage() {
		currentPageNo = 1;
		return getCurrentPage();
	}
	
	public List<Language> getPreviousPage() {
		currentPageNo--;
		return getCurrentPage();
	}
	
	public List<Language> getNextPage() {
		currentPageNo++;
		return getCurrentPage();
	}
	
	public List<Language> getLastPage() {
		currentPageNo = Integer.MAX_VALUE / pageSize;
		return getCurrentPage();
	}

}
