<#assign content>
<div id="app">
    <div id="upload">
        <div class="fullpage-form" id="uploadForm">
            <form method="POST" action="/query" onsubmit='loading()'>
                <h1>Query</h1>
                <br/>
                <label for="label">Label:</label>
                <input type="text" name="label" required>
                <br/>
                <label for="keywords">Keywords:</label>
                <input type="text" name="keywords" required>
                <br/>
                <label for="file">Files:</label>
                <br/>
                ${pdfs}
                <button class="button">Query</button>
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