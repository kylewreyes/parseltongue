<#assign content>
<div id="app">
    <div id="dashboard">
        <h1>Welcome Back, ${loggedIn}!</h1>
        <br/>
        <a href="/upload"><button class="button">Upload</button></a>
        <a href="/query"><button class="button">Query</button></a>
        <h2>Recent Queries:</h2>
        <div id="queries">
            ${queries}
        </div>
        <h2>PDFs:</h2>
        <div id="pdfs">
            ${pdfs}
        </div>
    </div>
</div>
</#assign>
<#include "main.ftl">