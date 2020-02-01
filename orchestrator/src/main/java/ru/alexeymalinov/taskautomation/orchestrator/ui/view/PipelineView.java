package ru.alexeymalinov.taskautomation.orchestrator.ui.view;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.StageEntity;
import ru.alexeymalinov.taskautomation.orchestrator.db.repository.PipelineRepository;
import ru.alexeymalinov.taskautomation.orchestrator.db.repository.StageRepository;

@Route("pipeline")
public class PipelineView extends VerticalLayout {

    private StageRepository stageRepository;
    private PipelineRepository pipelineRepository;
    private Grid<StageEntity> grid;
    PipelineView(StageRepository stageRepository, PipelineRepository pipelineRepository){
        this.stageRepository = stageRepository;
        this.pipelineRepository = pipelineRepository;
        this.grid = new Grid<>(StageEntity.class);
        grid.setItems(stageRepository.findByPipeline(pipelineRepository.findById(1).get()));
        add(grid);
    }
}
