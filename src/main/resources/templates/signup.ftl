<#include "header.ftl">

<form action="/view/sign-up" method="post">
    <div class="wrapper">
        <div class="form-group">
            <label for="exampleInputEmail1">Username</label>
            <input type="text" class="form-control" id="exampleInputEmail1" name="username" placeholder="Enter username">
        </div>
        <div class="form-group">
            <label for="exampleInputEmail1">Encryption key</label>
            <input type="text" class="form-control" id="exampleInputEmail1" name="encryptionKey" placeholder="Enter encryption key">
        </div>
        <div class="form-group">
            <label for="exampleInputPassword1">Password</label>
            <input type="password" class="form-control" id="exampleInputPassword1" name="password" placeholder="Password">
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
    </div>
</form>
</body>
<style>
    div.wrapper{
        width: 40%;
        margin: 70px auto auto 70px;
    }
</style>

</html>