package ru.alexeymalinov.taskautomation.orchestrator.ui.editor;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.StageEntity;
import ru.alexeymalinov.taskautomation.orchestrator.db.repository.StageRepository;
import ru.alexeymalinov.taskautomation.orchestrator.ui.view.StageView;

@SpringComponent
@UIScope
public class PipelineEditor extends AbstractEditor<StageRepository, StageEntity> {

    TextField name = new TextField("Name");
    IntegerField previousStageId = new IntegerField("PreviousId");
    IntegerField nextStageId = new IntegerField("NextId");

    Button edit = new Button("Edit", VaadinIcon.EDIT.create());

    Binder<StageEntity> binder = new Binder<>(StageEntity.class);

    public PipelineEditor(StageRepository repository) {
        super(repository);
        printEditor();
    }

    @Override
    public void printEditor() {
        binder.bindInstanceFields(this);
        actions.add(edit);
        add(name, previousStageId, nextStageId, actions);
        edit.addClickListener(e -> UI.getCurrent().navigate(StageView.class, entity.getId()));

    }

    @Override
    public void save() {
        repository.save(entity);
        changeHandler.onChange();
    }

    @Override
    public void delete() {
        StageEntity stageEntity = repository.findByNameAndPipeline(entity.getName(), entity.getPipeline());
        repository.delete(stageEntity);
        changeHandler.onChange();
    }

    @Override
    public void edit(StageEntity stageEntity) {
        if (stageEntity == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = stageEntity.getName() != null;
        if (persisted) {
            entity = repository.findByNameAndPipeline(stageEntity.getName(), stageEntity.getPipeline());
        } else {
            this.entity = stageEntity;
        }
        cancel.setVisible(persisted);
        edit.setVisible(persisted);

        binder.setBean(this.entity);

        setVisible(true);

        name.focus();
    }
}
