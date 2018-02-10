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

public class SingerTable {

    private final String dropTable = "DROP TABLE singer ";
	private final String createTable = "CREATE TABLE singer (" +
	    "    sing_no     VARCHAR(5)  "     +
	    "  , sing_na     VARCHAR(14) "     +
	    "  , num_fw      INT(2)      "     +
	    "  , num_pw      VARCHAR(1)  "     +
        "  , sex             VARCHAR(1)  "     +
	    "  , chor           VARCHAR(1)  "     +
        "  , hot             VARCHAR(1)  "     +
	    "  , area_ty       VARCHAR(1)  "     +
	    "  , pic_file       VARCHAR(5)  " ;

	private final String insertRecord = "insert into singer (sing_no , sing_na , num_fw , num_pw " +
            ", sex , chor , hot , area_ty , pic_file) " +
			"values(?,?,?,?,?,?,?,?,?)";
	
    private final String basicQuery = new String("select singer.* , singarea.area_na" +
            "    from singer" +
            "    join singarea on singer.area_ty=singarea.area_ty ") ;
    private final String rowsQuery = new String("select count(*) as rowCount" +
            "    from singer" +
            "    join singarea on singer.area_ty=singarea.area_ty ") ;
    
	private final Integer pageSize   = 20;

	private Integer recordsOfQuery  = 0;
	private Integer currentRecordNo = 0;
	private Integer currentPageNo   = 0;
	private Integer totalPageOfQuery = 0;
	private Singer singerRecord   = null;
	private String queryCondition   = new String("");

	private Statement stat = null;
	private ResultSet rs = null;
	private PreparedStatement pst = null;
	
	private Connection dbConn = null;
	
