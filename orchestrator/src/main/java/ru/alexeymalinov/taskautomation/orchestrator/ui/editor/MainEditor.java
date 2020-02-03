package ru.alexeymalinov.taskautomation.orchestrator.ui.editor;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.PipelineEntity;
import ru.alexeymalinov.taskautomation.orchestrator.db.repository.PipelineRepository;
import ru.alexeymalinov.taskautomation.orchestrator.ui.view.PipelineView;

@SpringComponent
@UIScope
public class MainEditor extends AbstractEditor<PipelineRepository, PipelineEntity> {

    private PipelineEntity entity;

    TextField name = new TextField("Name");

    Button edit = new Button("Edit", VaadinIcon.EDIT.create());

    Binder<PipelineEntity> binder = new Binder<>(PipelineEntity.class);

    public MainEditor(PipelineRepository repository) {
        super(repository);
        printEditor();
    }

    @Override
    public void printEditor() {
        binder.bindInstanceFields(this);
        edit.addClickListener(e -> edit.getUI().ifPresent(ui -> ui.navigate(PipelineView.class, entity.getId())));
        actions.add(edit);
        add(name, actions);
    }

    @Override
    public void save() {
        repository.save(entity);
        changeHandler.onChange();
    }

    @Override
    public void delete() {
        PipelineEntity pipelineEntity = repository.findByName(entity.getName());
        repository.delete(pipelineEntity);
        changeHandler.onChange();
    }

    @Override
    public void edit(PipelineEntity pipelineEntity) {
        if (pipelineEntity == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = pipelineEntity.getName() != null;
        if (persisted) {
            entity = repository.findByName(pipelineEntity.getName());
        } else {
            entity = pipelineEntity;
        }
        cancel.setVisible(persisted);
        edit.setVisible(persisted);

        binder.setBean(entity);

        setVisible(true);

        name.focus();
    }

}
