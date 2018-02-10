package com.smile.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.DatabaseMetaData;
import com.smile.dataModel.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;

public class SongTable {
	
    public final String songNoOrder     = "song.song_no";
    public final String vodNoOrder      = "song.vod_no";
    public final String songNaOrder     = "song.song_na";
    public final String langSongNaOrder = "CONCAT(song.lang_no,song.song_na)";
    public final String langSwordSongNaOrder = 
                    "CONCAT(song.lang_no, LPAD(CONVERT(song.s_num_word,CHAR(2)),2,'0'), song.song_na)";
    public final String langSwordSongNoOrder = 
                    "CONCAT(song.lang_no, LPAD(CONVERT(song.s_num_word,CHAR(2)),2,'0'), song.song_no)";
    public final String singerOrder1 = "sing1.sing_na";
    public final String singerOrder2 = "sing2.sing_na";
	
    private final String dropTable = "DROP TABLE song "; 
    private final String createTable = "CREATE TABLE song (" + 
        "    song_no     VARCHAR(6)  "     + 
        "  , song_na     VARCHAR(45) "     + 
        "  , lang_no     VARCHAR(1)  "     +
        "  , s_num_word  INT(2)      "     +
        "  , num_fw      INT(2)      "     +
        "  , num_pw      VARCHAR(1)  "     +
        "  , sing_no1    VARCHAR(5)  "     +
        "  , sing_no2    VARCHAR(5)  "     +
        "  , sele_tf     VARCHAR(1)  "     +
        "  , chor        VARCHAR(1)  "     +
        "  , n_mpeg      VARCHAR(2)  "     +
        "  , m_mpeg      VARCHAR(2)  "     +
        "  , vod_yn      VARCHAR(1)  "     +
        "  , vod_no      VARCHAR(6)  "     +
        "  , pathname    VARCHAR(6)  "     +
        "  , ord_no      INT(6)      "     +
        "  , order_num   INT(6)      "     +
        "  , ord_old_n   INT(6)      "     +
        "  , in_date     DATE       )" ;

    private final String insertsong = "INSERT INTO song (song_no,song_na,lang_no,s_num_word" +
                    ",num_fw,num_pw,sing_no1,sing_no2,sele_tf,chor,n_mpeg,m_mpeg,vod_yn,vod_no,pathname" +
                    ",ord_no,order_num,ord_old_n,in_date) " +
                    "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private final String basicQuery = "SELECT song.*,langu.lang_na ,sing1.sing_na as sing_na1,sing2.sing_na as sing_na2" +
            "    FROM song" +
            "    LEFT JOIN langu on song.lang_no=langu.lang_no" +
            "    LEFT JOIN singer as sing1 on song.sing_no1=sing1.sing_no" +
            "    LEFT JOIN singer as sing2 on song.sing_no2=sing2.sing_no";
    
    private final String rowsQuery = "SELECT count(*) AS rowCount FROM song ";

    private final Integer pageSize   = 20;

    private Connection dbConn = null;
	
    public SongTable(Connection dbConn) {
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
        ResultSet rs = null;
        try {
            dbm = (DatabaseMetaData)dbConn.getMetaData();
            // check if "song" table is there
            rs = dbm.getTables(null, null, "song", null);

            if (rs.next()) {
                // Table exists
                result = true;
            } else {
                // Table does not exist
                // Create song Table
                // createTable();
            }
        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println(e.toString());
        } finally {
            closeResultSetStatementPreparedStatement(rs,null,null);
        }

        return result;
    }
	
    private int createTable()
    { 
        int result = -1;
        Statement stat = null;
        try {
            result = 0;
            stat = dbConn.createStatement(); 
            stat.executeUpdate(createTable); 
        } 
        catch(SQLException e) {
            result = e.getErrorCode() ;
            System.out.println("CreateDB Exception :" + e.toString()); 
        } 
        finally {
            closeResultSetStatementPreparedStatement(null,stat,null); 
        } 

        return(result);
    } 
	
