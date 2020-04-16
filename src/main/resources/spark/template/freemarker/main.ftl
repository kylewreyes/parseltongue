<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <title>ParselTongue</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" type="image/png" href="img/logo.png">
    <#--  <link rel="stylesheet" href="css/normalize.css">
    <link rel="stylesheet" href="css/html5bp.css">  -->
    <link rel="stylesheet" href="css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
</head>
<body>
<nav>
    <div class="navHolder">
        <a href="/">
            <div id="navLeft">
                <img src="img/logo.png" id="navLogo">
                <span id="navHeader">Parsel<strong>Tongue<strong></span>
            </div>
        </a>
        <div id="navRight">
            <a href="/dashboard">Dashboard</a>
            <a href="/dashboard">Upload</a>
            <a href="/signup">Sign Up</a>
        </div>
    </div>
</nav>
<div id="app">
    ${content}
</div>
<footer>
        This project was made by Shalin Patel, Derick Toth, Kyle Reyes, and Nick Young as a final project for CSCI 0320 - Software Engineering at Brown University.
        <br/>
        The source code for this project can be found here.
</footer>
<script src="js/jquery-2.1.1.js"></script>
</body>
</html>