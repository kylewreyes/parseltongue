<#assign content>
<div id="app">
    <div id="upload">
        <div class="fullpage-form" id="uploadForm">
            <form method="POST" action="/upload" enctype='multipart/form-data' onsubmit='loading()'>
                <h1>Upload PDFs</h1>
                <br/>
                <label for="file">File:</label>
                <input type="file" name="file" accept='.pdf' multiple required>
                <br/>
                <button class="button">Upload</button>
            </form>
        </div>
    </div>
</div>
<div id="loading">
    <div class="loader"></div>
<div>
<script type="text/javascript">
    function loading() {
        document.getElementById("loading").style.visibility = 'visible';
        document.getElementById("loading").style.opacity = '1';
    }
</script>
</#assign>
<#include "main.ftl">