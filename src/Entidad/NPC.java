package Entidad;

import main.PanelDeJuego;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Random;

public class NPC extends Entidad{

    public NPC(PanelDeJuego pj){
        super(pj);
        direccion ="abajo";
        velocidad = 1;

        areaSolida = new Rectangle();
        areaSolida.x = 8;
        areaSolida.y = 16;
        areaSolidaDefectoX = areaSolida.x;
        areaSolidaDefectoY = areaSolida.y;
        areaSolida.width = 32;
        areaSolida.height = 32;
        getImagen();
    }

    public void getImagen(){
        try{

            arriba1 = ImageIO.read(getClass().getResourceAsStream("/npc/redslime_down_1.png"));
            arriba2 = ImageIO.read(getClass().getResourceAsStream("/npc/redslime_down_2.png"));
            abajo1 = ImageIO.read(getClass().getResourceAsStream("/npc/redslime_down_1.png"));
            abajo2 = ImageIO.read(getClass().getResourceAsStream("/npc/redslime_down_2.png"));
            izquierda1 = ImageIO.read(getClass().getResourceAsStream("/npc/redslime_down_1.png"));
            izquierda2 = ImageIO.read(getClass().getResourceAsStream("/npc/redslime_down_2.png"));
            derecha1 = ImageIO.read(getClass().getResourceAsStream("/npc/redslime_down_1.png"));
            derecha2 = ImageIO.read(getClass().getResourceAsStream("/npc/redslime_down_2.png"));

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setAccion(){

        bloquearAccion++;
        if(bloquearAccion == 120){
            Random random = new Random();
            int i = random.nextInt(100)+1;
            if(i <= 25){direccion = "arriba";}
            if(i > 25 && i <= 50){direccion = "abajo";}
            if(i > 50 && i <= 75){direccion = "izquierda";}
            if(i > 75 && i <= 100){direccion = "derecha";}
            bloquearAccion =0;
        }
    }
}
