/* ***************************************************************
* Autor............: Guilherme Oliveira
* Inicio...........: 08/06/2024 - 10:55
* Ultima alteracao.: 13/06/2024 - 19:04
* Nome.............: Confeitaria Concorrente
* Funcao...........: Inicializa e configura todo o programa, trata eventos lancados pelo usuario, e fornece todo o controle
  e organizacao necessarios para a execucao do programa
*************************************************************** */
package controller;

import model.*;

import javafx.scene.image.ImageView;
import javafx.scene.control.Slider;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class ControladorGeral extends HBox
{
  private ProdutorConsumidor gerenciadorDoAlgoritmo;//classe que inicializa as threads produtor consumidor
  private Pane painelPrincipalDoCenario;//pane onde os nodes de cenario ficarao
  private Pane painelDeControle;//pane onde os nodes de controle ficaram
  
  private Slider[] velocidadesDeControle;//Sliders que controlam a velocidade do programa
  private Button botaoDeReset;//botoes de pause e reset
  
  private final int VALOR_INICIAL_DOS_SLIDERS = 23;
  
  /* ***************************************************************
  * Metodo: construtor
  * Funcao: instancia as variaveis e objetos necessarios para inicializar o programa
  * Parametros: nenhum
  * Retorno: nenhum
  *************************************************************** */
  public ControladorGeral()
  {
    painelPrincipalDoCenario = new Pane(new ImageView("images/imagensCenario/CenarioPrincipal.png"));//adiciona o cenario ao pane
    painelDeControle = new Pane(new ImageView("images/imagensControle/PainelControleBackground.png"));//adicone o background ao pane
    gerenciadorDoAlgoritmo = new ProdutorConsumidor(painelPrincipalDoCenario);//instancia o gerenciador
    
    velocidadesDeControle = new Slider[4];//cria um array e quatro sliders
    for (int indice = 0; indice < velocidadesDeControle.length; indice++)
      velocidadesDeControle[indice] = new Slider();
      
    botaoDeReset = new Button();
  }//fim do construtor
  
  /* ***************************************************************
  * Metodo: inicializarPrograma
  * Funcao: Inicializa e configura todos os componentes necessarios
  * Parametros: nenhum
  * Retorno: void
  *************************************************************** */
  public void inicializarPrograma()
  {
    getStylesheets().add("style/stylePrincipal.css");//adiciona o arquivo css ao root
    
    painelPrincipalDoCenario.setPrefSize(836, 680);
    getChildren().add(painelPrincipalDoCenario);//adiciona o painel principal ao root
    
    configuraPainelControle();
    getChildren().add(painelDeControle);//adiciona o painel de controle ao root 
    
    gerenciadorDoAlgoritmo.inicializaThreads();//comeca de fato o programa
  }//fim do inicializarPrograma
  
  /* ***************************************************************
  * Metodo: configuraPainelControle
  * Funcao: configura os componentes de controle e os adiciona ao pane
  * Parametros: nenhum
  * Retorno: void
  *************************************************************** */
  private void configuraPainelControle()
  {
    configuraSliders();
    configuraButtons();
    painelDeControle.getChildren().addAll(velocidadesDeControle);//adiciona os sliders ao painel de controle
    painelDeControle.getChildren().add(botaoDeReset);//adiciona os botoes ao painel de controle
  }//fim do configuraPainelControle
  
   /* ***************************************************************
  * Metodo: configuraButtons
  * Funcao: configura todos os objetos Button do programa
  * Parametros: nenhum
  * Retorno: void
  *************************************************************** */
  private void configuraButtons()
  {
    botaoDeReset.setOnAction(evento -> resetProgram());//adiciona um listener ao botao reset
    botaoDeReset.setPrefWidth(111);//ajusta tamanho e posicao do botaoReset
    botaoDeReset.setPrefHeight(33);
    botaoDeReset.setLayoutX(61);
    botaoDeReset.setLayoutY(125);
  }//fim do configuraButtons
  
  /* ***************************************************************
  * Metodo: configuraSliders
  * Funcao: configura todos os objetos Sliders do programa
  * Parametros: nenhum
  * Retorno: void
  *************************************************************** */
  private void configuraSliders()
  {
    for (int indice = 0; indice < velocidadesDeControle.length; indice++)
    {
      velocidadesDeControle[indice].setId(String.format("slider%d", indice));//adiciona um css Id diferente para cada slider
      velocidadesDeControle[indice].setMax(VALOR_INICIAL_DOS_SLIDERS * 2);//configura valor, tamanho e posicao X dos sliders
      velocidadesDeControle[indice].setValue(VALOR_INICIAL_DOS_SLIDERS);
      velocidadesDeControle[indice].setPrefWidth(153);
      velocidadesDeControle[indice].setLayoutX(40);
 
      int tipoSlider = indice; //adiciona listeners aos sliders de velocidade
      velocidadesDeControle[indice].setOnMouseDragged(evento -> gerenciadorDoAlgoritmo.alteraVelocidades(tipoSlider,
       (int) velocidadesDeControle[tipoSlider].getValue()));
      velocidadesDeControle[indice].setOnMouseClicked(evento -> gerenciadorDoAlgoritmo.alteraVelocidades(tipoSlider,
       (int) velocidadesDeControle[tipoSlider].getValue()));
    }//fim do for
    
    velocidadesDeControle[0].setLayoutY(245);//configura posicao Y do e classe css dos sliders
    velocidadesDeControle[0].getStyleClass().add("sliderProdutor");
    
    velocidadesDeControle[1].setLayoutY(285);
    velocidadesDeControle[1].getStyleClass().add("sliderProdutor");
    
    velocidadesDeControle[2].setLayoutY(430);
    velocidadesDeControle[2].getStyleClass().add("sliderConsumidor");
    
    velocidadesDeControle[3].setLayoutY(470);
    velocidadesDeControle[3].getStyleClass().add("sliderConsumidor");
  }//fim do configuraSliders
  
  
  /* ***************************************************************
  * Metodo: resetProgram
  * Funcao: reinicia todo o programa
  * Parametros: nenhum
  * Retorno: void
  *************************************************************** */
  private void resetProgram()
  {
    for(int indice = 0; indice < velocidadesDeControle.length; indice++)//altera o valor dos slider para seus valor inicial
      velocidadesDeControle[indice].setValue(VALOR_INICIAL_DOS_SLIDERS);
      
    painelPrincipalDoCenario.getChildren().clear();//remove todos os nodes no painel principal
    painelPrincipalDoCenario.getChildren().add(new ImageView("images/imagensCenario/CenarioPrincipal.png"));
    gerenciadorDoAlgoritmo.reset();//reinicia outros componentes do programa
  }//fim do resetProgram
}//fim da classe ControleGeral
