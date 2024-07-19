/* ***************************************************************
* Autor............: Guilherme Oliveira
* Inicio...........: 08/06/2024 - 16:03
* Ultima alteracao.: 14/06/2024 - 16:37
* Nome.............: Confeitaria Concorrente
* Funcao...........: Serve como buffer para armazenar os itens produzidos e retornar os itens consumidos
*************************************************************** */
package util;

import javafx.scene.image.ImageView;
import java.security.SecureRandom;

public class BalcaoDeBolos
{
  private ImageView[] bufferDeBolos;
  private ImageView imagemDoBalcao;
  
  private SecureRandom posicaoAleatoria;//necessario para colocar e remover bolos em posicoes aleatorias no buffer
  private final int QUANTIDADE_DE_LUGARES_NO_BUFFER;
  
  /* ***************************************************************
  * Metodo: construtor
  * Funcao: instancia e configura as variaveis necessarias para o buffer
  * Parametros: painelPrincipal = pane principal usado para remover bolos do pane
  * Retorno: void
  *************************************************************** */
  public BalcaoDeBolos(int numeroMaximoDeBolos)
  {
    imagemDoBalcao = new ImageView("images/imagensCenario/BalcaoDeBolos.png");//instancia e ajusta posicao da imagem do buffer
    imagemDoBalcao.setLayoutY(182);
    imagemDoBalcao.setLayoutX(105);
    
    bufferDeBolos = new ImageView[numeroMaximoDeBolos];//lugares vazios para bolos
    QUANTIDADE_DE_LUGARES_NO_BUFFER = numeroMaximoDeBolos;
    posicaoAleatoria = new SecureRandom();
  }//fim do construtor
  
  /* ***************************************************************
  * Metodo: posicaoAleatoria
  * Funcao: retorna uma posicao aleatoria com ou sem bolo no buffer dependendo do parametro
  * Parametros: posicaoVazia = true procura uma posicao vazia no buffer, false procura uma posicao com bolo no buffer
  * Retorno: int = posicao vazia ou cheia do buffer
  *************************************************************** */
  public int posicaoAleatoria(boolean posicaoVazia)
  {
    int posicao;
    if (posicaoVazia)//encontra uma posicao vazia no buffer
    {
      for (posicao = posicaoAleatoria.nextInt(QUANTIDADE_DE_LUGARES_NO_BUFFER);
        bufferDeBolos[posicao] != null; posicao = (posicao + 1) % QUANTIDADE_DE_LUGARES_NO_BUFFER);
    }
    else//encontra uma posicao cheia no buffer
    {
      for (posicao = posicaoAleatoria.nextInt(QUANTIDADE_DE_LUGARES_NO_BUFFER);
        bufferDeBolos[posicao] == null; posicao = (posicao + 1) % QUANTIDADE_DE_LUGARES_NO_BUFFER);
    }
    return posicao;
  }//fim do posicaoAleatoria

  /* ***************************************************************
  * Metodo: removerBolo
  * Funcao: remove e retorna o bolo do buffer de acordo com a posicao
  * Parametros: posicao = posicao que o bolo sera removido e retornado
  * Retorno: ImageView = bolo removido do buffer
  *************************************************************** */
  public ImageView removerBolo(int posicao)
  {
     ImageView bolo = bufferDeBolos[posicao];
     bufferDeBolos[posicao] = null;
     return bolo;
  }//fim do removerBolo
    
  /* ***************************************************************
  * Metodo: depositarBolo
  * Funcao: deposita o bolo na posicao correspondente do buffer
  * Parametros: bolo = bolo que sera depositado, posicao = posicao do buffer que o bolo sera depositado
  * Retorno: void
  *************************************************************** */
  public void depositarBolo(ImageView bolo, int posicao)
  {
    bufferDeBolos[posicao] = bolo;
    depositarBoloNaPosicaoCorreta(posicao);//coloca o bolo na posicao correta sobre o buffer
  }//fim do depositarBolo
  
  /* ***************************************************************
  * Metodo: depositarBoloNaPosicaoCorreta
  * Funcao: deixa o bolo depositado na posicao correta de acordo com o indice da posicao
  * Parametros: posicao = posicao que o bolo sera inserido
  * Retorno: void
  *************************************************************** */
  private void depositarBoloNaPosicaoCorreta(int posicao)
  {
    int boloLayoutX = (int) imagemDoBalcao.getLayoutX() + 16;//posicao exata para o primeiro elemento no buffer
    int boloLayoutY = (int) imagemDoBalcao.getLayoutY() + 136; 
    
    for(int posicaoAtual = 0; posicaoAtual < posicao; posicaoAtual++)
    {//incrementa a posicao linearmente para deixalo na posicao correta
      boloLayoutX += 46;
      boloLayoutY -= 23;
    }//fim do for
    
    bufferDeBolos[posicao].setLayoutX(boloLayoutX);
    bufferDeBolos[posicao].setLayoutY(boloLayoutY);
  }//fim do depositarBoloNaPosicaoCorreta
  
  /* ***************************************************************
  * Metodo: getImagemDoBalcao
  * Funcao: retorna a referencia da imagem do balcao
  * Parametros: nenhum
  * Retorno: ImageView = imagem do balcao
  *************************************************************** */
  public ImageView getImagemDoBalcao()
  {
    return imagemDoBalcao;
  }//fim do getImagemDoBalcao
}//fim da classe BalcaoDeBolos