    private int dropTable()
    { 
        int result = -1;
        Statement stat = null;
        try {
            result = 0;
            stat = dbConn.createStatement(); 
            stat.executeUpdate(dropTable); 
        } 
        catch(SQLException e) {
            result = e.getErrorCode() ;
            System.out.println("DropDB Exception :" + e.toString()); 
        } 
        finally {
            closeResultSetStatementPreparedStatement(null,stat,null);
        } 

        return(result);
    } 

    public int insertOneRecordSongTable(Song song) {
        int result = -1;
        PreparedStatement pst = null;
        try {
            pst = dbConn.prepareStatement(insertsong); 
            pst.setString(1, song.getSongNo()); 
            pst.setString(2, song.getSongNa());
            pst.setInt(3, song.getLanguage().getId());
            pst.setInt(4,song.getSNumWord());
            pst.setInt(5,song.getNumFw());
            pst.setString(6,song.getNumPw());
            pst.setInt(7,song.getSinger1Id());
            pst.setInt(8,song.getSinger2Id());
            pst.setByte(9,song.getSeleTf());
            pst.setString(10,song.getChor());
            pst.setString(11,song.getNMpeg());
            pst.setString(12,song.getMMpeg());
            pst.setString(13,song.getVodYn());
            pst.setString(14,song.getVodNo());
            pst.setString(15,song.getPathname());
            pst.setInt(16,song.getOrdNo());
            pst.setInt(17,song.getOrderNum());
            pst.setInt(18,song.getOrdOldN());
            pst.setDate(19, (java.sql.Date)song.getInDate());
            pst.executeUpdate();
            result = 0;
        }
        catch(SQLException e) {
            result = e.getErrorCode() ;
            System.out.println("InsertDB Exception :" + e.toString()); 
        } 
        finally {
            closeResultSetStatementPreparedStatement(null,null,pst);
        } 

        return(result);
    }
	
    // update data 
    public int updateOneRecordSongTable(String orgSong_no,Song song) {
        int result = -1;
        String regex = "[0-9]+";
        String updatedbSQL;
        if ( (song.getSongNo() == null) || (song.getSongNo().isEmpty()) ) {
            return(result);
        }
        if (!orgSong_no.matches(regex)) {
            return(result);
        }
        updatedbSQL = "UPDATE song set song_no="+enCloseString(song.getSongNo())
                 +",song_na="+enCloseString(song.getSongNa())
                 +",language_id="+song.getLanguage().getId()
                 +",s_num_word="+song.getSNumWord()
                 +",num_fw="+song.getNumFw()
                 +",num_pw="+enCloseString(song.getNumPw())
                 +",sing_no1="+song.getSinger1Id()
                 +",sing_no2="+song.getSinger2Id()
                 +",sele_tf="+song.getSeleTf()
                 +",chor="+enCloseString(song.getChor())
                 +",n_mpeg="+enCloseString(song.getNMpeg())
                 +",m_mpeg="+enCloseString(song.getMMpeg())
                 +",vod_yn="+enCloseString(song.getVodYn())
                 +",vod_no="+enCloseString(song.getVodNo())
                 +",pathname="+enCloseString(song.getPathname())
                 +",ord_no="+song.getOrdNo()
                 +",order_num="+song.getOrderNum()
                 +",ord_old_n="+song.getOrdOldN()
                 +",in_date="+enCloseString(song.getInDate().toString().trim())

                 +" WHERE song.song_no="+enCloseString(orgSong_no);
    				
        			PreparedStatement pst = null;
        try {
            pst = dbConn.prepareStatement(updatedbSQL); 
            pst.executeUpdate();
            result = 0;
        }
        catch(SQLException e) {
            result = e.getErrorCode() ;
            System.out.println("UpdateDB Exception :" + e.toString()); 
        } 
        finally {
            closeResultSetStatementPreparedStatement(null,null,pst);
        } 

        return(result);
    } 		
	