    public SingerTable(Connection dbConn) {
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
			// check if "singer" table is there
			ResultSet tables = dbm.getTables(null, null, "singer", null);

			if (tables.next()) {
				// Table exists
                result = true;
			}
			else {
				// Table does not exist
				// Create singer Table
				// createTable();
			}
			
			singerRecord = new Singer();
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

	public int insertOneRecordSingerTable()
	{ 
		int result = -1;
		
        try
        { 
          result = 1;
		
	      pst = dbConn.prepareStatement(insertRecord);
	      
	      pst.setString(1, singerRecord.getSingNo());
	      pst.setString(2, singerRecord.getSingNa());
	      pst.setInt(3,singerRecord.getNumFw());
	      pst.setString(4,singerRecord.getNumPw());
	      pst.setString(5,singerRecord.getSex());
	      pst.setString(6,singerRecord.getChor());
	      pst.setString(7,singerRecord.getHot());
          pst.setString(8,singerRecord.getPicFile());

	      pst.executeUpdate();
	    }
	  
	    catch(SQLException e) 
	      { 
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
	public int updateOneRecordSingerTable(String orgSing_no)
	{ 
		int result = -1;
		
		String regex = new String("[0-9]+");
		String updatedbSQL;
		
		if ( (singerRecord.getSingNo()==null) || (singerRecord.getSingNo().isEmpty()) )
		{
			return(result);
		}
		
		if (!orgSing_no.matches(regex)) {
			return(result);
		}

		updatedbSQL = "UPDATE singer set sing_no="+enCloseString(singerRecord.getSingNo())
		             +",sing_na="+enCloseString(singerRecord.getSingNa())
		             +",num_fw="+String.valueOf(singerRecord.getNumFw())
		             +",num_pw="+enCloseString(singerRecord.getNumPw())
		             +",sex="+enCloseString(singerRecord.getSex().trim())
		             +",chor="+enCloseString(singerRecord.getChor())
		             +",hot="+enCloseString(singerRecord.getHot())
		             +",pic_file="+enCloseString(singerRecord.getPicFile())
		             +" where singer.sing_no="+enCloseString(orgSing_no);
        
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
	public int deleteOneRecordSingerTable(String orgSing_no)
	{
		int result = -1;
		
		String  regex = new String("[0-9]+");
		String  deletedbSQL;
		
		if ( (singerRecord.getSingNo()==null) || (singerRecord.getSingNo().isEmpty()) )
		{
			return(result);
		}
		
		if (!orgSing_no.matches(regex)) {
			return(result);
		}
		
		deletedbSQL = "DELETE FROM singer WHERE sing_no="+enCloseString(orgSing_no);
        
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

	// one sing by no
	public int getOneRecordSingerbySingno(String orgSing_no)
	{ 
		String stab;
		int    result=-1;
        
		stab = basicQuery
			  +" where singer.sing_no="+enCloseString(orgSing_no.trim()) ;
		
	    try
	    {
	      stat = dbConn.createStatement();
	      ResultSet rrs   = stat.executeQuery(stab);
          
	      if (rrs.next()) // sing_no is primary key, so there is only one record or nothing
	      { 
		    	  result = 1 ;
		    	  singerRecord.setSingNo(rrs.getString("singer.sing_no"));
		    	  singerRecord.setSingNa(rrs.getString("singer.sing_na"));
		    	  singerRecord.setNumFw(rrs.getInt("singer.num_fw"));
		    	  singerRecord.setNumPw(rrs.getString("singer.num_pw"));
		    	  singerRecord.setSex(rrs.getString("singer.sex"));
		    	  singerRecord.setChor(rrs.getString("singer.chor"));
		    	  singerRecord.setHot(rrs.getString("singer.hot"));
		    	  singerRecord.setPicFile(rrs.getString("singer.pic_file"));
	      }
	      else
	      {
	    	  	  singerRecord.initiateSingerrecord();  // empty the data of singerRecord
	      }
	    }
	      
		catch(SQLException e) 
		{ 
			System.out.println("QueryDB Exception :" + e.toString()); 
	    	singerRecord.initiateSingerrecord();  // empty the data of singerRecord
		} 
	    finally 
	    { 
	    	Close();
	    }
	    
	    return(result);
	}
	
	// one sing by name
	public List<Singer> selectRecordssingerbySingername(String singna)
	{
        String temp = new String(queryCondition);
		queryCondition = " where trim(singer.sing_na)="+enCloseString(singna.trim()) + queryCondition;
        List<Singer> sings = getNextPageOfSingerTable();
        queryCondition = temp;
	    return(sings);
	}	

    public List<Singer> getNextPageOfSingerTable() {
    	List<Singer> sings = new ArrayList<Singer>();
    	
		currentPageNo++;
		if (currentPageNo<=0) {
            currentPageNo = 1;
		}
		currentRecordNo = (currentPageNo-1) * pageSize;
		
        try {
    		stat = dbConn.createStatement();
    		ResultSet rrs = stat.executeQuery(rowsQuery+queryCondition);
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
            				Singer singer = new Singer();
            				singer.setSingNo(rs.getString("singer.sing_no"));
            				singer.setSingNa(rs.getString("singer.sing_na"));
            				singer.setNumFw(rs.getInt("singer.num_fw"));
            				singer.setNumPw(rs.getString("singer.num_pw"));
            				singer.setSex(rs.getString("singer.sex"));
            				singer.setChor(rs.getString("singer.chor"));
            				singer.setHot(rs.getString("singer.hot"));
            				singer.setPicFile(rs.getString("singer.pic_file"));

            				sings.add(singer);
        			
            				i++;
            			}
            			else {
            				i = pageSize;
            			}
            		}

                    if (sings.size()>0) {
                        setSingerrecord(sings.get(0));
                    }
            	}
        	}
        	
        } catch (SQLException e) {
            e.printStackTrace();
            return sings;
        }
        
        return sings;
    }


    public List<Singer> getFirstPageOfSingerTable() {
        currentPageNo = 0;
        return getNextPageOfSingerTable();
    }
    
    public List<Singer> getPreviousPageOfSingerTable() {
    	currentPageNo -= 2;
    	return getNextPageOfSingerTable();
    }

    public List<Singer> getLastPageOfSingerTable() {
        currentPageNo = totalPageOfQuery -1;
        System.out.println(currentPageNo);
        return getNextPageOfSingerTable();
    }

    public void setSingerrecord(Singer singerRecord) {
  	  	this.singerRecord.setSingNo(singerRecord.getSingNo());
  	  	this.singerRecord.setSingNa(singerRecord.getSingNa());
  	  	this.singerRecord.setNumFw(singerRecord.getNumFw());
  	  	this.singerRecord.setNumPw(singerRecord.getNumPw());
  	  	this.singerRecord.setSex(singerRecord.getSex());
  	  	this.singerRecord.setChor(singerRecord.getChor());
  	  	this.singerRecord.setHot(singerRecord.getHot());
  	  	this.singerRecord.setPicFile(singerRecord.getPicFile());
    }
    
    public Singer getSingerrecord() {
    		return this.singerRecord;
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
	    	String cc = new String("select count(*) as rowCount from singer "+queryCondition);
	    	if (queryCondition.trim()=="") {
	    		cc = cc + " Where sing_no<="+enCloseString(singerRecord.getSingNo());
	    	}
	    	else {
			cc = cc + " and "+"where sing_no<="+enCloseString(singerRecord.getSingNo());
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

