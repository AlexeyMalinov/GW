package ru.alexeymalinov.taskautomation.orchestrator.ui.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.*;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.*;
import ru.alexeymalinov.taskautomation.orchestrator.db.repository.*;
import ru.alexeymalinov.taskautomation.orchestrator.service.runner.Impl.PipelineRunnerService;
import ru.alexeymalinov.taskautomation.orchestrator.ui.editor.PipelineEditor;

@Route
public class PipelineView extends AbstractView<StageRepository, StageEntity, PipelineEditor> implements HasUrlParameter<Integer> {

    private final RunRepository runRepository;
    private final PipelineRunnerService pipelineRunnerService;
    private final PipelineRepository pipelineRepository;
    private PipelineEntity pipelineEntity;
    private Grid<RunEntity> logGrid = new Grid<>(RunEntity.class);

    PipelineView(StageRepository stageRepository, PipelineRepository pipelineRepository, PipelineEditor editor, RunRepository runRepository, PipelineRunnerService pipelineRunnerService) {
        super(stageRepository, editor, new Grid<>(StageEntity.class));
        this.pipelineRepository = pipelineRepository;
        this.runRepository = runRepository;
        this.pipelineRunnerService = pipelineRunnerService;
    }

    @Override
    public void print() {
        listItem();

        Button addStageButton = new Button("Create Stage", VaadinIcon.PLUS.create());
        addStageButton.addClickListener(e -> editor.edit(new StageEntity(pipelineEntity)));

        Button runPipeline = new Button("Run", VaadinIcon.CARET_RIGHT.create());
        runPipeline.addClickListener(e -> {
            pipelineRunnerService.run(pipelineEntity, new RunEntity());
        });

        Button refreshButton = new Button("Refresh", VaadinIcon.REFRESH.create());
        refreshButton.addClickListener(e -> listItem());

        HorizontalLayout actions = new HorizontalLayout(runPipeline, addStageButton, backMainButton, refreshButton);

        grid.addColumns("previousStageId", "nextStageId");

        add(actions, grid, this.editor, logGrid);

    }


    @Override
    public void setParameter(BeforeEvent beforeEvent, Integer id) {
        pipelineEntity = pipelineRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Pipeline not found"));
        print();
    }

    @Override
    protected void listItem() {
        grid.setItems(repository.findByPipeline(pipelineEntity));
        logGrid.setItems(runRepository.findByPipelineId(pipelineEntity.getId()));
    }
}
