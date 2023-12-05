package space.techsmart.mqttprovider.ui;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.vaadin.crudui.crud.impl.GridCrud;
import space.techsmart.mqttprovider.backend.entities.Command;
import space.techsmart.mqttprovider.backend.services.CommandService;

@Route(value="commands", layout = MainLayout.class)
@RolesAllowed("ADMIN")
@PageTitle("Commands | MQTTProvider")
public class CommandView extends VerticalLayout {

    public CommandView(CommandService service) {
        var crud = new GridCrud<>(Command.class, service);
        crud.getGrid().setColumns("name","state");
        //crud.getGrid().addItemClickListener(event -> UI.getCurrent().navigate("/devices?scenario=" + event.getItem().getName()));
        crud.getCrudFormFactory().setVisibleProperties("name","state");
        add(
                new H2("Commands"),
                crud
        );
    }
}
