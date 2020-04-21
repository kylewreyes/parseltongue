<#assign content>
<div id="app">
    <div id="dashboard">
        <h1>Welcome Back, ${loggedIn}!</h1>
        <h2>View saved snippets:</h2>
        <p>
            Some Snippets
        </p>
        
    </div>
</div>
</#assign>
<#if loggedIn == "0">
    <#include "error.ftl">
<#else>
    <#include "mainLogged.ftl">
</#if>