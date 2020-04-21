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
        <button class="button">Learn More</button>
        <button class="button">Getting Started</button>
    </div>

    <hr>

    <div id="about">
        <h1>What is ParselTongue?</h1>
        <p>
            ParselTongues essential function is to make your research process as easy as possible, eliminating long hours spent parsing though long documents just to find the specific sections relevant to your inquiry.

            Here’s some more info on ParselTongue. Its super cool. This is filler text and shouldn’t be kept in the final document. Also this text is too clumped. Not easy to read.
        </p>
        <h1>How To Use ParselTongue</h1>
        <p>
            ParselTongue is engineered to be as easy to use as possible. Just provide us a link to your document on the upload page and we’ll do the heavy lifting. 
        </p>
        <h1>How Does ParselTongue Work?</h1>
        <p>
            ParselTongue is built with a PankRank algorithm.
        </p>
    </div>
</div>
<footer>
    This project was made by Shalin Patel, Derick Toth, Kyle Reyes, and Nick Young as a final project for CSCI 0320 - Software Engineering at Brown University.
    <br/>
    The source code for this project can be found here.
</footer>
</#assign>
<#if loggedIn == "0">
    <#include "mainUnlogged.ftl">
 <#else>
    <#include "mainLogged.ftl">
</#if>
