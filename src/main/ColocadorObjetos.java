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

        pj.obj[1] = new Puerta();
        pj.obj[1].mundoX = 14 * pj.tamañoCuadro;
        pj.obj[1].mundoY = 7 * pj.tamañoCuadro;

        pj.obj[2] = new Puerta();
        pj.obj[2].mundoX = 14 * pj.tamañoCuadro;
        pj.obj[2].mundoY = 5 * pj.tamañoCuadro;
    }

    public void setNPC(){
        pj.npc[0] = new NPC(pj);
        pj.npc[0].x = pj.tamañoCuadro *4;
        pj.npc[0].y = pj.tamañoCuadro * 4;

        pj.npc[1] = new NPC(pj);
        pj.npc[1].x = pj.tamañoCuadro *6;
        pj.npc[1].y = pj.tamañoCuadro * 6;

        pj.npc[2] = new NPC(pj);
        pj.npc[2].x = pj.tamañoCuadro *8;
        pj.npc[2].y = pj.tamañoCuadro * 8;


    }


}
