package space.techsmart.mqttprovider.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.util.concurrent.ListenableFuture;
import space.techsmart.mqttprovider.backend.entities.Scenario;
import space.techsmart.mqttprovider.backend.services.FileSystemStorageService;
import space.techsmart.mqttprovider.backend.services.PlayerService;
import space.techsmart.mqttprovider.backend.services.ScenarioService;

@Route(value="player", layout = MainLayout.class)
@RolesAllowed("ADMIN")
@PageTitle("Player | MQTTProvider")
public class PlayerView extends VerticalLayout {

    private PlayerService playerService;
    private ListenableFuture<String> future;
    private ProgressBar progressBar = new ProgressBar();
    private Button cancelButton = new Button("Cancel task execution");

    public PlayerView(PlayerService playerService, ScenarioService scenarioService, FileSystemStorageService fileSystemStorageService) {

        this.playerService = playerService;

        TextField brokerIP = new TextField("Broker Address");
        TextField brokerPort = new TextField("Broker Port");
        TextField timeOfExperiment = new TextField("Duration in Seconds");
        timeOfExperiment.setAllowedCharPattern("[0-9]");

        ComboBox<Scenario> scenarioBox = new ComboBox<>("Scenario");
        scenarioBox.setItems(scenarioService.findAll());
        scenarioBox.setItemLabelGenerator(Scenario::getName);

        progressBar.setWidth("15em");
        progressBar.setIndeterminate(true);

        progressBar.setVisible(false);
        cancelButton.setVisible(false);

        Button startButton = new Button("Start Generator", clickEvent -> {
            UI ui = clickEvent.getSource().getUI().orElseThrow();
            future = playerService.playerTask(brokerIP.getValue(),brokerPort.getValue(),Long.parseLong(timeOfExperiment.getValue()),scenarioBox.getValue(),fileSystemStorageService);

            progressBar.setVisible(true);
            cancelButton.setVisible(true);
            cancelButton.addClickListener(e -> cancelTask());

            Notification.show("Player Started!");

            future.addCallback(
                    successResult -> updateUi(ui, "Task finished: " + successResult),
                    failureException -> updateUi(ui, "Task failed: " + failureException.getMessage())
            );
        });

        add(new HorizontalLayout(brokerIP, brokerPort, scenarioBox), timeOfExperiment, startButton, new HorizontalLayout(progressBar, cancelButton));//, isBlockedButton);
    }

    private void cancelTask() {
        playerService.abortDevices();
        future.cancel(true);
    }

    private void updateUi(UI ui, String result) {
        ui.access(() -> {
            Notification.show(result);
            progressBar.setVisible(false);
            cancelButton.setVisible(false);
        });
    }

}
