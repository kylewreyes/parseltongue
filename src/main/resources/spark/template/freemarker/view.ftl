<#assign content>
<div id="view">
    <div id="sideSnippets">
        <h2>Detected Keywords</h2>
        ${keywords}
        <button class="similar" onclick="selectAll()">Select All</button>
        <button class="similar" onclick="deselectAll()">Deselect All</button>
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