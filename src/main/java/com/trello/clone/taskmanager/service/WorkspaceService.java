package com.trello.clone.taskmanager.service;

import com.trello.clone.taskmanager.exception.ResourceNotFoundException;
import com.trello.clone.taskmanager.model.Workspace;
import com.trello.clone.taskmanager.model.WorkspaceUser;
import com.trello.clone.taskmanager.model.WorkspaceUserId;
import com.trello.clone.taskmanager.model.WorkspaceUserRole;
import com.trello.clone.taskmanager.repository.WorkspaceRepository;
import com.trello.clone.taskmanager.repository.WorkspaceUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class WorkspaceService {

    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceUserRepository workspaceUserRepository;
    private final UserService userService;


    public WorkspaceService(
            WorkspaceRepository workspaceRepository,
            WorkspaceUserRepository workspaceUserRepository,
            UserService userService) {
        this.workspaceRepository = workspaceRepository;
        this.workspaceUserRepository = workspaceUserRepository;
        this.userService = userService;

    }

    public Page<Workspace> findAllWorkspaces(Pageable pageable) {
        return workspaceRepository.findAll(pageable);
    }

    public Workspace findWorkspaceById(Long id) {
        return workspaceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Workspace", "id", id));
    }

    public Workspace findWorkspaceByName(String name) {
        return workspaceRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Workspace", "name", name));
    }

    @Transactional
    public Workspace createWorkspace(Workspace workspace, Long creatorUserId) {
        if (workspaceRepository.existsByName(workspace.getName())) {
            throw new IllegalArgumentException("Workspace with this name already exists.");
        }

        Workspace savedWorkspace = workspaceRepository.save(workspace);

        com.trello.clone.taskmanager.model.User creator = userService.findUserById(creatorUserId);

        WorkspaceUser workspaceUser = new WorkspaceUser();
        WorkspaceUserId workspaceUserId = new WorkspaceUserId(creator.getId(), savedWorkspace.getId());
        workspaceUser.setId(workspaceUserId);

        workspaceUser.setWorkspace(savedWorkspace);
        workspaceUser.setUser(creator);
        workspaceUser.setRole(WorkspaceUserRole.CREATOR);

        workspaceUserRepository.save(workspaceUser);

        return savedWorkspace;

    }

    @Transactional
    public Workspace updateWorkspace(Long id, Workspace workspaceDetails) {
        Workspace existingWorkspace = findWorkspaceById(id);

        if (workspaceDetails.getName() != null) {
            if (!existingWorkspace.getName().equals(workspaceDetails.getName()) && workspaceRepository.existsByName(workspaceDetails.getName())) {
                throw new IllegalArgumentException("Workspace with this name already exists.");
            }
            existingWorkspace.setName(workspaceDetails.getName());
        }
        if (workspaceDetails.getVisibility() != null) {
            existingWorkspace.setVisibility(workspaceDetails.getVisibility());
        }
        if (workspaceDetails.getBackgroundImageUrl() != null) {
            existingWorkspace.setBackgroundImageUrl(workspaceDetails.getBackgroundImageUrl());
        }

        return workspaceRepository.save(existingWorkspace);
    }

    @Transactional
    public void deleteWorkspace(Long id) {
        Workspace workspaceToDelete = findWorkspaceById(id);
        workspaceRepository.delete(workspaceToDelete);
    }
}