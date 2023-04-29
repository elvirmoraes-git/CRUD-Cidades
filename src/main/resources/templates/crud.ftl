<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CRUD Cidades</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

</head>
<body>
    <div class="container-fluid">
        <div class="jumbotron mt-5">
            <h1>GERENCIAMENTO DE Cidades</h1>
            <p>UM CRUD PARA CRIAR, ALTERAR, EXCLUIR E LISTAR CIDADES</p>
        </div>

        <#if cidadeAtual??>
            <form action="/alterar" method="POST" class="needs-validation" novalidate>
            <input type="hidden" name="nomeAtual" value="${(cidadeAtual.nome)!}" />
            <input type="hidden" name="estadoAtual" value="${(cidadeAtual.estado)!}" />
        <#else>
            <form action="/criar" method="POST" class="needs-validation" novalidate>
        </#if>

            <div class="form-group">
                <label for="nome">Cidade:</label>
                <input required 
                       value="${(cidadeAtual.nome)!}${nomeInformado!}" 
                       name="nome" 
                       type="text" 
                       class="form-control ${(nome??)?then('is-invalid','')}" 
                       placeholder="Informe o nome da cidade" 
                       id="nome">

                       <div class="invalid-feedback">
                            ${nome!}
                       </div>
            </div>
            <div class="form-group">
                <label for="estado">Estado:</label>
                <input maxlength="2" 
                       required 
                       value="${(cidadeAtual.estado)!}${estadoInformado!}" 
                       name="estado" 
                       type="text" 
                       class="form-control ${(estado??)?then('is-invalid','')}" 
                       placeholder="Informe o estado ao qual a cidade pertence" 
                       id="estado">

                       <div class="invalid-feedback">
                            ${estado!}
                       </div>


            </div>
            <button type="submit" class="btn btn-primary">CRIAR</button>

        <#if cidadeAtual??>
            <form action="/alterar" method="POST">
            <input type="hidden" name="nomeAtual" value="${(cidadeAtual.nome)!}" />
            <input type="hidden" name="estadoAtual" value="${(cidadeAtual.estado)!}" />
        <#else>
            <form action="/criar" method="POST">
        </#if>    

        </form>
        <table class="table table-stripped table-hover mt-5">
            <thead class="thead-dark">
                <tr>
                    <th>Nome</th>
                    <th>Estado</th>
                    <th>Ações</th>
                </tr>
            </thead>
            <tbody>
                <#list listaCidades as cidade>
                <tr>
                    <td>${cidade.nome}</td>
                    <td>${cidade.estado}</td>
                    <td>
                        <div class="d-flex d-justify-content-center">
                            <a href="/preparaAlterar?nome=${cidade.nome}&estado=${cidade.estado}" class="btn btn-warning mr-3">ALTERAR</a>
                            <a href="/excluir?nome=${cidade.nome}&estado=${cidade.estado}" class="btn btn-danger">EXCLUIR</a>
                            
                        </div>
                    </td>
                </tr>
                </#list>
            </tbody>
        </table>
    </div>
</body>
</html>