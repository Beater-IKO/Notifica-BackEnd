package br.com.bd_notifica.enums;

public enum Prioridade {

    GRAU_LEVE("Leve"),
    GRAU_MEDIO("Mediano"),
    GRAU_ALTO("Alto"),
    GRAU_URGENTE("Urgente");

    private final String descricao;

    Prioridade(String descricao){
        this.descricao = descricao;
    }

        public String getDescricao(){
            return descricao;
        }

        public static Prioridade fromOpcao(int opcao){

            return switch (opcao){

                case 1 -> GRAU_LEVE;
                case 2 -> GRAU_MEDIO;
                case 3 -> GRAU_ALTO;
                case 4 -> GRAU_URGENTE;

                default -> throw new IllegalArgumentException("Opção Inválida!");


            };

        }


}