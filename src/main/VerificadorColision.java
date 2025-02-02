package main;

import Entidad.Entidad;

public class VerificadorColision {

    PanelDeJuego pj;

    public VerificadorColision(PanelDeJuego pj){
        this.pj = pj;

    }

    public void verificarBaldosa(Entidad  entidad){
        int entidadIzquierdaX = entidad.x + entidad.areaSolida.x;
        int entidadDerechaX = entidad.x + entidad.areaSolida.x + entidad.areaSolida.width;
        int entidadArribaY = entidad.y + entidad.areaSolida.y;
        int entidadAbajoY = entidad.y + entidad.areaSolida.y + entidad.areaSolida.height;

        int baldosaNum1, baldosaNum2;

        int entidadIzquierdaColumna = entidadIzquierdaX / pj.tamañoCuadro;
        int entidadDerechaColumna = entidadDerechaX / pj.tamañoCuadro;
        int entidadArribaFila = entidadArribaY / pj.tamañoCuadro;
        int entidadAbajoFila = entidadAbajoY / pj.tamañoCuadro;

        switch (entidad.direccion){
            case"arriba":
                entidadArribaFila = (entidadArribaY - entidad.velocidad)/pj.tamañoCuadro;
                baldosaNum1 = pj.baldosaM.mapaBaldosaNumero[entidadIzquierdaColumna][entidadArribaFila];
                baldosaNum2 = pj.baldosaM.mapaBaldosaNumero[entidadDerechaColumna][entidadArribaFila];
                if(pj.baldosaM.baldosa[baldosaNum1].colision || pj.baldosaM.baldosa[baldosaNum2].colision){
                    entidad.colisionEncendida = true;
                }
                break;
            case "abajo":
                entidadAbajoFila = (entidadAbajoY + entidad.velocidad)/pj.tamañoCuadro;
                baldosaNum1 = pj.baldosaM.mapaBaldosaNumero[entidadIzquierdaColumna][entidadAbajoFila];
                baldosaNum2 = pj.baldosaM.mapaBaldosaNumero[entidadDerechaColumna][entidadAbajoFila];
                if(pj.baldosaM.baldosa[baldosaNum1].colision || pj.baldosaM.baldosa[baldosaNum2].colision){
                    entidad.colisionEncendida = true;
                }
                break;
            case "derecha":
                entidadDerechaColumna = (entidadDerechaX + entidad.velocidad)/pj.tamañoCuadro;
                baldosaNum1 = pj.baldosaM.mapaBaldosaNumero[entidadDerechaColumna][entidadArribaFila];
                baldosaNum2 = pj.baldosaM.mapaBaldosaNumero[entidadDerechaColumna][entidadAbajoFila];
                if(pj.baldosaM.baldosa[baldosaNum1].colision || pj.baldosaM.baldosa[baldosaNum2].colision){
                    entidad.colisionEncendida = true;
                }
                break;
            case "izquierda":
                entidadIzquierdaColumna = (entidadIzquierdaX - entidad.velocidad)/pj.tamañoCuadro;
                baldosaNum1 = pj.baldosaM.mapaBaldosaNumero[entidadIzquierdaColumna][entidadArribaFila];
                baldosaNum2 = pj.baldosaM.mapaBaldosaNumero[entidadIzquierdaColumna][entidadAbajoFila];
                if(pj.baldosaM.baldosa[baldosaNum1].colision || pj.baldosaM.baldosa[baldosaNum2].colision){
                    entidad.colisionEncendida = true;
                }break;
        }
    }

    public int verificarObjeto(Entidad entidad, boolean jugador){
        int indice = 999;

        for(int i =0; i < pj.obj.length; i++){
            if(pj.obj[i] != null){
                entidad.areaSolida.x = entidad.x + entidad.areaSolida.x;
                entidad.areaSolida.y = entidad.y + entidad.areaSolida.y;

                pj.obj[i].areaSolida.x = pj.obj[i].mundoX + pj.obj[i].areaSolida.x;
                pj.obj[i].areaSolida.y = pj.obj[i].mundoY + pj.obj[i].areaSolida.y;

                switch (entidad.direccion){
                    case "arriba":
                        entidad.areaSolida.y -= entidad.velocidad;
                        if(entidad.areaSolida.intersects(pj.obj[i].areaSolida)){
                            if(pj.obj[i].colision){
                                entidad.colisionEncendida = true;
                            }
                            if(jugador){
                                indice = i;
                            }
                        }
                        break;
                    case "abajo":
                        entidad.areaSolida.y += entidad.velocidad;
                        if(entidad.areaSolida.intersects(pj.obj[i].areaSolida)){
                            if(pj.obj[i].colision){
                                entidad.colisionEncendida = true;
                            }
                            if(jugador){
                                indice = i;
                            }
                        }
                        break;
                    case "izquierda":
                        entidad.areaSolida.x -= entidad.velocidad;
                        if(entidad.areaSolida.intersects(pj.obj[i].areaSolida)){
                            if(pj.obj[i].colision){
                                entidad.colisionEncendida = true;
                            }
                            if(jugador){
                                indice = i;
                            }
                        }
                        break;
                    case "derecha":
                        entidad.areaSolida.x += entidad.velocidad;
                        if(entidad.areaSolida.intersects(pj.obj[i].areaSolida)){
                            if(pj.obj[i].colision){
                                entidad.colisionEncendida = true;
                            }
                            if(jugador){
                                indice = i;
                            }
                        }
                        break;
                }
                entidad.areaSolida.x = entidad.areaSolidaDefectoX;
                entidad.areaSolida.y = entidad.areaSolidaDefectoY;
                pj.obj[i].areaSolida.x = pj.obj[i].areaSolidaDefectoX;
                pj.obj[i].areaSolida.y = pj.obj[i].areaSolidaDefectoY;
            }
        }
        return indice;
    }

