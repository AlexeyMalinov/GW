package ru.alexeymalinov.taskautomation.orchestrator.ui.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.JobEntity;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.PipelineEntity;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.StageEntity;
import ru.alexeymalinov.taskautomation.orchestrator.db.repository.JobRepository;
import ru.alexeymalinov.taskautomation.orchestrator.db.repository.PipelineRepository;
import ru.alexeymalinov.taskautomation.orchestrator.db.repository.StageRepository;
import ru.alexeymalinov.taskautomation.orchestrator.ui.editor.PipelineEditor;
import ru.alexeymalinov.taskautomation.orchestrator.ui.editor.StageEditor;

@Route
public class StageView extends VerticalLayout implements HasUrlParameter<Integer> {

    private StageRepository stageRepository;
    private JobRepository jobRepository;
    private StageEditor editor;
    private Grid<JobEntity> grid;
    private StageEntity stageEntity;

    StageView(StageRepository stageRepository, JobRepository jobRepository, StageEditor editor) {
        this.stageRepository = stageRepository;
        this.jobRepository = jobRepository;
        this.editor = editor;
        this.grid = new Grid<>(JobEntity.class);
    }

    private void print() {
        listStages();

        Button addJobButton = new Button("Create Job", VaadinIcon.PLUS.create());
        addJobButton.addClickListener(e -> editor.edit(new JobEntity(stageEntity)));

        Button backStagesButton = new Button("Stages", VaadinIcon.ARROW_BACKWARD.create());
        backStagesButton.addClickListener(e -> {
            backStagesButton.getUI().ifPresent(ui -> ui.navigate(PipelineView.class, stageEntity.getPipeline().getId()));
        });

        Button backPipelineButton = new Button("Pipelines", VaadinIcon.ARROW_BACKWARD.create());
        backPipelineButton.addClickListener(e -> backPipelineButton.getUI().ifPresent((ui -> ui.navigate(MainView.class))));

        HorizontalLayout actions = new HorizontalLayout(addJobButton, backStagesButton, backPipelineButton);
        add(actions, grid, this.editor);

        grid.removeColumnByKey("stage");
        grid.setHeight("300px");
        grid.setColumns("id", "name", "taskName", "repositoryUrl", "robotsGroupId", "startTime", "count");
        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listStages();
        });

        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.edit(e.getValue());
        });


    }


    @Override
    public void setParameter(BeforeEvent beforeEvent, Integer id) {
        stageEntity = stageRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Stage not found"));
        print();
    }

    private void listStages() {
        grid.setItems(jobRepository.findAllByStage(stageEntity));
    }


}
