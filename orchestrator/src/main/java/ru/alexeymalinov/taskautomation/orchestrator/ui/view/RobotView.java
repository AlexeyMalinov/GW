package ru.alexeymalinov.taskautomation.orchestrator.ui.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.RobotEntity;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.RobotsGroupEntity;
import ru.alexeymalinov.taskautomation.orchestrator.db.repository.RobotRepository;
import ru.alexeymalinov.taskautomation.orchestrator.db.repository.RobotsGroupRepository;
import ru.alexeymalinov.taskautomation.orchestrator.ui.editor.RobotEditor;

import javax.annotation.PostConstruct;

@Route
public class RobotView extends AbstractView<RobotRepository, RobotEntity, RobotEditor> implements HasUrlParameter<Integer> {

    RobotsGroupEntity robotsGroupEntity;
    RobotsGroupRepository robotsGroupRepository;

    RobotView(RobotRepository repository, RobotsGroupRepository robotsGroupRepository, RobotEditor editor) {
        super(repository, editor, new Grid<>(RobotEntity.class));
        this.robotsGroupRepository = robotsGroupRepository;
    }

    @Override
    protected void print(){
        listItem();

        Button addRobotButton = new Button("New robot", VaadinIcon.PLUS.create());
        addRobotButton.addClickListener(e -> editor.edit(new RobotEntity(robotsGroupEntity)));

        Button backRobotGroupButton = new Button("Groups of robots", VaadinIcon.ARROW_BACKWARD.create());
        backRobotGroupButton.addClickListener(e -> backRobotGroupButton.getUI().ifPresent((ui -> ui.navigate(RobotsGroupView.class))));

        HorizontalLayout actions = new HorizontalLayout(addRobotButton, backMainButton, backRobotGroupButton);

        add(actions, grid, editor);
    }

    @Override
    protected void listItem() {
          grid.setItems(repository.findByRobotsGroup(robotsGroupEntity));
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Integer integer) {
        robotsGroupEntity = robotsGroupRepository.findById(integer).get();
        print();
    }
}
