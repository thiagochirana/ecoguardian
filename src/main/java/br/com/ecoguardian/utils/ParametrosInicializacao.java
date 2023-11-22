package br.com.ecoguardian.utils;

import br.com.ecoguardian.models.Denuncia;
import br.com.ecoguardian.models.Usuario;
import br.com.ecoguardian.models.enums.TipoPerfil;
import br.com.ecoguardian.models.records.DenunciaJSON;
import br.com.ecoguardian.models.records.NovoUsuarioJSON;
import br.com.ecoguardian.services.DenunciaService;
import br.com.ecoguardian.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ParametrosInicializacao {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private DenunciaService denuncias;

    public void validarEPopularBancoPrimeiraInicializacao(){
        List<Usuario> users = usuarioService.listarTodos();
        if (users.isEmpty()){
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
                    "1",
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
                    "2",
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
                    "3",
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
                    "4",
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
    }
}
