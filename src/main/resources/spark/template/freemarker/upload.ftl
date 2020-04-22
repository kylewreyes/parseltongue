<#assign content>
<div id="app">
    <div id="upload">
        <h1>How do I parse a PDF?</h1>
        <p>
            To parse a PDF, upload it using the form below, and enter your keywords. A keyword is a word or phrase that you are looking for.
        </p>
        <div class="fullpage-form" id="uploadForm">
            <form method="POST" action="/upload" enctype='multipart/form-data'>
                <h1>Upload PDF</h1>
                <br/>
                <br/>
                <label for="query">Query:</label>
                <input type="text" name="query">
                <br/>
                <label for="file">File:</label>
                <input type="file" name="file" accept='.pdf' required>
                <br/>
                <button class="button">Upload</button>
            </form>
        </div>
    </div>
</div>
</#assign>
<#include "main.ftl">