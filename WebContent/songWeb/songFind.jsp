<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 
	
	<script type="text/javascript" src="../jQuery/jquery-1.11.3.min.js"></script>
	<script type="text/javascript">
	    $( document ).ready(function() { 
	       $('.table form input, .table select').attr('disabled', true);
	       $('input[name="search_type"]:radio').change(function(){
	          $('.table form input, .table select').attr('disabled', true);
	          $(this).parent().find('form').find('input, select').attr('disabled', false);
	          $(this).parent().find('form input, form select').first().focus();
	       }); 
               
               $('input[name="search_type"]:radio').first().prop('checked',true);
               $('input[name="search_type"]:radio').first().trigger('change');
               
               $('form').keydown(function(e) {
                   if (e.keyCode === 13) {
                        toSubmit("Submit");
                   }
               });
               
	    });
            
	    function toSubmit(type){
                var $checkRadio =  $('input[name="search_type"]:checked');
                if ($checkRadio.val() === undefined){
                    if (type.toUpperCase()==="BACK") {
                        $checkRadio = $('input[name="search_type"]:first');
                    }
	        }
                $checkRadio.parent().find('form').attr("action","../song2Controller/Song2FindServlet");
                $checkRadio.parent().find('form').append("<input type='hidden' name='search_type' value='"+$checkRadio.val()+"'>");
                $checkRadio.parent().find('form').append("<input type='hidden' name='submitButton' value='"+type+ "'>");
                $checkRadio.parent().find('form').append('<input type="hidden" name="song2AttributesObject" value="${song2AttributesObject}" readonly >');
                $checkRadio.parent().find('form').submit();
	    }
	</script>
	
	<style>
            body {
                    font-family:"New Roman";
                    font-size:20px;
                    background-color:#d0e4fe;
            }
            h1 {
                    color:green;
                    text-align:center;
            }

	    table {
	        border: 2px solid #000000;
	        width:80%;
	        margin-left: auto;
	        margin-right: auto;
	    }
	    .table label {
	    	font-family:"New Roman";
	        color:blue;
	    }
	    .table label.radio {
	    	font-family:"New Roman";
	    	color:red;
	    }
	    .table select{
	    }
	    td,th {
	    	font-family:"New Roman";
	    	border: 1px solid #000000;
	    }
	    input {
	    	font-family:"New Roman";
	    	font-size:20px;
	    }
	    button {
                font-family:"New Roman";
                font-size:30px;width:200px;
                text-align:left;
                color:yellow;
                background:green;
	    }
	</style>      
	        
	<title>Finding a song</title>
</head>

<body>
    <h1> Find a song</h1>
	
    <table class="table">
        <tr>
            <td>            
                <input type="radio" name="search_type" value='song_no'/><label class="radio">Song NO.</label><br />
                <form action="" method="POST" data-search='song_no'>
                    <label>Song NO:
                        <input name="song_no" type="text" maxlength="6" style='width:120px;' value=""/>
                    </label>
                </form>
            </td>
            <td>
                <input type="radio" name="search_type" value='vod_no'/><label class="radio">VOD NO.</label> <br />
                <form action="" method="POST" data-search='vod_no'>
                    <label>VOD NO:
                        <input name="vod_no" type="text" maxlength="6" style='width:120px;' value=""/>
                    </label>
                </form>                   
            </td>
        </tr>
        <tr>
            <td>
                <input type="radio" name="search_type" value='song_na'/><label class="radio">Song Name</label> <br />
                <form action="" method="POST" data-search='song_na'>
                    <label>Song Name:
                        <input name="song_na" type="text" maxlength="36" style='width:460px;' value=""/>
                    </label>
                </form>
            </td>
            <td>
                <input type="radio" name="search_type" value='lang_songname'/><label class="radio">Language+Song Name</label> <br />
                <form action="" method="POST" data-search='lang_songname'>
                    <label >Language:
                        <select name='lang_no'><option value='1'>1</option><option value='2'>2</option><option value='7'>7</option></select>
                    </label>
                    <br />
                    <label>Song Name:
                        <input name="song_na" type="text" maxlength="36" style='width:460px;' value=""/>
                    </label>
                </form>
            </td>
        </tr>
        <tr>
            <td>
            	<input type="radio" name="search_type" value='singer1_name'/><label class="radio">Singer1 Name</label> <br />
                <form action="" method="POST" data-search='singer1_name'>
                    <label>Singer1 Name:
                        <input name="sing_na1" type="text" maxlength="30" style='width:400px;' value=""/>
                    </label>
                </form>
            </td>
            <td>
                <input type="radio" name="search_type" value='lang_sword_songname'/><label class="radio">Language+Number of Words+Song Name</label> <br />
                <form action="" method="POST" data-search='lang_sword_songname'>
                    <label>Language:
                        <select name='lang_no' data-field=1><option value='1'>1</option><option value='2'>2</option><option value='7'>7</option></select>
                    </label>
                    <label>Number of Words:
                        <select name='sword' data-field=2><option value='1'>1</option><option value='2'>2</option><option value='10'>10</option></select>
                    </label>
                    <br />
                    <label>Song Name:
                        <input name=song_na data-field=3 type="text" maxlength="36" style='width:460px;' value=""/>
                    </label>
                </form>                   
            </td>                
        </tr>
        <tr>
            <td>
            	<input type="radio" name="search_type" value='singer2_name'/><label class="radio">Singer2 Name</label> <br />
                <form action="" method="POST" data-search='singer2_name'>
                    <label>Singer2 Name:
                        <input name="sing_na2" type="text" maxlength="30" style='width:400px;' value=""/>
                    </label>
                </form>
            </td>
            <td>
            	<input type="radio" name="search_type" value='lang_sword_songno'/><label class="radio">Language+Number of Words+Song NO</label> <br />
                <form action="" method="POST" data-search='lang_sword_songno'>
                    <label>Language:
                        <select name='lang_no' data-field=1><option value='1'>1</option><option value='2'>2</option><option value='7'>7</option></select>
                    </label>
                    <label>Number of Words:
                        <select name='sword' data-field=2><option value='1'>1</option><option value='2'>2</option><option value='10'>10</option></select>
                    </label>
                    <br />
                    <label>Song NO:
                        <input name='song_no' data-field=3 type="text" maxlength="6" style='width:120px;' value=""/>
                    </label>
                </form>                   
            </td>                
        </tr>        
    </table>
    <br />
    <br />
    
    <div style='margin:auto;text-align:center'>
        <button type="submit" onclick='toSubmit("Submit")'>Submit</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <button type='submit' onclick='toSubmit("Back")'>Back</button>
    </div>
</body>
</html>