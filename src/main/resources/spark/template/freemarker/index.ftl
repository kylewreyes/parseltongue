<#assign content>
<div id="app">
    <div id="jumbotron">
        <div id="jumbotronImage">
            <img src="img/front.png"/>
        </div>
        <div id="jumbotronText">
            Research paper parsing done <strong>right</strong>.
        </div>
    </div>
    <div id="buttons">
        <#if loggedIn == "0">
        <button href="#" onclick="document.getElementById('id01').style.display='block'" class="button">Get Started</button>
        <#else>
        <a href="/dashboard"><button class="button">Dashboard</button></a>
        </#if>
    </div>

    <hr>

    <div id="about">
        <h1>What is ParselTongue?</h1>
        <p>
            Across all research disciplines, huge amounts of time and money are spent reading. While much of this reading is constructive, a good amount of it is also repetitive, becoming a huge sink for both energy and resources.
        </p>
        <p>
            ParselTongue was designed to vastly expedite the research process, eliminating long hours spent reading though long documents just to find the specific sections relevant to you. We've engineered the perfect solution to skimming articles - just tell us what you're looking to learn from the paper, and we'll show you the parts that are most relevant to you!
        </p>
        <h1>How Does ParselTongue Work?</h1>
        <p>
            First, we take your PDF and split it into paragraphs. Then, we apply the PageRank algorithm on these paragraphs to return the paragraphs most relevant to your query. We store the PDF and "snippets" for your perusal later, so that you can manage your research with us.
        </p>
    </div>
</div>
<footer>
    This project was made by Shalin Patel, Derick Toth, Kyle Reyes, and Nick Young as a final project for CSCI 0320 - Software Engineering at Brown University.
    <br/>
    The source code for this project can be found here.
</footer>
</#assign>
<#include "main.ftl">
