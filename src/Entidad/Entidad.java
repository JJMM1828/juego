package Entidad;

import main.PanelDeJuego;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entidad {

    PanelDeJuego pj;

    public int x, y;
    public int velocidad;
    public BufferedImage arriba1, arriba2, abajo1, abajo2, derecha1, derecha2, izquierda1, izquierda2;
    public String direccion;

    public int spriteContador =0;
    public int spriteNumero = 1;

    public Rectangle areaSolida = new Rectangle(0,0,48,48);
    public boolean colisionEncendida = false;

    public int areaSolidaDefectoX, areaSolidaDefectoY;

    public int  bloquearAccion =0;

    public Entidad(PanelDeJuego pj){
        this.pj = pj;
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

    public void setAccion(){

    }

    public void actualizar(){
        setAccion();
        colisionEncendida = false;
        pj.vColision.verificarBaldosa(this);
        pj.vColision.verificarObjeto(this,false);
        pj.vColision.verificarJugador(this);

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

