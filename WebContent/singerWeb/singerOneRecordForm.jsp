<input type="hidden" name="singerAttributesObject" value="${singerAttributesObject}" />
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
        <td>Singer No.</td>
        <td>
            <input type="text" maxlength="6"  name="song_no" value="${singer.sing_no}" /> 		      
        </td>
    </tr>
    <tr>
        <td>Singer Name</td>
        <td>
            <input type="text" maxlength="36" name="song_na" value="${singer.sing_na}" />
        </td>
    </tr>
    <tr>
        <td>Num_fw</td>
        <td>
        <input type="text" maxlength="2"  name="num_fw" value="${singer.num_fw}" />
        </td>
    </tr>
    <tr>
        <td>Num_pw</td>
        <td>
            <input type="text" maxlength="1"  name="num_pw" value="${singer.num_pw}" />
        </td>
    </tr>
    <tr>
        <td>Sex</td>
        <td>
            <select id="sex_mf" name="sex">
                <option value="1">1</option>
                <option value="2">2</option>
            </select>
        </td>
    </tr>
    <tr>
        <td>Chor</td>
        <td>
            <select id="chor_yn" name="chor">
                <option value="N">No</option>
                <option value="Y">Yes</option>
            </select>
        </td>
    </tr>
    <tr>
        <td>Hot</td>
        <td>
            <select id="hotYN" name="hot">
                <option value="Y">Yes</option>
                <option value="N">No</option>
            </select>
        </td>
    </tr>
    <tr>
        <td>Area Id</td>
        <td>
            <input type="text" maxlength="1"  name="area_id" value="${singer.area_id}" />
        </td>
    </tr>
    <tr>
        <td>Picture Name</td>
        <td>
            <input type="text" maxlength="5"  name="pic_file" value="${singer.pic_file}" />
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
