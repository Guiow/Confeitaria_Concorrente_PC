/* ***************************************************************
* Autor............: Guilherme Oliveira
* Inicio...........: 08/06/2024 - 11:06
* Ultima alteracao.: 13/06/2024 - 19:20
* Nome.............: Confeitaria Concorrente
* Funcao...........: Inicializa e gerencia as threads produtor e consumidor, alem de configura-las adequadamente
*************************************************************** */
package model;

import util.*;

import java.util.concurrent.Semaphore;
import javafx.scene.layout.Pane;

public class ProdutorConsumidor
{
  private Semaphore bufferVazio;//semaforos compartilhados
  private Semaphore bufferCheio;
  private Semaphore mutex;
  private BalcaoDeBolos bufferDeBolos;//buffer compartilhado
  
  private Produtor produtor;//threads produtor e consumidor
  private Consumidor consumidor;
  
  private Pane painelPrincipal;
  private final int NUMERO_DE_MAXIMO_DE_ITENS = 8;//no buffer
  
  /* ***************************************************************
  * Metodo: construtor
  * Funcao: armazena o pane principal para passa-lo as threads que necessitam
  * Parametros: painelPrincipal = pane principal do programa
  * Retorno: nenhum
  *************************************************************** */
  public ProdutorConsumidor(Pane painelPrincipal)
  {
    this.painelPrincipal = painelPrincipal;
  }//fim do construtor

  /* ***************************************************************
  * Metodo: inicializaThreads
  * Funcao: inicializa e re-inicializa os semaforos e as threads produtor e consumidor
  * Parametros: nenhum
  * Retorno: void
  *************************************************************** */
  public void inicializaThreads()
  {
    bufferVazio = new Semaphore(NUMERO_DE_MAXIMO_DE_ITENS);//empty = N
    bufferCheio = new Semaphore(0);//full = 0
    mutex = new Semaphore(1);//mutex = 1
    
    bufferDeBolos = new BalcaoDeBolos(NUMERO_DE_MAXIMO_DE_ITENS);
    //instancia os threads passando como paremetros semaforo e buffer para que sejam compartilhados
    produtor = new Produtor(bufferDeBolos, bufferVazio, bufferCheio, mutex, painelPrincipal);
    consumidor = new Consumidor(bufferDeBolos, bufferVazio, bufferCheio, mutex, painelPrincipal);
    
    produtor.setDaemon(true);//para as threads finalizarem quando o programa for finalizado
    consumidor.setDaemon(true);
    
    adicionaImagensPainelPrincipal();//adiciona as imagens ao pane principal
    
    produtor.start();
    consumidor.start();
  }//fim do inicializaThreads
  
  /* ***************************************************************
  * Metodo: adicionaImagensPainelPrincipal
  * Funcao: adiciona as imagens necessarias para animacao no pane principal
  * Parametros: nenhum
  * Retorno: void
  *************************************************************** */
  private void adicionaImagensPainelPrincipal()
  {
    painelPrincipal.getChildren().addAll(produtor.getCozinha().getImagemDaCozinha(), 
      produtor.getFuncionario().getImagemDoFuncionario(), bufferDeBolos.getImagemDoBalcao(),
      consumidor.getFuncionario().getImagemDoFuncionario(), consumidor.getCaminhaoDeEntrega().getImagemDoCaminhao());
  }//fim do adicionaImagensPainelPrincipal
  
  /* ***************************************************************
  * Metodo: alteraVelocidades
  * Funcao: controla as velocidades dos componentes do produtor e do consumidor
  * Parametros: objetoCosrrespondente = indica qual e o objeto que tera a velocidade alterada,
    novaVelocidade = valor que ira substituir o antigo valor da velocidade
  * Retorno: void
  *************************************************************** */
  public void alteraVelocidades(int objetoCorrespondente, int novaVelocidade)
  {
    switch(objetoCorrespondente)
    {
      case 0:
        produtor.getCozinha().setVelocidadeDeProducao(novaVelocidade);//altera velocidade de producao
        break;
        
      case 1:
        produtor.getFuncionario().setVelocidadeDeTrabalho(novaVelocidade);//altera velocidade de insercao no buffer
        break;
        
      case 2:
        consumidor.getCaminhaoDeEntrega().setVelocidadeDeEntrega(novaVelocidade);//altera velocidade de consumo
        break;
        
      default:
        consumidor.getFuncionario().setVelocidadeDeTrabalho(novaVelocidade);//altera velocidade de remocao do buffer
    }//fim do switch
  }//fim do alteraVelocidades
  
  /* ***************************************************************
  * Metodo: reset
  * Funcao: interrompe as threads e as reinicializa
  * Parametros: nenhum
  * Retorno: void
  *************************************************************** */
  public void reset()
  {
    produtor.interrupt();
    consumidor.interrupt();
    inicializaThreads();
  }//fim do reset
}//fim da classe ProdutorConsumidor
