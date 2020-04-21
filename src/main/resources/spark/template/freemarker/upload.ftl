<#assign content>
<div id="app">
    <div id="upload">
        <h1>How do I parse a PDF?</h1>
        <p>
            To parse a PDF, upload it using the form below, and enter your keywords. A keyword is a word or phrase that you are looking for. TODO: Make this better
        </p>
        <div class="fullpage-form" id="uploadForm">
            <form method="POST" action="/upload">
                <h1>Upload PDF</h1>
                <br/>
                <br/>
                <label for="keywords">Keywords:</label>
                <br/>
                <textarea name="keywords"></textarea>
                <br/>
                <label for="file">File:</label>
                <input type="file" name="file">
                <br/>
                <button class="button">Upload</button>
            </form>
        </div>
    </div>
</div>
</#assign>
<#if loggedIn == "0">
    <#include "error.ftl">
<#else>
    <#include "mainLogged.ftl">
</#if>