<#assign content>
<div id="view">
    <div id="sideSnippets">
        <h2>Keywords</h2>
        ${keywords}
    </div>
    <div id="mainSnippets">
        <h1>${label}</h1>
        <h2>Query: "${query}"</h2>
        ${snippets}
    </div>
</div>
<script src="/js/snippets.js"></script>
</#assign>
<#assign footer>
</#assign>
<#include "main.ftl">