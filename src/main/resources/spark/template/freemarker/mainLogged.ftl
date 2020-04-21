<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8" />
    <title>ParselTongue</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="icon" type="image/png" href="img/logo.png" />
    <link rel="stylesheet" href="css/style.css" />
    <link
      href="https://fonts.googleapis.com/css2?family=Roboto&display=swap"
      rel="stylesheet"
    />
  </head>
  <body>
    <nav>
      <div class="navHolder">
        <a href="/">
          <div id="navLeft">
            <img src="img/logo.png" id="navLogo" />
            <span id="navHeader">Parsel<strong>Tongue</strong></span>
          </div>
        </a>
        <div id="navRight">
          <a href="/dashboard">Dashboard</a>
          <a href="/upload">Upload</a>
          <a href="/logout">Logout</a>
        </div>
      </div>
    </nav>
    ${content}
    <script src="js/jquery-2.1.1.js"></script>
  </body>
</html>
