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

//Para o CPF que é carregado na página, o de cima é para input
document.addEventListener('DOMContentLoaded', function() {
    // Função para formatar o CPF
    function formatarCPFPagina() {
        const cpfInput = document.querySelector('.cpf');
        if (cpfInput) {
            const cpfValue = cpfInput.value;
            // Lógica para formatar o CPF
            // Exemplo: 12345678900 -> 123.456.789-00
            // Adapte essa lógica de acordo com a sua necessidade
            cpfInput.value = cpfValue.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, '$1.$2.$3-$4');
        }
    }

    // Chamada da função para formatar o CPF
    formatarCPFPagina();
});

function validarEmailOnExit(input) {
    const email = input.value;
    const formattedEmail = email.replace(/[^a-zA-Z0-9@._%+-]/g, '');

    if (formattedEmail !== email) {
        input.value = formattedEmail;
    }

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(formattedEmail) && formattedEmail.length > 0) {
        const modal = new bootstrap.Modal(document.getElementById('emailInvalidoModal'));
        modal.show();
    }
}


function formatarTelefone(input) {
    var telefone = input.value.replace(/\D/g, '');

    if (telefone.length > 11) {
        telefone = telefone.slice(0, 11);
    }

    if (telefone.length > 0) {
        telefone = telefone.replace(/^(\d{2})/, '($1');
    }
    if (telefone.length > 2) {
        telefone = telefone.replace(/^(\(\d{2})(\d{5})/, '$1) $2');
    }
    if (telefone.length > 7) {
        telefone = telefone.replace(/^(\(\d{2}\)\s\d{5})(\d{4})/, '$1-$2');
    }

    input.value = telefone;
}






function formatarEndereco(inputEndereco){
        inputEndereco.value = inputEndereco.value.replace(/,\s*([^,\s])|,/g, (match, group1) => {
        return group1 ? ', ' + group1 : ',';
    });
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
    inputNormal.value = inputFormatado(inputNormal.value);
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

//Validação dos campos na tela de criar conta (desativar botão)
function verificarCampos() {
    const nome = document.getElementById('nome').value;
    const cpf = document.getElementById('cpf').value;
    const email = document.getElementById('email').value;
    const senha = document.getElementById('senha').value;
    const confirmarSenha = document.getElementById('confirmarSenha').value;
    const telefone = document.getElementById('telefone').value;

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const telefoneRegex = /\(\d{2}\)\s\d{5}-\d{4}/;
    const cpfRegex = /^\d{3}\.\d{3}\.\d{3}-\d{2}$/;

    const camposPreenchidos = nome && cpf && email && senha && confirmarSenha && telefone;
    const emailValido = emailRegex.test(email);
    const telefoneValido = telefoneRegex.test(telefone);
    const cpfValido = cpfRegex.test(cpf);
    const senhasCoincidem = senha === confirmarSenha;

    return camposPreenchidos && emailValido && telefoneValido && cpfValido && senhasCoincidem;
}


//Usa a função de cima para com o retorno dos campos válidos
function habilitarBotaoCadastrar() {
    const botaoCadastrar = document.querySelector('.btn-success'); // Seletor do botão "Cadastrar"
    const camposValidos = verificarCampos();
    if (camposValidos) {
        botaoCadastrar.disabled = false; // Habilita o botão
    } else {
        botaoCadastrar.disabled = true; // Desabilita o botão
    }
}


function habilitarBotaoSalvarDenuncia() {
    const logradouro = document.getElementById('logradouro-den').value.trim();
    const bairro = document.getElementById('bairro-den').value.trim();
    const pontoDeReferencia = document.getElementById('pontoDeReferencia-den').value.trim();
    const numero = document.getElementById('numero-endereco-den').value.trim();
    const estado = document.getElementById('estado').value.trim();
    const municipio = document.getElementById('idIBGE').value.trim();
    const titulo = document.getElementById('titulo').value.trim();
    const descricao = document.getElementById('descricao').value.trim();
    const categoria = document.getElementById('categoria').value.trim();
    const subcategoria = document.getElementById('subcategoria').value.trim();
    const fotos = document.getElementById('formFile').files;

    const camposTextoSaoValidos = [logradouro, bairro, pontoDeReferencia, numero, estado, municipio, titulo, descricao, categoria, subcategoria]
        .every(campo => campo !== '');

    const mensagemErroArquivos = document.getElementById('mensagemErroArquivos');
    const mensagemErroArquivosTipo = document.getElementById('mensagemErroArquivosTipo');

    verificarTiposDeArquivo();

    const fotosSaoValidas = Array.from(fotos).every(file => file.size <= 50 * 1024 * 1024); // 50 MB em bytes

    if (!fotosSaoValidas) {
        mensagemErroArquivos.innerHTML = 'O tamanho total dos arquivos deve ser inferior a 50 MB.';
    } else {
        mensagemErroArquivos.innerHTML = '';
    }

    const botaoSalvar = document.querySelector('.salvar-btn');
    botaoSalvar.disabled = !(camposTextoSaoValidos && mensagemErroArquivos.innerHTML === '' && mensagemErroArquivosTipo.innerHTML === '');
}

// Adiciona o evento de input ao formFile para chamar a função habilitarBotaoSalvarDenuncia
document.getElementById('formFile').addEventListener('input', habilitarBotaoSalvarDenuncia);

function calcularTamanhoArquivos(arquivos) {
    let tamanhoTotal = 0;

    for (const arquivo of arquivos) {
        tamanhoTotal += arquivo.size;
    }

    return tamanhoTotal;
}

function verificarTiposDeArquivo() {
    const formFileInput = document.getElementById('formFile');
    const mensagemErroArquivosTipo = document.getElementById('mensagemErroArquivosTipo');

    const tiposPermitidos = ['image/jpeg', 'image/png'];

    const arquivos = formFileInput.files;
    let tiposInvalidos = false;

    for (const arquivo of arquivos) {
        if (!tiposPermitidos.includes(arquivo.type)) {
            tiposInvalidos = true;
            break;
        }
    }

    if (tiposInvalidos) {
        mensagemErroArquivosTipo.innerHTML = 'Tipos de arquivo inválidos. Apenas arquivos JPEG e PNG são permitidos.';
    } else {
        mensagemErroArquivosTipo.innerHTML = '';
    }
}


function validarSenha() {
    const senha = document.getElementById('senha').value;
    const confirmarSenha = document.getElementById('confirmarSenha').value;
    const msgErroSenha = document.getElementById('msgErroSenha');
    const btnCadastrar = document.getElementById('btnCadastrar');
    const regexSenha = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

    if (senha !== confirmarSenha || senha === '' || confirmarSenha === '') {
        msgErroSenha.textContent = 'As senhas não coincidem ou estão vazias.';
        btnCadastrar.disabled = true;
    } else if (!regexSenha.test(senha)) {
        msgErroSenha.textContent = 'A senha deve conter pelo menos 8 caracteres, incluindo uma letra maiúscula, uma minúscula, um número e um caractere especial.';
        btnCadastrar.disabled = true;
    } else {
        msgErroSenha.textContent = '';
        btnCadastrar.disabled = false;
    }
}





//Usa a função de cima para com o retorno dos campos válidos
function habilitarBotaoCadastrar() {
    const botaoCadastrar = document.querySelector('.btn-success'); // Seletor do botão "Cadastrar"
    const camposValidos = verificarCampos();

    if (camposValidos) {
        botaoCadastrar.disabled = false; // Habilita o botão
    } else {
        botaoCadastrar.disabled = true; // Desabilita o botão
    }
}
// Para o campo de senha
function toggleMostrarSenha(inputId) {
    const input = document.getElementById(inputId);
    if (input.type === 'password') {
        input.type = 'text';
    } else {
        input.type = 'password';
    }
}

document.getElementById('mostrarSenha').addEventListener('click', function() {
    toggleMostrarSenha('senha');
});

document.getElementById('mostrarConfirmarSenha').addEventListener('click', function() {
    toggleMostrarSenha('confirmarSenha');
});



