function getSimilar(snippetId, queryId) {
  const snippet = $("#".concat(snippetId));
  const postParameters = {
    snippetId: snippetId,
    queryId,
    queryId,
  };
  $.post("/nearest", postParameters, (res) => {
    const results = JSON.parse(res);
    console.log(results);
    snippet.children().eq(3).nextAll().remove();
    results.result.map((elt) => {
      snippet.append(
        "<div class='secondary-snippet'><div class='snippet-score'>Source: ".concat(
          elt.filename,
          ", pg. ",
          elt.page,
          "</div>",
          elt.content,
          "</div>"
        )
      );
    });
  });
}
