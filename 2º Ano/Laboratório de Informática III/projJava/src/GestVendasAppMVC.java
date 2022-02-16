import Model.IGestVendasModel;
import Controller.IGestVendasController;
import View.IGestVendasView;
import Controller.GestVendasController;
import Model.GestVendasModel;
import View.GestVendasView;




public class GestVendasAppMVC {

    public static void main(String[] args){
        IGestVendasModel model = new GestVendasModel();
        IGestVendasView view = new GestVendasView();
        IGestVendasController controller = new GestVendasController(model, view);
        controller.run();
    }

}
