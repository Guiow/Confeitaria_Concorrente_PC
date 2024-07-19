/* ***************************************************************
* Autor............: Guilherme Oliveira
* Inicio...........: 11/06/2024 18:05
* Ultima alteracao.: 13/06/2024 19:28
* Nome.............: Confeitaria Concorrente
* Funcao...........: tem a funcao de retornar as urls dos bolos aleatoriamente, para que se crie varios bolos diferentes de forma aleatoria
*************************************************************** */
package util;

import java.security.SecureRandom;

public enum Bolo
{
  BOLOS("images/bolos/boloTipo");//objeto enum contendo parte da url do bolo
  
  private final int QUANTIDADE_DE_TIPOS_DE_BOLO = 31;
  private final SecureRandom boloAleatorio;
  private final String urlDoBolo;
  
  /* ***************************************************************
  * Metodo: construtor
  * Funcao: instancia o objeto BOLOS e cria um objeto SecureRandom
  * Parametros: nenhum
  * Retorno: nenhum
  *************************************************************** */
  Bolo(String urlDoBolo)
  {
    boloAleatorio = new SecureRandom();
    this.urlDoBolo = urlDoBolo;
  }//fim do construtor
  
  /* ***************************************************************
  * Metodo: getBoloAleatorio
  * Funcao: retorna a url de um bolo aleatorio
  * Parametros: nenhum
  * Retorno: String = url de um bolo aleatorio
  *************************************************************** */
  public String getBoloAleatorio()
  {
    return String.format("%s%d.png", urlDoBolo, boloAleatorio.nextInt(QUANTIDADE_DE_TIPOS_DE_BOLO));
  }//fim do getBoloAleatorio
}//fim da classe Bolo
