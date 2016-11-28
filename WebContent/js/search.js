/**
 * 
 */

var dataCount = 0;
var isPaging = false;

function init(){
	$("#resultTableDiv").hide();
	$("#resultCountDiv").hide();
}

        
function search(){
	$("#resultTableDiv").hide();
	$("#resultTable tbody").html("");
	$("#resultcount").html("");
	$(".tcdPageCode").html("");
	var userId=encodeURI(encodeURI("%" + $("#staffId").val() + "%"));
	var pageSize=$("#pageSize").val();
	var limit = '0';
	isPaging = false;
	
	getPageData(userId,limit,pageSize);
}

function getPageData(userId,limit,pageSize){
	$("#resultTable tbody").html("");
	$("#resultTableDiv").hide();
	
    var url = "rest/staff/"+ userId + "/" + limit + "/" + pageSize;
	
    $.getJSON(url,function(data){ 
        $.each(data.data, function(k , v){ 
        	var tdid = "ID_"+ v.id;
            $("#resultTable tbody").append(
            		 "<tr >"+
            		  "<td id='"+ tdid +"'>" + "<font size='3' color='blue'>" +v.id +"</font>"+ "</td>"+
            		  "<td>" + v.name + "</td>"+
            		  "<td>" + v.email + "</td>"+
            		  "<td>" + v.notesid + "</td>"+
            		  "<td>" + v.department + "</td>"+
            		  "<td>" + v.band + "</td>"+
            		  "<td>" + v.jobrole + "</td>"+
            		  "<td>" + v.externaltel + "</td>"+
            		  "<td>" + v.mobiletel + "</td>"+
            		  "</tr>");
            
            var url = "edit.html?id="+v.id;
            $("#"+ tdid).css("cursor","pointer").click(function(){
                window.open(url)
            });

        	$("#resultTableDiv").show();

        });
        dataCount = data.dataCount;
        
        if(!isPaging){
        	$("#resultcount").append("检索到"+dataCount+"条");
        	$("#resultCountDiv").show();
        }
        
        if(!isPaging && dataCount > 0){
            $(".tcdPageCode").createPage({
                pageCount:Math.ceil(dataCount/pageSize),
                current:1,
                backFn:function(p){
                	getPageData(userId,(p-1)*pageSize,pageSize);
                }
            });
            isPaging=true;
        }
    });
}

function searchById() {

	var userId = encodeURI(encodeURI($("#Id").val()));
	
	if ($.trim(userId) == "") return;
	
	var url = "rest/staff/" + userId;

	$("#editMsg").html("");

	$.getJSON(url, function(data) {
		dataCount = data.dataCount;
		if (dataCount > 0) {
			
			var v = data.data[0];
			$("#Name").val(v.name);
			$("#Email").val(v.email);
			$("#NotesId").val(v.notesid);
			$("#Band").val(v.band);
			$("#Department").val(v.department);
			$("#JobRole").val(v.jobrole);
			$("#ExternalTel").val(v.externaltel);
			$("#MobileTel").val(v.mobiletel);

			$("#editMsg").append("<font color='blue' size='2'>*记录存在</font>");
			
		} else {
			$("#editMsg").append("<font color='blue' size='2'>*记录不存在</font>");
		}
	});
}
    

function update(){
	var staff = new Object();
	staff.id = $("#Id").val();
	staff.name = $("#Name").val();
	staff.email = $("#Email").val();
	staff.notesId = $("#NotesId").val();
	staff.department = $("#Department").val();
	staff.band = $("#Band").val();
	staff.jobRole = $("#JobRole").val();
	staff.externalTel = $("#ExternalTel").val();
	staff.mobileTel = $("#MobileTel").val();

	$("#editMsg").html("");
	
	$.ajax({  
		  type:'PUT',      
		  url:"rest/staff",  
		  data:JSON.stringify(staff),
		  contentType : 'application/json',
		  cache:false,  
		  dataType:'json',  
		  success:function(data){
			  var msg;
			  if (parseInt(data.code) > 0){
				  msg = "<font color='blue' size='2'>"+data.msg+"</font>";
			  }else{
				  msg = "<font color='red' size='2'>"+data.msg+"</font>";
			  }
			  $("#editMsg").append(msg);
		  }  
		});
}