    // delete data 
    public int deleteOneRecordSongTable(String orgSong_no) {
        int result = -1;
        String  regex = "[0-9]+";
        String  deletedbSQL;
        if ( (orgSong_no == null) || (orgSong_no.isEmpty()) ) {
            return(result);
        }
        if (!orgSong_no.matches(regex)) {
            return(result);
        }
        deletedbSQL = "DELETE FROM song WHERE song_no=" + enCloseString(orgSong_no);
        PreparedStatement pst = null;
        try {
            pst = dbConn.prepareStatement(deletedbSQL); 
            pst.executeUpdate(); 
            result = 0;
        }
        catch(SQLException e) {
            result = e.getErrorCode() ;
            System.out.println("DeleteDB Exception :" + e.toString()); 
        } 
        finally {
            closeResultSetStatementPreparedStatement(null,null,pst);
        } 

        return(result);
    } 

    // one song by no
    public Song getOneRecordSongbySongno(String orgSong_no, String byOrder) {
        String stab;
        stab = basicQuery
            +" WHERE song.song_no="+enCloseString(orgSong_no.trim())+" ORDER BY "+byOrder;
        Song song = null;
        Statement stat = null;
        ResultSet rs = null;
        try {
            stat = dbConn.createStatement(); 
            rs   = stat.executeQuery(stab);
            if (rs.next()) { // song_no is primary key, so there is only one record or nothing
                song = new Song();
                song.setSongNo(rs.getString("song.song_no"));
                song.setSongNa(rs.getString("song.song_na"));
                song.setLanguage((Language)rs.getObject("song.language"));
                song.setSNumWord(rs.getInt("song.s_num_word"));
                song.setNumFw(rs.getInt("song.num_fw"));
                song.setNumPw(rs.getString("song.num_pw"));
                song.setSinger1Id(rs.getInt("song.singer1id"));
                song.setSinger1Id(rs.getInt("song.singer1id"));
                song.setSeleTf(rs.getByte("song.sele_tf"));
                song.setChor(rs.getString("song.chor"));
                song.setNMpeg(rs.getString("song.n_mpeg"));
                song.setMMpeg(rs.getString("song.m_mpeg"));
                song.setVodYn(rs.getString("song.vod_yn"));
                song.setVodNo(rs.getString("song.vod_no"));
                song.setPathname(rs.getString("song.pathname"));
                song.setOrdNo(rs.getInt("song.ord_no"));
                song.setOrderNum(rs.getInt("song.order_num"));
                song.setOrdOldN(rs.getInt("song.ord_old_n"));
                song.setInDate(rs.getDate("song.in_date"));	    	  
            }
        } catch(SQLException e) { 
            System.out.println("QueryDB Exception :" + e.toString()); 
        } finally { 
            closeResultSetStatementPreparedStatement(rs,stat,null);
        }

        return(song);
    }
	
    // one song by name
    public List<Song> selectRecordsSongbySongno(String songno,String queryCondition,String byOrder)
    {
        String temp = queryCondition+" trim(song.song_no)=" + enCloseString(songno.trim());
        List<Song> songs = getOnePageOfSongTable(1, temp, byOrder); // from 1st page
        return(songs);
    }

    // one song by name
    public List<Song> selectRecordsSongbySongname(String songna, String queryCondition,String byOrder)
    {
        String temp = queryCondition+" trim(song.song_na)=" + enCloseString(songna.trim());
        List<Song> songs = getOnePageOfSongTable(1, temp, byOrder); // from 1st page
        return(songs);
    }
	
