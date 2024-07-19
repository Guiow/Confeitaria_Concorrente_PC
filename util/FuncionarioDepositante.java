/* ***************************************************************
* Autor............: Guilherme Oliveira
* Inicio...........: 08/06/2024 - 14:30
* Ultima alteracao.: 15/06/2024 - 18:57
* Nome.............: Confeitaria Concorrente
* Funcao...........: Cria um tipo de Funcionario que insere itens no buffer compartilhado
*************************************************************** */
package util;

import javafx.application.Platform;
import javafx.scene.image.ImageView;

public class FuncionarioDepositante extends Funcionario
{
  Cozinha cozinha;//necessario para mudar a imagem da cozinha quando o bolo for pego
  /* ***************************************************************
  * Metodo: construtor
  * Funcao: chama o construtor pai para iniciar as variaveis principais e armazena a imagemDaCozinha
  * Parametros: cozinha = para alterar a imagem da cozinha quando o bolo for pego
  * Retorno: nenhum
  *************************************************************** */ 
  public FuncionarioDepositante(Cozinha cozinha)
  {
    super("images/funcionariaDepositante/funcionaria", 80, 270);
    this.cozinha = cozinha;
  }//fim do construtor
  
  /* ***************************************************************
  * Metodo: depositaBoloNoBalcao
  * Funcao: faz a animacao de depositar bolo no buffer e deposita o bolo em uma posicao aleatoria do buffer
  * Parametros: bolo = item a ser inserido no buffer, bufferDeBolos
  * Retorno: void
  *************************************************************** */ 
  public void depositaBoloNoBuffer(ImageView bolo, BalcaoDeBolos bufferDeBolos)
    throws InterruptedException
  {
    caminhar(17, 0, 1, -6, -3);//animacao de movimentar o funcionario
    segurarBolo(bolo);//armazena a referencia do bolo
    cozinha.setImagemDaCozinha(11);//muda a imagem da cozinha para parecer que pegou o bolo na animacao
    caminhar(17, 2, 3, 6, 3);
    
    int posicaoDeDeposito = bufferDeBolos.posicaoAleatoria(true);//busca uma posicao aleatoria vazia no buffer
    
    caminhar(posicaoDeDeposito * 7, 3, 2, 6, -3);
    Platform.runLater(() -> bufferDeBolos.depositarBolo(soltarBolo(), posicaoDeDeposito));//insere o bolo em uma posicao null do buffer
    caminhar(posicaoDeDeposito * 7, 0, 1, -6, 3);
    setImagemDoFuncionario(4);//altera a imagem para uma que representa parado
  }//fim do depositaBoloNoBuffer
}//fim da classe FuncionarioDepositante
