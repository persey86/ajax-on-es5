<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
    <title>ErrorPage</title>
</head>
<body>


 <div class="container">
     <div class="jumbotron alert-danger">

         <h1>Oops. Something went wrong</h1>

     </div>

     <div>
         <a class="button info shadow-hover" href="<c:url value="/"/>">Home page</a>
     </div>

 </div>
</body>
</html>
