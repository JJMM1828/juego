package baldosa;
import main.PanelDeJuego;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class BaldosaManager {

    PanelDeJuego pj;
    public Baldosa[] baldosa;
    public int mapaBaldosaNumero[][];

    public BaldosaManager(PanelDeJuego pj, String mapa){

        this.pj = pj;
        baldosa = new Baldosa[10];
        mapaBaldosaNumero = new int[pj.tamañoColumnasPantalla][pj.tamañoFilasPantalla];
        getImagenBaldosa();
        cargarMapa(mapa);
    }

    public void getImagenBaldosa(){

        try{
            baldosa[0] =new Baldosa();
            baldosa[0].imagen = ImageIO.read(getClass().getResourceAsStream("/baldosas/grass.png"));

            baldosa[1] =new Baldosa();
            baldosa[1].imagen = ImageIO.read(getClass().getResourceAsStream("/baldosas/wall.png"));
            baldosa[1].colision = true;

            baldosa[2] =new Baldosa();
            baldosa[2].imagen = ImageIO.read(getClass().getResourceAsStream("/baldosas/water.png"));
            baldosa[2].colision = true;

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void cargarMapa(String filePath){

        try{

            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int columna = 0;
            int fila = 0;

            while(columna < pj.tamañoColumnasPantalla && fila < pj.tamañoFilasPantalla){

                String linea = br.readLine();
                while(columna < pj.tamañoColumnasPantalla){
                    String numeros[] = linea.split(" ");
                    int numero = Integer.parseInt(numeros[columna]);
                    mapaBaldosaNumero[columna][fila] = numero;
                    columna ++;
                }
                if(columna == pj.tamañoColumnasPantalla){
                    columna =0;
                    fila++;
                }
            }
            br.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void dibujar(Graphics2D g2){
        int columna =0;
        int fila = 0;
        int x =0;
        int y =0;

        while(columna < pj.tamañoColumnasPantalla && fila < pj.tamañoFilasPantalla){

            int baldosaNumero = mapaBaldosaNumero[columna][fila];
            g2.drawImage(baldosa[baldosaNumero].imagen,x,y,pj.tamañoCuadro, pj.tamañoCuadro, null );
            columna++;
            x += pj.tamañoCuadro;

            if(columna == pj.tamañoColumnasPantalla){
                columna = 0;
                x = 0;
                fila++;
                y += pj.tamañoCuadro;
            }
        }
    }
}
