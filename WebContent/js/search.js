/**
 * 
 */

var dataCount = 0;
var isPaging = false;

function init(){
	$("#resultTableDiv").hide();
	$("#resultCountDiv").hide();
}

function updateStatus(chkbox,id){
	var url = "Update?redId="+encodeURI(encodeURI(id)) +"&checked="+encodeURI(encodeURI(chkbox.checked))
	$.ajax({  
		  type:'POST',      
		  url:url,  
		  data:"",  
		  cache:false,  
		  dataType:'json',  
		  success:function(data){  
		  }  
		});
}
        
function search(){
	$("#resultTableDiv").hide();
	$("#resultTable tbody").html("");
	$("#resultcount").html("");
	$(".tcdPageCode").html("");
	var userId=encodeURI(encodeURI($("#userId").val()));
	
	var limit = '0';
	isPaging = false;
	
	getPageData(userId);
}

function getPageData(userId){
	$("#resultTable tbody").html("");
	
    var url = "rest/staff/"+ userId;
	  
    $.getJSON(url,function(data){ 
        $.each(data.data, function(k , v){ 
            $("#resultTable tbody").append(
            		 "<tr >"+
            		  "<td>" + v.id + "</td>"+
            		  "<td>" + v.name + "</td>"+
            		  "<td>" + v.email + "</td>"+
            		  "<td>" + v.notesid + "</td>"+
            		  "<td>" + v.department + "</td>"+
            		  "<td>" + v.band + "</td>"+
            		  "<td>" + v.jobrole + "</td>"+
            		  "<td>" + v.externaltel + "</td>"+
            		  "<td>" + v.mobiletel + "</td>"+
            		  "</tr>");

        });
        dataCount = data.dataCount;
        
    	$("#resultcount").append("检索到"+dataCount+"条");
    	$("#resultCountDiv").show();
        
        if(!isPaging && dataCount > 0){
        	$("#resultTableDiv").show();
            $(".tcdPageCode").createPage({
                pageCount:Math.ceil(dataCount/pageSize),
                current:1,
                backFn:function(p){
                	getPageData(userId);
                }
            });
            isPaging=true;
        }
    });
	
}


