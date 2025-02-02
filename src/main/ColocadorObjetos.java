package main;
import Entidad.NPC;
import objetos.Llave;
import objetos.Puerta;

public class ColocadorObjetos {

    PanelDeJuego pj;


    public ColocadorObjetos(PanelDeJuego pj){
        this.pj = pj;

    }

    public void setObjeto(){
        pj.obj[0] = new Llave();
        pj.obj[0].mundoX = 5 * pj.tamañoCuadro;
        pj.obj[0].mundoY = 5 * pj.tamañoCuadro;

        pj.obj[1] = new Puerta("izquierda");
        pj.obj[1].mundoX = 14 * pj.tamañoCuadro;
        pj.obj[1].mundoY = 7 * pj.tamañoCuadro;


        pj.obj[2] = new Puerta("derecha");
        pj.obj[2].mundoX = 14 * pj.tamañoCuadro;
        pj.obj[2].mundoY = 5 * pj.tamañoCuadro;
    }

    public void setNPC(int numeroEnemigos, int[] posX, int[]posY){
        for(int i =0; i < numeroEnemigos; i++){
            if(numeroEnemigos < 10 && numeroEnemigos>0){
                pj.npc[i] = new NPC(pj);
                pj.npc[i].x = pj.tamañoCuadro * posX[i];
                pj.npc[i].y = pj.tamañoCuadro * posY[i];
            }else{
                break;
            }

        }
    }
}
