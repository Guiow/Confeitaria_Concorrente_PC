/* ***************************************************************
* Autor............: Guilherme Oliveira
* Inicio...........: 08/06/2024 - 14:32
* Ultima alteracao.: 14/06/2024 - 15:20
* Nome.............: Confeitaria Concorrente
* Funcao...........: Cria um tipo de Funcionario que remove itens no buffer compartilhado
*************************************************************** */
package util;

import javafx.scene.image.ImageView;

public class FuncionarioRetirante extends Funcionario
{
  /* ***************************************************************
  * Metodo: construtor
  * Funcao: chama o construtor pai para iniciar as variaveis principais e deixa inicialmente invisivel o imageView do funcionario
  * Parametros: nenhum
  * Retorno: nenhum
  *************************************************************** */ 
  public FuncionarioRetirante()
  {
    super("images/funcionarioRetirante/funcionario", 310, 395);
  }//fim do construtor
  
  /* ***************************************************************
  * Metodo: retiraBoloDoBuffer
  * Funcao: faz a animacao de retirar um bolo do buffer, remove o bolo do buffer e retorna sua referencia
  * Parametros: bufferDeBolos
  * Retorno: ImageView = bolo removido do buffer
  *************************************************************** */ 
  public ImageView retiraBoloDoBuffer(BalcaoDeBolos bufferDeBolos)
    throws InterruptedException
  {
    mudaVisibilidade(true);//deixa visivel
    caminhar(17, 2, 3, -6, -3);//animacao de movimentar o funcionario
    
    int posicaoDeRemocao = bufferDeBolos.posicaoAleatoria(false);//remove um bolo de uma posicao aleatoria
    
    caminhar(posicaoDeRemocao * 7, 0, 1, 6, -3);
    segurarBolo(bufferDeBolos.removerBolo(posicaoDeRemocao));//armazena a referencia do bolo removido
    caminhar(posicaoDeRemocao * 7, 2, 3, -6, 3);
    caminhar(17, 0, 1, 6, 3);
    
    mudaVisibilidade(false);
    return soltarBolo();
  }//fim do retiraBoloDoBuffer
}//fim da classe FuncionarioRetirante
