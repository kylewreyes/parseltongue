<#assign content>
<div id="app">
    <div id="snippets">
        <h1>${filename}</h1>
        <h2>Query: "${query}"</h2>
        ${snippets}
    </div>
</div>
</#assign>
<#assign footer>
</#assign>
<#include "main.ftl">