    public int verificarEntidad(Entidad entidad, Entidad[] objetivo){

        int indice = 999;

        for(int i =0; i < objetivo.length; i++){
            if(objetivo[i] != null){
                entidad.areaSolida.x = entidad.x + entidad.areaSolida.x;
                entidad.areaSolida.y = entidad.y + entidad.areaSolida.y;

                objetivo[i].areaSolida.x = objetivo[i].x + objetivo[i].areaSolida.x;
                objetivo[i].areaSolida.y = objetivo[i].y + objetivo[i].areaSolida.y;

                switch (entidad.direccion){
                    case "arriba":
                        entidad.areaSolida.y -= entidad.velocidad;
                        if(entidad.areaSolida.intersects(objetivo[i].areaSolida)){

                            entidad.colisionEncendida = true;
                            indice = i;

                        }
                        break;
                    case "abajo":
                        entidad.areaSolida.y += entidad.velocidad;
                        if(entidad.areaSolida.intersects(objetivo[i].areaSolida)){

                            entidad.colisionEncendida = true;
                            indice = i;
                        }
                        break;
                    case "izquierda":
                        entidad.areaSolida.x -= entidad.velocidad;
                        if(entidad.areaSolida.intersects(objetivo[i].areaSolida)){

                            entidad.colisionEncendida = true;
                            indice = i;
                        }
                        break;
                    case "derecha":
                        entidad.areaSolida.x += entidad.velocidad;
                        if(entidad.areaSolida.intersects(objetivo[i].areaSolida)){

                            entidad.colisionEncendida = true;
                            indice = i;
                        }
                        break;
                }
                entidad.areaSolida.x = entidad.areaSolidaDefectoX;
                entidad.areaSolida.y = entidad.areaSolidaDefectoY;
                objetivo[i].areaSolida.x = objetivo[i].areaSolidaDefectoX;
                objetivo[i].areaSolida.y = objetivo[i].areaSolidaDefectoY;
            }
        }
        return indice;

    }

    public void verificarJugador(Entidad entidad){
        entidad.areaSolida.x = entidad.x + entidad.areaSolida.x;
        entidad.areaSolida.y = entidad.y + entidad.areaSolida.y;

        pj.jugador.areaSolida.x = pj.jugador.x + pj.jugador.areaSolida.x;
        pj.jugador.areaSolida.y = pj.jugador.y + pj.jugador.areaSolida.y;

        switch (entidad.direccion){
            case "arriba":
                entidad.areaSolida.y -= entidad.velocidad;
                if(entidad.areaSolida.intersects(pj.jugador.areaSolida)){

                    entidad.colisionEncendida = true;

                }
                break;
            case "abajo":
                entidad.areaSolida.y += entidad.velocidad;
                if(entidad.areaSolida.intersects(pj.jugador.areaSolida)){

                    entidad.colisionEncendida = true;
                }
                break;
            case "izquierda":
                entidad.areaSolida.x -= entidad.velocidad;
                if(entidad.areaSolida.intersects(pj.jugador.areaSolida)){

                    entidad.colisionEncendida = true;
                }
                break;
            case "derecha":
                entidad.areaSolida.x += entidad.velocidad;
                if(entidad.areaSolida.intersects(pj.jugador.areaSolida)){

                    entidad.colisionEncendida = true;
                }
                break;
        }
        entidad.areaSolida.x = entidad.areaSolidaDefectoX;
        entidad.areaSolida.y = entidad.areaSolidaDefectoY;
        pj.jugador.areaSolida.x = pj.jugador.areaSolidaDefectoX;
        pj.jugador.areaSolida.y = pj.jugador.areaSolidaDefectoY;
    }
}
