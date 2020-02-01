package ru.alexeymalinov.taskautomation.orchestrator.ui.view;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.PipelineEntity;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.StageEntity;
import ru.alexeymalinov.taskautomation.orchestrator.db.repository.PipelineRepository;
import ru.alexeymalinov.taskautomation.orchestrator.db.repository.StageRepository;
import ru.alexeymalinov.taskautomation.orchestrator.ui.editor.PipelineEditor;

@Route
public class PipelineView extends VerticalLayout implements HasUrlParameter<Integer> {

    private StageRepository stageRepository;
    private PipelineRepository pipelineRepository;
    private PipelineEditor editor;
    private Grid<StageEntity> grid;
    private PipelineEntity pipelineEntity;

    PipelineView(StageRepository stageRepository, PipelineRepository pipelineRepository, PipelineEditor editor){
        this.stageRepository = stageRepository;
        this.pipelineRepository = pipelineRepository;
        this.editor = editor;
        this.grid = new Grid<>(StageEntity.class);
    }

    private void print(){
        listStages();

        Button addStageButton = new Button("Create Stage", VaadinIcon.PLUS.create());

        Button backPipelineButton = new Button("Pipelines", VaadinIcon.ARROW_BACKWARD.create());
        backPipelineButton.addClickListener(e -> backPipelineButton.getUI().ifPresent((ui -> ui.navigate(MainView.class))));

        HorizontalLayout actions = new HorizontalLayout(addStageButton, backPipelineButton);
        add(actions, grid, this.editor);

        grid.removeColumnByKey("pipeline");
        grid.removeColumnByKey("jobs");
        grid.setHeight("300px");
        grid.setColumns("id","name","previousStageId", "nextStageId");
        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listStages();
        });

        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editStage(e.getValue());
        });

        addStageButton.addClickListener(e -> editor.editStage(new StageEntity(pipelineEntity)));

    }


    @Override
    public void setParameter(BeforeEvent beforeEvent, Integer id) {
        pipelineEntity = pipelineRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Pipeline not found"));
        print();
    }

    private void listStages(){
        grid.setItems(stageRepository.findByPipeline(pipelineEntity));
    }
}
