<#assign content>
<div id="view">
    <div id="sidepanel">
        <h2>Toggle Keywords</h2>
        <div id="keywords" style="text-align: left; margin-left: 30px;">
            <input type="checkbox" id="circus" name="circus">
            <label for="circus">Circus</label>
            <br/>
            <input type="checkbox" id="circus" name="circus">
            <label for="circus">Big</label>
            <br/>
            <input type="checkbox" id="circus" name="circus">
            <label for="circus">Shelf</label>
            <br/>
            <input type="checkbox" id="circus" name="circus">
            <label for="circus">Book</label>
            <br/>
            <input type="checkbox" id="circus" name="circus">
            <label for="circus">River</label>
            <br/>
            <input type="checkbox" id="circus" name="circus">
            <label for="circus">Cat</label>
            <br/>
            <input type="checkbox" id="circus" name="circus">
            <label for="circus">Matt</label>
            <br/>
            <input type="checkbox" id="circus" name="circus">
            <label for="circus">Laptop</label>
            <br/>
            <input type="checkbox" id="circus" name="circus">
            <label for="circus">Water</label>
            <br/>
            <input type="checkbox" id="circus" name="circus">
            <label for="circus">Electricity</label>
            <br/>
        </div>
    </div>
    <div id="snippets">
        <h1>Snippets</h1>
        <div class="snippet">
            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
        </div>
        <div class="snippet">
            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
        </div>
        <div class="snippet">
            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
        </div>
        <div class="snippet">
            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
        </div>
        <div class="snippet">
            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
        </div>
        <div class="snippet">
            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
        </div>
    </div>
</div>
</#assign>
<#assign footer>
</#assign>
<#if loggedIn == "0">
    <#include "error.ftl">
<#else>
    <#include "mainLogged.ftl">
</#if>