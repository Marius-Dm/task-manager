package com.trello.clone.taskmanager.controller;

import com.trello.clone.taskmanager.dto.workspace.WorkspaceCreationDTO;
import com.trello.clone.taskmanager.dto.workspace.WorkspaceUpdateDTO;
import com.trello.clone.taskmanager.model.Workspace;
import com.trello.clone.taskmanager.model.WorkspaceUserId;
import com.trello.clone.taskmanager.service.UserService;
import com.trello.clone.taskmanager.service.WorkspaceService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workspaces")
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    public WorkspaceController(WorkspaceService workspaceService, UserService userService) {
        this.workspaceService = workspaceService;
    }

    @PostMapping
    public ResponseEntity<Workspace> createWorkspace(
            @Valid @RequestBody WorkspaceCreationDTO workspaceCreationDTO,
            @RequestParam Long creatorUserId
    ) {
        Workspace workspace = new Workspace();
        workspace.setName(workspaceCreationDTO.getName());
        workspace.setVisibility(workspaceCreationDTO.getVisibility());
        workspace.setBackgroundImageUrl(workspaceCreationDTO.getBackgroundImgUrl());

        Workspace createdWorkspace = workspaceService.createWorkspace(workspace, creatorUserId);


        return new ResponseEntity<>(createdWorkspace, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<Workspace>> getAllWorkspaces(Pageable pageable) {
        Page<Workspace> workspaces = workspaceService.findAllWorkspaces(pageable);
        return new ResponseEntity<>(workspaces, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Workspace> getWorkspaceById(@PathVariable Long id) {
        Workspace workspace = workspaceService.findWorkspaceById(id);
        return new ResponseEntity<>(workspace, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Workspace> updateWorkspace(
            @PathVariable Long id,
            @Valid @RequestBody WorkspaceUpdateDTO workspaceUpdateDTO
    ) {
        Workspace workspace = new Workspace();
        workspace.setName(workspaceUpdateDTO.getName());
        workspace.setVisibility(workspaceUpdateDTO.getVisibility());
        workspace.setBackgroundImageUrl(workspaceUpdateDTO.getBackgroundImgUrl());

        Workspace updatedWorkspace = workspaceService.updateWorkspace(id, workspace);
        return new ResponseEntity<>(updatedWorkspace, HttpStatus.OK);
    }

}
