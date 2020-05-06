<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8" />
    <title>ParselTongue</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="icon" type="image/png" href="/img/logo.png" />
    <link rel="stylesheet" href="/css/style.css" />
    <link
      href="https://fonts.googleapis.com/css2?family=Roboto&display=swap"
      rel="stylesheet"
    />
  </head>
  <body>
    <nav>
      <div id="navHolder">
        <a href="/">
          <div id="navLeft">
            <img src="/img/logo.png" id="navLogo" />
            <span id="navHeader">Parsel<strong>Tongue</strong></span>
          </div>
        </a>
        <div id="navRight">
        <#if loggedIn == "0">
            <a href="#" onclick="document.getElementById('id01').style.display='block'"
            >Login</a
          >
        <#else>
          <a href="/dashboard">Dashboard</a>
          <a href="/logout">Logout</a>
        </#if>
        </div>
      </div>
    </nav>
     <!-- Login Modal -->
    <div id="id01" class="modal">
      <form class="modal-content animate" action="/login" method="POST">
        <h1 style="text-decoration: underline; margin-bottom: 20px">Login</h1>
        <div class="container">
          <label for="username"><b>Email</b></label>
          <input
            type="text"
            placeholder="Enter Username"
            name="username"
            required
          />
        <br/>
          <label for="password"><b>Password</b></label>
          <input
            type="password"
            placeholder="Enter Password"
            name="password"
            required
          />
        <br/>
          <button class="button" type="submit" >Login</button>
        <br/>
          <a href="/register">Create an Account</a>
        </div>
      </form>
    </div>
    ${content}
    <script>
      var modal = document.getElementById("id01");
      window.onclick = function (event) {
        if (event.target == modal) {
          modal.style.display = "none";
        }
      };
    </script>
    <script src="/js/jquery-2.1.1.js"></script>
  </body>
</html>
