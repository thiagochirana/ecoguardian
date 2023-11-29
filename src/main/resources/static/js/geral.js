function formatarCPF(input) {
    var cpf = input.value.replace(/\D/g, '');

    if (cpf.length >= 3 && cpf.length <= 6) {
        cpf = cpf.replace(/(\d{3})(\d)/, '$1.$2');
    } else if (cpf.length >= 7 && cpf.length <= 9) {
        cpf = cpf.replace(/(\d{3})(\d{3})(\d)/, '$1.$2.$3');
    } else if (cpf.length >= 10) {
        cpf = cpf.replace(/(\d{3})(\d{3})(\d{3})(\d)/, '$1.$2.$3-$4');
    }

    input.value = cpf;
}

function formatarEndereco(inputEndereco){
    let resultado = inputEndereco.value.replace(/,\s*([^,\s])|,/g, (match, group1) => {
        return group1 ? ', ' + group1 : ',';
    });
    inputEndereco.value = resultado;
}

function inputFormatado(value) {
    if (!value) return value;

    const numeroTelefone = value.replace(/[^\d]/g, '');
    const tamanhoNumero = numeroTelefone.length;

    if (tamanhoNumero <= 2) {
        return `(${numeroTelefone}`;
    }

    if (tamanhoNumero <= 6) {
        return `(${numeroTelefone.slice(0, 2)}) ${numeroTelefone.slice(2)}`;
    }

    if (tamanhoNumero <= 11) {
        return `(${numeroTelefone.slice(0, 2)}) ${numeroTelefone.slice(2, 6)}-${numeroTelefone.slice(6)}`;
    }

    return `(${numeroTelefone.slice(0, 2)}) ${numeroTelefone.slice(2, 7)}-${numeroTelefone.slice(7, 11)}`;
}


function formatadorTelefone() {
    const inputNormal = document.getElementById('telefone');
    const inputFormatadoValue = inputFormatado(inputNormal.value);
    inputNormal.value = inputFormatadoValue;
}


let cpfInputs = document.getElementsByClassName("cpf");
for (let i = 0; i < cpfInputs.length; i++) {
    cpfInputs[i].setAttribute("maxlength", "14");
}

//Obter municipios
$(document).ready(function () {
    $('#estado').change(function () {
        var estadoId = $(this).val();
        // Fazer chamada AJAX
        $.get('/municipios/daUF/' + estadoId, function (data) {
            // Limpar e preencher o dropdown de municípios
            var municipioDropdown = $('#idIBGE');
            municipioDropdown.empty();
            console.log(data)
            $.each(data, function (index, municipio) {
                municipioDropdown.append('<option value="' + municipio.idIBGE + '">' + municipio.nome + '</option>');
            });
        });
    });
});

//obter subcategoria da categoria selecionada
$(document).ready(function () {
    $('#categoria').change(function () {
        var categoriaId = $(this).val();
        $.get('/denuncia/categoria/' + categoriaId + '/subcategorias', function (data) {
            var subcategoriasDropdown = $('#subcategoria');
            subcategoriasDropdown.empty();
            console.log(data)
            $.each(data, function (index, subcat) {
                subcategoriasDropdown.append('<option value="' + subcat.idSubcategoria + '">' + subcat.descricao + '</option>');
            });
        });
    });
});

// funcao para add icons automaticamente
function adicionarIconeAosBotoes(classeBtn, classeIcon) {
    let botoes = document.querySelectorAll(classeBtn);
    botoes.forEach(function(botao) {
        let icone = document.createElement("i");
        icone.className = classeIcon;
        botao.appendChild(icone);
    });
}

// Momento que a tela é carregada e é acrescido esses icons nos botoes
document.addEventListener('DOMContentLoaded', function () {
    adicionarIconeAosBotoes('salvar-btn', 'bi bi-floppy-fill');
    adicionarIconeAosBotoes('novo-btn', 'bi bi-file-earmark-plus');
});

