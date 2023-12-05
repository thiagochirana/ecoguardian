function submitFormEmEndpoint(endpoint, form){
    form.action = endpoint;
    form.submit();
}

// -------------------------------------------
//      FILTRAGEM DE DENUNCIAS
// -------------------------------------------

function filtrarDenunciasAnalista(){
    let protocolo = $("#protocoloForm").val();
    let municipioId = $("#estadoForm").val();
    let categoriaId = $("#categoriaForm").val();
    let dataOcorrencia = $("#dataOcorrencia").val();
    let dataCadastro = $("#dataCadastro").val();
    let statusDenuncia = $("#statusDenunciaForm").val();
    let tBody = $("#tbodyDenunciasTable");
    tBody.empty();
    getTablePelosFiltros(protocolo,municipioId, categoriaId, dataOcorrencia, dataCadastro, statusDenuncia, tBody);
}

function filtrarDenunciasUser(){
    let protocolo = $("#protocoloFormUser").val();
    let municipioId = $("#estadoFormUser").val();
    let categoriaId = $("#categoriaFormUser").val();
    let dataOcorrencia = $("#dataOcorrenciaUser").val();
    let dataCadastro = $("#dataCadastroUser").val();
    let statusDenuncia = $("#statusDenunciaFormUser").val();
    let tBody = $("#tbodyDenunciasDoUsuarioTable");
    tBody.empty();
    getTablePelosFiltros(protocolo,municipioId, categoriaId, dataOcorrencia, dataCadastro, statusDenuncia, tBody);
}

//cada campo é um valor
function getTablePelosFiltros(protocolo, municipioId, categoriaId, dataOcorrencia, dataCadastro, statusDenuncia, tBody){

    params = '';

    if (protocolo != null && protocolo.trim() !== ''){
        params = adicionarParams(params, 'protocolo='+protocolo)
    }
    if (municipioId != null && municipioId > 0 ){
        params = adicionarParams(params, 'municipioId='+municipioId)
    }
    if (categoriaId != null && categoriaId > 0 ){
        params = adicionarParams(params, 'categoriaId='+categoriaId)
    }
    if (dataOcorrencia != null && dataOcorrencia.trim() !== '' && dataOcorrencia !== 'dd/mm/aaaa'){
        params = adicionarParams(params, 'dataOcorrencia='+formatarData(dataOcorrencia))
    }
    if (dataCadastro != null && dataCadastro.trim() !== '' && dataCadastro !== 'dd/mm/aaaa'){
        params = adicionarParams(params, 'dataCadastro='+formatarData(dataCadastro))
    }
    if (statusDenuncia != null && statusDenuncia !== ''){
        params = adicionarParams(params, 'status='+statusDenuncia)
    }

    urlReq = '/denuncia/filtrar' + params
    denuncias = getListaDenuncias(urlReq);

    renderizarTableDenuncia(denuncias, tBody);
}

function adicionarParams(params, valor){
    if (params !== ''){
        params += '&' + valor;
    } else {
        params += '?' + valor;
    }
    return params
}

function formatarData(dataString){
    let data = new Date(dataString);
    return data.getDate()+1 + "/" + (data.getMonth() + 1) + "/" + data.getFullYear();
}



// -------------------------------------------
//      Comportamento Table Denúncias
// -------------------------------------------

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
    renderizarTableDenuncia(getListaDenuncias('/denuncia/filtrar?verSomenteUsuarioLogado=true'), $("#tbodyDenunciasDoUsuarioTable"));
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



