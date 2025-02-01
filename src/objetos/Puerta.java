package objetos;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Puerta extends SuperObjeto{

    public Puerta(){
        nombre = "Puerta";
        try{
            imagen = ImageIO.read(getClass().getResourceAsStream("/objetos/door.png"));
        }catch (IOException e){

            e.printStackTrace();
        }
        colision = true;
    }
}
