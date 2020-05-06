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

function highlight(keyword) {
  value = keyword.value;
  checked = keyword.checked;
  snippets = $(".snippet, .secondary-snippet");
  snippets.each((e) => {
    ret = "";
    snippet = snippets[e];
    text = snippet.innerHTML;
    var pattern = new RegExp(value, "gi");
    if (checked) {
      text = text.replace(
        pattern,
        "<span style='background-color: yellow;'>" + value + "</span>"
      );
    } else {
      text = text.replace(
        pattern,
        "<span style='background-color: #eee;'>" + value + "</span>"
      );
    }
    console.log(text);
    snippet.innerHTML = text;
  });
}
