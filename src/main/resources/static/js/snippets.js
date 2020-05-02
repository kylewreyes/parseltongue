function getSimilar(snippetId, queryId) {
    const snippet = $("#".concat(snippetId));
    const postParameters = {
        snippetId: snippetId,
        queryId, queryId
    };
    $.post("/nearest", postParameters, res => {
        const results = JSON.parse(res);
        console.log(results);
        results.result.map(elt => {
            snippet.append("<div class='secondary-snippet'>".concat(elt, "</div>"));
        });
    });
}