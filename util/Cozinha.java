/* ***************************************************************
* Autor............: Guilherme Oliveira
* Inicio...........: 08/06/2024 - 14:10
* Ultima alteracao.: 15/06/2024 - 18:56
* Nome.............: Confeitaria Concorrente
* Funcao...........: Classe que produz os itens e cria uma animacao de producao
*************************************************************** */
package util;

import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Cozinha
{
  private final ImageView PASSAGEM_PARA_COZINHA;//referencia para imagem da passagem da cozinha
  private ImageView imagemDaCozinha;//imagem atual da cozinha
  private Image[] imagensDeAnimacao;//array de imagens para animacao
  
  private final Bolo bolos;//usada para conseguir as urls dos bolos
  private Pane painelPrincipal;
  
  private int velocidadeDeProducao;
  private boolean pausado;//verifica se esta pausado
  private final int QUANTIDADE_DE_IMAGENS_DE_ANIMACAO = 12;
  
  /* ***************************************************************
  * Metodo: construtor
  * Funcao: instancia e configura a classe, e armazena referencia para o pane principal
  * Parametros: painelPrincipal = pane principal usado para inserir bolos no pane
  * Retorno: nenhum
  *************************************************************** */
  public Cozinha(Pane painelPrincipal)
  {
    PASSAGEM_PARA_COZINHA = new ImageView("images/imagensCenario/PassagemParaCozinha.png");
    
    imagensDeAnimacao = new Image[QUANTIDADE_DE_IMAGENS_DE_ANIMACAO];//cria um vetor de images
    for (int indiceDaImagem = 0; indiceDaImagem < QUANTIDADE_DE_IMAGENS_DE_ANIMACAO; indiceDaImagem++)
      imagensDeAnimacao[indiceDaImagem] = new Image(String.format("images/cozinhaAnimacao/ProduzindoBolo%d.png", indiceDaImagem)) ;
      
    imagemDaCozinha = new ImageView(imagensDeAnimacao[QUANTIDADE_DE_IMAGENS_DE_ANIMACAO - 1]);
    imagemDaCozinha.setLayoutY(97);//ajusta o imageView para posicao correta
    imagemDaCozinha.setLayoutX(150);
    
    this.painelPrincipal = painelPrincipal;
    bolos = Bolo.BOLOS;
    velocidadeDeProducao = 345;//velocidade inicial
  }//fim do construtor
  
  /* ***************************************************************
  * Metodo: produzBolo
  * Funcao: gerencia a producao de bolo e a animacao
  * Parametros: nenhum
  * Retorno: ImageView = bolo produzido
  *************************************************************** */
  public ImageView produzBolo()
    throws InterruptedException
  {
    produzindoBoloAnimacao();//faz a animacao do bolo sendo produzido
    ImageView boloProduzido = new ImageView(bolos.getBoloAleatorio());//cria um imageView com a imagem de um bolo aleatorio
    
    Platform.runLater(() -> 
    {
      painelPrincipal.getChildren().add(boloProduzido);//adiciona a imagem do bolo ao pane
      painelPrincipal.getChildren().remove(PASSAGEM_PARA_COZINHA);//remove e adiciona a passagem para cozinha para o bolo nao a sobrepor
      painelPrincipal.getChildren().add(PASSAGEM_PARA_COZINHA);
    });
    
    return boloProduzido;
  }//fim do produzBolo
  
  /* ***************************************************************
  * Metodo: produzindoBoloAnimacao
  * Funcao: passa as imagens para criar uma animacao de producao de bolo
  * Parametros: nenhum
  * Retorno: void
  *************************************************************** */
  private void produzindoBoloAnimacao()
    throws InterruptedException
  {
    for (int indiceDeAnimacao = 0; indiceDeAnimacao < 6; indiceDeAnimacao++)
    {
      setImagemDaCozinha(indiceDeAnimacao % 2);//cria a repeticao de mecher a tigela
      pausarAtividade();
    }//fim do for
    
    for (int indiceDeAnimacao = 2, iteracoes = QUANTIDADE_DE_IMAGENS_DE_ANIMACAO - 1; indiceDeAnimacao < iteracoes; indiceDeAnimacao++)
    {
      setImagemDaCozinha(indiceDeAnimacao);//passa os frames restantes
      pausarAtividade();
    }//fim do for
  }//fim do produzindoBoloAnimacao
  
  /* ***************************************************************
  * Metodo: pausarAtividade
  * Funcao: pausa o programa se estiver pausado for true e controla a velocidade dos loops
  * Parametros: nenhum
  * Retorno: void
  *************************************************************** */
  private void pausarAtividade()
    throws InterruptedException
  {
    while (pausado)
      Thread.sleep(500);
    Thread.sleep(velocidadeDeProducao);
  }//fim do pausarAtividade
  
  /* ***************************************************************
  * Metodo: setVelocidadeDeProducao
  * Funcao: altera a velocidade da producao e para a producao caso a velocidade seja 0
  * Parametros: novaVelocidade = velocidade substituta
  * Retorno: void
  *************************************************************** */
  public void setVelocidadeDeProducao(int novaVelocidade)
  {
    if (novaVelocidade == 0)
      pausado = true;
    else
    {
      pausado = false;
      velocidadeDeProducao = (52 - novaVelocidade) * 15;
    }//fim do else
  }//fim do setVelocidadeDeProducao
  
  /* ***************************************************************
  * Metodo: getImagemDaCozinha
  * Funcao: retorna a referencia do ImageView que representa a cozinha
  * Parametros: nenhum
  * Retorno: ImageView = imagem atual da cozinha
  *************************************************************** */
  public ImageView getImagemDaCozinha()
  {
    return imagemDaCozinha;
  }//fim do getImagemDaCozinha
  
  /* ***************************************************************
  * Metodo: setImagemDaCozinha
  * Funcao: muda a imagem atual da cozinha para uma imagem pertencente ao vetor de imagems
  * Parametros: indice = indice do vetor de imagems
  * Retorno: void
  *************************************************************** */
  public void setImagemDaCozinha(int indice)
  {
    Platform.runLater(() -> imagemDaCozinha.setImage(imagensDeAnimacao[indice]));
  }//fim do setImagemDaCozinha
}//fim da classe Cozinha
