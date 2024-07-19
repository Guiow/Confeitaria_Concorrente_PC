/* ***************************************************************
* Autor............: Guilherme Oliveira
* Inicio...........: 08/06/2024 - 15:20
* Ultima alteracao.: 14/06/2024 - 16:12
* Nome.............: Confeitaria Concorrente
* Funcao...........: Classe que consome os itens e cria a animacao de consumir
*************************************************************** */
package util;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.application.Platform;
import javafx.scene.layout.Pane;

public class CaminhaoDeEntrega
{
  private ImageView imagemDoCaminhao;//imagem atual do caminhao
  private Image[] imagensDeAnimacao;
  private Pane painelPrincipal;
  
  private final int PONTO_X_ESTACIONAMENTO = 290;
  private final int PONTO_Y_ESTACIONAMENTO = 395;
  private final int INCREMENTO_X = 10;
  private final int INCREMENTO_Y = 5;
  
  private int velocidadeDeEntrega;
  private boolean pausado;//verifica se esta pausado

  /* ***************************************************************
  * Metodo: construtor
  * Funcao: instancia e configura a classe, e armazena referencia para o pane principal
  * Parametros: painelPrincipal = pane principal usado para remover bolos do pane
  * Retorno: nenhum
  *************************************************************** */
  public CaminhaoDeEntrega(Pane painelPrincipal)
  {
    imagensDeAnimacao = new Image[4];
    for (int indiceDaImagem = 0; indiceDaImagem < imagensDeAnimacao.length; indiceDaImagem++)//cria um vetor de images
      imagensDeAnimacao[indiceDaImagem] = new Image(String.format("images/caminhaoAnimacao/caminhaoDeEntrega%d.png", indiceDaImagem));
      
    imagemDoCaminhao = new ImageView(imagensDeAnimacao[0]);
    imagemDoCaminhao.setLayoutX(PONTO_X_ESTACIONAMENTO);
    imagemDoCaminhao.setLayoutY(PONTO_Y_ESTACIONAMENTO);
    
    velocidadeDeEntrega = 32;//velocidade inicial
    this.painelPrincipal = painelPrincipal;
  }//fim do construtor

  /* ***************************************************************
  * Metodo: fazEntregaDoBolo
  * Funcao: gerencia a animacao de consumo do bolo e o consome
  * Parametros: bolo = bolo que ira ser removido do pane principal
  * Retorno: void
  *************************************************************** */
  public void fazEntregaDoBolo(ImageView bolo)
    throws InterruptedException
  {
    Platform.runLater(() -> painelPrincipal.getChildren().remove(bolo));//remove bolo do pane principal
       
    caminhaoMovimento(840, 2, 3);//movimenta o caminhao para o final do pane 
    Platform.runLater(() -> 
    {
      imagemDoCaminhao.setLayoutY(PONTO_Y_ESTACIONAMENTO + 300);//coloca a imagem do caminhao em uma posicao abaixo do pane
      imagemDoCaminhao.setLayoutX(PONTO_X_ESTACIONAMENTO - 600);//para ele vir de baixo
    });
    Thread.sleep(300);//pequena pausa para o caminhao nao voltar instantaneamente
    caminhaoMovimento(PONTO_X_ESTACIONAMENTO, 0, 1);//movimenta o caminhao para posicao inicial
  }//fim do fazEntregaDoBolo
  
  /* ***************************************************************
  * Metodo: caminhaoMovimento
  * Funcao: Faz a animacao do movimento do caminhao e muda a posicao da imagem do caminhao
  * Parametros: pontoLimiteX = ponto X que o caminhao chegara ate, indiceDeAnimacao1 = indice da primeira imagem para animacao,
    indiceDeAnimacao2 = indice da segunda imagem para animacao
  * Retorno: void
  *************************************************************** */
  private void caminhaoMovimento(int pontoLimiteX, int indiceDeAnimacao1, int indiceDeAnimacao2)
    throws InterruptedException
  {
    boolean trocaImagem = true;//para alterar os indices de animacao
    for (int pontoXAtual = (int) imagemDoCaminhao.getLayoutX(); pontoXAtual < pontoLimiteX; pontoXAtual += INCREMENTO_X)
    {
      pausarAtividade();
      int indiceDeAnimacao = trocaImagem ? indiceDeAnimacao1 : indiceDeAnimacao2;
      Platform.runLater(() -> 
      {
        imagemDoCaminhao.setLayoutX(imagemDoCaminhao.getLayoutX() + INCREMENTO_X);//incrementa posicao X e Y
        imagemDoCaminhao.setLayoutY(imagemDoCaminhao.getLayoutY() - INCREMENTO_Y);
        imagemDoCaminhao.setImage(imagensDeAnimacao[indiceDeAnimacao]);//alterna entre as imagens de indice 1 e 2
      });
      trocaImagem = !trocaImagem;
    }//fim do for
  }//fim do caminhaoMovimento
  
   /* ***************************************************************
  * Metodo: pausarAtividade
  * Funcao: pausa o programa se pausado for true e controla a velocidade dos loops
  * Parametros: nenhum
  * Retorno: void
  *************************************************************** */
  private void pausarAtividade()
    throws InterruptedException
  {
    while (pausado)
      Thread.sleep(500);
    Thread.sleep(velocidadeDeEntrega);
  }//fim do pausarAtividade
  
  /* ***************************************************************
  * Metodo: setVelocidadeDeEntrega
  * Funcao: altera a velocidade de consumo e para o consumo caso a velocidade seja 0
  * Parametros: novaVelocidade = velocidade substituta
  * Retorno: void
  *************************************************************** */
  public void setVelocidadeDeEntrega(int novaVelocidade)
  {
    if (novaVelocidade == 0)
      pausado = true;
    else
    {
      pausado = false;
      velocidadeDeEntrega = 55 - novaVelocidade;
    }//fim do else
  }//fim do setVelocidadeDeEntrega
  
  /* ***************************************************************
  * Metodo: getImagemDoCaminhao
  * Funcao: retorna o imageView que representa a imagem do caminhao
  * Parametros: nenhum
  * Retorno: ImageView = imagem atual do caminhao
  *************************************************************** */
  public ImageView getImagemDoCaminhao()
  {
    return imagemDoCaminhao;
  }//fim do getImagemDoCaminhao
}//fim da classe CaminhaoDeEntrega
