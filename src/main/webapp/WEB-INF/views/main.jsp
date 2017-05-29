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
	
	
	<style>

		#custom-search-input{
		    padding: 3px;
		    border: solid 1px #E4E4E4;
		    border-radius: 6px;
		    background-color: #fff;
		}
		
		#custom-search-input input{
		    border: 0;
		    box-shadow: none;
		}
		
		#custom-search-input button{
		    margin: 2px 0 0 0;
		    background: none;
		    box-shadow: none;
		    border: 0;
		    color: #666666;
		    padding: 0 8px 0 10px;
		    border-left: solid 1px #ccc;
		}
		
		#custom-search-input button:hover{
		    border: 0;
		    box-shadow: none;
		    border-left: solid 1px #ccc;
		}
		
		#custom-search-input .glyphicon-search{
		    font-size: 23px;
		}
		
	</style>
  	
  
  </head>
  <body>
  	<div class="col-sm-12 col-md-12">
  		<h1>Linked Data Browser</h1>
        <form class="navbar-form" role="search" action="search.htm" method="POST">
        <div class="input-group">
            <input type="text" class="form-control" placeholder="Search" name="search">
            <div class="input-group-btn">
                <button class="btn btn-default" type="submit">
                	<i class="glyphicon glyphicon-search"></i>
				</button>
            </div>
        </div>
        </form>
    </div>
  </body>
</html>