function getSimilar(snippetId, queryId) {
  const snippet = $("#".concat(snippetId));
  const postParameters = {
    snippetId: snippetId,
    queryId,
    queryId,
  };
  $.post("/nearest", postParameters, (res) => {
    const results = JSON.parse(res);
    snippet.find(".similar").nextAll().remove();
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
    $(".keyword-toggle").each((e) => {
      highlight($(".keyword-toggle")[e]);
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
    snippet.innerHTML = text;
  });
}

function selectAll() {
  $(".keyword-toggle").each((e) => {
    $(".keyword-toggle")[e].checked = 0;
    $(".keyword-toggle")[e].click();
  });
}

function deselectAll() {
  $(".keyword-toggle").each((e) => {
    $(".keyword-toggle")[e].checked = 1;
    $(".keyword-toggle")[e].click();
  });
}
