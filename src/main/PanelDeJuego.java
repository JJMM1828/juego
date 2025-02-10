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

    public int numeroIdentificacion;
    public volatile String eleccionPuerta ="";


    //Ajustes de ventana
    final int tamañoOriginalCuadro = 16;
    final int escalar = 3;
    public final int tamañoCuadro = tamañoOriginalCuadro * escalar;
    public final int tamañoColumnasPantalla =18;
    public final int tamañoFilasPantalla = 14;
    public final int anchoPantalla = tamañoCuadro * tamañoColumnasPantalla;
    public final int alturaPantalla = tamañoCuadro * tamañoFilasPantalla;

    //intanciar otras clases
    public BaldosaManager baldosaM;
    public ManipuladorTeclas teclas = new ManipuladorTeclas();
    public VerificadorColision vColision = new VerificadorColision(this);
    public ColocadorObjetos cObjetos;
    public Jugador jugador = new Jugador(this, teclas);
    public SuperObjeto obj[] = new SuperObjeto[3];
    public Entidad npc[] = new Entidad[20];
    public int posX[];
    public int posY[];


    //constructor
    public PanelDeJuego(String mapa, int numeroEnemigos, int posX[], int posY[]){
        configurarPanel();
        this.numeroIdentificacion = new Random().nextInt(100);
        this.posX = posX;
        this.posY = posY;
        baldosaM = new BaldosaManager(this, mapa);
        cObjetos = new ColocadorObjetos(this);
        cObjetos.setObjeto();
        cObjetos.setNPC(numeroEnemigos, posX, posY);
    }
    //constructor para especificar coordenadas llave- puerta
    public PanelDeJuego(String mapa, int numeroEnemigos, int posX[], int posY[], int XLLave, int YLlave, int XPuerta1, int YPuerta1, int XPuerta2, int YPuerta2){
        configurarPanel();
        this.numeroIdentificacion = new Random().nextInt(100);
        this.posX = posX;
        this.posY = posY;
        baldosaM = new BaldosaManager(this, mapa);
        cObjetos = new ColocadorObjetos(this);
        cObjetos.setObjeto();
        obj[0].mundoX = XLLave * tamañoCuadro;
        obj[0].mundoY = YLlave * tamañoCuadro;
        obj[1].mundoX = XPuerta1 * tamañoCuadro;
        obj[1].mundoY = YPuerta1 * tamañoCuadro;
        obj[2].mundoX = XPuerta2 * tamañoCuadro;
        obj[2].mundoY = YPuerta2 * tamañoCuadro;
        cObjetos.setNPC(numeroEnemigos, posX, posY);
    }
    public void configurarPanel(){
        this.setPreferredSize(new Dimension(anchoPantalla, alturaPantalla));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(teclas);
        this.setFocusable(true);
    }

    public void setNumeroIdentificacion(int id){
        this.numeroIdentificacion = id;
    }
    public int getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void terminarNivel() {
        if (obj[1] == null) {
            eleccionPuerta = "izquierda";
        } else if (obj[2] == null) {
            eleccionPuerta = "derecha";
        }
    }



    public void actualizar() {
        jugador.actualizar();
        for (Entidad e : npc) {
            if (e != null) {
                e.actualizar();
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (baldosaM != null) {
            baldosaM.dibujar(g2);
        }
        for (SuperObjeto objeto : obj) {
            if (objeto != null) {
                objeto.dibujar(g2, this);
            }
        }
        for (Entidad e : npc) {
            if (e != null) {
                e.dibujar(g2);
            }
        }
        jugador.dibujar(g2);
        g2.dispose();
    }
}
