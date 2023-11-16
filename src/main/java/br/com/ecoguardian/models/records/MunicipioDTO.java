package br.com.ecoguardian.models.records;
import br.com.ecoguardian.models.enums.Estado;
import com.fasterxml.jackson.annotation.JsonProperty;

public record MunicipioDTO(
        @JsonProperty("id") long id,
        @JsonProperty("nome") String nome,
        @JsonProperty("microrregiao") MicrorregiaoDTO microrregiao,
        @JsonProperty("regiao-imediata") RegiaoImediataDTO regiaoImediata
) {
    public record MicrorregiaoDTO(
            @JsonProperty("id") long id,
            @JsonProperty("nome") String nome,
            @JsonProperty("mesorregiao") MesorregiaoDTO mesorregiao
    ) {
        public record MesorregiaoDTO(
                @JsonProperty("id") long id,
                @JsonProperty("nome") String nome,
                @JsonProperty("UF") UFDTO UF
        ) {
            public record UFDTO(
                    @JsonProperty("id") long id,
                    @JsonProperty("sigla") String sigla,
                    @JsonProperty("nome") String nome,
                    @JsonProperty("regiao") RegiaoDTO regiao
            ) {
                public record RegiaoDTO(
                        @JsonProperty("id") long id,
                        @JsonProperty("sigla") String sigla,
                        @JsonProperty("nome") String nome
                ) {}
            }
        }
    }

    public record RegiaoImediataDTO(
            @JsonProperty("id") long id,
            @JsonProperty("nome") String nome,
            @JsonProperty("regiao-intermediaria") RegiaoIntermediariaDTO regiaoIntermediaria
    ) {
        public record RegiaoIntermediariaDTO(
                @JsonProperty("id") long id,
                @JsonProperty("nome") String nome,
                @JsonProperty("UF") UFDTO UF
        ) {
            public record UFDTO(
                    @JsonProperty("id") long id,
                    @JsonProperty("sigla") String sigla,
                    @JsonProperty("nome") String nome,
                    @JsonProperty("regiao") RegiaoDTO regiao
            ) {

                public Estado siglaUF(){
                    for (Estado e : Estado.values()){
                        if (e.getSigla().equals(this.sigla.toUpperCase())){
                            return e;
                        }
                    }
                    return null;
                }
                public record RegiaoDTO(
                        @JsonProperty("id") long id,
                        @JsonProperty("sigla") String sigla,
                        @JsonProperty("nome") String nome
                ) {}
            }
        }
    }
}