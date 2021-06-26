<#assign
known = Session.SPRING_SECURITY_CONTEXT??
>

<#if known>
    <#assign
    user = Session.SPRING_SECURITY_CONTEXT.authentication
    name = user.name
    >
<#else>
    <#assign
    name = "unknown"
    isAdmin = false
    >
</#if>