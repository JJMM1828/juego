package main;
import Entidad.Entidad;
import Entidad.Jugador;
import baldosa.Baldosa;
import baldosa.BaldosaManager;
import objetos.SuperObjeto;
import java.awt.*;
import java.util.Random;
import javax.swing.JPanel;

public class PanelDeJuego extends JPanel{

    //implentacion arbol (se puede modificar)
    int numeroIdentificacion;
    PanelDeJuego izquierda, derecha;

    //hasta aqui
    volatile String eleccionPuerta;


    //Ajustes de ventana
    final int tamañoOriginalCuadro = 16;
    final int escalar = 3;
    public final int tamañoCuadro = tamañoOriginalCuadro * escalar;
    public final int tamañoColumnasPantalla =16;
    public final int tamañoFilasPantalla = 12;
    public final int anchoPantalla = tamañoCuadro * tamañoColumnasPantalla;
    public final int alturaPantalla = tamañoCuadro * tamañoFilasPantalla;

    int FPS = 60;

    //intanciar otras clases

    //listo
    BaldosaManager baldosaM;
    //dejar quieto
    ManipuladorTeclas teclas = new ManipuladorTeclas();
    public VerificadorColision vColision = new VerificadorColision(this);
    public ColocadorObjetos cObjetos;
    public Jugador jugador = new Jugador(this, teclas);
    public SuperObjeto obj[] = new SuperObjeto[3];
    public Entidad npc[] = new Entidad[10];
    public int posX[];
    public int posY[];




    //Contructor
    public PanelDeJuego(){
        this.setPreferredSize(new Dimension(anchoPantalla, alturaPantalla));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(teclas);
        this.setFocusable(true);
    }
    //segundo constructor
    public PanelDeJuego(String mapa, int numeroEnemigos, int posX[], int posY[]){
        this.setPreferredSize(new Dimension(anchoPantalla, alturaPantalla));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(teclas);
        this.setFocusable(true);

        this.numeroIdentificacion = new Random().nextInt(100);


        this.posX = posX;
        this.posY = posY;


        baldosaM = new BaldosaManager(this, mapa);
        cObjetos = new ColocadorObjetos(this);

        cObjetos.setObjeto();
        cObjetos.setNPC(numeroEnemigos, posX, posY);



    }





    public void terminarNivel(){
        if(obj[1] == null){
             eleccionPuerta = "izquierda";
        }
        else if(obj[2] == null){
            eleccionPuerta = "derecha";
        }

    }



    public void actualizar(){
        jugador.actualizar();
        for(int i =0; i<npc.length; i++){
            if(npc[i] != null){
                npc[i].actualizar();
            }
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        baldosaM.dibujar(g2);

        //dibujar objetos
        for(int i =0; i < obj.length; i++ ){
            if(obj[i]!= null){
                obj[i].dibujar(g2, this);
            }
        }

        //dibujar NPCs
        for(int i =0; i < npc.length; i++){
            if(npc[i] != null){
                npc[i].dibujar(g2);
            }
        }
        // dibujar jugador
        jugador.dibujar(g2);
        g2.dispose();
    }
}
