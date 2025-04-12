package br.com.bd_notifica.enums;

public enum Area {
    INTERNA("Interna"),
    EXTERNA("Externa");

    private final String descricao;

    Area(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Area fromOpcao(int opcao) {
        return switch (opcao) {
            case 1 -> INTERNA;
            case 2 -> EXTERNA;
            default -> throw new IllegalArgumentException("Opção inválida para área.");
        };
    }
}