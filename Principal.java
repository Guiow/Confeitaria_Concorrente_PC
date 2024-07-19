/* ***************************************************************
* Autor............: Guilherme Oliveira
* Inicio...........: 08/06/2024 - 10:39
* Ultima alteracao.: 13/06/2024 - 18:58
* Nome.............: Confeitaria Concorrente
* Funcao...........: Classe utilizada para iniciar o programa e exibir a GUI inicial.
*************************************************************** */
import controller.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Principal extends Application
{
  /* ***************************************************************
  * Metodo: start
  * Funcao: Inicializa a GUI, carrega e exibe o SceneInicial para o usuario.
  * Parametros: stage = parametro Stage para armazenar e exibir os scenes.
  * Retorno: void
  *************************************************************** */
  @Override
  public void start(Stage stage)
  {
    ControladorGeral controlador = new ControladorGeral();//cria o controlador geral do programa
    Scene scene = new Scene(controlador, 1065, 680);//adiciona-o ao scene
    controlador.inicializarPrograma();//e inicializa as variaveis necessarias para execucao do programa
    
    stage.setTitle("Confeitaria Concorrente");
    stage.setResizable(false);
    stage.setY(0);
    stage.setScene(scene);
    stage.show();
  }//fim do start
  
  /* ***************************************************************
  * Metodo: main
  * Funcao: Utilizado para começar o programa quando o programa for compilado e depois executado
  * Parametros: args = argumentos para compilacao do programa, nao necessario nessa situacao
  * Retorno: void
  *************************************************************** */
  public static void main(String[] args)
  {
    launch();
  }//fim do main
}//fim da classe Principal
