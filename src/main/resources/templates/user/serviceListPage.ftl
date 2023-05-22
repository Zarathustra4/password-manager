<#include "authHeader.ftl">
<head>
    <title>Services</title>
</head>

    <#list serviceList as service>
        <div class="service">
            <h3>${service.title}</h3>
            <p>${service.domain}</p>
            <p>${service.description}</p>
        </div>
    </#list>
<style>
    div.service{
        width: 50%;
        background-color: darkgray;
        border-radius: 10px;
        font-family: "Fira Code";
        color: beige;
        margin: 70px auto 70px auto;
        padding: 30px;
    }

</style>
<#include "../footer.ftl">