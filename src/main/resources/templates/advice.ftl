<#include "header.ftl">

    <#if isStrong>
        <h3>The password is strong</h3>
    <#else>
        <h3>The password is weak</h3>
        <h4>Problems</h4>
        <#list descriptions as description>
            <p>${description}</p>
        </#list>
    </#if>


<#include "footer.ftl">