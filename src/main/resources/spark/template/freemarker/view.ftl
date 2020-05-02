<#assign content>
<div id="app">
    <div id="snippets">
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