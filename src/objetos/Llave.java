package objetos;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Llave extends SuperObjeto{

    public Llave(){
        nombre = "Llave";
        try{
            imagen = ImageIO.read(getClass().getResourceAsStream("/objetos/key.png"));
        }catch (IOException e){

            e.printStackTrace();
        }
    }
}
