<#assign content>
<div id="app">
    <div id="upload">
        <div class="fullpage-form" id="registerForm">
            <form method="POST" action="/register">
                <h1>Register</h1>
                <br/>
                <br/>
                <label for="username">Email:</label>
                <input type="email" name="username">
                <br/>
                <label for="password">Password:</label>
                <input type="password" name="password">
                <br/>
                <button type="submit" class="button">Register</button>
            </form>
        </div>
    </div>
</div>
</#assign>
<#include "main.ftl">