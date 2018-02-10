package com.smile.dao;

import com.mysql.jdbc.DatabaseMetaData;
import com.smile.dataModel.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LanguageTable {

    private final String dropTable = "DROP TABLE langu ";
	private final String createTable = "CREATE TABLE langu (" +
	    "    lang_no     VARCHAR(1)  "     +
	    "  , lang_na     VARCHAR(8) "     +
        "  , lang_en     VARCHAR(20) " ;

	private final String insertRecord = "insert into language (lang_no , lang_na , lang_en ) " +
			"values(?,?,?)";
	
    private final String basicQuery = new String("select *  from language ");
    private final String rowsQuery = new String("select count(*) as rowCount  from language ");
    
	private final Integer pageSize   = 20;
	
	private Integer recordsOfQuery  = 0;
	private Integer currentRecordNo = 0;
	private Integer currentPageNo   = 0;
	private Integer totalPageOfQuery = 0;
	private Language languRecord   = null;
	private String queryCondition   = new String("");

	private Statement stat = null;
	private ResultSet rs = null;
	private PreparedStatement pst = null;

	private Connection dbConn = null;
	
    public LanguageTable(Connection dbConn) {
    	if (dbConn != null) {
    		this.dbConn = dbConn;
    		existTable();
    	}
    }
    
    public Connection getConnection() {
    		return dbConn;
    }
    
    private boolean existTable() {

        boolean result = false;
		DatabaseMetaData dbm;

		try {
			dbm = (DatabaseMetaData)dbConn.getMetaData();
			// check if "language" table is there
			ResultSet tables = dbm.getTables(null, null, "language", null);

			if (tables.next()) {
				// Table exists
                result = true;
			}
			else {
				// Table does not exist
				// Create language Table
				// createTable();
			}
			
			languRecord = new Language();
		}
		
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return result;
	}
	
	private int createTable()
	{ 
		int result = -1;
	    try 
	    { 
		    	result = 1;
		    	stat = dbConn.createStatement();
		    	stat.executeUpdate(createTable);
	    } 
	    catch(SQLException e) 
	    { 
		    	result = -1;
		    	System.out.println("CreateDB Exception :" + e.toString()); 
	    } 
	    finally 
	    { 
	    		Close(); 
	    } 
	    
	    return(result);
	} 
	
	private int dropTable()
	{ 
		int result = -1;
	    try 
	    { 
		    	result = 1;
		    	stat = dbConn.createStatement();
		    	stat.executeUpdate(dropTable);
	    } 
	    catch(SQLException e) 
	    { 
		    	result = -1;
		    	System.out.println("DropDB Exception :" + e.toString()); 
	    } 
	    finally 
	    { 
	    		Close(); 
	    } 
	    
	    return(result);
	} 

	public int insertOneRecordLanguTable()
	{ 
		int result = -1;
		
        try { 
        		result = 1;
        		pst = dbConn.prepareStatement(insertRecord);
        		pst.setString(1, languRecord.getLangNo());
        		pst.setString(2, languRecord.getLangNa());
        		pst.setString(3, languRecord.getLangEn());
        		pst.executeUpdate();
        		
	    } catch(SQLException e) { 
		    	result = -1 ;
		    	System.out.println("InsertDB Exception :" + e.toString()); 
	    } 
	    finally 
	    { 
	    		Close(); 
	    } 
	    
	    return(result);
	}
	
	// update data 
	public int updateOneRecordLanguTable(String orgLang_no)
	{ 
		int result = -1;
		
		String regex = new String("[0-9]+");
		String updatedbSQL;
		
		if ( (languRecord.getLangNo()==null) || (languRecord.getLangNo().isEmpty()) )
		{
			return(result);
		}
		
		if (!orgLang_no.matches(regex)) {
			return(result);
		}

		updatedbSQL = "UPDATE language set lang_no="+enCloseString(languRecord.getLangNo())
		             +",lang_na="+enCloseString(languRecord.getLangNa())
                     +",lang_en="+enCloseString(languRecord.getLangEn())

		             +" where language.lang_no="+enCloseString(orgLang_no);
        
		try
        { 
			result = 1;
			pst = dbConn.prepareStatement(updatedbSQL);
			pst.executeUpdate();
	    }
	  
	    catch(SQLException e) 
	      { 
	    	result = -1;
	    	System.out.println("UpdateDB Exception :" + e.toString()); 
	      } 
	    finally 
	    { 
	    	Close(); 
	    } 
	    
	    return(result);
	  } 		
	
	// delete data 
	public int deleteOneRecordLanguTable(String orgLang_no)
	{
		int result = -1;
		
		String  regex = new String("[0-9]+");
		String  deletedbSQL;
		
		if ( (languRecord.getLangNo()==null) || (languRecord.getLangNo().isEmpty()) )
		{
			return(result);
		}
		
		if (!orgLang_no.matches(regex)) {
			return(result);
		}
		
		deletedbSQL = "DELETE FROM language WHERE lang_no="+enCloseString(orgLang_no);
        
		try
        { 
			result = 1;
			pst = dbConn.prepareStatement(deletedbSQL);
			pst.executeUpdate(); 
	    }
	  
	    catch(SQLException e) 
	      { 
	    	result = -1;
	    	System.out.println("DeleteDB Exception :" + e.toString()); 
	      } 
	    finally 
	    { 
	    Close(); 
	    } 
        
	    return(result);
	  } 

	// one language by no
	public int getOneRecordLangubyLangno(String orgLang_no)
	{ 
		String stab;
		int    result=-1;
        
		stab = basicQuery
			  +" where language.lang_no="+enCloseString(orgLang_no.trim()) ;
		
	    try
	    {
	      stat = dbConn.createStatement();
	      ResultSet rrs   = stat.executeQuery(stab);
          
	      if (rrs.next()) // lang_no is primary key, so there is only one record or nothing
	      { 
	    	  	result = 1 ;
	    	  	languRecord.setLangNo(rrs.getString("language.lang_no"));
	    	  	languRecord.setLangNa(rrs.getString("language.lang_na"));
            languRecord.setLangEn(rrs.getString("language.lang_en"));
	      }
	      else
	      {
	    	  	languRecord.initiateLangurecord();  // empty the data of languRecord
	      }
	    }
	      
		catch(SQLException e) 
		{ 
			System.out.println("QueryDB Exception :" + e.toString()); 
			languRecord.initiateLangurecord();  // empty the data of languRecord
		} 
	    finally 
	    { 
	    		Close();
	    }
	    
	    return(result);
	}
	
	// one language by name
	public List<Language> selectRecordsLangubyLangname(String langna)
	{
        String temp = new String(queryCondition);
		queryCondition = " where trim(language.lang_na)="+enCloseString(langna.trim()) + queryCondition;
        List<Language> langs = getNextPage();
        queryCondition = temp;
	    return(langs);
	}	

    public List<Language> getCurrentPage() {
    	
    		List<Language> languages = new ArrayList<Language>();    	
		
    		if (currentPageNo<=0) {
            currentPageNo = 1;
		}
		currentRecordNo = (currentPageNo-1) * pageSize;
		
		System.out.println("LanguageTable ---> getCurrentPage() started.");
        
		try {
    			stat = dbConn.createStatement();
    			ResultSet rrs = stat.executeQuery(rowsQuery + queryCondition);
    			if (rrs.next()) {
    				recordsOfQuery = rrs.getInt("rowCount");
    				totalPageOfQuery = recordsOfQuery / pageSize;
    				if ((totalPageOfQuery*pageSize)<recordsOfQuery) {
    					totalPageOfQuery++;
    				}
    				if (currentPageNo>totalPageOfQuery) {
    					currentPageNo = totalPageOfQuery;
    					currentRecordNo = (currentPageNo-1) * pageSize;
    				}

    				String stab = new String("");
    				stab = stab+basicQuery+queryCondition+" limit "
            	           +currentRecordNo.toString()+","
            			   +pageSize.toString();
            	
    				stat = dbConn.createStatement();
    				rs   = stat.executeQuery(stab);
            	
    				if (rs != null) {
    					int i = 0;
    					while (i<pageSize) {
    						if (rs.next()) {
    							Language language = new Language();
    							language.setId(rs.getInt("language.id"));
    							language.setLangNo(rs.getString("language.lang_no"));
    							language.setLangNa(rs.getString("language.lang_na"));
    							language.setLangEn(rs.getString("language.lang_en"));
    							languages.add(language);
    							i++;
    						} else {
    							break;
    						}
    					}
    					if (languages.size()>0) {
    						setLangurecord(languages.get(0));
    					}
    				}
    			}
    			System.out.println("LanguageTable ---> languages.size() = " + languages.size());
        	
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
        
        return languages;
    }
    
    public List<Language> getNextPage() {     
    		currentPageNo++;
        return getCurrentPage();
    }


    public List<Language> getFirstPage() {
        currentPageNo = 1;
        return getCurrentPage();
    }
    
    public List<Language> getPreviousPage() {
    		currentPageNo --;
        return getCurrentPage();
    }

    public List<Language> getLastPage() {
        currentPageNo = totalPageOfQuery;
        return getCurrentPage();
    }

    public void setLangurecord(Language languRecord) {
  	  	this.languRecord.setLangNo(languRecord.getLangNo());
  	  	this.languRecord.setLangNa(languRecord.getLangNa());
        this.languRecord.setLangEn(languRecord.getLangEn());
    }
    
    public Language getLangurecord() {
    		return this.languRecord;
    }
    
    public void setQueryCondition(String condition) {
	    	if (condition==null) {
	    		condition = new String("");
	    	}
	    	this.queryCondition = condition;
    }
    
    public void setCurrentPageNo(Integer currentPageNo) {
    		this.currentPageNo = currentPageNo;
    }
    
    public Integer getCurrentPageNo() {
    		return this.currentPageNo;
    }
    
    public void recalculatePageNo() {
	    	String cc = new String("select count(*) as rowCount from language "+queryCondition);
	    	if (queryCondition.trim()=="") {
	    		cc = cc + " Where lang_no<="+enCloseString(languRecord.getLangNo());
	    	}
	    	else {
	    			cc = cc + " and "+"where lang_no<="+enCloseString(languRecord.getLangNo());
	    	}
	
	    	try {
	    		stat = dbConn.createStatement();
	    		ResultSet rrs = stat.executeQuery(cc); 
	    		if (rrs.next()) {
	    			Integer rQuery = rrs.getInt("rowCount");
	    			currentPageNo = rQuery/pageSize;
	    	    	
	    			if ((currentPageNo*pageSize)==rQuery) {
	    				currentPageNo--;
	    			}
	    		}
	
	    	} catch (SQLException e) {
	    		e.printStackTrace();
	    	}
    }
    
    public void setCurrentRecordNo(Integer currentRecordNo) {
    		this.currentRecordNo = currentRecordNo;
    }
    
    public Integer getCurrentRecordNo() {
    		return this.currentRecordNo;
    } 

	private void Close() 
	  { 
	    try 
	    { 
	      if(rs!=null) 
	      { 
	        rs.close(); 
	        rs = null;
	      } 
	      if(stat!=null) 
	      { 
	        stat.close(); 
	        stat = null;
	      } 
	      if(pst!=null) 
	      { 
	        pst.close(); 
	        pst = null;
	      } 
	    } 
	    catch(SQLException e) 
	    { 
	      System.out.println("Close Exception :" + e.toString()); 
	    }  
	  } 
		
	public int closeConnection()
	{
		int result=-1;
		
	    try 
	    { 
	      if(dbConn != null)
	      { 
	    	  result = 1;
	    	  dbConn.close();
	    	  dbConn = null;
	      } 
	    } 
	    catch(SQLException e) 
	    { 
	      System.out.println("Close Exception :" + e.toString()); 
	    } 
	    
	    return(result);
	}

	    
	private String enCloseString(String sString) {

		String tString = new String("");
		
		tString = sString.replace("\\", "\\\\");
		tString = tString.replace('"', '\"');
		tString = tString.replace("'", "\'");
		tString = tString.replace("\n", "\\n");
		
	 	tString = '"'+tString+'"';
	 	
	  	return tString;
	  }

}

