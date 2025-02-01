package Entidad;
import main.ManipuladorTeclas;
import main.PanelDeJuego;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Jugador extends Entidad{


    ManipuladorTeclas teclas;
    int tieneLlaves =0;

    public Jugador(PanelDeJuego pj, ManipuladorTeclas teclas){


        super(pj);
        this.teclas = teclas;

        areaSolida = new Rectangle();
        areaSolida.x = 8;
        areaSolida.y = 16;
        areaSolidaDefectoX = areaSolida.x;
        areaSolidaDefectoY = areaSolida.y;
        areaSolida.width = 32;
        areaSolida.height = 32;

        setValoresDefecto();
        getImagenJuegador();
    }

    public void setValoresDefecto(){
        x = 100;
        y = 100;
        velocidad = 4;
        direccion = "abajo";
    }
    public void getImagenJuegador(){
        try{

            arriba1 = ImageIO.read(getClass().getResourceAsStream("/jugador/boy_up_1.png"));
            arriba2 = ImageIO.read(getClass().getResourceAsStream("/jugador/boy_up_2.png"));
            abajo1 = ImageIO.read(getClass().getResourceAsStream("/jugador/boy_down_1.png"));
            abajo2 = ImageIO.read(getClass().getResourceAsStream("/jugador/boy_down_2.png"));
            izquierda1 = ImageIO.read(getClass().getResourceAsStream("/jugador/boy_left_1.png"));
            izquierda2 = ImageIO.read(getClass().getResourceAsStream("/jugador/boy_left_2.png"));
            derecha1 = ImageIO.read(getClass().getResourceAsStream("/jugador/boy_right_1.png"));
            derecha2 = ImageIO.read(getClass().getResourceAsStream("/jugador/boy_right_2.png"));

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void actualizar(){

        if(teclas.arribaPresionado || teclas.abajoPresionado || teclas.derechaPresionado || teclas.izquierdaPresionado){
            if(teclas.arribaPresionado){
                direccion = "arriba";

            }
            else if(teclas.abajoPresionado){
                direccion = "abajo";

            }
            else if (teclas.derechaPresionado) {
                direccion = "derecha";

            }
            else if (teclas.izquierdaPresionado) {
                direccion = "izquierda";

            }

            //colisiones
            colisionEncendida = false;
            pj.vColision.verificarBaldosa(this);


            int objIndice = pj.vColision.verificarObjeto(this, true);
            tomarObjeto(objIndice);

            //NPC colision
            int npcIndice = pj.vColision.verificarEntidad(this, pj.npc);
            interacturarNPC(npcIndice);



            if(colisionEncendida ==false){
                switch (direccion){
                    case "arriba": y -= velocidad; break;
                    case "abajo": y += velocidad; break;
                    case "derecha": x += velocidad; break;
                    case "izquierda": x -= velocidad; break;
                }
            }

            spriteContador++;
            if(spriteContador > 12){
                if(spriteNumero == 1){
                    spriteNumero = 2;
                }
                else if(spriteNumero ==2){
                    spriteNumero = 1;
                }
                spriteContador = 0;
            }
        }
    }

    public void tomarObjeto(int i){
        if(i != 999){
            String nombreObjeto = pj.obj[i].nombre;

            switch (nombreObjeto){
                case "Llave":
                    tieneLlaves++;
                    pj.obj[i] = null;
                    break;
                case "Puerta":
                    if(tieneLlaves > 0){
                        pj.obj[i] = null;
                        tieneLlaves--;
                    }
                    break;
            }
        }
    }

    public void interacturarNPC(int i){
        if(i != 999){
            System.out.println("golpe");
        }
    }

    public void dibujar(Graphics2D g2){
        BufferedImage imagen = null;

        switch (direccion){
            case "arriba":
                if(spriteNumero ==1){
                    imagen = arriba1;
                }
                if(spriteNumero == 2){
                    imagen = arriba2;
                }
                break;
            case "abajo":
                if(spriteNumero ==1){
                    imagen = abajo1;
                }
                if(spriteNumero == 2){
                    imagen = abajo2;
                }
                break;
            case "derecha":
                if(spriteNumero ==1){
                    imagen = derecha1;
                }
                if(spriteNumero == 2){
                    imagen = derecha2;
                }
                break;
            case "izquierda":
                if(spriteNumero ==1){
                    imagen = izquierda1;
                }
                if(spriteNumero == 2){
                    imagen = izquierda2;
                }
                break;
        }
        g2.drawImage(imagen, x, y, pj.tamañoCuadro, pj.tamañoCuadro, null);
    }
}
