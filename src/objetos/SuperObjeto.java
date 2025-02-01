package objetos;
import main.PanelDeJuego;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObjeto {

    public BufferedImage imagen;
    public String nombre;
    public boolean colision = false;
    public int mundoX, mundoY;
    public Rectangle areaSolida = new Rectangle(0,0,48,48);
    public int areaSolidaDefectoX =0;
    public int areaSolidaDefectoY =0;

    public void dibujar(Graphics2D g2, PanelDeJuego pj){
        g2.drawImage(imagen, mundoX, mundoY, pj.tamañoCuadro, pj.tamañoCuadro, null);
    }
}


