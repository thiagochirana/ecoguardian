function submitFormEmEndpoint(endpoint, form){
    form.action = endpoint;
    form.submit();
}

// -------------------------------------------
//      Comportamento Table Denúncias
// -------------------------------------------

function getTablePelosFiltros(){
    let protocolo = $("#protocoloForm");
    let municipioId = $("#estadoForm");
    let categoriaId = $("#categoriaForm");
    let dataOcorrencia = $("#dataOcorrencia");
    let dataCadastro = $("#dataCadastro");
    let statusDenuncia = $("#statusDenunciaForm");
}

function getListaDenuncias(urlComParams){
    let request = new XMLHttpRequest();
    request.open("GET", urlComParams, false);  // Definindo para síncrono
    request.send();

    if (request.status === 200) {
        return JSON.parse(request.responseText);
    } else if (request.status === 204){
        return [];
    } else {
        console.error("Deu erro na requisição:", request.status, request.statusText);
        return [];
    }
}

function carregarTableAoAcessaPagina(){
    console.log('Renderizando table de denuncias para Analista ou Admin')
    renderizarTableDenuncia(getListaDenuncias('/denuncia/filtrar'), $("#tbodyDenunciasTable"));
}

function carregarTableSuasDenuncias(){
    console.log('Renderizando table de denuncias do usuario...')
    let tbody = $("#tbodyDenunciasDoUsuarioTable");
    console.log("Tbody Element da table de denuncias do Usuario:", tbody);

    // renderizarTableDenuncia(getListaDenuncias('/denuncia/filtrar?verSomenteUsuarioLogado=true', tbody));
    renderizarTableDenuncia(getListaDenuncias('/denuncia/filtrar?verSomenteUsuarioLogado=true'), tbody);
}

function renderizarTableDenuncia(json, tableBody){
    console.log(json)
    try{
        tableBody.empty();
    } catch (e){
        console.log('Deu ruim em tentar limpar a tbody, talvez esteja vazia...');
    }

    json.denuncias.forEach(function (denuncia) {
        let row = $("<tr>")
        row.append("<td>" + denuncia.protocolo + "</td>");

        if (json.usuarioTemAcessoTotal || json.usuarioIsAdminOuAnalista){
            if (denuncia.sigilo){
                row.append("<td>Anônimo</td>");
            } else {
                row.append("<td>"+denuncia.nomeDenunciante+"</td>");
            }
        }

        row.append("<td>"+denuncia.titulo+"</td>")
            .append("<td>"+denuncia.nomeMunicipio+"</td>")
            .append("<td>"+denuncia.nomeEstado+"</td>")
            .append("<td>"+denuncia.dataHoraAbertura+"</td>")
            .append("<td>"+denuncia.nomeStatusDenuncia+"</td>")

        let tdButtons = $('<td>');

        let divDFlex = $('<div>', { 'class' : 'd-flex' });
        let divColAuto1 = $('<div>', { 'class' : 'col-md-auto' });
        let divColAuto2 = $('<div>', { 'class' : 'col-md-auto' });
        let divColAuto3 = $('<div>', { 'class' : 'col-md-auto' });

        let btnVisualizarDen = renderizarBtnTable('/denuncia/'+denuncia.id+'/visualizar','bi bi-eye','Visualizar Denúncia');
        let btnVisualizarRegistros = renderizarBtnTable('/denuncia/'+denuncia.id+'/registro/historico','bi bi-search', 'Visualizar Registros');
        let btnRegistroIniciarAnalise = renderizarBtnTable('/denuncia/'+denuncia.id+'/registro/adicionarComentario','bi bi-play-fill', 'Iniciar Análise');
        let btnRegistroAdicionarComent = renderizarBtnTable('/denuncia/'+denuncia.id+'/registro/adicionarComentario','bi bi-chat-dots', 'Adicionar Comentário');

        divColAuto1.append(btnVisualizarDen);
        divColAuto2.append(btnVisualizarRegistros);

        if (denuncia.precisaIniciar) {
            if (json.usuarioTemAcessoTotal || json.usuarioIsAdminOuAnalista){
                divColAuto3.append(btnRegistroIniciarAnalise);
            } else {
                divColAuto3.append(btnRegistroAdicionarComent);
            }
        } else {
            divColAuto3.append(btnRegistroAdicionarComent);
        }

        divDFlex.append(divColAuto1);
        divDFlex.append(divColAuto2);
        divDFlex.append(divColAuto3);

        tdButtons.append(divDFlex);
        row.append(tdButtons);
        tableBody.append(row);
    });

}

function renderizarBtnTable(url, classIcon, textoBotao){
    var spanElement = $('<span>', {
        'class': 'd-inline-block',
        'tabindex': '0',
        'data-bs-toggle': 'popover',
        'data-bs-trigger': 'hover focus',
        'data-bs-content': textoBotao
    });

    var aElement = $('<a>', {
        'href': url,
        'type': 'submit',
        'class': 'btn btn-outline-success me-2 sem-borda'
    });

    var iElement = $('<i>', { 'class': classIcon });

    aElement.append(iElement);
    spanElement.append(aElement);

    return spanElement;
}



