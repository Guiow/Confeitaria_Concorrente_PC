/* ***************************************************************
* Autor............: Guilherme Oliveira
* Inicio...........: 08/06/2024 - 11:32
* Ultima alteracao.: 15/06/2024 - 19:23
* Nome.............: Confeitaria Concorrente
* Funcao...........: Classe que extende Thread com a funcao de atuar como produtor do programa, produzindo e inserindo itens no buffer
*************************************************************** */
package model;

import util.*;

import java.util.concurrent.Semaphore;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Produtor extends Thread
{
  private BalcaoDeBolos bufferDeBolos;//buffer compartilhado
  private Semaphore bufferVazio;//semaforos compartilhados
  private Semaphore bufferCheio;
  private Semaphore mutex;
  
  private FuncionarioDepositante funcionarioDepositante;//parte que insere valores no buffer
  private Cozinha cozinha;//parte que produz os itens
  
  /* ***************************************************************
  * Metodo: construtor
  * Funcao: recebe e armazena as referencias do buffer e dos semaforos compartilhados
    e instancia os objetos que atuam como parte do produtor
  * Parametros: bufferDeBolos = buffer compartilhado, bufferVazio = semaforo empty,
    bufferCheio = semaforo full, mutex = semaforo mutex, painelPrincipal = pane principal do programa
  * Retorno: nenhum
  *************************************************************** */
  public Produtor(BalcaoDeBolos bufferDeBolos, Semaphore bufferVazio, Semaphore bufferCheio, Semaphore mutex, Pane painelPrincipal)
  {
    this.bufferDeBolos = bufferDeBolos;//buffer
    this.bufferVazio = bufferVazio;//empty
    this.bufferCheio = bufferCheio;//full
    this.mutex = mutex;//mutex
    
    cozinha = new Cozinha(painelPrincipal);
    funcionarioDepositante = new FuncionarioDepositante(cozinha);
  }//fim do construtor
  
  /* ***************************************************************
  * Metodo: run
  * Funcao: realiza a tarefa correspondente ao produtor ate o fim do programa atuando com semaforos para exclusao mutua e
    produzindo os itens a ser inseridos no buffer
  * Parametros: nenhum
  * Retorno: void
  *************************************************************** */
  @Override
  public void run()
  {
    try
    {
      ImageView boloProduzido;
      while(true)
      {
        boloProduzido = cozinha.produzBolo();//produz item
        bufferVazio.acquire();//down no empty
        mutex.acquire();//down no mutex
        funcionarioDepositante.depositaBoloNoBuffer(boloProduzido, bufferDeBolos);//insere item no buffer
        mutex.release();//up no mutex
        bufferCheio.release();//up no full
      }//fim do while
    }//fim do try
    catch (InterruptedException ignora){}
  }//fim do run
  
  /* ***************************************************************
  * Metodo: getFuncionario
  * Funcao: retorna a referencia do funcionario depositante
  * Parametros: nenhum
  * Retorno: Funcionario = funcionario que realiza a acao de depositar itens no buffer
  *************************************************************** */
  public Funcionario getFuncionario()
  {
    return funcionarioDepositante;
  }//fim do getFuncionario
  
  /* ***************************************************************
  * Metodo: getCozinha
  * Funcao: retorna a referencia da cozinha
  * Parametros: nenhum
  * Retorno: Cozinha = que tem a funcao de produzir os bolos
  *************************************************************** */
  public Cozinha getCozinha()
  {
    return cozinha;
  }//fim do getCozinha
}//fim da classe Produtor