// Javascript que se o elemento conter a classe "obrigatorio" ele deixa-o em negrito e adiciona um asterisco vermelho indicando que é obrigatório
document.addEventListener('DOMContentLoaded', function () {
    let elementosObrigatorios = document.querySelectorAll('.obrigatorio');

    elementosObrigatorios.forEach(function (elemento) {
        let strongElement = document.createElement('strong');
        strongElement.textContent = elemento.textContent;

        elemento.innerHTML = '';
        elemento.appendChild(strongElement);

        let asteriscoElement = document.createElement('span');
        asteriscoElement.textContent = '*';
        asteriscoElement.style.color = 'red';

        elemento.appendChild(asteriscoElement);
    });
});

//CEP
document.getElementById('cep-den').addEventListener('input', function (event) {
    let cep = event.target.value;
    cep = cep.replace(/\D/g, '');
    cep = cep.slice(0, 8);
    if (cep.length <= 5) {
        cep = cep.replace(/^(\d{0,5})/, '$1');
    } else {
        cep = cep.replace(/^(\d{5})(\d{0,3})/, '$1-$2');
    }

    event.target.value = cep;
});

//DINAMIZAR TITULOS DE PÁGINAS HTML
document.addEventListener('DOMContentLoaded', function() {
    function updateTitle() {
        let titulo = document.querySelector('h4.titulo-pagina');
        if (titulo) {
            document.title = titulo.innerText + " | EcoGuardian";
        }
    }

    let observer = new MutationObserver(function(mudancas) {
        mudancas.forEach(function(mudou) {
            if (mudou.type === 'childList' && mudou.addedNodes.length > 0) {
                // Se novos elementos foram adicionados, atualizar o título
                updateTitle();
            }
        });
    });

    observer.observe(document.body, { childList: true, subtree: true });

    updateTitle();
});



// ============================================
// OBTER LATITUDE E LONGITUDE
// ============================================
document.getElementById('obterCoordenadasBtn').addEventListener('click', function () {
    verificarPreenchimento();
});

function verificarPreenchimento() {
    const todosPreenchidos =
        document.getElementById('logradouro-den').value.trim() !== '' &&
        document.getElementById('numero-endereco-den').value.trim() !== null &&
        document.getElementById('bairro-den').value.trim() !== '' &&
        document.getElementById('estado-nome').value.trim() !== '' &&
        document.getElementById('municipio-nome').value.trim() !== '';

    if (todosPreenchidos) {
        obterCoordenadas();
    }
}

function obterCoordenadas() {
    // Captura os valores dos campos específicos
    let logradouro = document.getElementById('logradouro-den').value;
    let numero = document.getElementById('numero-endereco-den').value;
    let bairro = document.getElementById('bairro-den').value;
    let estado = document.getElementById('estado-nome').value;
    let municipio = document.getElementById('municipio-nome').value;
    let cep = document.getElementById('cep-den').value;

    //Tirar traço do cep
    cep = cep.replace(/\D/g, '');

    // Cria a string de endereço
    let endereco = "";
    if (logradouro !== null && logradouro.trim() !== ""){
        endereco += `${logradouro} `
    }
    if (numero !== null && numero.trim() !== ""){
        endereco += `${numero}, `
    }
    if (cep !== null && cep.trim() !== ""){
        endereco += `${cep}, `
    }
    if (bairro !== null && bairro.trim() !== ""){
        endereco += `${bairro}, `
    }
    if (municipio !== null && municipio.trim() !== ""){
        endereco += `${municipio}, `
    }
    if (estado !== null && estado.trim() !== ""){
        endereco += `${estado}, `
    }

    endereco += " Brasil"


    console.log(endereco);
    // Faz a requisição para o endpoint
    fetch(`/coordenadas/obter`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ endereco: endereco }),
    })
        .then(response => response.json())
        .then(data => {
            // Atualiza os inputs de latitude e longitude com os valores recebidos
            document.getElementById('latitude').value = data.latitude;
            document.getElementById('longitude').value = data.longitude;
        })
        .catch(error => {
            console.error('Erro ao obter coordenadas:', error);
        });
}

function selecionarNomeMunicipio(){
    let sel = document.getElementById('idIBGE');
    document.getElementById('municipio-nome').value = sel.options[sel.selectedIndex].text;
    verificarPreenchimento();
}

function selecionarNomeEstado(){
    let sel = document.getElementById('estado');
    document.getElementById('estado-nome').value = sel.options[sel.selectedIndex].text;
    verificarPreenchimento();
}
// ============================================
// FIM --- OBTER LATITUDE E LONGITUDE
// ============================================