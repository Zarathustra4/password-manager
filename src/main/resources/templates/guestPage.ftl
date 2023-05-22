<#include "header.ftl">
    <head>
        <title>Guest Page</title>
    </head>
    <main>
        <form action="/view/guest/generate" method="get">
            <button class="btn btn-secondary" type="submit">
                <h4>Generate password</h4>
            </button>
        </form>
        <form action="/view/guest/advice" method="get">
            <input type="text" class="form-control" name="password" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-sm">

            <button class="btn btn-secondary" type="submit">
                <h4>Get advice of your password</h4>
            </button>
        </form>
    </main>

<style>
    form{
        width: 40%;
        margin: 70px auto 70px auto;
        padding: 30px;
        background-color: darkgray;
    }
</style>
<#include "footer.ftl">