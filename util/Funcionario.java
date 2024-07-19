/* ***************************************************************
* Autor............: Guilherme Oliveira
* Inicio...........: 08/06/2024 - 14:26
* Ultima alteracao.: 16/06/2024 - 15:02
* Nome.............: Confeitaria Concorrente
* Funcao...........: classe abstrata que fornece metodos e construtor genericos para classes Funcionarios filhos
*************************************************************** */
package util;

import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public abstract class Funcionario
{
  private ImageView imagemAtual;//imagem do funcionario
  private ImageView boloCarregado;//imagem do bolo sendo carregado
  private Image[] imagensDeAnimacao;
  
  private int velocidadeDeTrabalho;
  private boolean pausado;//verifica se esta pausado
  private boolean segurandoBolo;//verifica se esta segurando bolo
  
  /* ***************************************************************
  * Metodo: construtor
  * Funcao: configura e instancia objetos usados pelas classes filhos
  * Parametros: nenhum
  * Retorno: nenhum
  *************************************************************** */ 
  protected Funcionario(String urlAnimacao, int posicaoXInicial, int posicaoYInicial)
  {
    imagensDeAnimacao = new Image[5];
    for (int indiceAnimacao = 0; indiceAnimacao < 5; indiceAnimacao++)//cria um array de images
      imagensDeAnimacao[indiceAnimacao] = new Image(String.format("%s%d.png", urlAnimacao, indiceAnimacao));
      
    imagemAtual = new ImageView(imagensDeAnimacao[imagensDeAnimacao.length - 1]);//imagem inicial
    imagemAtual.setLayoutX(posicaoXInicial);//ajusta posicao inicial
    imagemAtual.setLayoutY(posicaoYInicial);
    
    this.velocidadeDeTrabalho = 25;//velocidade inicial
  }//fim do construtor
  
  /* ***************************************************************
  * Metodo: caminhar
  * Funcao: move a imagem atual em determinada distancia e fornece a animacao de andar
  * Parametros: distancia = quantidade de vezes que ele ira se movimentar, indiceImagem1 = imagem 1 que ira ser alternada,
    indiceImagem2 = imagem 2 que ira ser alternada com a imagem 1, valorDeIncrementoX = quantidade de pixels na posicao X que ira se movimentar a cada iteracao, valorDeIncrementoY = quantidade de pixels na posicao Y que ira se movimentar a cada iteracao.
  * Retorno: void
  *************************************************************** */ 
  protected void caminhar(int distancia, int indiceDaImagem1,
   int indiceDaImagem2, int valorDeIncrementoX, int valorDeIncrementoY)
   throws InterruptedException
  {
    setImagemDoFuncionario(indiceDaImagem1);//muda a imagem logo quando o metodo e chamado
    for (boolean alternadorDePassos = true; distancia > 0; distancia--)
    {   
      Platform.runLater(() -> 
      {
        imagemAtual.setLayoutX(getImagemDoFuncionario().getLayoutX() + valorDeIncrementoX);//incrementa posicao X e Y
        imagemAtual.setLayoutY(getImagemDoFuncionario().getLayoutY() + valorDeIncrementoY);
        if (segurandoBolo)//movimenta o bolo carregado com o funcionario
          movimentaBoloComPersonagem();
      });
      
      if (distancia % 5 == 0)//alterna entre as imagens para fornecer uma animacao de caminhar
      {
        setImagemDoFuncionario(alternadorDePassos ? indiceDaImagem1 : indiceDaImagem2);
        alternadorDePassos = !alternadorDePassos;
      }//fim do if
      pausarAtividade();
    }//fim do for
  }//fim do caminhar
   
  /* ***************************************************************
  * Metodo: pausarAtividade
  * Funcao: pausa o programa se estiver parado e controla a velocidade dos loops
  * Parametros: nenhum
  * Retorno: void
  *************************************************************** */
  private void pausarAtividade()
    throws InterruptedException
  {
    while (pausado)
      Thread.sleep(500);
    Thread.sleep(velocidadeDeTrabalho);
  }//fim do pausarAtividade
  
  /* ***************************************************************
  * Metodo: movimentaBoloComPersonagem
  * Funcao: movimenta o bolo correspondendo a posicao da imagem do funcionario
  * Parametros: nenhum
  * Retorno: void
  *************************************************************** */
  private void movimentaBoloComPersonagem()
  {
    boloCarregado.setLayoutX(imagemAtual.getLayoutX() - 10);//ajusta posicao do bolo carregado
    boloCarregado.setLayoutY(imagemAtual.getLayoutY() + 14);
  }//fim do movimentaBoloComPersonagem
  
  /* ***************************************************************
  * Metodo: segurarBolo
  * Funcao: adiciona um novo bolo para ser carregado e configura a sua posicao para ficar de acordo com a imagem do funcinario
  * Parametros: bolo = bolo que vai substituir a referencia do bolo carregado
  * Retorno: void
  *************************************************************** */
  protected void segurarBolo(ImageView bolo)
  {
    boloCarregado = bolo;
    segurandoBolo = true;
    Platform.runLater(() -> movimentaBoloComPersonagem());//ajusta posicao
  }//fim do segurarBolo
  
  /* ***************************************************************
  * Metodo: soltarBolo
  * Funcao: retorna a referencia do bolo carregado e autualiza a variavel de segurar bolo
  * Parametros: nenhum
  * Retorno: ImageView = bolo carregado
  *************************************************************** */
  protected ImageView soltarBolo()
  {
    segurandoBolo = false;
    return boloCarregado;
  }//fim do soltarBolo
  
  /* ***************************************************************
  * Metodo: setImagemDoFuncionario
  * Funcao: troca a imagem atual do funcionario para uma que pertenca a animacao
  * Parametros: indiceDaImagem = do array
  * Retorno: void
  *************************************************************** */
  protected void setImagemDoFuncionario(int indiceDaImagem)
  {
    Platform.runLater(() -> imagemAtual.setImage(imagensDeAnimacao[indiceDaImagem]));
  }//fim do setImagemDoFuncionario
  
  /* ***************************************************************
  * Metodo: mudaVisibilidade
  * Funcao: altera visibilidade da imagem do funcionario
  * Parametros: visivel = true deixa visivel, false deixa invisivel
  * Retorno: void
  *************************************************************** */
  protected void mudaVisibilidade(boolean visivel)
  {
    Platform.runLater(() -> imagemAtual.setVisible(visivel));
  }//fim do mudaVisibilidade
  
  /* ***************************************************************
  * Metodo: setVelocidadeDeTrabalho
  * Funcao: altera a velocidade de trabalho
  * Parametros: novaVelocidade = velocidade que ira substituir a velocidade de trabalho atual
  * Retorno: void
  *************************************************************** */
  public void setVelocidadeDeTrabalho(int novaVelocidade)
  {
    if (novaVelocidade == 0)//caso a nova velocidade seja 0 modifica a variavel que identifica o pause
      pausado = true;
    else
    {
      pausado = false;
      velocidadeDeTrabalho = 60 - novaVelocidade;
    }//fim do else
  }//fim do setVelocidadeDeTrabalho
  
  /* ***************************************************************
  * Metodo: getImagemDoFuncionario
  * Funcao: retorna o imageView do funcionario
  * Parametros: nenhum
  * Retorno: ImageView = imagem atual do funcionario
  *************************************************************** */
  public ImageView getImagemDoFuncionario()
  {
    return imagemAtual;
  }//fim do getImagemDoFuncionario
}//fim da classe Funcionario
