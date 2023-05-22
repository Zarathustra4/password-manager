<#include "authHeader.ftl">

<form action="/view/passwords/get-password" method="get">
    <input type="text" placeholder="Service Domain" class="form-control" name="serviceDomain" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-sm">

    <button class="btn btn-secondary" type="submit">
        <h5>Get password</h5>
    </button>
</form>

<form action="/view/passwords/store" method="post">
    <input type="text" placeholder="Service Domain" class="form-control" name="serviceDomain" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-sm">
    <input type="text" placeholder="Service Name" class="form-control" name="serviceName" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-sm">
    <input type="password" placeholder="Password" class="form-control" name="password" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-sm">

    <button class="btn btn-secondary" type="submit">
        <h5>Store password</h5>
    </button>
</form>

<form action="/view/passwords/change" method="post">
    <input type="text" placeholder="Service Domain" class="form-control" name="serviceDomain" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-sm">
    <input type="password" placeholder="Password" class="form-control" name="password" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-sm">

    <button class="btn btn-secondary" type="submit">
        <h5>Change password</h5>
    </button>
</form>

<form action="/view/passwords/delete" method="post">
    <input type="text" placeholder="Service Domain" class="form-control" name="serviceDomain" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-sm">

    <button class="btn btn-secondary" type="submit">
        <h5>Delete password</h5>
    </button>
</form>

<form action="/view/services/list" method="get">
    <button class="btn btn-secondary" type="submit">
        <h5>Get services list</h5>
    </button>
</form>

<form action="/view/passwords/analysis" method="get">
    <button class="btn btn-secondary" type="submit">
        <h5>Analyze system</h5>
    </button>
</form>
<style>
    form{
        width: 40%;
        margin: 70px auto 70px auto;
        padding: 30px;
        background-color: darkgray;
    }
</style>
<#include "../footer.ftl">