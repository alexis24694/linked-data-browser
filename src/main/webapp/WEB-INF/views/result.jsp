<%@ include file="/WEB-INF/views/include.jsp" %>

<!DOCTYPE html>
<html>
  <head>
  	<title>Linked Data Browser</title>
  	
  	<!-- JQUERY -->
  	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  	
  	<!-- BOOTSTRAP-->
  	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
	
	<!-- Optional theme -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
	
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>  	 
  	
  	<script>
	  	var successHandler = function( data, textStatus, jqXHR ) {
	  	  // After success reload table with JavaScript
	  	  $('#navigable-table').empty();
	  	  $('#navigable-table').append('<tr><th>Propiedad</th><th>Valor</th></tr>');
		  for(var i = 0; i < data.statements.length; i++) {
			    var obj = data.statements[i];				
			    $('#navigable-table').append('<tr><td>'+obj.predicate+'</td><td><a href="#" id="navigable-concept" onclick="navigationClick(this)"">'+obj.object+'</a></td></tr>');
			}
	  	};
	
	  	var errorHandler = function( jqXHR, textStatus, errorThrown ) {
	  	  // Error handler. AJAX request failed.
	  	  // 404 or KO from server...
	  	  alert('Something bad happened while reloading the table.');
	  	};
	
	  	var reloadData = function(navigationURI) { 
	  	  // Process your request
	  	  var request = new Object();
	  	  request.navigationURI = navigationURI;
	
	  	  // Make the AJAX call
	  	  jQuery.ajax({
	  	    type       : 'POST',
	  	    url        : 'navigate.htm',
	  	    contentType: 'application/json',
	  	    data       : JSON.stringify(request),
	  	    success    : successHandler,
	  	    error      : errorHandler
	  	  });
	  	};
  	</script>
  	
  	<script>
  	function navigationClick(elem) {
  		reloadData(elem.textContent);
  	}
  	</script>
  	
  	<style>
	table {
	    font-family: arial, sans-serif;
	    border-collapse: collapse;
	    width: 100%;
	}
	
	td, th {
	    border: 1px solid #dddddd;
	    text-align: left;
	    padding: 8px;
	}
	
	tr:nth-child(even) {
	    background-color: #dddddd;
	}
	</style>
  </head>
  
  <body>
	  <div class="col-sm-12 col-md-12">
	    <h1>Resultados</h1>
	    
	    <h1>Conceptos relacionados</h1>
	    
	    <table id="navigable-table">
	    	<tr>
		   		<th>Propiedad</th>
		   		<th>Valor</th>
		   	</tr>
		   	<c:forEach items="${mapList[0]}" var="item">	    	
			    <tr>
			    	<td>${item.key.predicate.name}</td>
			   		<td><a href="#" id="navigable-concept" onclick="navigationClick(this)">${item.key.subject.name}</a></td>			   		
			   	</tr>
			</c:forEach>
	    </table>
	    
	    <h1>Información relevante extraída de repositorios académicos</h1>
	    <table>
	    	<tr>
		   		<th>Recurso</th>
		   		<th>Propiedad</th>
		   		<th>Valor</th>
		   	</tr>
	 		<c:forEach items="${mapList[1]}" var="item">	    	
			    <tr>
			   		<td><a href="">${item.key.subject.name}</a> </td>
			   		<td>${item.key.predicate.name}</td>
			   		<td>${item.key.object.name}</td>
			   	</tr>
			</c:forEach>
		</table>
		
		<h1>Relación con otra publicación</h1>
		
		<c:forEach items="${mapList[2]}" var="item">
			<table>
				<tr>
					<th>Recurso inicial</th>
					<th>Título del recurso inicial</th>
					<th>URL de la publicación</th>
				</tr>
				
				<tr>
					<td>${item.key.subject.uri}</td>
					<td>${item.key.subject.name}</td>
					<td><a href="">${item.key.subject.url}</a></td>
				</tr>
				
			</table>
			
			<table>
				<tr>
					<th>Recurso referenciado</th>
					<th>Título del recurso referenciado</th>
					<th>URL de la publicación</th>
				</tr>
				
				<tr>
					<td>${item.key.object.uri}</td>
					<td>${item.key.object.name}</td>
					<td><a href="${item.key.object.url}">${item.key.object.url}</a></td>
				</tr>
			</table>
		</c:forEach>
		
	 </div>
  </body>
</html>