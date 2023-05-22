<#include "authHeader.ftl">

    <#list analysisList as analysis>
        <div class="analysis">
            <h3>Password: ${analysis.password}</h3>
            <#if analysis.strong>
                <h3>The password is strong</h3>
                <#else>
                <h3>The password is weak</h3>
            </#if>
            <#if (analysis.similarPasswords?size > 0)>
                <h3>Similar passwords: </h3>
            </#if>
                <#list analysis.similarPasswords as password>
                    <div class = "similar">
                        <p>Service domain: ${password.serviceDomain}</p>
                        <p>Similarity: ${password.similarity}</p>
                        <p>Password: ${password.password}</p>
                    </div>
                </#list>

        </div>
    </#list>
<style>
    div.analysis{
        width: 50%;
        background-color: darkgray;
        border-radius: 10px;
        font-family: "Fira Code", serif;
        color: beige;
        margin: 70px auto 70px auto;
        padding: 30px;
    }
    div.similar{
        background-color: beige;
        color: darkgray;
        padding: 5px;
        border-radius: 5px;
        margin-top: 10px;
    }
</style>
<#include "../footer.ftl">