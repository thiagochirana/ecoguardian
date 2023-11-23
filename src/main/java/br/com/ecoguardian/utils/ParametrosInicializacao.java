package br.com.ecoguardian.utils;

import br.com.ecoguardian.models.Categoria;
import br.com.ecoguardian.models.Denuncia;
import br.com.ecoguardian.models.Subcategoria;
import br.com.ecoguardian.models.Usuario;
import br.com.ecoguardian.models.enums.TipoPerfil;
import br.com.ecoguardian.models.records.DenunciaJSON;
import br.com.ecoguardian.models.records.NovoUsuarioJSON;
import br.com.ecoguardian.services.CategoriaService;
import br.com.ecoguardian.services.DenunciaService;
import br.com.ecoguardian.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ParametrosInicializacao {

    Log LOG = new Log(ParametrosInicializacao.class);

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private DenunciaService denuncias;

    @Autowired
    private CategoriaService categorias;

    public void validarEPopularBancoPrimeiraInicializacao(){
        LOG.info("Primeira inicialização, vou popular o banco com dados padrões");
        if (categorias.listar().isEmpty()){
            // FAUNA
            Categoria fauna = new Categoria();
            fauna.setNome("FAUNA");

            fauna.adicionarSubcategoria(new Subcategoria("Do transporte e comercialização de animais abatidos de forma ilegal", fauna));
            fauna.adicionarSubcategoria(new Subcategoria("Pesca ilegal, predatória ou por meio de explosivos, ou substâncias que em contato com " +
                    "a água produzem efeito semelhante. Assim como, transportar ou comercializar espécies " +
                    "provenientes de tais atos.", fauna));
            fauna.adicionarSubcategoria(new Subcategoria("Caça ilegal ou predatória, de animais em extinção ou fora de época, bem como entrar " +
                    "em locais de conservação portando instrumentos próprios para a atividade.", fauna));
            fauna.adicionarSubcategoria(new Subcategoria("Ferir, praticar maus-tratos, abuso ou mutilação de qualquer animal silvestre.", fauna));
            fauna.adicionarSubcategoria(new Subcategoria("Experiências que possam causar dor e sofrimento aos animais.", fauna));
            fauna.adicionarSubcategoria(new Subcategoria("Emissão de efluentes, substâncias tóxicas ou outro meio proibido que possa provocar a " +
                    "morte ou extinção de espécies aquáticas.", fauna));

            // FLORA
            Categoria flora = new Categoria();
            flora.setNome("FLORA");

            flora.adicionarSubcategoria(new Subcategoria("Destruir ou danificar florestas de preservação permanente, independentemente do estágio de formação.", flora));
            flora.adicionarSubcategoria(new Subcategoria("Destruir ou danificar qualquer vegetação do Bioma Mata Atlântica.", flora));
            flora.adicionarSubcategoria(new Subcategoria("Cortar árvores em florestas de preservação permanente, sem a devida permissão.", flora));
            flora.adicionarSubcategoria(new Subcategoria("Fabricar, vender, transportar ou soltar balões que podem provocar incêndios.", flora));
            flora.adicionarSubcategoria(new Subcategoria("Destruir, danificar, lesar ou maltratar, por qualquer meio ou modo, plantas de ordenação de espaços públicos ou em propriedades privadas alheias.", flora));


            // POLUIÇÃO
            Categoria poluicao = new Categoria();
            poluicao.setNome("POLUIÇÃO");

            poluicao.adicionarSubcategoria(new Subcategoria("Causar poluição atmosférica ou hídrica.", poluicao));
            poluicao.adicionarSubcategoria(new Subcategoria("Dificultar ou impedir o uso público das praias.", poluicao));
            poluicao.adicionarSubcategoria(new Subcategoria("Realizar pesquisa, lavra ou extração de recursos minerais sem autorização legal.", poluicao));
            poluicao.adicionarSubcategoria(new Subcategoria("Produzir, processar, embalar, importar, exportar, comercializar, fornecer, transportar, armazenar, guardar, ter em depósito ou usar substância tóxica perigosa, ou nociva à saúde humana ou ao meio ambiente, em desacordo com as exigências estabelecidas.", poluicao));
            poluicao.adicionarSubcategoria(new Subcategoria("Construir, reformar, ampliar, instalar ou fazer funcionar, estabelecimentos, obras ou serviços potencialmente poluidores, sem licença.", poluicao));
            poluicao.adicionarSubcategoria(new Subcategoria("Disseminar doença ou praga que cause dano à agricultura, pecuária, fauna, flora e aos ecossistemas.", poluicao));

            // ORDENAMENTO URBANO E PATRIMÔNIO CULTURAL
            Categoria ordenamentoCultural = new Categoria();
            ordenamentoCultural.setNome("ORDENAMENTO URBANO E PATRIMÔNIO CULTURAL");

            ordenamentoCultural.adicionarSubcategoria(new Subcategoria("Pixação em áreas urbanas.", ordenamentoCultural));
            ordenamentoCultural.adicionarSubcategoria(new Subcategoria("Alterar o aspecto ou estrutura bem como promover a construção em solo de locais protegidos em razão do seu valor paisagístico, ecológico, turístico, artístico, histórico, cultural, religioso, arqueológico, etnográfico ou monumental, sem autorização prévia da autoridade competente.", ordenamentoCultural));
            ordenamentoCultural.adicionarSubcategoria(new Subcategoria("Mineração, Ruído e Vibração Industrial.", ordenamentoCultural));

            // ADMINISTRAÇÃO AMBIENTAL
            Categoria administracaoAmbiental = new Categoria();
            administracaoAmbiental.setNome("ADMINISTRAÇÃO AMBIENTAL");

            administracaoAmbiental.adicionarSubcategoria(new Subcategoria("Práticas como afirmações falsas ou enganosas.", administracaoAmbiental));
            administracaoAmbiental.adicionarSubcategoria(new Subcategoria("Concessões de licenças, autorizações ou permissões emitidas pelos funcionários, porém em desacordo com as normas ambientais.", administracaoAmbiental));

            categorias.salvar(fauna);
            categorias.salvar(flora);
            categorias.salvar(poluicao);
            categorias.salvar(ordenamentoCultural);
            categorias.salvar(administracaoAmbiental);
        }

        if (usuarioService.listarTodos().isEmpty()){
            //Usuario Sistema
            usuarioService.salvar(new NovoUsuarioJSON(
                    "Eco Guardian",
                    "00000000000",
                    "000",
                    "000@gmail.com",
                    "000",
                    TipoPerfil.ECO_GUARDIAN
            ));

            //Usuario Admin
            usuarioService.salvar(new NovoUsuarioJSON(
                    "Administrador",
                    "11111111111",
                    "111",
                    "111@gmail.com",
                    "111",
                    TipoPerfil.ADMIN
            ));

            //Usuario Analista
            usuarioService.salvar(new NovoUsuarioJSON(
                    "Analista guardian",
                    "22222222222",
                    "222",
                    "222@gmail.com",
                    "222",
                    TipoPerfil.ANALISTA
            ));

            //Usuario Denunciante
            usuarioService.salvar(new NovoUsuarioJSON(
                    "Denunciante genérico",
                    "33333333333",
                    "333",
                    "333@gmail.com",
                    "333",
                    TipoPerfil.DENUNCIANTE
            ));

            //Usuario Denunciante
            usuarioService.salvar(new NovoUsuarioJSON(
                    "Anônimo secreto",
                    "44444444444",
                    "444",
                    "444@gmail.com",
                    "444",
                    TipoPerfil.ANONIMO
            ));
        }

        List<Denuncia> listarTodasDenuncias = denuncias.listarTodas();
        if (listarTodasDenuncias.isEmpty()){
            DenunciaJSON denunciaReal1 = new DenunciaJSON(
                    false,
                    "Av. Paulista",
                    "123",
                    "01310-100",
                    "Bela Vista",
                    "3550308",
                    "-23.5678",
                    "-46.6789",
                    "Próximo ao MASP",
                    "Problema na calçada",
                    "Buraco na calçada em frente ao prédio",
                    1L,
                    5L,
                    "2",
                    "email1@example.com",
                    "987654321",
                    "Outras informações sobre o problema",
                    "Teste1",
                    "Descrição do Autor 1"
            );

            DenunciaJSON denunciaReal2 = new DenunciaJSON(
                    false,
                    "Rua da Consolação",
                    "456",
                    "01223-456",
                    "Consolação",
                    "3550308",
                    "-23.5432",
                    "-46.6543",
                    "Em frente ao restaurante",
                    "Vazamento de água",
                    "Vazamento na rua causando alagamento",
                    2L,
                    9L,
                    "3",
                    "email2@example.com",
                    "987321654",
                    "Outras informações sobre o problema",
                    "Teste2",
                    "Descrição do Autor 2"
            );

            DenunciaJSON denunciaReal3 = new DenunciaJSON(
                    false,
                    "Rua da Praia",
                    "789",
                    "22010-001",
                    "Copacabana",
                    "3304557",
                    "-22.9876",
                    "-43.1234",
                    "Próximo ao posto de salva-vidas",
                    "Iluminação pública",
                    "Poste de luz apagado há dias",
                    3L,
                    13L,
                    "4",
                    "email3@example.com",
                    "987123456",
                    "Outras informações sobre o problema",
                    "Teste3",
                    "Descrição do Autor 3"
            );

            DenunciaJSON denunciaReal4 = new DenunciaJSON(
                    true,
                    "Rua das Flores",
                    "012",
                    "80010-080",
                    "Centro",
                    "4106902",
                    "-25.4321",
                    "-49.5678",
                    "Perto do Jardim Botânico",
                    "Lixo acumulado",
                    "Lixo acumulado na calçada",
                    4L,
                    20L,
                    "5",
                    "email4@example.com",
                    "987456123",
                    "Outras informações sobre o problema",
                    "Teste4",
                    "Descrição do Autor 4"
            );
            denuncias.abrir(denunciaReal1);
            denuncias.abrir(denunciaReal2);
            denuncias.abrir(denunciaReal3);
            denuncias.abrir(denunciaReal4);
        }
        LOG.info("Banco de dados populado com sucesso de informaçoes templates");
    }
}