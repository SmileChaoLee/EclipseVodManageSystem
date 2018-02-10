<input type="hidden" name="song2AttributesObject" value="${song2AttributesObject}" />
<table border="1">
    <tr>
        <td>
            <span class="errorMsg" style="color:blue">Error Message</span>
        </td>
        <td>
            <span class="errorMsg" style="color:red">${error}</span>
        </td>
    </tr>
    <tr>
        <td>Song No.</td>
        <td>
            <input type="text" maxlength="6"  name="song_no" value="${song2.song_no}" /> 		      
        </td>
    </tr>
    <tr>
        <td>Song Title</td>
        <td>
            <input type="text" maxlength="36" name="song_na" value="${song2.song_na}" />
        </td>
    </tr>
    <tr>
        <td>Language NO.</td>
        <td>
            <input type="text" style="width:100px;" maxlength="1"  name="lang_no" value="${song2.lang_no}" />
            <input type="text" style="width:300px;" name="lang_na" value="${song2.lang_na}" readonly />
        </td>
    </tr>
    <tr>
        <td>Num_Of_word</td>
        <td>
            <input type="text" maxlength="2"  name="s_num_word" value="${song2.s_num_word}" />
        </td>
    </tr>
    <tr>
        <td>Num_fw</td>
        <td>
        <input type="text" maxlength="2"  name="num_fw" value="${song2.num_fw}" />
        </td>
    </tr>
    <tr>
        <td>Num_pw</td>
        <td>
            <input type="text" maxlength="1"  name="num_pw" value="${song2.num_pw}" />
        </td>
    </tr>
    <tr>
        <td>Singer1 No.</td>
        <td>
            <input type="text" style="width:100px;" maxlength="5"  name="sing_no1" value="${song2.sing_no1}" />
            <input type="text" style="width:300px;" name="sing_na1" value="${song2.sing_na1}" readonly />
        </td>
    </tr>
    <tr>
        <td>Singer2 No.</td>
        <td>
            <input type="text" style="width:100px;" maxlength="5"  name="sing_no2" value="${song2.sing_no2}" />
            <input type="text" style="width:300px;" name="sing_na2" value="${song2.sing_na2}" readonly />
        </td>
    </tr>
    <tr>
        <td>Select_tf</td>
        <td>
            <select id="seleYN" name="sele_tf">
                <option value="N">No</option>
                <option value="Y">Yes</option>
            </select>
        </td>
    </tr>
    <tr>
        <td>Chor</td>
        <td>
            <select id="chorYN" name="chor">
                <option value="N">No</option>
                <option value="Y">Yes</option>
            </select>
        </td>
    </tr>
    <tr>
        <td>Music_track</td>
        <td>
            <input type="text" maxlength="2"  name="n_mpeg" value="${song2.n_mpeg}" />                    
        </td>
    </tr>
    <tr>
        <td>Vocal_Track</td>
        <td>
            <input type="text" maxlength="2"  name="m_mpeg" value="${song2.m_mpeg}" />
        </td>
    </tr>
    <tr>
        <td>Vod_yn</td>
        <td>
            <select id="vodYN" name="vod_yn">
                <option value="Y">Yes</option>
                <option value="N">No</option>
            </select>
        </td>
    </tr>
    <tr>
        <td>Vod_no</td>
        <td>
            <input type="text" maxlength="6" name="vod_no" value="${song2.vod_no}" />
        </td>
    </tr>
    <tr>
        <td>PathName</td>
        <td>
            <input type="text" maxlength="6" name="pathname" value="${song2.pathname}" />
        </td>
    </tr>
    <tr>
        <td>In_Date</td>
        <td>
            <input type="date" name="in_date" value="${song2.in_date}" />
        </td>
    </tr>
    <tr>
        <td></td>
        <td style="text-align:center">
            <input class="submit" type="submit" name="submitButton" value="Submit" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <input class="submit" type="submit" name="submitButton" value="Back" />
        </td>
    </tr>
</table>
