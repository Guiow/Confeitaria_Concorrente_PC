/* ***************************************************************
* Autor............: Guilherme Oliveira
* Inicio...........: 08/06/2024 - 11:39
* Ultima alteracao.: 14/06/2024 - 15:35
* Nome.............: Confeitaria Concorrente
* Funcao...........: Classe que extende Thread com a funcao de atuar como consumidor do programa, consumindo e removendo itens do buffer
*************************************************************** */
package model;

import util.*;

import java.util.concurrent.Semaphore;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Consumidor extends Thread
{
  private BalcaoDeBolos bufferDeBolos;//buffer compartilhado
  private Semaphore bufferVazio;//semaforos compartilhados
  private Semaphore bufferCheio;
  private Semaphore mutex;
  
  private FuncionarioRetirante funcionarioRetirante;//parte que insere itens no buffer
  private CaminhaoDeEntrega caminhao;//parte que consome os itens
  
  /* ***************************************************************
  * Metodo: construtor
  * Funcao: recebe e armazena as referencias do buffer e dos semaforos compartilhados
    e instancia os objetos que atuam como parte do produtor
  * Parametros: bufferDeBolos = buffer compartilhado, bufferVazio = semaforo empty,
    bufferCheio = semaforo full, mutex = semaforo mutex, painelPrincipal = pane principal do programa
  * Retorno: nenhum
  *************************************************************** */
  public Consumidor(BalcaoDeBolos bufferDeBolos, Semaphore bufferVazio, Semaphore bufferCheio, Semaphore mutex, Pane painelPrincipal)
  {
    this.bufferDeBolos = bufferDeBolos;//buffer
    this.bufferVazio = bufferVazio;//empty
    this.bufferCheio = bufferCheio;//full
    this.mutex = mutex;//mutex

    funcionarioRetirante = new FuncionarioRetirante();
    caminhao = new CaminhaoDeEntrega(painelPrincipal);
  }//fim do construtor
  
  /* ***************************************************************
  * Metodo: run
  * Funcao: realiza a tarefa correspondente ao consumidor ate o fim do programa atuando com semaforos para exclusao mutua e
    consumindo os itens no buffer
  * Parametros: nenhum
  * Retorno: void
  *************************************************************** */
  @Override
  public void run()
  {
    try
    {
      ImageView boloConsumido;
      while(true)
      {
        bufferCheio.acquire();//down no full
        mutex.acquire();//down no mutex
        boloConsumido = funcionarioRetirante.retiraBoloDoBuffer(bufferDeBolos);//remove item do buffer
        mutex.release();//up no mutex
        bufferVazio.release();//up no empty
        caminhao.fazEntregaDoBolo(boloConsumido);//consome item
      }//fim do while
    }//fim do try
    catch (InterruptedException ignora){}
  }//fim do run
  
  /* ***************************************************************
  * Metodo: getFuncionario
  * Funcao: retorna a referencia do funcionario depositante
  * Parametros: nenhum
  * Retorno: Funcionario = funcionario que realiza a acao de remover itens do buffer
  *************************************************************** */
  public Funcionario getFuncionario()
  {
    return funcionarioRetirante;
  }//fim do getFuncionario
  
  /* ***************************************************************
  * Metodo: getCaminhaoDeEntrega
  * Funcao: retorna a referencia do caminhao
  * Parametros: nenhum
  * Retorno: CaminhaoDeEntrega = que tem a funcao de consumir os bolos
  *************************************************************** */
  public CaminhaoDeEntrega getCaminhaoDeEntrega()
  {
    return caminhao;
  }//fim do getCaminhaoDeEntrega
}//fim da classe Consumidor
