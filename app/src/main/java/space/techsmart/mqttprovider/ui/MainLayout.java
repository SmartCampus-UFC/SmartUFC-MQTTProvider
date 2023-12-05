package space.techsmart.mqttprovider.ui;

import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.spring.security.AuthenticationContext;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.security.core.userdetails.UserDetails;
import space.techsmart.mqttprovider.backend.entities.Device;

import java.util.ArrayList;
import java.util.List;

public class MainLayout extends AppLayout {

    private final transient AuthenticationContext authContext;

    public MainLayout(AuthenticationContext authContext) {
        this.authContext = authContext;

        DrawerToggle toggle = new DrawerToggle();
        H1 title = new H1("MQTTProvider");
        title.addClassNames(
                LumoUtility.FontSize.LARGE,
                LumoUtility.Margin.MEDIUM);
        //title.addClassName("logo");
        //title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                //.set("margin", "0");
        HorizontalLayout
                header =
                authContext.getAuthenticatedUser(UserDetails.class)
                        .map(user -> {
                            Button logout = new Button("Logout", click ->
                                    this.authContext.logout());
                            Span loggedUser = new Span("Welcome " + user.getUsername());
                            return new HorizontalLayout(toggle, title, loggedUser, logout);
                        }).orElseGet(() -> new HorizontalLayout(toggle, title));
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(title);
        header.setWidthFull();
        header.addClassNames(
                LumoUtility.Padding.Vertical.NONE,
                LumoUtility.Padding.Horizontal.MEDIUM);

        //Tabs tabs = getTabs();
        Accordion accordion = new Accordion();

        accordion.add("Parameters",
                createContent(createTab(VaadinIcon.PACKAGE, "Device Groups", new RouterLink(DeviceGroupView.class)),
                        createTab(VaadinIcon.COG_O, "Commands", new RouterLink(CommandView.class))));

        accordion.add("Device Templates",
                createContent(createTab(VaadinIcon.CHART_TIMELINE, "Random Sensors", new RouterLink(TDSensorView.class)),
                        createTab(VaadinIcon.GROUP, "ED Sensors", new RouterLink(EDSensorView.class)),
                        createTab(VaadinIcon.LIGHTBULB, "Actuators", new RouterLink(ActuatorView.class)),
                        createTab(VaadinIcon.MAP_MARKER, "ED Mobile Sensors", new RouterLink(MobileSensorView.class))));

        accordion.add("Datasets",
                createContent(createTab(VaadinIcon.BUS, "Sumo", new RouterLink(SumoTraceView.class)),
                        createTab(VaadinIcon.CLUSTER, "SmartSpec", new RouterLink(SmartSpecDatasetView.class))));

        accordion.add("Experiments",
                createContent(createTab(VaadinIcon.AUTOMATION, "Devices", new RouterLink(DeviceView.class)),
                        createTab(VaadinIcon.LIST, "Scenarios", new RouterLink(ScenarioView.class)),
                        createTab(VaadinIcon.PLAY, "Run Generator", new RouterLink(PlayerView.class))));


        addToDrawer(accordion);
        addToNavbar(header);
    }

    /*private Tabs getTabs() {
        Accordion accordion = new Accordion();

        Tabs tabs = new Tabs();
        tabs.add(createTab(VaadinIcon.LIST, "Scenarios", new RouterLink(ScenarioView.class)),
                createTab(VaadinIcon.BUS, "Sumo ", new RouterLink(SumoTraceView.class)),
                createTab(VaadinIcon.BUS, "SmartSPEC", new RouterLink(SmartSpecDatasetView.class)),
                createTab(VaadinIcon.PACKAGE, "Device Groups", new RouterLink(DeviceGroupView.class)),
                createTab(VaadinIcon.AUTOMATION, "Devices", new RouterLink(DeviceView.class)),
                createTab(VaadinIcon.COG_O, "Commands", new RouterLink(CommandView.class)),
                createTab(VaadinIcon.LIGHTBULB, "Actuators", new RouterLink(ActuatorView.class)),
                createTab(VaadinIcon.CHART_TIMELINE, "TD Sensors", new RouterLink(TDSensorView.class)),
                createTab(VaadinIcon.GROUP, "ED Sensors", new RouterLink(EDSensorView.class)),
                createTab(VaadinIcon.MAP_MARKER, "Mobile Sensors", new RouterLink(MobileSensorView.class)),
                createTab(VaadinIcon.PLAY, "Run Generator", new RouterLink(PlayerView.class)));
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        return tabs;
    }*/



    private VerticalLayout createContent(Tab... tabs) {
        VerticalLayout content = new VerticalLayout();
        content.setPadding(false);
        content.setSpacing(false);
        content.add(tabs);
        return content;
    }

    private Tab createTab(VaadinIcon viewIcon, String viewName, RouterLink link) {
        Icon icon = viewIcon.create();
        icon.getStyle().set("box-sizing", "border-box")
                .set("margin-inline-end", "var(--lumo-space-m)")
                .set("margin-inline-start", "var(--lumo-space-xs)")
                .set("padding", "var(--lumo-space-xs)");

        link.add(icon, new Span(viewName));
        link.setTabIndex(-1);

        return new Tab(link);
    }

}
