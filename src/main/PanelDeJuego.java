package main;
import Entidad.Entidad;
import Entidad.Jugador;
import baldosa.Baldosa;
import baldosa.BaldosaManager;
import objetos.SuperObjeto;
import java.awt.*;
import javax.swing.JPanel;

public class PanelDeJuego extends JPanel  implements Runnable{

    //Ajustes de ventana
    final int tamañoOriginalCuadro = 16;
    final int escalar = 3;
    public final int tamañoCuadro = tamañoOriginalCuadro * escalar;
    public final int tamañoColumnasPantalla =16;
    public final int tamañoFilasPantalla = 12;
    public final int anchoPantalla = tamañoCuadro * tamañoColumnasPantalla;
    public final int alturaPantalla = tamañoCuadro * tamañoFilasPantalla;

    int FPS = 60;

    BaldosaManager baldosaM = new BaldosaManager(this);
    ManipuladorTeclas teclas = new ManipuladorTeclas();
    Thread hiloJuego;
    public VerificadorColision vColision = new VerificadorColision(this);
    public ColocadorObjetos cObjetos = new ColocadorObjetos(this);

    public Jugador jugador = new Jugador(this, teclas);
    public SuperObjeto obj[] = new SuperObjeto[10];
    public Entidad npc[] = new Entidad[10];

    //posiciones por defecto de jugador
    int jugadorX = 100;
    int jugadorY = 100;
    int jugadorVelocidad = 4;

    public PanelDeJuego(){
        this.setPreferredSize(new Dimension(anchoPantalla, alturaPantalla));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(teclas);
        this.setFocusable(true);
    }

    public void setupJuego(){
        cObjetos.setObjeto();
        cObjetos.setNPC();
    }

    public void iniciarHiloJuego(){
        hiloJuego = new Thread(this);
        hiloJuego.start();
    }

    //Game loop -buble de juego
    @Override
    public void run() {

        double intervaloDibujo = 1000000000/FPS;
        double delta = 0;
        long ultimoTiempo = System.nanoTime();
        long tiempoActual;

        while(hiloJuego != null){

            tiempoActual = System.nanoTime();
            delta +=(tiempoActual - ultimoTiempo)/intervaloDibujo;
            ultimoTiempo = tiempoActual;
            
            if(delta >=1){
                actualizar();
                repaint();
                delta--;
            }
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

        for(int i =0; i < obj.length; i++ ){
            if(obj[i]!= null){
                obj[i].dibujar(g2, this);
            }
        }

        //NPC
        for(int i =0; i < npc.length; i++){
            if(npc[i] != null){
                npc[i].dibujar(g2);
            }
        }


        // juegador
        jugador.dibujar(g2);
        g2.dispose();
    }
}
