<#include "../user/authHeader.ftl">

    <form action="/view/services/change" method="post">
        <input type="text" placeholder="Domain" class="form-control" name="domain" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-sm">
        <input type="text" placeholder="Title" class="form-control" name="title" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-sm">
        <input type="text" placeholder="Description" class="form-control" name="description" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-sm">

        <button class="btn btn-secondary" type="submit">
            <h5>Change/Store service</h5>
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