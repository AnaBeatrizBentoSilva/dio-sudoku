package br.com.dio.model;

import java.util.Collection;
import java.util.List;

import static br.com.dio.model.GameStatusEnum.COMPLETE;
import static br.com.dio.model.GameStatusEnum.INCOMPLETE;
import static br.com.dio.model.GameStatusEnum.NON_STARTED;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class Board {

    private final List<List<Space>> spaces;

    //Construtor que inicializa o tabuleiro com a matriz de espaços recebida.
    public Board(final List<List<Space>> spaces) {
        this.spaces = spaces;
    }

    //Retorna a matriz de espaços que compõem o tabuleiro.
    public List<List<Space>> getSpaces() {
        return spaces;
    }

     /**
     * Retorna o status atual do jogo:
     * - NON_STARTED: nenhum espaço preenchido ainda;
     * - INCOMPLETE: existem espaços não preenchidos;
     * - COMPLETE: todos os espaços preenchidos.
     */
    public GameStatusEnum getStatus(){
        if (spaces.stream().flatMap(Collection::stream).noneMatch(s -> !s.isFixed() && nonNull(s.getActual()))){
            return NON_STARTED;
        }

        return spaces.stream().flatMap(Collection::stream).anyMatch(s -> isNull(s.getActual())) ? INCOMPLETE : COMPLETE;
    }

    /**
     * Verifica se há erros no tabuleiro, ou seja, espaços preenchidos com valor diferente do esperado.
     * Retorna false se o jogo não começou.
     */
    public boolean hasErrors(){
        if(getStatus() == NON_STARTED){
            return false;
        }

        return spaces.stream().flatMap(Collection::stream)
                .anyMatch(s -> nonNull(s.getActual()) && !s.getActual().equals(s.getExpected()));
    }

    /**
     * Tenta alterar o valor do espaço na posição (col, row).
     * Só altera se o espaço não for fixo.
     * Retorna true se alterou, false caso contrário.
     */
    public boolean changeValue(final int col, final int row, final int value){
        var space = spaces.get(col).get(row);
        if (space.isFixed()){
            return false;
        }

        space.setActual(value);
        return true;
    }

    /**
     * Limpa o valor do espaço na posição (col, row).
     * Só limpa se o espaço não for fixo.
     * Retorna true se limpou, false caso contrário.
     */
    public boolean clearValue(final int col, final int row){
        var space = spaces.get(col).get(row);
        if (space.isFixed()){
            return false;
        }

        space.clearSpace();
        return true;
    }

    //Reseta o tabuleiro limpando todos os espaços (não fixos).
    public void reset(){
        spaces.forEach(c -> c.forEach(Space::clearSpace));
    }

    //Verifica se o jogo está finalizado, ou seja, não tem erros e está completo.
    public boolean gameIsFinished(){
        return !hasErrors() && getStatus().equals(COMPLETE);
    }

}