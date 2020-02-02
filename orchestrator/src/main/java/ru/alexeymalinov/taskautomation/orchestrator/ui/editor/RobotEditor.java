package ru.alexeymalinov.taskautomation.orchestrator.ui.editor;

import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.RobotEntity;
import ru.alexeymalinov.taskautomation.orchestrator.db.repository.RobotRepository;

@SpringComponent
@UIScope
public class RobotEditor extends AbstractEditor<RobotRepository, RobotEntity> {

    Binder<RobotEntity> binder = new Binder<>(RobotEntity.class);

    TextField name = new TextField("Name");

    public RobotEditor(RobotRepository repository) {
        super(repository);
        printEditor();
    }

    @Override
    public void printEditor() {
        setSpacing(true);
        binder.bindInstanceFields(this);
        add(name, actions);
    }

    @Override
    public void delete() {
        RobotEntity robotEntity = repository.findByNameAndRobotsGroup(entity.getName(), entity.getRobotsGroup());
        repository.delete(robotEntity);
        changeHandler.onChange();
    }

    @Override
    public void edit(RobotEntity robotEntity) {
        if (robotEntity == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = robotEntity.getName() != null;
        if (persisted) {
            entity = repository.findByNameAndRobotsGroup(robotEntity.getName(), robotEntity.getRobotsGroup());
        } else {
            this.entity = robotEntity;
        }
        cancel.setVisible(persisted);
        binder.setBean(this.entity);

        setVisible(true);

        name.focus();
    }
}
