package objetos;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Puerta extends SuperObjeto{
        String direccion;
    public Puerta(String direccion){
        nombre = "Puerta";
        this.direccion = direccion;
        try{
            imagen = ImageIO.read(getClass().getResourceAsStream("/objetos/door.png"));
        }catch (IOException e){

            e.printStackTrace();
        }
        colision = true;
    }
}