    public Integer getTotalRecordsOfQuery(String queryCondition) {
        Integer totalRecordsOfQuery = 0;
        Statement stat = null;
        ResultSet rs = null;
        try {
            stat = dbConn.createStatement();
            rs = stat.executeQuery(rowsQuery + " "+queryCondition);
            if (rs.next()) {
                totalRecordsOfQuery = rs.getInt("rowCount");
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            closeResultSetStatementPreparedStatement(rs,stat,null);
        }
        return totalRecordsOfQuery;
    }
	
    public Integer getTotalPageOfQuery(String queryCondition) {
        Integer totalPageOfQuery = 0;
        Statement stat = null;
        ResultSet rs = null;
        try {
            stat = dbConn.createStatement();
            rs = stat.executeQuery(rowsQuery + " "+queryCondition);
            if (rs.next()) {
                Integer recordsOfQuery = rs.getInt("rowCount");
                totalPageOfQuery = recordsOfQuery / pageSize;
                if ((totalPageOfQuery*pageSize)<recordsOfQuery) {
                        totalPageOfQuery++;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            closeResultSetStatementPreparedStatement(rs,stat,null);
        }
        return totalPageOfQuery;
    }

    public List<Song> getOnePageOfSongTable(Integer currentPageNo, String queryCondition,String byOrder) {
    	
    	if (currentPageNo <= 0) {
            currentPageNo = 1;
    	}
    	if (queryCondition == null) {
            queryCondition = "";
    	}
    	if ( (byOrder == null) || (byOrder.isEmpty()) ) {
            byOrder = "song.song_no";
    	}
    	
    	List<Song> songs = null;
        Integer totalPageOfQuery = getTotalPageOfQuery(queryCondition);

        if (currentPageNo>totalPageOfQuery) {
            currentPageNo = totalPageOfQuery;
        }
        if (currentPageNo<=0) {
            currentPageNo = 1;
        }
		
        Integer currentRecordNo = (currentPageNo-1) * pageSize;
		
    	String stab = "";
    	stab = stab+basicQuery+" "+queryCondition+" ORDER BY "+byOrder+" LIMIT "
                +currentRecordNo.toString()+","
                +pageSize.toString(); 	
    	Statement stat = null;
    	ResultSet rs = null;
    	try
    	{
            stat = dbConn.createStatement();
            rs   = stat.executeQuery(stab);
            	
            if (rs != null) {
                int i = 0;
                // songs = new ArrayList<Song>();
                songs = new ArrayList<>();
                while (i<pageSize) {
                    if (rs.next()) {
                    		
                        Song song = new Song();
                        song.setSongNo(rs.getString("song.song_no"));
                        song.setSongNa(rs.getString("song.song_na"));
                        song.setLanguage((Language)rs.getObject("song.Language"));
                        song.setSNumWord(rs.getInt("song.s_num_word"));
                        song.setNumFw(rs.getInt("song.num_fw"));
                        song.setNumPw(rs.getString("song.num_pw"));
                        song.setSinger1Id(rs.getInt("singer1id"));
                        song.setSinger2Id(rs.getInt("song.singer2id"));
                        song.setSeleTf(rs.getByte("song.sele_tf"));
                        song.setChor(rs.getString("song.chor"));
                        song.setNMpeg(rs.getString("song.n_mpeg"));
                        song.setMMpeg(rs.getString("song.m_mpeg"));
                        song.setVodYn(rs.getString("song.vod_yn"));
                        song.setVodNo(rs.getString("song.vod_no"));
                        song.setPathname(rs.getString("song.pathname"));
                        song.setOrdNo(rs.getInt("song.ord_no"));
                        song.setOrderNum(rs.getInt("song.order_num"));
                        song.setOrdOldN(rs.getInt("song.ord_old_n"));
                        song.setInDate(rs.getDate("song.in_date"));
                        songs.add(song);
                        
                        i++;
                    } else {
                        i = pageSize;
                    }
                }
            }	
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            closeResultSetStatementPreparedStatement(rs,stat,null);
        }
        
        return songs;
    }

    public List<Song> getLastPageOfsongTable(String queryCondition,String byOrder) {
    	Integer totalPageOfQuery = getTotalPageOfQuery(queryCondition);
        Integer currentPageNo = totalPageOfQuery;
        if (currentPageNo<=0) {
            currentPageNo = 1;
        }
        return getOnePageOfSongTable(currentPageNo, queryCondition,byOrder);
    }
    
    public Integer recalculatePageNo(String queryCondition,String conOrder,int offset,String byOrder,String orgQueryCondition) {

    	Statement stat = null;
    	ResultSet rs = null;
    	Integer currentPageNo = 0;
    	
    	System.out.println("conOrder , byOrder = "+conOrder+" "+byOrder);
    	
    	String cc = basicQuery + " " + queryCondition + " ORDER BY " + conOrder + " ASC LIMIT 1";
    	System.out.println("First query= "+cc);
    	// find the first record of the query in ascending order (eg. song.song_no>"12001")
    	String tempCondition = "";
    	try {
            stat = dbConn.createStatement();
            rs = stat.executeQuery(cc);
            if (rs.next()) {
                if (byOrder.equalsIgnoreCase(songNoOrder)) {
                    String temp = '"'+rs.getString("song.song_no")+'"';
                    tempCondition = " WHERE "+byOrder+" > " + temp;
                } else if (byOrder.equalsIgnoreCase(vodNoOrder)) {
                    String temp = '"'+rs.getString("song.vod_no")+'"';
                    tempCondition = " WHERE "+byOrder+" > " + temp;
                } else if (byOrder.equalsIgnoreCase(songNaOrder)) {
                    String temp = '"'+rs.getString("song.song_na")+'"';
                    tempCondition = " WHERE "+byOrder+" > " + temp;
                } else if (byOrder.equalsIgnoreCase(langSongNaOrder)) {
                    String lang = '"'+rs.getString("song.lang_no")+'"';
                    String temp = '"'+rs.getString("song.song_na")+'"';
                    tempCondition = " WHERE "+byOrder+" > CONCAT("+lang+","+temp+")";
                } else if (byOrder.equalsIgnoreCase(langSwordSongNaOrder)) {
                    String lang = '"'+rs.getString("song.lang_no")+'"';
                    String temp = '"'+rs.getString("song.song_na")+'"';
                    String tmp1 = '"'+String.format("%02d",rs.getInt("song.s_num_word"))+'"';
                    tempCondition = " WHERE "+byOrder+" > CONCAT("+lang+","+tmp1+","+temp+")";
                } else if (byOrder.equalsIgnoreCase(langSwordSongNoOrder)) {
                    String lang = '"'+rs.getString("song.lang_no")+'"';
                    String temp = '"'+rs.getString("song.song_no")+'"';
                    String tmp1 = '"'+String.format("%02d",rs.getInt("song.s_num_word"))+'"';
                    tempCondition = " WHERE "+byOrder+" > CONCAT("+lang+","+tmp1+","+temp+")";
                } else if (byOrder.equalsIgnoreCase(singerOrder1)) {
                    String temp = '"'+rs.getString("sing1.sing_na")+'"';
                    tempCondition = " WHERE sing1.sing_na > " + temp;
                } else if (byOrder.equalsIgnoreCase(singerOrder2)) {
                    String temp = '"'+rs.getString("sing2.sing_na")+'"';
                    tempCondition = " WHERE sing2.sing_na > " + temp;
                }
           }
    	} catch (SQLException e) {
            System.out.println(e.toString());
    	} finally {
            closeResultSetStatementPreparedStatement(rs,stat,null);
    	}
    	
    	cc = rowsQuery + " " + tempCondition;
    	Integer totalRecords = getTotalRecordsOfQuery(orgQueryCondition);
    	try {
            stat = dbConn.createStatement();
            rs = stat.executeQuery(cc); 
            if (rs.next()) {
                Integer rQuery = rs.getInt("rowCount")+offset;
                // because we use song.song_no>"12001" as a query for song_no="12001"
                // and we want to find song_no="12001" exactly or just grater than "12001"
                // if "12001" did not exist
                currentPageNo = (totalRecords - rQuery)/pageSize;
                if ((currentPageNo*pageSize)<(totalRecords-rQuery) ) {
                    currentPageNo++;
                }
            }
    	} catch (SQLException e) {
            System.out.println(e.toString());
    	} finally {
            closeResultSetStatementPreparedStatement(rs,stat,null);
    	}
    	
    	return currentPageNo;
    }

    private void closeResultSetStatementPreparedStatement(ResultSet rs,Statement stat,PreparedStatement pst ) {
        try {
            if(rs != null) {
                rs.close(); 
            } 
            if(stat != null) {
                stat.close(); 
            } 
            if(pst != null) {
                pst.close(); 
            } 
        } catch(SQLException e) {
            System.out.println(e.toString());
        }  
    } 
		
    public int closeConnection() {
        int result=-1;
        try {
            if(dbConn != null) {
                result = 0;
                dbConn.close(); 
                dbConn = null;
            } 
        } catch(SQLException e) {
            result = e.getErrorCode() ;
            System.out.println(e.toString());
        } 

        return(result);
    }
	    
    private String enCloseString(String sString) {
        String tString = sString.replace("\\", "\\\\");
        tString = tString.replace('"', '\"');
        tString = tString.replace("'", "\'");
        tString = tString.replace("\n", "\\n");
        tString = '"'+tString+'"';

        return tString;
    }

    // added on 2017-08-14
    public String findQueryConditionOnSongNo(String orgQuery, String song_no) {
        String qCon = "";
        if (orgQuery != null) {
            qCon = orgQuery;
        }
        song_no = '"' + song_no + '"';
        // to find the first record of the query (especially the exactly one)
        String temp = songNoOrder + ">=" + song_no;
        if (!qCon.isEmpty()) {
            qCon = " WHERE " + qCon + " AND " + temp;
        } else {
            qCon = " WHERE " + temp;
        }
        return qCon;
    }
	
    public String findQueryConditionOnVodNo(String orgQuery, String vod_no) {
        String qCon = "";
        if (orgQuery != null) {
            qCon = orgQuery;
        }
        vod_no = '"' + vod_no + '"';
        // to find the first record of the query (especially the exactly one)
        String temp = vodNoOrder + ">=" + vod_no;
        if (!qCon.isEmpty()) {
            qCon = " WHERE " + qCon + " AND " + temp;
        } else {
            qCon = " WHERE " + temp;
        }
        return qCon;
    }

    public String findQueryConditionOnSongNa(String orgQuery, String song_na) {
        String qCon = "";
        if (orgQuery != null) {
            qCon = orgQuery;
        }
        song_na = '"'+song_na+'"';
        // to find the first record of the query (especially the exactly one)
        String temp = songNaOrder+">="+song_na;
        if (!qCon.isEmpty()) {
            qCon = " WHERE " + qCon + " AND " + temp;
        } else {
            qCon = " WHERE " + temp;
        }
        return qCon;
    }
	
    public String findQueryConditionOnLangSongNa(String orgQuery, String lang_no,String song_na) {
        String qCon = "";
        if (orgQuery != null) {
            qCon = orgQuery;
        }
        // to find the first record of the query (especially the exactly one)
        lang_no = '"'+lang_no+'"';
        song_na = '"'+song_na+'"';
        String temp = langSongNaOrder+">= CONCAT("+lang_no+","+song_na+")";
        if (!qCon.isEmpty()) {
            qCon = " WHERE " + qCon + " AND " + temp;
        } else {
            qCon = " WHERE " + temp;
        }
        return qCon;
    }
	
    public String findQueryConditionOnLangSwordSongNa(String orgQuery, String lang_no,String sword,String song_na) {
        String qCon = "";
        if (orgQuery != null) {
            qCon = orgQuery;
        }
        // to find the first record of the query (especially the exactly one)
        lang_no = '"'+lang_no+'"';
        song_na = '"'+song_na+'"';
        sword = '"'+String.format("%02d", Integer.valueOf(sword))+'"';
        String temp = langSwordSongNaOrder+">=CONCAT("+lang_no+","+sword+","+song_na+")";
        if (!qCon.isEmpty()) {
            qCon = " WHERE " + qCon + " AND " + temp;
        } else {
            qCon = " WHERE " + temp;
        }
        return qCon;
    }
	
    public String findQueryConditionOnLangSwordSongNo(String orgQuery, String lang_no,String sword,String song_no) {
        String qCon = "";
        if (orgQuery != null) {
            qCon = orgQuery;
        }
        // to find the first record of the query (especially the exactly one)
        lang_no = '"'+lang_no+'"';
        song_no = '"'+song_no+'"';
        sword = '"'+String.format("%02d", Integer.valueOf(sword))+'"';
        String temp = langSwordSongNoOrder+">=CONCAT("+lang_no+","+sword+","+song_no+")";
        if (!qCon.isEmpty()) {
            qCon = " WHERE " + qCon + " AND " + temp;
        } else {
            qCon = " WHERE " + temp;
        }
        return qCon;
    }
	
    public String findQueryConditionOnSingNa1(String orgQuery, String sing_na1) {
        String qCon = "";
        if (orgQuery != null) {
            qCon = orgQuery;
        }
        sing_na1 = '"'+sing_na1+'"';
        // to find the first record of the query (especially the exactly one)
        String temp = singerOrder1 + ">=" + sing_na1;
        if (!qCon.isEmpty()) {
            qCon = " WHERE " + qCon + " AND " + temp;
        } else {
            qCon = " WHERE " + temp;
        }
        return qCon;
    }
	
    public String findQueryConditionOnSingNa2(String orgQuery, String sing_na2) {
        String qCon = "";
        if (orgQuery != null) {
            qCon = orgQuery;
        }
        sing_na2 = '"'+sing_na2+'"';
        // to find the first record of the query (especially the exactly one)
        String temp = singerOrder2 + ">=" + sing_na2;
        if (!qCon.isEmpty()) {
            qCon = " WHERE " + qCon + " AND " + temp;
        } else {
            qCon = " WHERE " + temp;
        }
        return qCon;
    }
    //
    
    // added on 2017-08-25
    public Song getDataFromJspView(HttpServletRequest request) {
        Song song = null;
        String tmp = request.getParameter("song_no");
        if (tmp != null) {
            song = new Song();
            song.setSongNo(tmp);
            song.setSongNa(request.getParameter("song_na"));
            // song.setLanguage((Language)request.getParameter("language_id"));
            String temp = request.getParameter("s_num_word");
            if ( (temp == null) || temp.isEmpty()) {
                song.setSNumWord(0);
            }
            else {
                song.setSNumWord(Integer.parseInt(temp));
            }
            temp = request.getParameter("num_fw");
            if ( (temp == null) || temp.isEmpty()) {
                song.setNumFw(0);
            } else {
                song.setNumFw(Integer.parseInt(temp));
            }
            song.setNumPw(request.getParameter("num_pw"));
            song.setSinger1Id(Integer.valueOf(request.getParameter("singer1id")));
            song.setSinger2Id(Integer.valueOf(request.getParameter("singer2id")));
            song.setSeleTf(Byte.valueOf(request.getParameter("sele_tf")));
            song.setChor(request.getParameter("chor"));
            song.setNMpeg(request.getParameter("n_mpeg"));
            song.setMMpeg(request.getParameter("m_mpeg"));
            song.setVodYn(request.getParameter("vod_yn"));
            song.setVodNo(request.getParameter("vod_no"));
            song.setPathname(request.getParameter("pathname"));

            temp = request.getParameter("in_date");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date dd;
            try {
                dd = df.parse(temp);
                song.setInDate(new java.sql.Date(dd.getTime()));
            } catch (ParseException e) {
                System.out.println(e.toString());
                song.setInDate(null);
            }
        }
        return song;
    }
    //
}

