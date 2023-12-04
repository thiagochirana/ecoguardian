package br.com.ecoguardian.models.records;

import java.util.List;

public record DenunciasTableJSON (
        Boolean usuarioTemAcessoTotal,
        Boolean usuarioIsAdminOuAnalista,
        List<DenunciaRespJSON> denuncias
){
}
